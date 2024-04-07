package com.scanner.d10r.hardware

import android.os.Bundle
import com.android.otalibrary.checkSelf
import com.android.otalibrary.hideProgressDialog
import com.android.otalibrary.showProgressDialog
import com.github.h4de5ing.base.date
import com.scanner.d10r.hardware.base.BaseBackActivity
import com.scanner.d10r.hardware.util.isDebug
import com.scannerd.d10r.hardware.BuildConfig
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.AboutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AboutActivity : BaseBackActivity() {
    private var count = 0
    private val scope = MainScope()
    private lateinit var binding: AboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvAppVersion.text = String.format(
            "v%s(%s)\n%s",
            BuildConfig.VERSION_NAME,
            BuildConfig.GIT_SHA,
            BuildConfig.BUILD_TIME.date()
        )
        binding.tvAppUpdateCode.text = getString(R.string.app_update_codes)
        binding.rlCheck.setOnClickListener {
            binding.rlCheck.isEnabled = false
            runOnUiThread { showProgressDialog(this) }
            scope.launch(Dispatchers.IO) {
                val job = async(Dispatchers.Main) {
                    delay(20000)
                    binding.rlCheck.isEnabled = true
                    hideProgressDialog()
                    showToast(getString(R.string.net_error))
                }
                checkSelf({
                    runOnUiThread {
                        if (it <= BuildConfig.VERSION_CODE) {
                            showToast(getString(R.string.latest_version))
                        }
                        binding.rlCheck.isEnabled = true
                        hideProgressDialog()
                        job.cancel()
                    }
                }, {
                    runOnUiThread {
                        showToast(getString(R.string.net_error))
                        binding.rlCheck.isEnabled = true
                        hideProgressDialog()
                        job.cancel()
                    }
                }, false)
            }
        }
    }
}