package com.andorid.scandemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.andorid.scandemo.symbologies.Honeywell2DActivity
import com.andorid.scandemo.symbologies.NewlandActivityActivity
import com.andorid.scandemo.symbologies.TotinfoActivity
import com.andorid.scandemo.utils.DataUtil
import com.andorid.scandemo.utils.logD
import com.andorid.scandemo.utils.spChange
import com.andorid.scannerlib.ConfigEnum
import com.andorid.scannerlib.Reader
import com.scanner.hardware.IRemoteCallback
import com.scanner.hardware.IRemoteService

class MainActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)
        tv.movementMethod = ScrollingMovementMethod()
        val intent = Intent()
        intent.action = "com.scanner.hardware.barcodeservice.SerialPortService"
        intent.setPackage("com.scanner.hardware")
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private var remoteService: IRemoteService? = null
    private var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            remoteService = IRemoteService.Stub.asInterface(service)
            try {
                remoteService?.asBinder()?.linkToDeath(mDeathRecipient, 0)
                remoteService?.registerCallback(callback)
                //String value = remoteService.getSettings(ConfigEnum.BaudRate.name())
                //一定要初始化以后才能调用Reader中的其他方法，也可以直接调用remoteService 的getSettings setSettings setCode方法
                Reader.init(remoteService)
                val value = Reader.getSettings(ConfigEnum.BaudRate.name)
                //设置前缀为字符串a
                Reader.setSettings(ConfigEnum.AddPrefix.name, "a")
                updateTv("onServiceConnected BaudRate $value")
            } catch (e: RemoteException) {
                updateTv("onServiceConnected RemoteException ${e.message}")
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            remoteService = null
            updateTv("onServiceDisconnected$name")
        }
    }
    private val mDeathRecipient: IBinder.DeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            remoteService!!.asBinder().unlinkToDeath(this, 0)
            remoteService = null
            updateTv("binderDied")
        }
    }
    var callback: IRemoteCallback = object : IRemoteCallback.Stub() {
        @Throws(RemoteException::class)
        //串口数据回调，是串口的原始数据
        override fun valueChanged(data: ByteArray) {
            "串口回来数据了${DataUtil.bytes2HexString(data)}".logD()
            spChange = data
            updateTv(String(data))
        }
    }

    private fun updateTv(message: String) {
        runOnUiThread {
            tv.append("\n${message}")
            val offset = tv.lineCount * tv.lineHeight - tv.height + 10
            if (offset >= 10000) tv.text = "" else tv.scrollTo(0, offset.coerceAtLeast(0))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.code0 -> startActivity(Intent(this, BroadcastActivity::class.java))
            R.id.code4 -> startActivity(Intent(this, Honeywell2DActivity::class.java))
            R.id.code5 -> startActivity(Intent(this, TotinfoActivity::class.java))
            R.id.code6 -> startActivity(Intent(this, NewlandActivityActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        if (remoteService != null && remoteService!!.asBinder().isBinderAlive) {
            try {
                remoteService!!.unregisterCallback(callback)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        unbindService(connection)
        super.onDestroy()
    }
}