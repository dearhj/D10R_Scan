package com.scanner.d10r.hardware.symbology

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scannerd.d10r.hardware.R

class FirstProvider(
    override val itemViewType: Int = 1,
    override val layoutId: Int = R.layout.item_node_first
) : BaseNodeProvider() {

    private val expandCollapsePayload = 110
    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        val firstNode = item as FirstNode
        helper.setText(R.id.title, firstNode.getTitle())
        setArrowSpin(helper, item, false)
    }

    override fun convert(helper: BaseViewHolder, item: BaseNode, payloads: List<Any>) {
        for (payload in payloads) {
            if (payload is Int && payload == expandCollapsePayload)
                setArrowSpin(helper, item, true)
        }
    }


    private fun setArrowSpin(helper: BaseViewHolder, data: BaseNode, isAnimate: Boolean) {
        val entity = data as FirstNode
        val imageView = helper.getView<ImageView>(R.id.iv)
        if (entity.isExpanded) {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                    .setInterpolator(DecelerateInterpolator())
                    .rotation(90f)
                    .start()
            } else imageView.rotation = 90f
        } else {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                    .setInterpolator(DecelerateInterpolator())
                    .rotation(0f)
                    .start()
            } else imageView.rotation = 0f
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        getAdapter()!!.expandOrCollapse(
            position,
            animate = true,
            notify = true,
            parentPayload = expandCollapsePayload
        )
    }
}