package com.scanner.hardware.ui

import android.annotation.SuppressLint
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
import com.scanner.hardware.R
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.util.characterMode
import com.scanner.hardware.util.deletePrefix
import com.scanner.hardware.util.deletePrefixChar
import com.scanner.hardware.util.deleteSuffix
import com.scanner.hardware.util.deleteSuffixChar
import com.scanner.hardware.util.positionMode
import com.scanner.hardware.util.updateKT
import com.suke.widget.SwitchButton
import java.util.Objects

class DeleteCharDialogFragment : DialogFragment() {
    private var initStatePositionMode = false
    private var initStateCharacterMode = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NORMAL, R.style.CustomProgressDialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_delete_char_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatePositionMode = positionMode
        initStateCharacterMode = characterMode
        val numStart = view.findViewById<TextInputEditText>(R.id.numStart_edit)
        numStart.setText("$deletePrefix")
        val numStop = view.findViewById<TextInputEditText>(R.id.numStop_edit)
        numStop.setText("$deleteSuffix")
        val charStart = view.findViewById<TextInputEditText>(R.id.charStart_edit)
        charStart.setText(deletePrefixChar)
        val charStop = view.findViewById<TextInputEditText>(R.id.charStop_edit)
        charStop.setText(deleteSuffixChar)
        val positionButton = view.findViewById<SwitchButton>(R.id.si_num)
        positionButton.isChecked = initStatePositionMode
        val characterButton = view.findViewById<SwitchButton>(R.id.si_char)
        characterButton.isChecked = initStateCharacterMode
        positionButton.setOnCheckedChangeListener { _, isChecked: Boolean ->
            if (isChecked && initStateCharacterMode) {
                characterButton.isChecked = false
                initStateCharacterMode = false
            }
            initStatePositionMode = isChecked
        }
        characterButton.setOnCheckedChangeListener { _, isChecked: Boolean ->
            if (isChecked && initStatePositionMode) {
                positionButton.isChecked = false
                initStatePositionMode = false
            }
            initStateCharacterMode = isChecked
        }
        val ok = view.findViewById<Button>(R.id.sure)
        ok.setOnClickListener {
            if (initStatePositionMode) {
                if (TextUtils.isEmpty(numStart.text)) {
                    numStart.requestFocus()
                    numStart.error = getString(R.string.enter_start)
                } else if (TextUtils.isEmpty(numStop.text)) {
                    numStop.requestFocus()
                    numStop.error = getString(R.string.enter_stop)
                } else {
                    if (numStart.text.toString().toInt() == 0) {
                        numStart.requestFocus()
                        numStart.error = getString(R.string.not_zero)
                    } else if (Objects.requireNonNull(numStop.text).toString().toInt() == 0) {
                        numStop.requestFocus()
                        numStop.error = getString(R.string.not_zero)
                    } else {
                        if (numStart.text.toString().toInt() > numStop.text.toString().toInt()) {
                            numStop.requestFocus()
                            numStop.error = getString(R.string.must_more_than_start)
                        } else {
                            updateKT(ConfigEnum.DeletePrefix.name, numStart.text.toString())
                            updateKT(ConfigEnum.DeleteSuffix.name, numStop.text.toString())
                            updateKT(ConfigEnum.PositionMode.name, initStatePositionMode.toString())
                            updateKT(
                                ConfigEnum.CharacterMode.name,
                                initStateCharacterMode.toString()
                            )
                            dismissDialog()
                        }
                    }
                }
            } else if (initStateCharacterMode) {
                if (TextUtils.isEmpty(charStart.text)) {
                    charStart.requestFocus()
                    charStart.error = getString(R.string.enter_char_start)
                } else if (TextUtils.isEmpty(charStop.text)) {
                    charStop.requestFocus()
                    charStop.error = getString(R.string.enter_char_stop)
                } else {
                    updateKT(ConfigEnum.DeletePrefixChar.name, charStart.text.toString())
                    updateKT(ConfigEnum.DeleteSuffixChar.name, charStop.text.toString())
                    updateKT(ConfigEnum.PositionMode.name, initStatePositionMode.toString())
                    updateKT(ConfigEnum.CharacterMode.name, initStateCharacterMode.toString())
                    dismissDialog()
                }
            } else {
                updateKT(ConfigEnum.PositionMode.name, initStatePositionMode.toString())
                updateKT(ConfigEnum.CharacterMode.name, initStateCharacterMode.toString())
                dismissDialog()
            }
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