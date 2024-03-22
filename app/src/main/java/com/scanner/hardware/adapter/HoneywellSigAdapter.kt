package com.scanner.hardware.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scanner.hardware.R
import com.scanner.hardware.db.SymbologiesItem

class HoneywellSigAdapter(layoutRes: Int = R.layout.item_recy_cardview_cb) :
    BaseQuickAdapter<SymbologiesItem, BaseViewHolder>(layoutRes) {
    override fun convert(holder: BaseViewHolder, item: SymbologiesItem) {
        holder.setText(R.id.tv_sigName, item.name)
        val checkBox: CheckBox = holder.getView(R.id.cb_choose)
        checkBox.isClickable = true
        checkBox.isFocusable = true
        checkBox.isChecked = item.state
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            change?.change(
                getItemPosition(item),
                isChecked
            )
        }
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