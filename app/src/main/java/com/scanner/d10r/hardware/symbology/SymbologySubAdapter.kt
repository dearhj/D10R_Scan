package com.scanner.d10r.hardware.symbology

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scannerd.d10r.hardware.R

class SymbologySubAdapter(layoutRes: Int = R.layout.item_recy_cardview_cb) :
    BaseQuickAdapter<Pair<String, Boolean>, BaseViewHolder>(layoutRes) {
    override fun convert(holder: BaseViewHolder, item: Pair<String, Boolean>) {
        holder.setText(R.id.tv_sigName, item.first)
        holder.setBackgroundColor(R.id.rl, if (item.second) Color.GREEN else Color.WHITE)
    }
}