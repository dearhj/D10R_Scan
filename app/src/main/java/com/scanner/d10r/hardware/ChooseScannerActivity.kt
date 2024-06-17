package com.scanner.d10r.hardware

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import com.android.otalibrary.isLicense
import com.android.otalibrary.showLicense
import com.github.h4de5ing.baseui.selected
import com.scanner.d10r.hardware.base.BaseBackActivity
import com.scanner.d10r.hardware.bean.Constants.em3100
import com.scanner.d10r.hardware.bean.Constants.hr22p
import com.scanner.d10r.hardware.bean.Constants.m1
import com.scanner.d10r.hardware.bean.Constants.me11
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.util.filterModuleIndex
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.setIsFirstOpen
import com.scanner.d10r.hardware.util.spChange
import com.scanner.d10r.hardware.util.updateKT
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.ActivityChooseScannerBinding

class ChooseScannerActivity : BaseBackActivity() {
    private lateinit var list: List<Pair<Int, String>>
    private var mPosition = 0
    private lateinit var binding: ActivityChooseScannerBinding
    private var chooseItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseItem = scanModule
        binding = ActivityChooseScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = listOf(
            Pair(hr22p, getString(R.string.hr22p)),
            Pair(em3100, getString(R.string.em3100)),
            Pair(me11, getString(R.string.me11)),
            Pair(m1, getString(R.string.m1))
        )
        binding.btnSure.setOnClickListener { showDialog() }
        mPosition = filterModuleIndex(chooseItem)
        binding.devices.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        binding.devices.selected {
            mPosition = it
            chooseItem = it + 1
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
            updateKT(ConfigEnum.ScanModule.name, "$chooseItem")
            spChange = "$chooseItem"
            setIsFirstOpen(false)
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