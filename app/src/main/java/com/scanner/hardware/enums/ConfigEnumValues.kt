package com.scanner.hardware.enums

import kotlinx.coroutines.flow.MutableStateFlow

object ConfigEnumValues {
    var isFloatButton2 = MutableStateFlow(false)
    var actionShareAction = MutableStateFlow("com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION")
}