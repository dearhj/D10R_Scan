package com.scanner.hardware.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.scanner.hardware.R
import com.scanner.hardware.databinding.DetailReplaceBinding
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.util.replaceChar
import com.scanner.hardware.util.updateKT

class DetailCharReplaceDialog : DialogFragment() {

    private var sourceString = ""
    private var replaceString = ""
    private lateinit var binding: DetailReplaceBinding

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DetailReplaceBinding.inflate(layoutInflater)
        binding.detailReplaceSure.setOnClickListener {
            if (binding.sourceCharEdit.text.toString() == "") {
                binding.sourceCharEdit.requestFocus()
                binding.sourceCharEdit.error = getString(R.string.s_character_null)
            } else {
                sourceString = if (binding.sourceCharEdit.text.toString()
                        .first() == '0' && binding.sourceCharEdit.text.toString().length == 2
                ) binding.sourceCharEdit.text.toString().substring(1)
                else binding.sourceCharEdit.text.toString()
                if (binding.aimCharEdit.text.toString() == "") {
                    binding.aimCharEdit.requestFocus()
                    binding.aimCharEdit.error = getString(R.string.a_character_null)
                } else {
                    replaceString = if (binding.aimCharEdit.text.toString()
                            .first() == '0' && binding.aimCharEdit.text.toString().length == 2
                    ) binding.aimCharEdit.text.toString().substring(1)
                    else binding.aimCharEdit.text.toString()
                    if (!checkValue(sourceString)) {
                        binding.sourceCharEdit.requestFocus()
                        binding.sourceCharEdit.error = getString(R.string.char_exists)
                    } else {
                        if (sourceString == replaceString) {
                            binding.aimCharEdit.requestFocus()
                            binding.aimCharEdit.error = getString(R.string.two_char_is_same)
                        } else {
                            addToList(sourceString, replaceString)
                            dismiss()
                        }
                    }
                }
            }
        }
        binding.detailReplaceCancel.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val dialogWindow = dialog?.window
        val lp = dialogWindow?.attributes
        lp?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        lp?.gravity = Gravity.CENTER
        dialogWindow?.attributes = lp
    }

    private fun checkValue(sourceChar: String): Boolean {
        if (!TextUtils.isEmpty(sourceChar)) {
            val list = replaceChar.split("&")
            for (item in list) {
                val spit = item.split(">")
                if (spit[0] == sourceChar) return false
            }
        }
        return true
    }

    private fun addToList(sourceChar: String, aimChar: String) {
        val charReplace = if (replaceChar != "") "$replaceChar&$sourceChar>$aimChar"
        else "$sourceChar>$aimChar"
        updateKT(ConfigEnum.ReplaceChar.name, charReplace)
    }
}