package com.scanner.hardware

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
import com.scanner.hardware.barcodeservice.Gh0stService
import com.scanner.hardware.barcodeservice.SerialPortService
import com.scanner.hardware.base.BaseActivity
import com.scanner.hardware.bean.Constants
import com.scanner.hardware.databinding.ActivityStartBinding
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.symbologies.HoneywellQrActivity
import com.scanner.hardware.symbologies.HoneywellSigActivity
import com.scanner.hardware.symbologies.MotoQrActivity2707
import com.scanner.hardware.symbologies.MotoSigActivity
import com.scanner.hardware.symbologies.NewlandActivity
import com.scanner.hardware.symbologies.TotinfoActivity
import com.scanner.hardware.util.SC_PREFIX
import com.scanner.hardware.util.SC_SUFFIX
import com.scanner.hardware.util.importConfig
import com.scanner.hardware.util.isAutoCleanEditText
import com.scanner.hardware.util.isDebug
import com.scanner.hardware.util.isEnable
import com.scanner.hardware.util.isEnterChoose
import com.scanner.hardware.util.keySwitchToTriggerPower
import com.scanner.hardware.util.scanModule
import com.scanner.hardware.util.setOnChange
import com.scanner.hardware.util.setSPChange
import com.scanner.hardware.util.tabChoose

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
        setSPChange { isImportConfig(it) }
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
        keySwitchToTriggerPower(true)
        SerialPortService.isContinuous = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity<SettingActivity>()
            R.id.symbologies -> {
                when (scanModule) {
                    Constants.DTHONEYWELLONE -> startActivity<HoneywellSigActivity>()
                    Constants.DTHONEYWELL -> startActivity<HoneywellQrActivity>()
                    Constants.DTMoToSE655 -> startActivity<MotoSigActivity>()
                    Constants.DTMoTOSE2707 -> startActivity<MotoQrActivity2707>()
                    Constants.DTTotinfo -> startActivity<TotinfoActivity>()
                    Constants.DTNewland -> startActivity<NewlandActivity>()
                    else -> showToast("未知模块 $scanModule")
                }
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
        if (isDebug()) binding.time.text = "$scanCount"
    }


    private fun initViews() {
        binding.show.movementMethod = ScrollingMovementMethod()
        binding.enable.isChecked = isEnable
        binding.enable.setOnCheckedChangeListener { _, isChecked ->
            update(ConfigEnum.enableScan.name, "$isChecked")
        }
        binding.buttonClear.setOnClickListener {
            if (isDebug()) {
                scanCount = 0
                binding.time.text = "$scanCount"
            }
            binding.show.text = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isEnable) {
            serviceIntent?.apply {
                stopService(serviceIntent)
                keySwitchToTriggerPower(false)
            }
        }
    }

    private fun isImportConfig(data: String) {
        runOnUiThread {
            if (isShow && data.contains(SC_PREFIX) && data.contains(SC_SUFFIX)) {
                importConfig(context = this,
                    data = data,
                    success = { showToast(getString(R.string.import_success)) },
                    error = { showToast(getString(R.string.import_failed)) })
            }
        }
    }

    private var lastTime = 0L

    private fun updateTv(message: String) {
        runOnUiThread {
            scanCount++
            if (isAutoCleanEditText) binding.show.text = ""
            var timeStr = ""
            if (isDebug()) {
                binding.time.text = "$scanCount"
                timeStr =
                    "[" + scanCount + "," + message.length + "," + (System.currentTimeMillis() - lastTime) + "]"
                lastTime = System.currentTimeMillis()
            }
            var data = message
            if ("ahead".equals(tabChoose, ignoreCase = true)) data = "\t$data"
            if ("behind".equals(tabChoose, ignoreCase = true)) data = "$data\t"
            if (isEnterChoose) data = "${data}\n"
            binding.show.append("$timeStr $data\n")
            if (scanCount >= 100000) {
                binding.show.text = ""
                binding.time.text = ""
            }
            binding.scrollView.post { binding.scrollView.fullScroll(View.FOCUS_DOWN) }
        }
    }
}