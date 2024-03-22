package com.scanner.hardware.util

import android.view.KeyEvent
import com.github.h4de5ing.baseui.interfaces.Change
import com.github.h4de5ing.baseui.interfaces.ChangeByteArray
import kotlin.properties.Delegates

//用于监听串口接收数据的变化
private var changeReceivedByteArray: ChangeByteArray? = null
var byteArrayReceivedChange: ByteArray by Delegates.observable(byteArrayOf(0x00)) { _, _, new ->
    changeReceivedByteArray?.change(new)
}

fun setReceived(ch: ((ByteArray) -> Unit)) {
    changeReceivedByteArray = object : ChangeByteArray {
        override fun change(data: ByteArray) {
            ch(data)
        }
    }
}

//用于监听串口发送数据的变化
//向串口发送ByteArray类型的数据
private var changeSendByteArray: ChangeByteArray? = null
var byteArraySendChange: ByteArray by Delegates.observable(byteArrayOf(0x00)) { _, _, new ->
    changeSendByteArray?.change(new)
}
private var changeSendString: Change? = null
var stringSendChange: String by Delegates.observable("") { _, _, new ->
    changeSendString?.change(new)
}

//向串口发送字符串
fun setSendString(ch: ((String) -> Unit)) {
    changeSendString = object : Change {
        override fun change(message: String) {
            ch(message)
        }
    }
}

//向串口发送ByteArray
fun setSendChangeByteArray(ch: ((ByteArray) -> Unit)) {
    changeSendByteArray = object : ChangeByteArray {
        override fun change(data: ByteArray) {
            ch(data)
        }
    }
}

interface ChangeKeyEvent {
    fun change(event: KeyEvent)
}

//监听按键变化
private var changeKeyString: ChangeKeyEvent? = null
var stringKeyChange: KeyEvent by Delegates.observable(KeyEvent(-1, 0)) { _, _, new ->
    println("有变化:${changeKeyString == null} $new")
    changeKeyString?.change(new)
}

fun setSendKeyString(ch: ((KeyEvent) -> Unit)) {
    changeKeyString = object : ChangeKeyEvent {
        override fun change(event: KeyEvent) {
            ch(event)
        }
    }
}
