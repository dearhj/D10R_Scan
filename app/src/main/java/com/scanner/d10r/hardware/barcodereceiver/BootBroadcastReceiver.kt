package com.scanner.d10r.hardware.barcodereceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scanner.d10r.hardware.barcodeservice.Gh0stService
import com.scanner.d10r.hardware.util.isOpenServiceReboot
import com.scanner.d10r.hardware.util.scanModule

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (scanModule in 1..4 && isOpenServiceReboot)
            context.startForegroundService(Intent(context, Gh0stService::class.java))
    }
}