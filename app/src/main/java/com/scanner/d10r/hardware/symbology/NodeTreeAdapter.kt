package com.scanner.d10r.hardware.symbology

import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode

class NodeTreeAdapter : BaseNodeAdapter() {
    init {
        addNodeProvider(FirstProvider())
        addNodeProvider(SecondProvider())
    }

    override fun getItemType(data: List<BaseNode>, position: Int): Int {
        val node = data[position]
        if (node is FirstNode) {
            return 1
        } else if (node is SecondNode) {
            return 2
        }
        return -1
    }
}