package com.scanner.d10r.hardware.adapter

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.util.editDialog
import com.scanner.d10r.hardware.util.replaceChar
import com.scanner.d10r.hardware.util.updateKT
import com.scannerd.d10r.hardware.R

class ReplaceAdapter :
    BaseQuickAdapter<Pair<String, String>, BaseViewHolder>(R.layout.detail_recycleview) {
    private lateinit var normalDialog: AlertDialog.Builder
    override fun convert(holder: BaseViewHolder, item: Pair<String, String>) {
        holder.setText(R.id.source_char, item.first)
        holder.setText(R.id.aim_char, item.second)
        val edit = holder.getView<ImageView>(R.id.edit)
        val delete = holder.getView<ImageView>(R.id.delete)
        edit.setOnClickListener {
            val v = View.inflate(context, R.layout.content_replace_char, null)
            val etTo = v.findViewById<EditText>(R.id.et_replace_to)
            etTo.setText(item.second)
            editDialog(context, v, "") {
                if (etTo.text.toString() != "") {
                    val reReplaceChar = if (etTo.text.toString()
                            .first() == '0' && etTo.text.toString().length == 2
                    ) etTo.text.toString().substring(1)
                    else etTo.text.toString()
                    if (reReplaceChar != item.first && reReplaceChar != item.second) updateData(
                        item, reReplaceChar, true
                    )
                }
            }
        }
        delete.setOnClickListener {
            normalDialog = AlertDialog.Builder(context)
            normalDialog.setTitle(R.string.deldata)
            normalDialog.setMessage(R.string.char_del_this)
            normalDialog.setPositiveButton(android.R.string.ok) { _, _ ->
                updateData(
                    item, null, false
                )
            }
            normalDialog.setNegativeButton(android.R.string.cancel, null)
            normalDialog.show()
        }
    }

    private fun updateData(data: Pair<String, String>, nothing: String?, flag: Boolean) {
        val spCharData = replaceChar
        var resultReplaceChar = ""
        val list = spCharData.split("&")
        for (item in list) {
            val spit = item.split(">")
            if (spit[0] == data.first && spCharData.startsWith(spit[0]) && list.size != 1) {
                resultReplaceChar = spCharData.split(data.first + ">" + data.second + "&")[1]
                if (flag) resultReplaceChar = data.first + ">" + nothing + "&" + resultReplaceChar
            } else if (spit[0] == data.first && list.size != 1) {
                resultReplaceChar =
                    spCharData.split("&" + data.first + ">" + data.second)[0] + spCharData.split("&" + data.first + ">" + data.second)[1]
                if (flag) resultReplaceChar =
                    spCharData.split("&" + data.first + ">" + data.second)[0] + "&" + data.first + ">" + nothing + spCharData.split(
                        "&" + data.first + ">" + data.second
                    )[1]
            } else if (list.size == 1) {
                resultReplaceChar = if (!flag) ""
                else data.first + ">" + nothing
            }
            updateKT(ConfigEnum.ReplaceChar.name, resultReplaceChar)
        }
    }
}