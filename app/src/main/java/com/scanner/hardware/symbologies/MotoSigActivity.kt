package com.scanner.hardware.symbologies

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.h4de5ing.baseui.logD
import com.scanner.hardware.MyApplication
import com.scanner.hardware.R
import com.scanner.hardware.adapter.MotoAdapter
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.databinding.ActivityRecyclerviewBinding
import com.scanner.hardware.db.MotoItem
import com.scanner.hardware.util.sendByteArray2SP0x00

class MotoSigActivity : BaseBackActivity() {
    private val adapter = MotoAdapter()
    private val list = mutableListOf<MotoItem>()
    private lateinit var binding: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, view, position ->
            val checkBox = view.findViewById<AppCompatCheckBox>(R.id.cb_choose)
            val isChecked = checkBox.isChecked
            checkBox.isChecked = !isChecked
            val barcode = list[position]
            barcode.state = !isChecked
            MyApplication.dao.updateMoto(barcode)
            "${list[position]} ${barcode.state}".logD()
            if (barcode.state) sendByteArray2SP0x00(barcode.closeValue) else sendByteArray2SP0x00(
                barcode.openValue
            )
        }
        getEntity()
    }

    private fun getEntity() {
        list.clear()
        list.addAll(MyApplication.dao.selectMotoBars())
        adapter.notifyDataSetChanged()
    }
}