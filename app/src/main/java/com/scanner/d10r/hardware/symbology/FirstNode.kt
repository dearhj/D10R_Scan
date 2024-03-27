package com.scanner.d10r.hardware.symbology

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode


class FirstNode(
    private val title: String, override val childNode: MutableList<BaseNode>?
) : BaseExpandNode() {
    init {
        isExpanded = false
    }

    fun getTitle() = title
}