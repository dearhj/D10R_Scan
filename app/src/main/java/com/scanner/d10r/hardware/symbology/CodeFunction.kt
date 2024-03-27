package com.scanner.d10r.hardware.symbology

class CodeFunction {
    var name: String
    var default: String = ""
    var query: String = ""
    var uiList: MutableList<String>? = null
    var uiValue: MutableList<String>? = null
    var length: CodeLength? = null
    var min: Int = -1
    var max: Int = -1
    var step: Int = 1
    var addValue: String = ""

    constructor(name: String, query: String, uiList: MutableList<String>, uiValue: MutableList<String>) {
        this.name = name
        this.query = query
        this.uiList = uiList
        this.uiValue = uiValue
    }

    constructor(name: String, default: String) {
        this.name = name
        this.default = default
    }

    constructor(name: String, query: String, addValue: String) {
        this.name = name
        this.query = query
        this.addValue = addValue
    }

    /**
     * 多参数设置
     */
    constructor(name: String, codeLength: CodeLength) {
        this.name = name
        this.length = codeLength
    }
}