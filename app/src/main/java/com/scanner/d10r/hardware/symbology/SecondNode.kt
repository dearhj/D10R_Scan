package com.scanner.d10r.hardware.symbology

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

class SecondNode(
    private val name: String,
    private val default: String,
    private val query: String,
    private val uiList: MutableList<String>?,
    private val uiValue: MutableList<String>?,
    private var min: Int = -1,
    private var max: Int = -1,
    private var step: Int = -1,
    private var length: CodeLength?,
    private var addValue: String?,
    override val childNode: MutableList<BaseNode>?
) : BaseExpandNode() {
    fun getName() = name
    fun getQuery() = query
    fun getUiList() = uiList
    fun getUiValue() = uiValue
    fun getDefault() = default
    fun getMin() = min
    fun getMax() = max
    fun getStep() = step
    fun getLength() = length
    fun getAddValue() = addValue
}