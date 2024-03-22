package com.andorid.scandemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.andorid.scandemo.utils.BaseActivity

/**
 * 此功能需要在 通用设置 -输出方式- 系统广播以后才能收到数据
 * 如果在自定义广播中修改了广播的action合data字段，请修改如下代码的action和data
 */
class BroadcastActivity : BaseActivity() {
    private lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)
        tv.movementMethod = ScrollingMovementMethod()
        val interFiler = IntentFilter()
        interFiler.addAction("com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION")
        registerReceiver(receiver, interFiler)
    }

    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            val data = intent?.getStringExtra("DATA")
            updateTv("$data")
        }
    }

    private fun updateTv(message: String) {
        runOnUiThread {
            tv.append("\n${message}")
            val offset = tv.lineCount * tv.lineHeight - tv.height + 10
            if (offset >= 10000) tv.text = "" else tv.scrollTo(0, offset.coerceAtLeast(0))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}