package com.scanner.d10r.hardware.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.util.actionShareAction
import com.scanner.d10r.hardware.util.actionShareData
import com.scanner.d10r.hardware.util.getKt
import com.scanner.d10r.hardware.util.updateKT
import com.scannerd.d10r.hardware.R

class BroadcastDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NORMAL, R.style.CustomProgressDialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_broadcast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value1 = view.findViewById<TextInputEditText>(R.id.et_value1)
        val value2 = view.findViewById<TextInputEditText>(R.id.et_value2)
        var action = getKt(ConfigEnum.BroadcastAction.name)
        if (TextUtils.isEmpty(action)) action = actionShareAction
        value1.setText(action)
        var data = getKt(ConfigEnum.BroadcastData.name)
        if (TextUtils.isEmpty(data)) data = actionShareData
        value2.setText(data)
        val ok = view.findViewById<Button>(R.id.ok)
        ok.setOnClickListener {
            updateKT(ConfigEnum.BroadcastAction.name, value1.text.toString())
            updateKT(ConfigEnum.BroadcastData.name, value2.text.toString())
            dismissDialog()
        }
        val cancel = view.findViewById<Button>(R.id.cancel)
        cancel.setOnClickListener { dismissDialog() }
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