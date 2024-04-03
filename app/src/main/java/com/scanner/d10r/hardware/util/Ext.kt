package com.scanner.d10r.hardware.util

import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.text.TextUtils
import android.util.Xml
import com.github.h4de5ing.baseui.interfaces.Change
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.scanner.d10r.hardware.MyApplication
import com.scanner.d10r.hardware.MyApplication.Companion.mContext
import com.scanner.d10r.hardware.bean.KeyValue
import com.scanner.d10r.hardware.db.Config
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scannerd.d10r.hardware.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileInputStream
import java.util.Timer
import java.util.TimerTask
import kotlin.properties.Delegates

var voiceValue = "GRBVLL20"   //解码提示音音量

var oneScanOverTime = "3000"   //一次读码超时时间
var reScanTime = "100"   //重读延迟

//EXPLVL0 普通模式
//EXPLVL2 屏幕模式
var scanSp = "EXPLVL0"   //识读偏好

//AMLENA1  开启
//AMLENA0  关闭
var aimLight = "AMLENA1"   //瞄准灯

//ILLSCN1  开启
//ILLSCN0  关闭
var externalLighting = "ILLSCN1"    //外部照明灯

var senseModeValue = "S_CMD_MS51"  //感应模式灵敏度

var model = ""  //设备型号

//SCNMOD0 电平触发
//SCNMOD2 感应模式
//SCNMOD3 连续扫描
//SCNMOD4 脉冲模式
var isScanModel = "SCNMOD0"
var isScanVoice = true
var isStartVoice = true
var isHIDChoose = false
var isWidgetChoose = true
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
var dataEncoding = "UTF-8"
var isOpenServiceReboot = true
var isEnable = true
var isAutoCleanEditText = false  //自动清空EditText
var actionShareAction = "com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION"
var actionShareData = "DATA"

var isOurApp = false //UI显示是否在我们自己的APP里面。如果是在自己的APP里面就不响应HID

//扫码模块
var scanModule = 0
var inputPath = ""

//超时时间
var overTime = 0

//广播配置
const val actionHIDWrite = "com.android.scanner.HID_WRITERDATA.ACTION"
const val actionHIDClean = "com.android.scanner.HID_CLEANDATA.ACTION"
const val actionSettings = "com.android.action.change.scan.settings" //通过广播修改通用设置

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

fun import2DB(list: List<KeyValue>) = list.forEach {
    println("这里的结果分别是》》》》   ${it.key}   ${it.value} ")
    update2Db(Config(0, it.key, it.value))
}

private fun Config.update() = MyApplication.dao.updateConfig(this)
private fun Config.insert() = MyApplication.dao.insertConfig(this)
fun getKt(key: String): String? = configs.firstOrNull { it.key == key }?.value

fun firstUpdate(data: List<Config>) {
    "firstUpdate ${data.size}".logD()
    configs.clear()
    configs.addAll(data)
    model = configs.firstOrNull { it.key == ConfigEnum.model.name }?.value.toString()

    isScanVoice =
        configs.firstOrNull { it.key == ConfigEnum.ScanVoice.name }?.value?.toBoolean() ?: true
    isStartVoice =
        configs.firstOrNull { it.key == ConfigEnum.StartVoice.name }?.value?.toBoolean() ?: true
    isScanModel = configs.firstOrNull { it.key == ConfigEnum.ScanModel.name }?.value ?: "SCNMOD0"
    oneScanOverTime = configs.firstOrNull { it.key == ConfigEnum.OneTime.name }?.value ?: "3000"
    senseModeValue =
        configs.firstOrNull { it.key == ConfigEnum.SenseScanValue.name }?.value ?: "S_CMD_MS51"

    scanModule = configs.firstOrNull { it.key == ConfigEnum.ScanModule.name }?.value?.toInt() ?: 0

    reScanTime = configs.firstOrNull { it.key == ConfigEnum.ReTime.name }?.value ?: "100"
    aimLight = configs.firstOrNull { it.key == ConfigEnum.AimLight.name }?.value ?: "AMLENA1"
    externalLighting =
        configs.firstOrNull { it.key == ConfigEnum.OutLight.name }?.value ?: "ILLSCN1"
    voiceValue = configs.firstOrNull { it.key == ConfigEnum.VoiceValue.name }?.value ?: "GRBVLL20"

    isReplaceInvisibleChar =
        configs.firstOrNull { it.key == ConfigEnum.ReplaceInvisibleChar.name }?.value?.toBoolean()
            ?: false
    isAutoCleanEditText =
        configs.firstOrNull { it.key == ConfigEnum.AUTO_CLEAN.name }?.value?.toBoolean() ?: false
    isClipBoardChoose =
        configs.firstOrNull { it.key == ConfigEnum.ClipBoardChoose.name }?.value?.toBoolean()
            ?: false
    isWidgetChoose =
        configs.firstOrNull { it.key == ConfigEnum.WidgetChoose.name }?.value?.toBoolean() ?: true
    isHIDChoose =
        configs.firstOrNull { it.key == ConfigEnum.HIDChoose.name }?.value?.toBoolean() ?: false
    isFilterSpace =
        configs.firstOrNull { it.key == ConfigEnum.FilterSpace.name }?.value?.toBoolean() ?: true
    isOpenServiceReboot =
        configs.firstOrNull { it.key == ConfigEnum.openServiceReboot.name }?.value?.toBoolean()
            ?: true
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
    inputPath = configs.firstOrNull { it.key == ConfigEnum.inputPath.name }?.value
        ?: "/sys/class/EM_BT_CONTROL/input_buf"
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

//监听模块选择
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

fun filterModuleIndex(deviceType: Int): Int {
    return when (deviceType) {
        com.scanner.d10r.hardware.bean.Constants.hr22p -> 0
        com.scanner.d10r.hardware.bean.Constants.em3100 -> 1
        com.scanner.d10r.hardware.bean.Constants.me11 -> 2
        else -> 0
    }
}

fun isDebug(): Boolean = BuildConfig.DEBUG || File("/sdcard/debug").exists()

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

val scope = MainScope()
val importList = listOf(
    ConfigEnum.Enable.name,
    ConfigEnum.ScanVoice.name,
    ConfigEnum.StartVoice.name,
    ConfigEnum.ScanModel.name,
    ConfigEnum.ReTime.name,
    ConfigEnum.OneTime.name,
    ConfigEnum.ScanSp.name,
    ConfigEnum.VoiceValue.name,
    ConfigEnum.AimLight.name,
    ConfigEnum.OutLight.name,
    ConfigEnum.SenseScanValue.name
)

fun importConfig(
    context: Context,
    data: String,
    importScan: (MutableList<KeyValue>) -> Unit,
    success: (() -> Unit) = {},
    error: (() -> Unit) = {}
) {
    try {
        scope.launch {
            val content = data.substring("CONFIG_P".length, data.length - "CONFIG_S".length)
            val list = mutableListOf<KeyValue>()
            val listScan = mutableListOf<KeyValue>()
            Json.decodeFromString<List<Pair<String, String>>>(content).forEach { pair ->
                if (pair.first in importList) {
                    listScan.add(KeyValue(pair.first, pair.second))
                } else list.add(KeyValue(pair.first, pair.second))
            }
            MyApplication.dao.deleteAllConfig()
            import2DB(list)
            importScan(listScan)
            if (scanModule == 3) import2DB(listScan)
            withContext(Dispatchers.Main) {
                firstUpdate(MyApplication.dao.selectAllConfig())
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
        val obj = Class.forName("com.scanner.d10r.hardware.util.Ascii")
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
    } catch (e: Exception) {
        (e.message + "->" + key + "=" + value).logE()
    }
}

fun setIsFirstOpen(isFirst: Boolean) =
    MyApplication.sp.edit().putBoolean("isFirst", isFirst).apply()

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
                    val value = xml.getAttributeValue(1)
                    "xml解析数据 $key=$value".logD()
                    configs.add(Config(0, key, value))
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

fun checkUsbDevice(pid: Int, vid: Int): Boolean {
    val manager = mContext.getSystemService(Context.USB_SERVICE) as UsbManager
    val deviceList = manager.deviceList
    deviceList.forEach {
        if (it.value.productId == pid && it.value.vendorId == vid) return true
    }
    return false
}


fun sendDateToUser(data: String) {
    MyApplication.application?.sendBroadcast(
        Intent(actionShareAction).putExtra(
            actionShareData, data
        )
    )
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

interface ChangeUsb {
    fun changeUsb(str: String)
}

private var changeUsb: ChangeUsb? = null
var deviceChange: String by Delegates.observable("") { _, _, new ->
    changeUsb?.changeUsb(new)
}

fun setOnChangeUsb(onchange: ((String) -> Unit)) {
    changeUsb = object : ChangeUsb {
        override fun changeUsb(str: String) {
            onchange(str)
        }
    }
}