package com.scanner.hardware

import android.os.Bundle
import com.android.otalibrary.checkSelf
import com.android.otalibrary.hideProgressDialog
import com.android.otalibrary.showProgressDialog
import com.github.h4de5ing.base.date
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.databinding.AboutBinding
import com.scanner.hardware.util.isDebug
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
            "v%s-%s(%s)\n%s",
            BuildConfig.VERSION_NAME,
            BuildConfig.FLAVOR.substring(0, 1),
            BuildConfig.GIT_SHA,
            BuildConfig.BUILD_TIME.date()
        )
        binding.tvAppUpdateCode.text = getString(R.string.app_update_codes)
        if (isDebug()) binding.tvAppVersion.setOnClickListener { onClick() }
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

    private fun onClick() {
        count++
        if (count > 7) {
            startActivity<SPTestActivity>()
            count = 0
        }
    }
}