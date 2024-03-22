package com.scanner.hardware.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.scanner.hardware.MyApplication
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.util.delayed
import com.scanner.hardware.util.firstUpdate
import com.scanner.hardware.util.isBlockVolumeKeys
import com.scanner.hardware.util.isEnable
import com.scanner.hardware.util.isOurApp
import com.scanner.hardware.util.keySwitchToTriggerPower
import com.scanner.hardware.util.scanModule
import com.scanner.hardware.util.send2Newland
import com.scanner.hardware.util.updateKT
import com.suke.widget.SwitchButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

open class BaseActivity : AppCompatActivity() {
    fun update(key: String, value: String) {
        updateKT(key, value)
        //新大陆模块当关闭扫面按钮时应该禁用扫码，以解决关闭扫码按钮后感应模式依然工作的问题。
        //这里有个坑，就是当扫码头通过send2Newland("SCNENA0")禁用扫码后，扫码头下电在上电，会自动启用扫码。
        if (key == ConfigEnum.enableScan.name && scanModule == 6) {
            if (value.toBoolean()) {
                isEnable = true
                keySwitchToTriggerPower(true)   //新大陆模块在启用扫码的时候，去上电
                send2Newland("SCNENA1")
            } else send2Newland("SCNENA0")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO 将此部分移到服务中去
        MyApplication.dao.observerConfigChange().observe(this) {
            "更新了数据库:${it.size}".logD()
            firstUpdate(it)
        }
    }

    override fun onResume() {
        super.onResume()
        isOurApp = true
    }

    override fun onPause() {
        super.onPause()
        isOurApp = false
    }

    fun isZh(): Boolean {
        return try {
            resources.configuration.locale.language.endsWith("zh")
        } catch (e: Exception) {
            false
        }
    }

    fun rebootAPP() {
        try {
            Handler(Looper.getMainLooper()).postDelayed({
                packageManager.getLaunchIntentForPackage(application.packageName)?.apply {
                    this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(this)
                }
            }, 1000)
            if(scanModule == 6) {
                MainScope().launch(Dispatchers.IO) {
                    delay(1000)
                    send2Newland("INTERF0")//设置通信接口类型RS-232
                    delayed(10) { send2Newland("ORTSET10000") }//超时10S
                    delay(50)
                    send2Newland("ALLINV1") //默认打开反相扫码。
                }
            }
        } catch (e: Exception) {
            "重启失败".logE()
            e.printStackTrace()
        }
    }

    private var toast: Toast? = null
    fun showToast(message: String) {
        runOnUiThread {
            try {
                toast?.cancel()
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
                toast?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun showProgressDialog(progressDialog: ProgressDialog, title: String, messageInfo: String) {
        progressDialog.setTitle(title)
        progressDialog.setMessage(messageInfo)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun SwitchButton.changed(change: ((Boolean) -> Unit)) =
        setOnCheckedChangeListener { view, isChecked ->
            if (view.isPressed) return@setOnCheckedChangeListener
            change(isChecked)
        }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && isBlockVolumeKeys) {
            true
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && isBlockVolumeKeys) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    inline fun <reified TARGET : Activity> Activity.startActivity(
        vararg params: Pair<String, Any>
    ) = startActivity(Intent(this, TARGET::class.java).putExtras(*params))

    open fun Intent.putExtras(vararg params: Pair<String, Any>): Intent {
        if (params.isEmpty()) return this
        params.forEach { (key, value) ->
            when (value) {
                is Int -> putExtra(key, value)
                is Byte -> putExtra(key, value)
                is Char -> putExtra(key, value)
                is Long -> putExtra(key, value)
                is Float -> putExtra(key, value)
                is Short -> putExtra(key, value)
                is Double -> putExtra(key, value)
                is Boolean -> putExtra(key, value)
                is Bundle -> putExtra(key, value)
                is String -> putExtra(key, value)
                is IntArray -> putExtra(key, value)
                is ByteArray -> putExtra(key, value)
                is CharArray -> putExtra(key, value)
                is LongArray -> putExtra(key, value)
                is FloatArray -> putExtra(key, value)
                is Parcelable -> putExtra(key, value)
                is ShortArray -> putExtra(key, value)
                is DoubleArray -> putExtra(key, value)
                is BooleanArray -> putExtra(key, value)
                is CharSequence -> putExtra(key, value)
                is Array<*> -> {
                    when {
                        value.isArrayOf<String>() -> putExtra(key, value as Array<String?>)

                        value.isArrayOf<Parcelable>() -> putExtra(key, value as Array<Parcelable?>)

                        value.isArrayOf<CharSequence>() -> putExtra(
                            key, value as Array<CharSequence?>
                        )

                        else -> putExtra(key, value)
                    }
                }

                is Serializable -> putExtra(key, value)
            }
        }
        return this
    }
}