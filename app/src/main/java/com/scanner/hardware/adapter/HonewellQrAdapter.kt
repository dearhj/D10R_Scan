package com.scanner.hardware.adapter

import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scanner.hardware.R
import com.scanner.hardware.util.isDebug

class HonewellQrAdapter(layoutRes: Int = R.layout.item_recy_cardview_cb) :
    BaseQuickAdapter<Triple<String, String, Boolean>, BaseViewHolder>(layoutRes) {
    override fun convert(holder: BaseViewHolder, item: Triple<String, String, Boolean>) {
        holder.setText(
            R.id.tv_sigName,
            "${item.first} ${if (isDebug()) "[${item.second}]" else ""}"
        )
        holder.setText(R.id.tv_command, item.second)
        val checkBox: CheckBox = holder.getView(R.id.cb_choose)
        checkBox.visibility = View.GONE
        holder.setBackgroundColor(R.id.rl, if (item.third) Color.GREEN else Color.WHITE)
    }

    private var change: CheckChange? = null
    fun setChange(ch: ((Int, Boolean) -> Unit)) {
        this.change = object : CheckChange {
            override fun change(position: Int, isChecked: Boolean) {
                ch(position, isChecked)
            }
        }
    }

    interface CheckChange {
        fun change(position: Int, isChecked: Boolean)
    }
}