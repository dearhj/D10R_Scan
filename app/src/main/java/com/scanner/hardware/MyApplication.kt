package com.scanner.hardware

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.multidex.MultiDexApplication
import com.github.h4de5ing.baseui.initLog
import com.scanner.hardware.bean.Constants
import com.scanner.hardware.db.BarCodeDao
import com.scanner.hardware.db.BarCodeDataBase
import com.scanner.hardware.db.BarCodeDataBase.Companion.create
import com.scanner.hardware.db.MotoItem
import com.scanner.hardware.db.SymbologiesItem
import com.scanner.hardware.enums.BarCodeEnum
import com.scanner.hardware.util.firstUpdate
import com.scanner.hardware.util.isDebug
import com.scanner.hardware.util.parserXML
import com.scanner.hardware.util.setIsFirstOpen
import java.io.File

class MyApplication : MultiDexApplication() {
    companion object {
        @JvmField
        var application: MyApplication? = null
        var SZ_COMPONENT = ""
        lateinit var database: BarCodeDataBase
        lateinit var dao: BarCodeDao
        lateinit var sp: SharedPreferences
        fun getPhoneWidthMetrics(): Int {
            val dm = DisplayMetrics()
            val manager = (application?.getSystemService(WINDOW_SERVICE) as WindowManager)
            manager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }
    }

    override fun onCreate() {
        super.onCreate()
        initLog(isDebug(), "gh0st")
        SZ_COMPONENT =
            "${BuildConfig.APPLICATION_ID}/com.scanner.hardware.barcodeservice.ScanKeyService"
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        application = this
        database = create(this)
        dao = database.barcodeDao()
        val isFirst = sp.getBoolean("isFirst", true)
        if (isFirst) {
            setIsFirstOpen(false)
            parserXML(File(Constants.configPath))
        }
        firstUpdate(dao.selectAllConfig())
        initSigData()
        allMotoSetting()
        enableAccessibilityService()
//        Settings.Secure.putString(
//            contentResolver,
//            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
//            "${BuildConfig.APPLICATION_ID}/com.scanner.hardware.barcodeservice.ScanKeyService"
//        )
//        Settings.Secure.putInt(
//            contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED, 1
//        )
    }

    //启动辅助服务
    private fun enableAccessibilityService() {
        var enabledAccessibilityServices = Settings.Secure.getString(
            application?.contentResolver,
            "enabled_accessibility_services"
        )
        if (enabledAccessibilityServices == null || enabledAccessibilityServices == "")
            enabledAccessibilityServices = SZ_COMPONENT
        if (!enabledAccessibilityServices.contains(SZ_COMPONENT))
            enabledAccessibilityServices = "$enabledAccessibilityServices:$SZ_COMPONENT"
        Settings.Secure.putString(
            application?.contentResolver,
            "enabled_accessibility_services",
            enabledAccessibilityServices
        )
        Settings.Secure.putInt(
            application?.contentResolver, "accessibility_enabled", 1
        )
    }

    //霍尼韦尔一维码设置
    private fun initSigData() {
        val symbologyItems: List<SymbologiesItem> = dao.selectSymbologiesBars()
        if (symbologyItems.isEmpty()) {
            for (i in Constants.symbolsSig.indices) {
                val isOpen = Constants.commandsSig_default[i].endsWith("1.")
                dao.insertSymbologies(
                    SymbologiesItem(
                        0,
                        BarCodeEnum.SymbologiesBar.ordinal,
                        Constants.symbolsSig[i],
                        Constants.commandsSig_default[i],
                        isOpen
                    )
                )
            }
        }
    }

    //摩托罗拉一维码设置
    fun allMotoSetting() {
        val motoBars: List<MotoItem> = dao.selectMotoBars()
        if (motoBars.isEmpty()) {
            for (i in Constants.motoSigName.indices) dao.insertMoto(
                MotoItem(
                    0,
                    BarCodeEnum.MOTOBar.ordinal,
                    Constants.motoSigName[i],
                    Constants.motoSigValueOpen[i],
                    Constants.motoSigValueClose[i],
                    Constants.motoSig_default[i] == 1
                )
            )
        }
    }
}