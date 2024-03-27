package com.scanner.d10r.hardware

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import com.android.otalibrary.isLicense
import com.android.otalibrary.showLicense
import com.github.h4de5ing.baseui.selected
import com.scanner.d10r.hardware.base.BaseBackActivity
import com.scanner.d10r.hardware.bean.Constants.hr22p
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.util.filterModuleIndex
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.setIsFirstOpen
import com.scanner.d10r.hardware.util.updateKT
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.ActivityChooseScannerBinding

class ChooseScannerActivity : BaseBackActivity() {
    private lateinit var list: List<Pair<Int, String>>
    private var mPosition = 0
    private lateinit var binding: ActivityChooseScannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = listOf(
            Pair(hr22p, getString(R.string.hr22p)),
            Pair(hr22p, getString(R.string.hr22p))
        )
        binding.btnSure.setOnClickListener { showDialog() }
        mPosition = filterModuleIndex(scanModule)
        binding.devices.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        binding.devices.selected {
            mPosition = it
            scanModule = it + 1
        }
        binding.devices.setSelection(mPosition)
        if (!isLicense()) {
            showLicense(this)
            binding.root1.removeAllViews()
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setIcon(R.mipmap.icon_tm)
        dialog.setTitle(getString(R.string.sure_modlue))
        dialog.setMessage(getString(R.string.sure_module_contents))
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.sure)) { dialog1, _ ->
            setIsFirstOpen(false)
            showToast(getString(R.string.set_module))
            dialog1.dismiss()
            startActivity<StartActivity>()
            finish()
        }
        dialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.str_cancel)
        ) { dialog12, _ -> dialog12.dismiss() }
        dialog.show()
    }
}