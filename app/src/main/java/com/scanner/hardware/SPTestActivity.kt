package com.scanner.hardware

import android.os.Bundle
import com.github.h4de5ing.base.toHexByteArray
import com.github.h4de5ing.base.toHexString
import com.github.h4de5ing.baseui.base.BaseReturnActivity
import com.scanner.hardware.databinding.ActivitySptestBinding
import com.scanner.hardware.util.byteArraySendChange
import com.scanner.hardware.util.setReceived
import com.scanner.hardware.util.stringSendChange
import java.util.*

/**
霍尔二维
读取固件版本
16 4d 0d 52 45 56 5f 57 41 3f 2e
恢复出厂设置
16 4d 0d 44 45 46 41 4c 54 2E
 */
class SPTestActivity : BaseReturnActivity() {
    private var continuity = false
    private var sendMS = 100L
    private var hexSend = false
    private var hexReceive = false
    private var isTimer = false
    private lateinit var binding: ActivitySptestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySptestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setReceived { data ->
            if (hexReceive) {
                updateTv("r:${data.toHexString()}\n")
            } else {
                updateTv("r:${String(data, 0, data.size)}\n")
            }
        }

        binding.cbContinuity.setOnCheckedChangeListener { _, isChecked -> continuity = isChecked }
        binding.cbHexSend.setOnCheckedChangeListener { _, isChecked -> hexSend = isChecked }
        binding.cbHexReceive.setOnCheckedChangeListener { _, isChecked -> hexReceive = isChecked }
        binding.btnSend.setOnClickListener {
            sendMS =
                if (binding.sendMs.text.isEmpty()) 100 else binding.sendMs.text.toString().toLong()
            val sendStr = binding.etInput.text.toString()
            if (sendStr.isNotEmpty()) {
                if (continuity) {
                    if (isTimer) {
                        binding.btnSend.text = "发送"
                        isTimer = false
                    } else {
                        binding.btnSend.text = "停止"
                        isTimer = true
                    }
                } else sendOneTime(sendStr)
            }
        }
        timer()
    }

    private fun timer() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (continuity && isTimer) {
                    val sendStr = binding.etInput.text.toString()
                    if (sendStr.isNotEmpty()) {
                        sendOneTime(sendStr)
                    }
                }
            }
        }, 0, sendMS)
    }

    private fun sendOneTime(message: String) {
        if (hexSend) {
            val data = message.toHexByteArray()
            updateTv("s:${data.toHexString()} \n")
            byteArraySendChange = data
        } else {
            updateTv("s:$message \n")
            stringSendChange = message
        }
    }

    private fun updateTv(message: String) {
        runOnUiThread {
            binding.result.append(message)
            val offset: Int =
                binding.result.lineCount * binding.result.lineHeight - binding.result.height
            if (offset >= 6000) binding.result.setText("")
            else binding.result.scrollTo(0, offset.coerceAtLeast(0))
        }
    }
}