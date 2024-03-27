package com.scanner.d10r.hardware.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.util.addPrefixEnter
import com.scanner.d10r.hardware.util.addSuffixEnter
import com.scanner.d10r.hardware.util.updateKT
import com.scannerd.d10r.hardware.R
import com.suke.widget.SwitchButton

class InputDataScanType(val data: String, val type: Int, val onChange: ((String) -> Unit) = {}) :
    DialogFragment() {
    private var etData: EditText? = null
    private var sbAddEnter: SwitchButton? = null
    private var isCheckedChange = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NORMAL, R.style.CustomProgressDialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_inputdate_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTitle = view.findViewById<TextView>(R.id.tv_dialog_title)
        etData = view.findViewById(R.id.et_data)
        sbAddEnter = view.findViewById(R.id.sb_add_enter)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        val btnSure = view.findViewById<Button>(R.id.btn_update)
        if (type == 1) { //前缀
            tvTitle.text = view.context.getString(R.string.add_pre)
            isCheckedChange = addPrefixEnter
        } else if (type == 2) { //后缀
            tvTitle.text = view.context.getString(R.string.add_back_data_title)
            isCheckedChange = addSuffixEnter
        }
        sbAddEnter?.isChecked = isCheckedChange
        etData?.setText(data)
        etData?.setSelection(data.length)
        sbAddEnter?.setOnCheckedChangeListener { _, isChecked: Boolean ->
            isCheckedChange = isChecked
        }
        btnCancel.setOnClickListener { dismissDialog() }
        btnSure.setOnClickListener {
            val dates = etData?.text.toString()
            if (type == 1) {
                updateKT(ConfigEnum.AddPrefix.name, dates)
                updateKT(ConfigEnum.AddPrefixEnter.name, "" + isCheckedChange)
            } else if (type == 2) {
                updateKT(ConfigEnum.AddSuffix.name, dates)
                updateKT(ConfigEnum.AddSuffixEnter.name, "" + isCheckedChange)
            }
            onChange(dates)
            dismissDialog()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val dialogWindow = dialog?.window
        val lp = dialogWindow?.attributes
        lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        lp?.gravity = Gravity.CENTER
        dialogWindow?.attributes = lp
    }

    private fun dismissDialog() {
        dialog?.apply { if (isShowing) dismiss() }
    }
}