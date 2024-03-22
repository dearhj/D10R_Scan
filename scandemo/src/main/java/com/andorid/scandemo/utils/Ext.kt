package com.andorid.scandemo.utils

import android.widget.SeekBar
import com.andorid.scannerlib.Reader
import java.io.FileWriter
import java.util.*
import kotlin.properties.Delegates

//监听串口原始数据
private var changeSP: ChangeByteArray? = null

/**
 * 设置数据的地方
 */
var spChange: ByteArray by Delegates.observable(byteArrayOf(0x00)) { _, _, new ->
    changeSP?.change(new)
}

/**
 * 任何页面都可以监听的回调
 */
fun setSPChangeListener(onchange: ((ByteArray) -> Unit)) {
    changeSP = object : ChangeByteArray {
        override fun change(data: ByteArray) {
            onchange(data)
        }
    }
}

//延时执行
fun delayed(delay: Long, block: () -> Unit) {
    Timer().schedule(object : TimerTask() {
        override fun run() {
            block()
        }
    }, delay)
}

fun SeekBar.change(selected: ((Int) -> Unit)) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar?,
            progress: Int,
            fromUser: Boolean
        ) {
            selected(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })
}

/**
 * 发生字符串指令到串口，霍尼二维
 */
fun sendString2SP(message: String) {
    Reader.setCode(byteArrayOf(0x16, 0x4D, 0x0D))
    delayed(50) { Reader.setCode(message.toByteArray()) }
}

/**
 * 发送图腾命令需要先发送0x00 之后才响应  0x00指令作用是将休眠中的硬件唤醒
 */
fun sendByteArray2SP0x00(byteArray: ByteArray) {
    Reader.setCode(byteArrayOf(0x00))
    delayed(50) { Reader.setCode(byteArray) }
}

private val head = byteArrayOf(0x7E, 0x01, 0x30, 0x30, 0x30, 0x30, 0x40)
private val tail = byteArrayOf(0x3B, 0x03)

/**
 * 发送指令到新大陆
 */
fun send2Newland(message: String) {
    Reader.setCode(byteArrayOf(0x00))
    val prefixAddMessage = DataUtil.byteArrayAddByteArray(head, message.toByteArray())
    delayed(50) { Reader.setCode(DataUtil.byteArrayAddByteArray(prefixAddMessage, tail)) }
}

fun write(path: String, content: String) {
    try {
        val writer = FileWriter(path)
        writer.write(content)
        writer.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}