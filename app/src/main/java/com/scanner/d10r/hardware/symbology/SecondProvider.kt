package com.scanner.d10r.hardware.symbology

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.h4de5ing.baseui.change
import com.scanner.d10r.hardware.barcodeservice.UsbScanService.Companion.ds
import com.scannerd.d10r.hardware.R

class SecondProvider(
    override val itemViewType: Int = 2, override val layoutId: Int = R.layout.item_node_second
) : BaseNodeProvider() {
    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        val secondNode = item as SecondNode
        helper.setText(R.id.title, secondNode.getName())
    }

    private val subAdapter = SymbologySubAdapter()
    private val tripleList = mutableListOf<Pair<String, Boolean>>()
    private val newTripleList = mutableListOf<Pair<String, Boolean>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        val entity = data as SecondNode
        val defaultCom = entity.getDefault()
        val length = entity.getLength()
        val addCom = entity.getAddValue()
        if (defaultCom != "") {
            confirmDefaultDialog(defaultCom)
        } else if (length != null) {
            val result = ds.getConfig(length.query)
            var hadLength = 1
            if (result != null) hadLength = result.substring(6).toInt()
            showSetLengthDialog(length, hadLength)
        } else if (addCom != "") {
            val result = ds.getConfig(entity.getQuery())
            if (result != null) showInputDialog(entity.getName(), result, addCom!!)
        } else {
            val resultQuery = ds.getConfig(entity.getQuery())
            if (resultQuery != null) showDialog(entity, resultQuery)
        }
    }

    private fun confirmDefaultDialog(com: String) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setIcon(R.mipmap.ic_launcher)
        alertBuilder.setTitle(context.getString(R.string.resetSymbology))
        alertBuilder.setPositiveButton(android.R.string.ok) { _, _ ->
            if (!ds.setConfig("@$com")) {
                println("设置失败！！")
            }
        }
        alertBuilder.setNegativeButton(android.R.string.cancel, null)
        alertBuilder.create().show()
    }

    private fun showDialog(entity: SecondNode, hadSet: String) {
        try {
            var currentIndex = -1
            entity.getUiValue()
                ?.forEachIndexed { index, value -> if (hadSet == value) currentIndex = index }
            val alertBuilder = AlertDialog.Builder(context)
            alertBuilder.setIcon(R.mipmap.ic_launcher)
            alertBuilder.setTitle(entity.getName())
            val v = View.inflate(context, R.layout.activity_recyclerview, null)
            val vList = v.findViewById<RecyclerView>(R.id.recyclerView)
            vList.layoutManager = LinearLayoutManager(context)
            vList.adapter = subAdapter
            tripleList.clear()
            entity.getUiList()?.forEachIndexed { index, it ->
                tripleList.add(Pair(it, index == currentIndex))
            }
            subAdapter.setNewInstance(tripleList.toMutableList())
            subAdapter.setOnItemClickListener { _, _, pos ->
                entity.getUiValue()?.get(pos)?.apply {
                    val result = ds.setConfig("@$this")
                    if (!result) {
                        println("设置失败！！")
                    } else {
                        currentIndex = pos
                        newTripleList.clear()
                        tripleList.forEachIndexed { index, triple ->
                            newTripleList.add(Pair(triple.first, currentIndex == index))
                        }
                        subAdapter.setNewInstance(newTripleList.toMutableList())
                    }
                }
            }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showSetLengthDialog(length: CodeLength, result: Int) {
        try {
            var setValue = result
            val alertBuilder = AlertDialog.Builder(context)
            alertBuilder.setIcon(R.mipmap.ic_launcher)
            alertBuilder.setTitle(length.name)
            val v = View.inflate(context, R.layout.dialog_slider, null)
            val currentValueTv = v.findViewById<TextView>(R.id.current_value)
            currentValueTv.text = "$result"
            val seekBar = v.findViewById<SeekBar>(R.id.seek_bar)
            seekBar.min = length.min
            seekBar.max = length.max
            seekBar.progress = result
            seekBar.change { progress ->
                val tempValue = (progress / length.step)
                setValue = tempValue * length.step
                currentValueTv?.text = "$setValue"
                seekBar?.progress = progress
            }
            val minValue = v.findViewById<TextView>(R.id.min_value)
            val maxValue = v.findViewById<TextView>(R.id.max_value)
            minValue.text = "${length.min}"
            maxValue.text = "${length.max}"
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(android.R.string.cancel, null)
            alertBuilder.setPositiveButton(android.R.string.ok) { _, _ ->
                if (!ds.setConfig("@${length.setCom}$setValue")) {
                    println("设置失败！！")
                }
            }
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showInputDialog(title: String, result: String, addCom: String) {
        try {
            var resultCode = ""
            if (result.length > 6) resultCode = result.substring(6)
            val alertBuilder = AlertDialog.Builder(context)
            alertBuilder.setIcon(R.mipmap.ic_launcher)
            val v = View.inflate(context, R.layout.item_input, null)
            val vEdit = v.findViewById<EditText>(R.id.input)
            vEdit.setText(resultCode)
            v.findViewById<TextView>(R.id.label).text = "$title (Hx)"
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(android.R.string.cancel, null)
            alertBuilder.setPositiveButton(android.R.string.ok) { _, _ ->
                if (resultCode != vEdit.text.toString()) {
                    if (!ds.setConfig("$addCom${vEdit.text}")) {
                        println("设置失败！！")
                    }
                }
            }
            val dialog = alertBuilder.create()
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}