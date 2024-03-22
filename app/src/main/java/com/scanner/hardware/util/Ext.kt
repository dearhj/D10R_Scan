package com.scanner.hardware.util

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Xml
import android.view.KeyEvent
import com.github.h4de5ing.base.add
import com.github.h4de5ing.base.toHexString
import com.github.h4de5ing.base.write
import com.github.h4de5ing.baseui.interfaces.Change
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.scanner.hardware.BuildConfig
import com.scanner.hardware.MyApplication
import com.scanner.hardware.barcodeservice.SerialPortService
import com.scanner.hardware.bean.Constants
import com.scanner.hardware.bean.KeyValue
import com.scanner.hardware.db.Config
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.ui.ScannerFloatView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileInputStream
import java.lang.Boolean.parseBoolean
import java.util.Timer
import java.util.TimerTask
import kotlin.properties.Delegates

//TODO 写一个工具类自动跟踪数据库变化，参考Saver的写法
//TODO LiveData+ViewModel 自动从数据库加载到变量中，自动将变量存入数据库中,
var model = ""
var SerialPortName = ""//串口号
var FileNodeCloseOpen = ""
var OpenScanValue = ""
var CloseScanValue = ""
var PowerOnValue = "0"
var PowerOffValue = "1"
var SerialPortSwitch = false
var FileNodeChannelSwitch = ""
var FileNodeChannelValue = ""
var isScanVoice = false
var isScanVibrate = false
var isHIDChoose = false
var isWidgetChoose = false
var isBlueToothScan = false
var isFilterSpace = true
var isReplaceInvisibleChar = false
var isClipBoardChoose = false
var replaceChar = ""   //字符替换
var addPrefix = ""
var addPrefixEnter = false
var addSuffix = ""
var addSuffixEnter = false
var actionPrefixToggle = false
var actionSuffixToggle = false
var actionPrefixKeyCode = 0
var actionSuffixKeyCode = 0
var positionMode = false
var deletePrefix = 0
var deleteSuffix = 0
var characterMode = false
var deletePrefixChar = ""
var deleteSuffixChar = ""
var tabChoose = "close"    //close  ahead  behind
var isEnterChoose = false
var dataEncoding = "UTF-8"
var isOpenServiceReboot = true
var isEnable = true
var isFloatButton = false
var floatSize = 0
var voiceChooseIndex = 0
var isAutoCleanEditText = false  //自动清空EditText
var isBlockVolumeKeys = false //屏蔽音量键
var actionShareAction = "com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION"
var actionShareData = "DATA"

//扫码模式 同步Async点击就取消 异步Sync 长按直到结果 Continuous 循环扫
var scanModel = "Async"

var baudRate = 9600

var isOurApp = false //UI显示是否在我们自己的APP里面。如果是在自己的APP里面就不响应回车换行符号

//扫码模块
var scanModule = 0
var inputPath = ""
var inputPathEnter = ""
var overTimeArray = arrayOf(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000)

//超时时间
var overTime = 0

//广播配置
const val actionHIDWrite = "com.android.scanner.HID_WRITERDATA.ACTION"
const val actionHIDClean = "com.android.scanner.HID_CLEANDATA.ACTION"
const val actionFDown = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_F_DOWN"
const val actionFUp = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_F_UP"
const val actionRDown = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_R_DOWN"
const val actionRUp = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_R_UP"
const val actionLDown = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_L_DOWN"
const val actionLUp = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_L_UP"
const val actionFloatViewShow = "com.android.barcodeservice.float_scan_code_SHOW"
const val actionFloatViewHide = "com.android.barcodeservice.float_scan_code_cancel"
const val actionBluetoothKeyDown = "com_android_action_bluetooth_scanner"
const val actionBluetoothKeyUp = "com_android_action_bluetooth_scanner_up"
const val actionSettings = "com.android.action.change.scan.settings" //通过广播修改通用设置
const val triggerValue = 1
const val triggerCancelValue = 0

//保存所有的配置项目
private val configs = mutableListOf<Config>()

//更新某一项目,如果某个项目是null 那么证明重来没有加入过，就insert一条
fun updateKT(key: String, value: String) {
    try {
        val bean = configs.firstOrNull { it.key == key }
        if (bean != null) {
            bean.value = value
            bean.update()
        } else Config(0, key, value).insert()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun update2Db(config: Config) {
    try {
        if (MyApplication.dao.isExits(config.key) == 1) config.update() else config.insert()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun import2DB(list: List<KeyValue>) = list.forEach { updateKT(it.key, it.value) }

private fun Config.update() = MyApplication.dao.updateConfig(this)
private fun Config.insert() = MyApplication.dao.insertConfig(this)
fun getKt(key: String): String? = configs.firstOrNull { it.key == key }?.value

//TODO 不要用枚举的查找的方式来做
fun firstUpdate(data: List<Config>) {
    "firstUpdate ${data.size}".logD()
    configs.clear()
    configs.addAll(data)
    SerialPortName =
        configs.firstOrNull { it.key == ConfigEnum.SerialPortName.name }?.value.toString()
    FileNodeCloseOpen =
        configs.firstOrNull { it.key == ConfigEnum.FileNodeCloseOpen.name }?.value.toString()
    PowerOnValue = configs.firstOrNull { it.key == ConfigEnum.PowerOnValue.name }?.value.toString()
    PowerOffValue =
        configs.firstOrNull { it.key == ConfigEnum.PowerOffValue.name }?.value.toString()
    SerialPortSwitch =
        configs.firstOrNull { it.key == ConfigEnum.SerialPortSwitch.name }?.value?.toBoolean()
            ?: false
    FileNodeChannelSwitch =
        configs.firstOrNull { it.key == ConfigEnum.FileNodeChannelSwitch.name }?.value.toString()
    FileNodeChannelValue =
        configs.firstOrNull { it.key == ConfigEnum.FileNodeChannelValue.name }?.value.toString()
    OpenScanValue =
        configs.firstOrNull { it.key == ConfigEnum.OpenScanValue.name }?.value.toString()
    CloseScanValue =
        configs.firstOrNull { it.key == ConfigEnum.CloseScanValue.name }?.value.toString()
    model = configs.firstOrNull { it.key == ConfigEnum.model.name }?.value.toString()
    isScanVoice =
        configs.firstOrNull { it.key == ConfigEnum.scanVoice.name }?.value?.toBoolean() ?: true
    isScanVibrate =
        configs.firstOrNull { it.key == ConfigEnum.scanVibrate.name }?.value?.toBoolean() ?: false
    isBlueToothScan =
        configs.firstOrNull { it.key == ConfigEnum.BLUETOOTH.name }?.value?.toBoolean() ?: false

    isReplaceInvisibleChar =
        configs.firstOrNull { it.key == ConfigEnum.ReplaceInvisibleChar.name }?.value?.toBoolean()
            ?: false
    isAutoCleanEditText =
        configs.firstOrNull { it.key == ConfigEnum.AUTO_CLEAN.name }?.value?.toBoolean() ?: false
    isClipBoardChoose =
        configs.firstOrNull { it.key == ConfigEnum.ClipBoardChoose.name }?.value?.toBoolean()
            ?: false
    isWidgetChoose =
        configs.firstOrNull { it.key == ConfigEnum.WidgetChoose.name }?.value?.toBoolean() ?: false
    isHIDChoose =
        configs.firstOrNull { it.key == ConfigEnum.HIDChoose.name }?.value?.toBoolean() ?: false
    isFloatButton =
        configs.firstOrNull { it.key == ConfigEnum.FloatButton.name }?.value?.toBoolean() ?: false
    floatSize = configs.firstOrNull { it.key == ConfigEnum.FloatSize.name }?.value.toNotEmptyInt()
    isFilterSpace =
        configs.firstOrNull { it.key == ConfigEnum.FilterSpace.name }?.value?.toBoolean() ?: true
    isOpenServiceReboot =
        configs.firstOrNull { it.key == ConfigEnum.openServiceReboot.name }?.value?.toBoolean()
            ?: true
    isEnable =
        configs.firstOrNull { it.key == ConfigEnum.enableScan.name }?.value?.toBoolean() ?: true
    scanModule = configs.firstOrNull { it.key == ConfigEnum.ScanModule.name }?.value.toNotEmptyInt()
    dataEncoding = configs.firstOrNull { it.key == ConfigEnum.DataEncoding.name }?.value ?: "UTF-8"
    replaceChar = configs.firstOrNull { it.key == ConfigEnum.ReplaceChar.name }?.value ?: ""
    addPrefix = configs.firstOrNull { it.key == ConfigEnum.AddPrefix.name }?.value ?: ""
    addSuffix = configs.firstOrNull { it.key == ConfigEnum.AddSuffix.name }?.value ?: ""
    actionPrefixToggle =
        configs.firstOrNull { it.key == ConfigEnum.ActionPrefixToggle.name }?.value.toBoolean()
    actionSuffixToggle =
        configs.firstOrNull { it.key == ConfigEnum.ActionSuffixToggle.name }?.value.toBoolean()
    actionPrefixKeyCode =
        configs.firstOrNull { it.key == ConfigEnum.ActionPrefix.name }?.value.toNotEmptyInt()
    actionSuffixKeyCode =
        configs.firstOrNull { it.key == ConfigEnum.ActionSuffix.name }?.value.toNotEmptyInt()
    addPrefixEnter =
        configs.firstOrNull { it.key == ConfigEnum.AddPrefixEnter.name }?.value.toBoolean()
    addSuffixEnter =
        configs.firstOrNull { it.key == ConfigEnum.AddSuffixEnter.name }?.value.toBoolean()
    positionMode = configs.firstOrNull { it.key == ConfigEnum.PositionMode.name }?.value.toBoolean()
    deletePrefix =
        configs.firstOrNull { it.key == ConfigEnum.DeletePrefix.name }?.value.toNotEmptyInt()
    deleteSuffix =
        configs.firstOrNull { it.key == ConfigEnum.DeleteSuffix.name }?.value.toNotEmptyInt()
    characterMode =
        configs.firstOrNull { it.key == ConfigEnum.CharacterMode.name }?.value.toBoolean()
    deletePrefixChar =
        configs.firstOrNull { it.key == ConfigEnum.DeletePrefixChar.name }?.value ?: ""
    deleteSuffixChar =
        configs.firstOrNull { it.key == ConfigEnum.DeleteSuffixChar.name }?.value ?: ""
    voiceChooseIndex =
        configs.firstOrNull { it.key == ConfigEnum.VoiceIndex.name }?.value.toNotEmptyInt()
    isEnterChoose =
        configs.firstOrNull { it.key == ConfigEnum.EnterChoose.name }?.value?.toBoolean() ?: false
    isBlockVolumeKeys =
        configs.firstOrNull { it.key == ConfigEnum.BlockVolumeKeys.name }?.value?.toBoolean()
            ?: false
    inputPath = configs.firstOrNull { it.key == ConfigEnum.inputPath.name }?.value
        ?: "/sys/class/EM_BT_CONTROL/input_buf"
    inputPathEnter = configs.firstOrNull { it.key == ConfigEnum.inputPathEnter.name }?.value
        ?: "/sys/class/EM_BT_CONTROL/input_buf_enter"
    tabChoose = configs.firstOrNull { it.key == ConfigEnum.TabChoose.name }?.value ?: "close"
    scanModel = configs.firstOrNull { it.key == ConfigEnum.ScanModel.name }?.value ?: "Async"
    baudRate = configs.firstOrNull { it.key == ConfigEnum.BaudRate.name }?.value?.toNotEmptyInt()
        ?: getDefaultBaud(scanModule)
    overTime = configs.firstOrNull { it.key == ConfigEnum.overTime.name }?.value.toNotEmptyInt()
    configs.firstOrNull { it.key == ConfigEnum.BroadcastAction.name }?.apply {
        if (!TextUtils.isEmpty(value)) actionShareAction = value
    }
    configs.firstOrNull { it.key == ConfigEnum.BroadcastData.name }?.apply {
        if (!TextUtils.isEmpty(value)) actionShareData = value
    }
}

//用于处理对象为null 转int导致的转换异常
fun Any?.toNotEmptyInt(): Int =
    if (this == null || TextUtils.isEmpty("$this")) 0 else toString().toInt()

//串口监听到的数据变化，主要用于通知本APP更新UI
private var change: Change? = null
var resultChange: String by Delegates.observable("") { _, _, new ->
    change?.change(new)
}

fun setOnChange(onchange: ((String) -> Unit)) {
    change = object : Change {
        override fun change(message: String) {
            onchange(message)
        }
    }
}

//监听串口原始字符串，只用于扫码导入
private var changeSP: Change? = null
var spChange: String by Delegates.observable("") { _, _, new ->
    changeSP?.change(new)
}

fun setSPChange(onchange: ((String) -> Unit)) {
    changeSP = object : Change {
        override fun change(message: String) {
            onchange(message)
        }
    }
}

fun getDefaultBaud(deviceType: Int): Int {
    return when (deviceType) {
        Constants.DTMoToSE655 -> 9600
        Constants.DTMoTOSE2707 -> 9600
        Constants.DTHONEYWELLONE -> 9600
        Constants.DTHONEYWELL -> 115200
        Constants.DTTotinfo -> 115200
        Constants.DTNewland -> 9600
        else -> 9600
    }
}

fun filterModuleIndex(deviceType: Int): Int {
    return when (deviceType) {
        Constants.DTMoToSE655 -> 0
        Constants.DTMoTOSE2707 -> 1
        Constants.DTHONEYWELLONE -> 2
        Constants.DTHONEYWELL -> 3
        Constants.DTTotinfo -> 4
        Constants.DTNewland -> 5
        else -> 0
    }
}

fun is195T(): Boolean = model == "RD195T"
fun isDebug(): Boolean = BuildConfig.DEBUG || File("/sdcard/debug").exists()

fun isSerialPort(): Boolean = BuildConfig.FLAVOR == "serialport"


/**
 * 发送图腾命令需要先发送0x00 之后才响应  0x00指令作用是将休眠中的硬件唤醒
 */
fun sendByteArray2SP0x00(byteArray: ByteArray) {
    SerialPortService.readType = Constants.ReadType.CMD
    byteArraySendChange = byteArrayOf(0x00)
    SerialPortService.readType = Constants.ReadType.CMD
    delayed(50) { byteArraySendChange = byteArray }
}

fun sendByteArray2SP(byteArray: ByteArray) {
    SerialPortService.readType = Constants.ReadType.CMD
    byteArraySendChange = byteArray
}

fun setEXT() {
    if (isSerialPort()) { //增加前后缀来解决断行问题
        sendByteArray2SP(
            byteArrayOf(
                0x16, 0x4D, 0x0D, 0x53, 0x55, 0x46, 0x42, 0x4B, 0x32, 0x39, 0x39, 0x30, 0x33, 0x2E
            )
        )
    }
}

/**
 * 发生字符串指令到串口,只能用于霍尼韦尔
 */
fun sendString2SP(message: String) {
    SerialPortService.readType = Constants.ReadType.CMD
    delayed(50) { byteArraySendChange = byteArrayOf(0x16, 0x4D, 0x0D).add(message.toByteArray()) }
}


private val head = byteArrayOf(0x7E, 0x01, 0x30, 0x30, 0x30, 0x30, 0x40)
private val tail = byteArrayOf(0x3B, 0x03)

/**
 * 发送指令到新大陆
 */
fun send2Newland(message: String) {
    SerialPortService.readType = Constants.ReadType.CMD
    byteArraySendChange = byteArrayOf(0x00)
    val byteArray = head.add(message.toByteArray()).add(tail)
    "新大陆 串口指令发出去了${byteArray.toHexString()}->${String(byteArray)}".logD()
    //控制minimum message length参数能够发送成功
    val prefixBytearray = byteArrayOf(
        0x7E,
        0x01,
        0x30,
        0x30,
        0x30,
        0x30,
        0x40,
        0x49,
        0x4E,
        0x54,
        0x45,
        0x52,
        0x46,
        0x30,
        0x3B,
        0x03
    )
//    delayed(50) { byteArraySendChange = prefixBytearray }
    delayed(50) { byteArraySendChange = byteArray }
}

fun delayed(delay: Long, block: () -> Unit) {
    Timer().schedule(object : TimerTask() {
        override fun run() {
            block()
        }
    }, delay)
}

fun replaceCharData(value: ByteArray, s: String): ByteArray {
    val list = s.split("&")
    value.forEachIndexed { index, it ->
        list.forEach { item ->
            val spit = item.split(">")
            try {
                val rep = spit[0].toInt(16).toByte()
                val repTo = spit[1].toInt(16).toByte()
                if (it == rep) value[index] = repTo
            } catch (_: Exception) {
            }
        }
    }
    return value
}

const val SC_PREFIX = "[SCAN_CONF]"
const val SC_SUFFIX = "[/SCAN_CONF]"
val scope = MainScope()
fun importConfig(
    context: Context, data: String, success: (() -> Unit) = {}, error: (() -> Unit) = {}
) {
    try {
        val content = data.substring(SC_PREFIX.length, data.length - SC_SUFFIX.length)
        val list = mutableListOf<KeyValue>()
        Json.decodeFromString<List<Map<String, String>>>(content).forEach { map ->
            map.keys.forEach { key ->
                val value = map[key]
                list.add(KeyValue(key, "$value"))
            }
        }
        import2DB(list)
        scope.launch {
            withContext(Dispatchers.Main) {
                firstUpdate(MyApplication.dao.selectAllConfig())
                updateFloatView(isFloatButton)
                ScannerFloatView.resize()
                SoundPlayUtil.init(context, voiceChooseIndex)
                success()
                delay(100)
                context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(this)
                }
            }
        }
    } catch (e: Exception) {
        error()
        e.printStackTrace()
    }
}

fun replaceInvisibleChar(sor: ByteArray): ByteArray {
    try {
        var data = sor
        val obj = Class.forName("com.scanner.hardware.util.Ascii")
        val list = obj.fields.map { Pair(it.name, it.get(obj)) }
        val preArray = byteArrayOf(0x5B)
        val sufArray = byteArrayOf(0x5D)
        val index1 = data.indexOfFirst { it in list.map { it1 -> it1.second } }
        if (index1 != -1) {
            list.forEach {
                if (it.second == data[index1]) {
                    val afterReplace = it.first.toByteArray()
                    val afterReplaceArray = preArray + afterReplace + sufArray
                    val preData = data.copyOfRange(0, index1)
                    val sufData = data.copyOfRange(index1 + 1, data.size)
                    data = preData + afterReplaceArray + sufData
                    data = replaceInvisibleChar(data)
                }
            }
        }
        return data
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

fun updateOneConfig(key: String, value: String) {
    try {
        "应用设置:$key=$value".logD()
        updateKT(key, value)
        if ("FloatButton" == key) updateFloatView(parseBoolean(value))
    } catch (e: Exception) {
        (e.message + "->" + key + "=" + value).logE()
    }
}

fun setIsFirstOpen(isFirst: Boolean) =
    MyApplication.sp.edit().putBoolean("isFirst", isFirst).apply()

/**
 * 针对某些Android 11 或者特定的头需要不停发送0x00 通过这个方法判断是否需要发送
 *
 * @return true 需要发送0x00 false 不需要发送
 */
fun needHandleMoto(): Boolean {
    var res = false
    val module = scanModule
    if (model.equals("Q86_5G", ignoreCase = true)) {
        res = true
//            res = (module == Constans.DTMoTOSE2707 || module == Constans.DTHONEYWELL || module == Constans.DTNewland);
    } else if (model.equals("Q86M", ignoreCase = true)) {
        res = module == Constants.DTMoTOSE2707
    }
    return res
}

//过滤不要的字段
val blockList = listOf("ReaderID", "ScreenDirection", "SerialPortSwitch", "openService_reboot")
fun parserXML(file: File) {
    val configs = mutableListOf<Config>()
    try {
        val inputStream = FileInputStream(file)
        val xml = Xml.newPullParser()
        xml.setInput(inputStream, "UTF-8")
        var eventType = xml.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val name = xml.name
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {}
                XmlPullParser.START_TAG -> if (name.equals("property", ignoreCase = true)) {
                    val key = xml.getAttributeValue(0)
                    var value = xml.getAttributeValue(1)
                    "xml解析数据 $key=$value".logD()
                    if (key !in blockList) {
                        when (key) {
                            "HIDChoose" -> value = "false"
                            "WidgetChoose" -> value = "true"
                        }
                        configs.add(Config(0, key, value))
                    }
                }

                XmlPullParser.END_TAG -> {}
            }
            eventType = xml.next()
        }
        configs.add(Config(0, "openServiceReboot", "true"))
        configs.add(Config(0, "FilterSpace", "true"))
        for (config in configs) update2Db(config)
        firstUpdate(configs)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun keySwitchToTrigger(value: Int) {
//    val fileNode = FileNodeCloseOpen
//    SerialPortService.readType = Constants.ReadType.SCAN
//    val string = if (value == 1) {
//        OpenScanValue
//    } else CloseScanValue
//    File(fileNode).writeText(string)
}

private val handler = Handler(Looper.getMainLooper())
private val runnable1 = Runnable { keySwitchToTrigger(0) }

private class MyRunnable : Runnable {
    override fun run() {
        //一直扫描
        if (SerialPortService.isContinuous && isEnable && "Continuous" == scanModel) {
            //handleMotoSend
            if (4 == scanModule || 5 == scanModule) keySwitchToTrigger(0)
            keySwitchToTrigger(1)
            val timeout = overTimeArray[overTime]
            handler.postDelayed(myRunnable, timeout.toLong())
        }
    }
}

private val myRunnable = MyRunnable()

fun startScan() {
//    keySwitchToTrigger(0)
//    if ("Async" == scanModel) {
//        SerialPortService.isContinuous = false
//        //handleMotoSend
//        keySwitchToTrigger(1)
//    } else if ("Sync" == scanModel) {
//        SerialPortService.isContinuous = false
//        //handleMotoSend
//        handler.removeCallbacks(runnable1)
//        keySwitchToTrigger(1)
//        handler.postDelayed(runnable1, 3000)
//    } else if ("Continuous" == scanModel) {
//        SerialPortService.isContinuous = !SerialPortService.isContinuous
//        handler.post(myRunnable)
//        if (SerialPortService.isContinuous) WakeLockUtil.acquire()
//        else {
//            WakeLockUtil.release()
//            keySwitchToTrigger(0)
//        }
//    }
}

fun handleMotoSend(send: Boolean) {
    if (needHandleMoto()) {
        if (send) {

        } else {

        }
    }
}

fun stopScan() {
//    if ("Async" == scanModel) keySwitchToTrigger(0)
}

fun sendDateToUser(data: String) {
    MyApplication.application?.sendBroadcast(
        Intent(actionShareAction).putExtra(
            actionShareData, data
        )
    )
}

fun keySwitchToTriggerChange() {
    if (SerialPortSwitch) File(FileNodeChannelSwitch).write(FileNodeChannelValue)
}

fun keySwitchToTriggerPower(on: Boolean) {
    if ("R88" != model && "T81X" != model) { //R88 T81X平台不进行上下电
        //新大陆模块，在感应模式下，且扫码开关关闭的情况下，不去上电，否则会导致扫码开关关闭的情况下，仍然能扫码
        //这里有个坑，难搞，当下电后，码制查询设置之类的全部不能用，扫码头恢复出厂设置之类的都不能用了。
        if (scanModule == 6 && scanModel == "Induction" && !isEnable && on) return
        File(FileNodeCloseOpen).write(if (on) PowerOnValue else PowerOffValue)
    }
}

fun replaceChar(data: ByteArray): ByteArray {
    val newData: ByteArray = try {
        if (!TextUtils.isEmpty(replaceChar)) replaceCharData(data, replaceChar)
        else data
    } catch (e: Exception) {
        data
    }
    return newData
}

val scanKeyCode = listOf(286, 289, 306)
val scanKeyName = listOf("KEYCODE_SCAN_F", "KEYCODE_SCAN_L", "KEYCODE_SCAN_R")
fun mirrorKeyEvent(keyEvent: KeyEvent, start: (() -> Unit), cancel: (() -> Unit)) {
    try {
        val keyCode = keyEvent.keyCode
        val keyEventName = KeyEvent.keyCodeToString(keyCode)
        if (keyCode in scanKeyCode || keyEventName in scanKeyName) {
            if (KeyEvent.ACTION_DOWN == keyEvent.action) start()
            else if (KeyEvent.ACTION_UP == keyEvent.action) cancel()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun updateFloatView(isShow: Boolean) {
    try {
        MyApplication.application?.sendBroadcast(Intent(if (isShow) actionFloatViewShow else actionFloatViewHide))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun actionSettings(settings: String?) {
    try {
        settings?.apply {
            val split = split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (item in split) {
                val spit = item.split("=".toRegex(), limit = 2).toTypedArray()
                val key = spit[0]
                val value = spit[1]
                updateOneConfig(key, value)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun initConfig() {
    try {
//        MyApplication.dao.selectAllConfig().forEach {
//
//        }
        val clazz = Class.forName("com.scanner.hardware.enums.ConfigEnumValues").declaredFields
        clazz.forEach {
            it.isAccessible = true
            "变量名称:${it.name} ${it.get(clazz)}".logD()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}