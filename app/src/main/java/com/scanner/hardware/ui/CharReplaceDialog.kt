package com.scanner.hardware.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scanner.hardware.MyApplication
import com.scanner.hardware.adapter.ReplaceAdapter
import com.scanner.hardware.databinding.MainReplaceCharBinding
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.util.replaceChar

class CharReplaceDialog : DialogFragment() {
    private val adapter = ReplaceAdapter()
    private lateinit var binding: MainReplaceCharBinding

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MainReplaceCharBinding.inflate(layoutInflater)
        binding.add.setOnClickListener {
            DetailCharReplaceDialog().show(parentFragmentManager, "2")
        }
        binding.close.setOnClickListener { dismiss() }
        val layoutManager = LinearLayoutManager(context)
        binding.replaceDataRecyclerview.layoutManager = layoutManager
        binding.replaceDataRecyclerview.adapter = adapter
        adapter.setList(toList(replaceChar))
        MyApplication.dao.observerReplaceCharChange(ConfigEnum.ReplaceChar.name)
            .observe(this) { data ->
                if (!TextUtils.isEmpty(data)) adapter.setNewInstance(toList(data))
                else adapter.setNewInstance(arrayListOf())
            }
        return binding.root
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

    private fun toList(charReplace: String): MutableList<Pair<String, String>> {
        val listData = mutableListOf<Pair<String, String>>()
        if (!TextUtils.isEmpty(charReplace)) {
            val list = charReplace.split("&")
            for (item in list) {
                val spit = item.split(">")
                listData.add(Pair(spit[0], spit[1]))
            }
        }
        return listData
    }
}

