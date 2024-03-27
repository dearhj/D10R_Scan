package com.scanner.d10r.hardware

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.otalibrary.isLicense
import com.android.otalibrary.showLicense
import com.scanner.d10r.hardware.barcodeservice.Gh0stService
import com.scanner.d10r.hardware.base.BaseActivity
import com.scanner.d10r.hardware.bean.Constants.hr22p
import com.scanner.d10r.hardware.symbology.SymbologyHrNewLandActivity
import com.scanner.d10r.hardware.util.checkUsbDevice
import com.scanner.d10r.hardware.util.isAutoCleanEditText
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.setOnChange
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.ActivityStartBinding

@SuppressLint("SetTextI18n")
class StartActivity : BaseActivity() {
    private var serviceIntent: Intent? = null
    private var isShow = false
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setOnChange { updateTv(it) }
        serviceIntent = Intent(this, Gh0stService::class.java)
        startForegroundService(serviceIntent)
        if (MyApplication.dao.selectData() >= 5000000) showToast(getString(R.string.clear_data))
        if (!isLicense()) {
            showLicense(this)
            binding.root1.removeAllViews()
        }
        try {
            val userManager = getSystemService(Context.USER_SERVICE) as UserManager
            title = "${getString(R.string.app_name)} - ${userManager.userName}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onPause() {
        super.onPause()
        isShow = false
    }

    private var scanCount: Long = 0
    override fun onResume() {
        super.onResume()
        isShow = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                if (checkUsbDevice(6690, 7851)) startActivity<SettingActivity>()
                else showToast("设备未接入，请检查扫码设备连接状况后，再次尝试。")
            }
            R.id.symbologies -> {
                if (checkUsbDevice(6690, 7851)) {
                    when (scanModule) {
                        hr22p -> startActivity<SymbologyHrNewLandActivity>()
                    }
                } else showToast("设备未接入，请检查扫码设备连接状况后，再次尝试。")
            }
            R.id.about -> startActivity<AboutActivity>()
            R.id.module_setting -> startActivity<ChooseScannerActivity>()
            R.id.data -> startActivity<DataActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    private var rotationData: String? = ""
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        rotationData = binding.show.text.toString()
        outState.putString("rotation", rotationData)
        outState.putLong("count", scanCount)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rotationData = savedInstanceState.getString("rotation")
        scanCount = savedInstanceState.getLong("count")
        binding.show.text = rotationData
        binding.textCount.text = scanCount.toString()
    }


    private fun initViews() {
        binding.show.movementMethod = ScrollingMovementMethod()
        binding.buttonClear.setOnClickListener {
            binding.textCount.text = ""
            binding.show.text = ""
            scanCount = 0
        }
    }

    private fun updateTv(message: String) {
        runOnUiThread {
            scanCount++
            if (isAutoCleanEditText) binding.show.text = ""
            val data = message
            binding.textCount.text = scanCount.toString()
            binding.show.append("$data\n")
            if (scanCount >= 100000) {
                binding.show.text = ""
            }
            binding.scrollView.post { binding.scrollView.fullScroll(View.FOCUS_DOWN) }
        }
    }
}