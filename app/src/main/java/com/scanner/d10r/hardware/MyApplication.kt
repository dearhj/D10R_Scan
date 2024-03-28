package com.scanner.d10r.hardware

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.github.h4de5ing.baseui.initLog
import com.scanner.d10r.hardware.bean.Constants
import com.scanner.d10r.hardware.db.BarCodeDao
import com.scanner.d10r.hardware.db.BarCodeDataBase
import com.scanner.d10r.hardware.db.BarCodeDataBase.Companion.create
import com.scanner.d10r.hardware.util.firstUpdate
import com.scanner.d10r.hardware.util.isDebug
import com.scanner.d10r.hardware.util.parserXML
import com.scanner.d10r.hardware.util.setIsFirstOpen
import java.io.File

class MyApplication : MultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmField
        var application: MyApplication? = null
        lateinit var database: BarCodeDataBase
        lateinit var dao: BarCodeDao
        lateinit var sp: SharedPreferences
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        initLog(isDebug(), "gh0st")
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        application = this
        mContext = this
        database = create(this)
        dao = database.barcodeDao()
        val isFirst = sp.getBoolean("isFirst", true)
        if (isFirst) {
            setIsFirstOpen(false)
            parserXML(File(Constants.configPath))
        }
        firstUpdate(dao.selectAllConfig())
    }

}