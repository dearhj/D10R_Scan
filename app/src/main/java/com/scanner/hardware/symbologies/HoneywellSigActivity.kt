package com.scanner.hardware.symbologies

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.h4de5ing.baseui.logD
import com.scanner.hardware.MyApplication
import com.scanner.hardware.R
import com.scanner.hardware.adapter.HoneywellSigAdapter
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.databinding.ActivityRecyclerviewBinding
import com.scanner.hardware.db.SymbologiesItem
import com.scanner.hardware.util.sendString2SP

//霍尼韦尔一维码
class HoneywellSigActivity : BaseBackActivity() {
    private var list = mutableListOf<SymbologiesItem>()
    private val adapter = HoneywellSigAdapter()
    private lateinit var binding: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = MyApplication.dao.selectSymbologiesBars()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, view, position ->
            val checkBox = view.findViewById<AppCompatCheckBox>(R.id.cb_choose)
            val isChecked = checkBox.isChecked
            checkBox.isChecked = !isChecked
            val barcode = list[position]
            barcode.state = !isChecked
            MyApplication.dao.updateSymbologies(barcode)
            var command = barcode.subCommand
            if (!barcode.state) command = command.substring(0, command.length - 2) + "0."
            "command = $command".logD()
            sendString2SP(command)
            showToast("${if (barcode.state) "open" else "close"} ${barcode.name}")
        }
    }
}