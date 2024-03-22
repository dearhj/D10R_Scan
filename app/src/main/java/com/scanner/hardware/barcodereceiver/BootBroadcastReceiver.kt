package com.scanner.hardware.barcodereceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scanner.hardware.barcodeservice.Gh0stService
import com.scanner.hardware.util.isOpenServiceReboot
import com.scanner.hardware.util.scanModule

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (scanModule in 1..6 && isOpenServiceReboot)
            context.startForegroundService(Intent(context, Gh0stService::class.java))
    }
}