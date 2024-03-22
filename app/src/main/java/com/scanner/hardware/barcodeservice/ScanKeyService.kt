package com.scanner.hardware.barcodeservice

import android.accessibilityservice.AccessibilityService
import android.system.Os
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.github.h4de5ing.baseui.logI
import com.scanner.hardware.util.scanKeyCode
import com.scanner.hardware.util.stringKeyChange

class ScanKeyService : AccessibilityService() {
    override fun onKeyEvent(event: KeyEvent): Boolean {
        val isTrue = (Os.getuid() != 1000) && event.keyCode in scanKeyCode
        "$isTrue ${event.keyCode}->$event".logI()
        if (isTrue) stringKeyChange = event
        return isTrue
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit
}