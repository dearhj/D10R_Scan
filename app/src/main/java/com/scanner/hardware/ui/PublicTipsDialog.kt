package com.scanner.hardware.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.scanner.hardware.MyApplication
import com.scanner.hardware.R

class PublicTipsDialog(
    val title: String? = "",
    val sureOnClick: (() -> Unit) = {},
    val cancelOnClick: (() -> Unit) = {}
) : DialogFragment() {
    private var btnSure: Button? = null
    private var btnCancel: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_public_style)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_public_tips_layout, null)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (0.8 * MyApplication.getPhoneWidthMetrics()).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTips = view.findViewById<TextView>(R.id.tv_dialog_tips)
        tvTips.text = title
        btnSure = view.findViewById(R.id.btn_update)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnCancel?.text = getText(android.R.string.cancel)
        btnSure?.text = getString(android.R.string.ok)
        btnSure?.setOnClickListener {
            sureOnClick()
            dismissDialog()
        }
        btnCancel?.setOnClickListener {
            cancelOnClick()
            dismissDialog()
        }
    }


    private fun dismissDialog() {
        dialog?.apply { if (isShowing) dismiss() }
    }
}