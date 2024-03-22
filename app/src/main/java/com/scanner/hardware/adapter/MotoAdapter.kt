package com.scanner.hardware.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scanner.hardware.R
import com.scanner.hardware.db.MotoItem

class MotoAdapter(layoutRes: Int = R.layout.item_recy_cardview_cb) :
    BaseQuickAdapter<MotoItem, BaseViewHolder>(layoutRes) {
    override fun convert(holder: BaseViewHolder, item: MotoItem) {
        holder.setText(R.id.tv_sigName, item.name)
        val checkBox: CheckBox = holder.getView(R.id.cb_choose)
        checkBox.isChecked = item.state
    }
}