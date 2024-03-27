package com.scanner.d10r.hardware.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.adapter.SpinnerAdapter
import com.scanner.d10r.hardware.util.actionPrefixKeyCode
import com.scanner.d10r.hardware.util.actionPrefixToggle
import com.scanner.d10r.hardware.util.actionSuffixKeyCode
import com.scanner.d10r.hardware.util.actionSuffixToggle
import com.scanner.d10r.hardware.util.updateKT
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.FragmentActionBinding
import com.scannerd.d10r.hardware.databinding.IncludeButtonSureAndCancelBinding

@SuppressLint("SetTextI18n")
class ActionFragment(val type: Int, val onChange: ((Int) -> Unit) = {}) : DialogFragment() {
    private lateinit var binding: FragmentActionBinding
    private lateinit var okBinding: IncludeButtonSureAndCancelBinding
    private var actionKeyCode = 0
    private var buttonStatus = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setStyle(STYLE_NORMAL, R.style.CustomProgressDialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = FragmentActionBinding.inflate(layoutInflater)
        okBinding = binding.ok
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionValue = resources.getStringArray(R.array.action_value)
        val value = try {
            actionValue.map { KeyEvent.keyCodeToString(it.toInt()) }
        } catch (e: Exception) {
            emptyList<String>()
        }
        actionKeyCode = if (type == 1) actionPrefixKeyCode else actionSuffixKeyCode
        buttonStatus = if (type == 1) actionPrefixToggle else actionSuffixToggle

        context?.apply {
            binding.toggle.isChecked = buttonStatus

            binding.toggle.setOnCheckedChangeListener { _, isChecked -> buttonStatus = isChecked }
            binding.title.setText(if (type == 1) R.string.enable_prefix else R.string.enable_suffix)

            val intActionValue = actionValue.map { it.toInt() }
            if (actionKeyCode == 0) actionKeyCode = intActionValue[0]

            var isFirstClick = false
            val adapter = SpinnerAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, value
            ) { position ->
                if (isFirstClick) {
                    actionKeyCode = intActionValue[position]
                } else isFirstClick = true
                binding.actionValue.text = "${KeyEvent.keyCodeToString(actionKeyCode)}[$actionKeyCode]"
            }
            binding.spinner.adapter = adapter

            okBinding.btnUpdate.setOnClickListener {
                if (type == 1) {
                    actionPrefixKeyCode = actionKeyCode
                    actionPrefixToggle = buttonStatus
                    updateKT(ConfigEnum.ActionPrefix.name, "$actionKeyCode")
                    updateKT(ConfigEnum.ActionPrefixToggle.name, "$buttonStatus")
                } else {
                    actionSuffixKeyCode = actionKeyCode
                    actionSuffixToggle = buttonStatus
                    updateKT(ConfigEnum.ActionSuffix.name, "$actionKeyCode")
                    updateKT(ConfigEnum.ActionSuffixToggle.name, "$buttonStatus")
                }
                onChange(actionKeyCode)
                dialog?.dismiss()
            }
            okBinding.btnCancel.setOnClickListener { dialog?.dismiss() }
        }

        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode != KeyEvent.KEYCODE_BACK) {
                actionKeyCode = keyCode
                binding.actionValue.text = "${KeyEvent.keyCodeToString(keyCode)}[${keyCode}]"
            }
            true
        }
    }
}