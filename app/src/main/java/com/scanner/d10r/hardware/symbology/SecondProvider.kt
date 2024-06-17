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
import com.scanner.d10r.hardware.MyApplication
import com.scanner.d10r.hardware.barcodeservice.UsbScanService.Companion.ds
import com.scanner.d10r.hardware.db.ME11SymbologyData
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.update2SymbologyData
import com.scannerd.d10r.hardware.R
import com.tsingtengms.scanmodule.libhidpos.HIDManager
import com.tsingtengms.scanmodule.libhidpos.util.HexUtil

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

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        val entity = data as SecondNode
        val length = entity.getLength()
        val addCom = entity.getAddValue()
        if (scanModule == 1 || scanModule == 2) {
            val defaultCom = entity.getDefault()
            if (defaultCom != "") {
                confirmDefaultDialog(defaultCom)
            } else if (length != null) {
                val result = ds.getConfig(length.query)
                var hadLength = 1
                if (result != null) hadLength = result.substring(6).toInt()
                showSetLengthDialog(length, hadLength, 0)
            } else if (addCom != "") {
                val result = ds.getConfig(entity.getQuery())
                if (result != null) showInputDialog(entity.getName(), result, addCom!!)
            } else {
                val resultQuery = ds.getConfig(entity.getQuery())
                if (resultQuery != null) showDialog(entity, resultQuery, 0)
            }
        } else {
            if (length == null) {
                val symbologyName = entity.getQuery()
                val symbologyItemInfo = entity.getName()
                val symItem = MyApplication.dao.selectSymbologyDataFromSymbologyData(
                    symbologyName,
                    symbologyItemInfo
                )
                    .firstOrNull { it.symbologyData == symbologyName && it.symbologyItemInfo == symbologyItemInfo }
                if (symItem == null) showDialog(entity, "", 0)
                else showDialog(entity, symItem.symbologyItemValue, symItem.id)
            } else {
                val lengthItemInfo = length.name // --> symbologyItemInfo
                val lengthItemName = length.query  // --> symbologyData
                val lengthItem = MyApplication.dao.selectSymbologyDataFromSymbologyData(
                    lengthItemName,
                    lengthItemInfo
                )
                    .firstOrNull { it.symbologyData == lengthItemName && it.symbologyItemInfo == lengthItemInfo }
                if (lengthItem == null) showSetLengthDialog(length, 0, 0)
                else showSetLengthDialog(
                    length,
                    lengthItem.symbologyItemValue.toInt(),
                    lengthItem.id
                )
            }
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

    private fun showDialog(entity: SecondNode, hadSet: String, id: Long) {
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
                    if (scanModule == 1 || scanModule == 2) {
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
                    } else {
                        HIDManager.getInstance().sendData(HexUtil.stringToAscii(this))
                        currentIndex = pos
                        newTripleList.clear()
                        tripleList.forEachIndexed { index, triple ->
                            newTripleList.add(Pair(triple.first, currentIndex == index))
                        }
                        subAdapter.setNewInstance(newTripleList.toMutableList())
                        update2SymbologyData(
                            ME11SymbologyData(id, entity.getQuery(), entity.getName(), this)
                        )
                    }
                }
            }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }

    private fun showSetLengthDialog(length: CodeLength, result: Int, id: Long) {
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
                if (scanModule == 1 || scanModule == 2) {
                    if (!ds.setConfig("@${length.setCom}$setValue")) {
                        println("设置失败！！")
                    }
                } else {
                    if (length.setCom.contains("A")) {
                        val num = MyApplication.dao.selectSymbologyDataFromSymbologyData(
                            length.query,
                            "Maximum Length 0f Code Reading"
                        )
                            .firstOrNull { it.symbologyData == length.query && it.symbologyItemInfo == "Maximum Length 0f Code Reading" }
                        if (num != null && num.symbologyItemValue.toInt() < setValue) return@setPositiveButton
                    }
                    if (length.setCom.contains("B")) {
                        val num = MyApplication.dao.selectSymbologyDataFromSymbologyData(
                            length.query,
                            "Minimum Length 0f Code Reading"
                        )
                            .firstOrNull { it.symbologyData == length.query && it.symbologyItemInfo == "Minimum Length 0f Code Reading" }
                        if (num != null && num.symbologyItemValue.toInt() > setValue) return@setPositiveButton
                    }
                    HIDManager.getInstance().sendData(
                        HexUtil.stringToAscii(
                            "${length.setCom}${String.format("%02X", setValue)}"
                        )
                    )
                    update2SymbologyData(
                        ME11SymbologyData(id, length.query, length.name, "$setValue")
                    )
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