package com.scanner.hardware

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import com.android.otalibrary.isLicense
import com.android.otalibrary.showLicense
import com.github.h4de5ing.baseui.selected
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.bean.Constants
import com.scanner.hardware.databinding.ActivityChooseScannerBinding
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.util.filterModuleIndex
import com.scanner.hardware.util.scanModule
import com.scanner.hardware.util.setIsFirstOpen
import com.scanner.hardware.util.updateKT

class ChooseScannerActivity : BaseBackActivity() {
    private lateinit var list: List<Triple<Int, String, Int>>
    private var mPosition = 0
    private lateinit var binding: ActivityChooseScannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = listOf(
            Triple(Constants.DTMoToSE655, getString(R.string.motoloraone), 9600),
            Triple(Constants.DTMoTOSE2707, getString(R.string.motorolatwo), 9600),
            Triple(Constants.DTHONEYWELLONE, getString(R.string.honeywellone), 9600),
            Triple(Constants.DTHONEYWELL, getString(R.string.honeywelltwo), 115200),
            Triple(Constants.DTTotinfo, getString(R.string.totinfo_scanner), 115200),
            Triple(Constants.DTNewland, getString(R.string.newland_scanner), 9600)
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
            updateKT(ConfigEnum.ScanModule.name, "${list[mPosition].first}")
            updateKT(ConfigEnum.BaudRate.name, "${list[mPosition].third}")
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