package com.scanner.d10r.hardware.bean

/**
 * 码制功能项
 * functionName 总的功能名称
 * functionList 功能列表
 *  pair.first 子功能名称
 *  pair.second 子功能命令
 *  queryString 查询命令
 *  lengthFunction 长度设置命令
 */
class CodeFunction {
    var functionName: String
    var functionList: List<Pair<String, String>>
    var queryString: String? = null
    var lengthFunction: LengthFunction? = null
    var codePageList: List<CodePageFunction>? = null
//    var asciiList: List<ASCII>? = null

    //用于   名称  设置选项列表
    constructor(
        functionName: String,
        functionList: List<Pair<String, String>>
    ) {
        this.functionName = functionName
        this.functionList = functionList
    }

    //用于名称 设置选项列表  查询设置命令
    constructor(
        functionName: String,
        functionList: List<Pair<String, String>>,
        queryString: String?
    ) {
        this.functionName = functionName
        this.functionList = functionList
        this.queryString = queryString
    }

    //用于 名称 长度设置
    constructor(
        functionName: String,
        functionList: List<Pair<String, String>>,
        lengthFunction: LengthFunction?
    ) {
        this.functionName = functionName
        this.functionList = functionList
        this.lengthFunction = lengthFunction
    }

    //用于选中ASCII查表
//    constructor(
//        functionName: String,
//        functionList: List<Pair<String, String>>,
//        asciiList: List<ASCII>,
//        queryString: String?,
//    ) {
//        this.functionName = functionName
//        this.functionList = functionList
//        this.asciiList = asciiList
//        this.queryString = queryString
//    }

    //用于名称 CodePage设置
    constructor(
        functionName: String,
        functionList: List<Pair<String, String>>,
        queryString: String?,
        codePageList: List<CodePageFunction>?
    ) {
        this.functionName = functionName
        this.functionList = functionList
        this.queryString = queryString
        this.codePageList = codePageList
    }
}

/**
 * min 最小长度
 * max 最大长度
 * command 设置命令或者查询命令的回显，cbr  min?. 查询  cbr   min 4. 表示最小设置为4
 */
data class LengthFunction(
    var min: Int,
    var max: Int,
    var command: String
)

/**
 * codePageCountry   国家描述，@eg United States 用于UI显示
 * value             国家代码 @eg 1  用于和functionCommand 组合成一条完整的命令 aztdcp1.
 */
data class CodePageFunction(
    val codePageCountry: String,
    val value: String
)