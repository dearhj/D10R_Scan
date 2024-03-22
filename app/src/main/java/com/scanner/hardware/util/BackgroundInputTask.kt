package com.scanner.hardware.util;

import android.app.Instrumentation
import android.content.Intent
import android.os.AsyncTask
import android.text.TextUtils
import com.scanner.hardware.MyApplication
import com.scanner.hardware.barcodeservice.SerialPortService
import java.io.File

//TODO 采用kotlin 协程异步替换
class BackgroundInputTask : AsyncTask<String, Int, Int>() {
    override fun doInBackground(vararg params: String?): Int {
        val res = 0
        try {
            var data = params[0] ?: ""
            if (!TextUtils.isEmpty(data)) {
                if (isWidgetChoose) {
                    if ("ahead".equals(tabChoose, ignoreCase = true)) data = "\t${data}"
                    if ("behind".equals(tabChoose, ignoreCase = true)) data = "${data}\t"
                    if (isEnterChoose) data = "${data}\n"

                    if (actionPrefixToggle && actionPrefixKeyCode > 0) {
                        Instrumentation().sendKeyDownUpSync(actionPrefixKeyCode)
                        Thread.sleep(50)
                    }
                    val intent = Intent(actionHIDWrite)
                    intent.putExtra("DATA", data)
                    MyApplication.application?.sendBroadcast(intent)
                    if (actionSuffixToggle && actionSuffixKeyCode > 0) {
                        Thread.sleep(50)
                        Instrumentation().sendKeyDownUpSync(actionSuffixKeyCode)
                    }
                } else {
                    if (actionPrefixToggle && actionPrefixKeyCode > 0) {
                        Instrumentation().sendKeyDownUpSync(actionPrefixKeyCode)
                        Thread.sleep(50)
                    }
                    if ("ahead".equals(tabChoose, ignoreCase = true)) data = "\t$data"
                    if ("behind".equals(tabChoose, ignoreCase = true)) data = "$data\t"
                    if (isEnterChoose) File(inputPathEnter).writeText(data)
                    else File(inputPath).writeText(data)
                    if (actionSuffixToggle && actionSuffixKeyCode > 0) {
                        Thread.sleep(50)
                        Instrumentation().sendKeyDownUpSync(actionSuffixKeyCode)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }
}