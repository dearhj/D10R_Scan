package com.scanner.d10r.hardware

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.scanner.d10r.hardware.base.BaseActivity
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.setIsFirstOpen
import com.scannerd.d10r.hardware.BuildConfig
import com.scannerd.d10r.hardware.R

@SuppressLint("SetTextI18n")
class WelcomeActivity : BaseActivity() {
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val tv = findViewById<TextView>(R.id.tv_version)
        tv.text = getString(R.string.app_name) + " v" + BuildConfig.VERSION_NAME
        mHandler.postDelayed({
            if (scanModule in 1..3) {
                setIsFirstOpen(false)
                startActivity<StartActivity>()
            } else startActivity<ChooseScannerActivity>()
            finish()
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}