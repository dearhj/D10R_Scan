package com.android.trigger

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.scanner.hardware.IRemoteService

class MainActivity : Activity() {
    private var tv: TextView? = null
    private var action = "com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION"
    private var data = "DATA"
    private var init = false
    private var handler = Handler(Looper.getMainLooper())
    private val myService: MyService = MyService { first, second ->
        if (!TextUtils.isEmpty(first) && !TextUtils.isEmpty(second)) {
            action = "$first"
            data = "$second"
            etAction?.setText(action)
            etData?.setText(data)
        }
    }
    private var etAction: EditText? = null
    private var etData: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etAction = findViewById(R.id.action)
        etData = findViewById(R.id.data)
        findViewById<Button>(R.id.l).setOnClickListener { sendBroadcast("com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_L_DOWN") }
        findViewById<Button>(R.id.r).setOnClickListener { sendBroadcast("com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_R_DOWN") }
        findViewById<Button>(R.id.f).setOnClickListener { sendBroadcast("com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_F_DOWN") }
        tv = findViewById(R.id.result)
        findViewById<Button>(R.id.init).setOnClickListener { init() }
        bind(this)
        handler.postDelayed({ init() }, 1000)
//        tv?.setOnKeyListener { p0, action, event ->
//            println("$event")
//            tv?.append("按下:${action} ${event?.keyCode}\n")
//            true
//        }
    }

    private fun init() {
        val etActionStr = etAction?.text.toString()
        val etDataStr = etData?.text.toString()
        if (TextUtils.isEmpty(etActionStr) && TextUtils.isEmpty(etDataStr)) {
            action = etActionStr
            data = etDataStr
            if (init) unregisterReceiver()
            registerReceiver()
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
        }
        registerReceiver()
    }

    private fun registerReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentFilter = IntentFilter(action)
            intentFilter.addAction("com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_L_DOWN")
            intentFilter.addAction("com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_R_DOWN")
            intentFilter.addAction("com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_F_DOWN")
            registerReceiver(receiver, intentFilter)
        }
        init = true
    }

    private fun unregisterReceiver() {
        unregisterReceiver(receiver)
        init = false
    }

    private fun sendBroadcast(action: String) {
        try {
            sendBroadcast(Intent(action))
        } catch (e: Exception) {
            tv?.append(e.message)
            e.printStackTrace()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                tv?.append(intent?.getStringExtra(data) + "\n")
//                tv?.append("${intent?.action}\n")
            } catch (_: Exception) {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver()
        unbindService(myService)
    }

    private fun bind(context: Context) {
        val intent = Intent()
        intent.action = "com.scanner.hardware.barcodeservice.SerialPortService"
        intent.setPackage("com.scanner.hardware")
        context.bindService(intent, myService, Context.BIND_AUTO_CREATE)
    }

    private var mService: IRemoteService? = null

    inner class MyService(val change: ((String?, String?) -> Unit)) : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            try {
                println("onServiceConnected")
                mService = IRemoteService.Stub.asInterface(service)
                val action = mService?.getSettings("BroadcastAction")
                val data = mService?.getSettings("BroadcastData")
                change(action, data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            println("onServiceDisconnected")
            mService = null
        }
    }
}