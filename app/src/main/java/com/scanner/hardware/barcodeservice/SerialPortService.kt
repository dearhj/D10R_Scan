package com.scanner.hardware.barcodeservice

import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Parcel
import android.os.PowerManager
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.os.UserManager
import android.os.Vibrator
import android.provider.Settings
import android.system.Os
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.github.h4de5ing.base.toHexString
import com.github.h4de5ing.base.write
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.github.h4de5ing.document.initHttp
import com.github.h4de5ing.vanserialport.SerialPortIO
import com.github.h4de5ing.vanserialport.SerialPortIO.start
import com.github.h4de5ing.vanserialport.SerialPortIO.stop
import com.scanner.hardware.IRemoteCallback
import com.scanner.hardware.IRemoteService
import com.scanner.hardware.MyApplication
import com.scanner.hardware.R
import com.scanner.hardware.bean.Constants.ReadType
import com.scanner.hardware.db.ScannerData
import com.scanner.hardware.ui.ScannerFloatView
import com.scanner.hardware.ui.ScannerFloatView.OnFloatClickListener
import com.scanner.hardware.util.BackgroundInputTask
import com.scanner.hardware.util.CloseScanValue
import com.scanner.hardware.util.FileNodeCloseOpen
import com.scanner.hardware.util.Http
import com.scanner.hardware.util.JWSS
import com.scanner.hardware.util.OpenScanValue
import com.scanner.hardware.util.SerialPortName
import com.scanner.hardware.util.SoundPlayUtil
import com.scanner.hardware.util.ThreadPoolManager
import com.scanner.hardware.util.WakeLockUtil
import com.scanner.hardware.util.actionBluetoothKeyDown
import com.scanner.hardware.util.actionBluetoothKeyUp
import com.scanner.hardware.util.actionFDown
import com.scanner.hardware.util.actionFUp
import com.scanner.hardware.util.actionFloatViewHide
import com.scanner.hardware.util.actionFloatViewShow
import com.scanner.hardware.util.actionHIDClean
import com.scanner.hardware.util.actionLDown
import com.scanner.hardware.util.actionLUp
import com.scanner.hardware.util.actionRDown
import com.scanner.hardware.util.actionRUp
import com.scanner.hardware.util.actionSettings
import com.scanner.hardware.util.addPrefix
import com.scanner.hardware.util.addSuffix
import com.scanner.hardware.util.baudRate
import com.scanner.hardware.util.byteArrayReceivedChange
import com.scanner.hardware.util.characterMode
import com.scanner.hardware.util.dataEncoding
import com.scanner.hardware.util.delayed
import com.scanner.hardware.util.deletePrefix
import com.scanner.hardware.util.deletePrefixChar
import com.scanner.hardware.util.deleteSuffix
import com.scanner.hardware.util.deleteSuffixChar
import com.scanner.hardware.util.getKt
import com.scanner.hardware.util.is195T
import com.scanner.hardware.util.isAutoCleanEditText
import com.scanner.hardware.util.isBlueToothScan
import com.scanner.hardware.util.isClipBoardChoose
import com.scanner.hardware.util.isDebug
import com.scanner.hardware.util.isEnable
import com.scanner.hardware.util.isFilterSpace
import com.scanner.hardware.util.isFloatButton
import com.scanner.hardware.util.isHIDChoose
import com.scanner.hardware.util.isOurApp
import com.scanner.hardware.util.isReplaceInvisibleChar
import com.scanner.hardware.util.isScanVibrate
import com.scanner.hardware.util.isScanVoice
import com.scanner.hardware.util.isWidgetChoose
import com.scanner.hardware.util.keySwitchToTriggerChange
import com.scanner.hardware.util.keySwitchToTriggerPower
import com.scanner.hardware.util.mirrorKeyEvent
import com.scanner.hardware.util.needHandleMoto
import com.scanner.hardware.util.overTime
import com.scanner.hardware.util.overTimeArray
import com.scanner.hardware.util.positionMode
import com.scanner.hardware.util.replaceChar
import com.scanner.hardware.util.replaceInvisibleChar
import com.scanner.hardware.util.resultChange
import com.scanner.hardware.util.scanModel
import com.scanner.hardware.util.scanModule
import com.scanner.hardware.util.send2Newland
import com.scanner.hardware.util.sendDateToUser
import com.scanner.hardware.util.setSendChangeByteArray
import com.scanner.hardware.util.setSendKeyString
import com.scanner.hardware.util.setSendString
import com.scanner.hardware.util.spChange
import com.scanner.hardware.util.triggerCancelValue
import com.scanner.hardware.util.triggerValue
import com.scanner.hardware.util.updateKT
import com.scanner.hardware.util.voiceChooseIndex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset
import java.util.UUID

class SerialPortService : LifecycleService() {
    companion object {
        var readType = ReadType.SCAN
        var isContinuous = false
    }

    private var floatView: ScannerFloatView? = null
    private var clipboardManager: ClipboardManager? = null
    private var timeout: Long = 100
    private val handler = Handler(Looper.getMainLooper())
    private var mKeyguardManager: KeyguardManager? = null
    private var powerManager: PowerManager? = null
    private var isScreenOn500MS = false
    private var jwss: JWSS? = null
    private var strTwo = StringBuilder()
    private var resultByteArray: ByteArray = byteArrayOf()
    private var startDelay = true
    private var count: Long = 0
    private var startTime = 0L
    private var scannerData: String? = null
    private var userManager: UserManager? = null
    override fun onCreate() {
        super.onCreate()
        if (Os.getuid() == 1000) Http.init()
        "扫码模块:$scanModule  isWidget:$isWidgetChoose isHid:$isHIDChoose".logD()
        SoundPlayUtil.init(this, voiceChooseIndex)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mChannel = NotificationChannel(
            "barcode_scanner_id", "barcode_scanner", NotificationManager.IMPORTANCE_NONE
        )
        notificationManager.createNotificationChannel(mChannel)
        val notification =
            Notification.Builder(this, "barcode_scanner_id").setOngoing(true).setContentTitle(
                getString(
                    R.string.app_name
                )
            ).setSmallIcon(R.mipmap.icon_tm).setColor(Color.argb(255, 0, 37, 150)).setCategory(
                NotificationCompat.CATEGORY_SERVICE
            ).build()
        startForeground(1, notification)
        keySwitchToTriggerPower(true)
        keySwitchToTrigger(triggerCancelValue)
        try {
            openUart(baudRate)
            if (scanModule == 6) {
                MainScope().launch(Dispatchers.IO) {
                    send2Newland("INTERF0")//设置通信接口类型RS-232
                    delayed(10) { send2Newland("ORTSET10000") }//超时10S
                    delay(50)
                    send2Newland("ALLINV1") //默认打开反相扫码。
                    //此处延时的作用是，防止两条指令下发时间隔太短，导致扫码头回复粘连在一起，从而导致没有办法将readType状态更改过来。
                    delay(100)
                    //下面的两行代码作用时，防止在感应扫码模式下强制结束应用并清空数据后，再次打开应用，仍然是感应扫码，但UI显示是按键触发的bug
                    if (scanModel != "Induction") send2Newland("SCNMOD0")
                    else send2Newland("SCNMOD2")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        register()
        if (isFloatButton) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                        "package:$packageName"
                    )
                )
                startActivity(intent)
            } else showFloatView()
        }
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        mKeyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        userManager = getSystemService(USER_SERVICE) as UserManager
        try {
            jwss = JWSS(1234) { message ->
                "收到ws信息:$message".logD()
                if (message.startsWith("scan_start")) {
                    scanStart()
                } else if (message.startsWith("scan_stop")) {
                    if ("Async".equals(scanModel, ignoreCase = true)) keySwitchToTrigger(
                        triggerCancelValue
                    )
                } else if (message.startsWith("settings:")) {
                    try {
                        val items = message.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                        actionSettings(items[1])
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            delayed(1000) {
                setSendKeyString { keyEvent: KeyEvent ->
                    if (isEnable) {
                        mirrorKeyEvent(keyEvent, { scanStart() }) {
                            if ("Async".equals(scanModel, ignoreCase = true)) keySwitchToTrigger(
                                triggerCancelValue
                            )
                        }
                    }
                }
            }
            jwss?.start()
            initHttp(this, 4680)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openUart(baudRate: Int) {
        start(SerialPortName, baudRate) { bytes: ByteArray, length: Int ->
            if (length > 0) {
                "$length SP=${bytes.toHexString(length)}".logD()
                if (isDebug()) "字符串:${String(bytes, 0, length)} $readType".logD()
                try {
                    if (mKeyguardManager != null && mKeyguardManager!!.inKeyguardRestrictedInputMode()) return@start //没有解锁屏蔽数据
                    if (!powerManager!!.isInteractive) return@start //没有亮屏屏蔽掉数据
                    if (isScreenOn500MS) return@start //亮屏500ms内屏蔽掉数据
                } catch (ignored: Exception) {
                }
                val data = ByteArray(length)
                System.arraycopy(bytes, 0, data, 0, length)
                update(data)
                if (readType == ReadType.SCAN) { //扫码结果
                    try {
                        count = 0
                        //下面这一行作用是实现字符替换功能。
                        val codeData = replaceChar(data)
                        //下面这一行的作用是实现不可见字符替换。
                        val visibleCodeData: ByteArray =
                            if (isReplaceInvisibleChar) replaceInvisibleChar(codeData) else codeData
                        if (startDelay) {
                            handler.postDelayed({
                                startDelay = true
                                //此处strOne作用为向上层传递字符转换后的数据，
                                share2Third(
                                    String(
                                        resultByteArray,
                                        0,
                                        resultByteArray.size,
                                        Charset.forName(dataEncoding)
                                    )
                                )
                                resultByteArray = byteArrayOf()
                                //此处strTwo作用为判断原始的data数据是否是码制导入的数据
                                spChange = strTwo.toString()
                                strTwo = StringBuilder()
                            }, overTimeArray[overTime].toLong())
                        }
                        startDelay = false
                        resultByteArray = resultByteArray.plus(visibleCodeData)

                        strTwo.append(String(data, 0, data.size))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                if (bytes[0].toInt() == 0x02 && bytes[1].toInt() == 0x01 && scanModule == 6) {
                    if (bytes[12].toInt() == 0x44 && bytes[13].toInt() == 0x32)   //收到开启感应模式的回复消息
                        readType = ReadType.SCAN
                    if (bytes[11].toInt() == 0x4E && bytes[12].toInt() == 0x41 && bytes[13].toInt() == 0x31) //收到开启扫码的回复消息
                        readType = ReadType.SCAN
                }
                byteArrayReceivedChange = data
            }
        }
        setSendChangeByteArray { writeCmd(it) }
        setSendString { writeCmd(it.toByteArray()) }
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return MyBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (isBlueToothScan) ThreadPoolManager.getInstance().execute(mRB)
        keySwitchToTriggerChange()
        return START_NOT_STICKY
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            "收到广播:$action".logD()
            if (action != null) {
                when (action) {
                    actionSettings -> actionSettings(intent.getStringExtra("settings"))

                    actionFloatViewShow -> showFloatView()
                    actionFloatViewHide -> hideFloatView()
                    actionBluetoothKeyDown -> if (isBlueToothScan) scanStart()
                    actionBluetoothKeyUp -> {
                        if (!"Sync".equals(scanModel, ignoreCase = true)) keySwitchToTrigger(
                            triggerCancelValue
                        )
                    }

                    Intent.ACTION_SCREEN_ON -> {
                        "亮屏".logD()
                        keySwitchToTriggerPower(true)
                        keySwitchToTrigger(triggerCancelValue)
                        isScreenOn500MS = true
                        handler.postDelayed({ isScreenOn500MS = false }, 2000)
                    }

                    Intent.ACTION_SCREEN_OFF -> {
                        "息屏".logD()
                        keySwitchToTrigger(triggerCancelValue)
                        keySwitchToTriggerPower(false)
                        isContinuous = false
                    }

                    Intent.ACTION_USER_PRESENT -> "解锁".logD()
                    else -> {
                        val km = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                        val ok = !km.isKeyguardLocked
                        if (ok && isEnable) {
                            if (action == actionLDown || action == actionRDown || action == actionFDown) scanStart()
                            if (action == actionLUp || action == actionRUp || action == actionFUp) if ("Async".equals(
                                    scanModel, ignoreCase = true
                                )
                            ) keySwitchToTrigger(triggerCancelValue)
                        }
                    }
                }
            }
        }
    }

    private fun scanStart() {
        keySwitchToTrigger(triggerCancelValue)
        if ("Async".equals(scanModel, ignoreCase = true)) { //同步 点击就取消
            isContinuous = false
            handleMotoSend(true)
            keySwitchToTrigger(triggerValue)
//            handler.postDelayed({ keySwitchToTrigger(triggerCancelValue) }, 3000)
        } else if ("Sync".equals(scanModel, ignoreCase = true)) { //异步点击后 直到扫描结果才取消，如果3S内没有扫到就取消扫描
            isContinuous = false
            handleMotoSend(true)
            handler.removeCallbacks(runnable1)
            keySwitchToTrigger(triggerValue)
            handler.postDelayed(runnable1, 3000)
        } else if ("Continuous".equals(scanModel, ignoreCase = true)) { //连续扫描
            continuous()
        }
    }

    private val runnable1 = Runnable { keySwitchToTrigger(triggerCancelValue) }
    private fun continuous() {
        isContinuous = !isContinuous
        handler.post(runnable)
        if (isContinuous) WakeLockUtil.acquire() else WakeLockUtil.release()
        timeout = overTimeArray[overTime].toLong()
        if (is195T() || scanModule == 2) timeout += 100
        if (!isContinuous) keySwitchToTrigger(triggerCancelValue)
    }

    /**
     * 1 摩托一维 se655
     * 2 摩托二维 2707
     * 3 霍尼一维 4313
     * 4 霍尼二维 3680 4680
     * 5 图腾 483
     * 6 新大陆 E660 N1 取消上一次扫码休眠10ms才能一直扫码，但是会有扫码灯闪烁问题
     *
     *
     * 部分扫码头需要取消上一次扫码在进行下一次扫码，否则出现无法一直扫的问题
     * 图腾 霍尔
     * 部分扫码头不能取消上一次扫码，否则会出现断码问题，会出现乱码
     * 摩托二维
     */
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if (isContinuous && isEnable && scanModel == "Continuous") {
                handleMotoSend(true)
                if (scanModule == 4 || scanModule == 5) {
                    keySwitchToTrigger(triggerCancelValue)
//                    if (scanModule == 6) SystemClock.sleep(10)
                }
                keySwitchToTrigger(triggerValue)
                handler.postDelayed(this, timeout)
                if (scanModule == 2) {
                    if (count > 50) {
                        keySwitchToTrigger(triggerCancelValue)
                        count = 0
                    }
                    count++
                }
            }
        }
    }
    private val wakeMotoRunnable: Runnable = object : Runnable {
        override fun run() {
            write(byteArrayOf(0x00))
            handler.postDelayed(this, 100)
        }
    }

    private fun handleMotoSend(send: Boolean) {
        if (needHandleMoto()) {
            if (send) handler.post(wakeMotoRunnable) else handler.removeCallbacks(wakeMotoRunnable)
        }
    }

    private val mRB = Runnable { doBluetoothConnect() }
    private fun keySwitchToTrigger(value: Int) {

        synchronized(this) {
            val string: String
            val fileNode = FileNodeCloseOpen
            readType = ReadType.SCAN
            if (value == triggerValue) {
                if (System.currentTimeMillis() - startTime <= overTimeArray[overTime] + 50 && !"Continuous".equals(
                        scanModel, ignoreCase = true
                    )
                ) return
                startTime = System.currentTimeMillis()
                string = OpenScanValue
            } else string = CloseScanValue
            File(fileNode).write(string)
        }
    }

    private fun register() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(actionLDown)
        intentFilter.addAction(actionLUp)
        intentFilter.addAction(actionRDown)
        intentFilter.addAction(actionRUp)
        intentFilter.addAction(actionFDown)
        intentFilter.addAction(actionFUp)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_USER_PRESENT)
        intentFilter.addAction(actionFloatViewShow)
        intentFilter.addAction(actionFloatViewHide)
        intentFilter.addAction(actionBluetoothKeyDown)
        intentFilter.addAction(actionBluetoothKeyUp)
        intentFilter.addAction(actionSettings)
        val listenToBroadcastsFromOtherApps = false
        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
            ContextCompat.RECEIVER_EXPORTED
        } else {
            ContextCompat.RECEIVER_NOT_EXPORTED
        }
        registerReceiver(receiver, intentFilter, receiverFlags)
    }

    private val interfaces = RemoteCallbackList<IRemoteCallback>()

    private inner class MyBinder : IRemoteService.Stub() {
        override fun registerCallback(receiveListener: IRemoteCallback) {
            interfaces.register(receiveListener)
        }

        override fun unregisterCallback(receiveListener: IRemoteCallback) {
            interfaces.unregister(receiveListener)
        }

        override fun setSettings(key: String, value: String) {
            updateKT(key, value)
        }

        override fun getSettings(key: String): String {
            return getKt(key) ?: ""
        }

        override fun setCode(data: ByteArray) {
            writeCmd(data)
        }

        @Throws(RemoteException::class)
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            return super.onTransact(code, data, reply, flags)
        }
    }

    private fun update(data: ByteArray) {
        for (i in 0..<interfaces.beginBroadcast()) {
            interfaces.getBroadcastItem(i)?.apply {
                try {
                    valueChanged(data)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        interfaces.finishBroadcast()
    }

    private fun doBluetoothConnect() {
        try {
            (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter?.apply {
                "开启蓝牙".logD()
                if (!isEnabled) {
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    if (ActivityCompat.checkSelfPermission(
                            this@SerialPortService, "android.permission.BLUETOOTH_CONNECT"
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    startActivity(intent)
                }
                bondedDevices?.apply {
                    if (size > 0) {
                        for (device in this) {
                            if (device.name == "BLE_Handle" || device.name.startsWith("BLE_")) {
                                val uuid = device.uuids
                                val mUuid = UUID.fromString(uuid[0].toString())
                                val socket: BluetoothSocket
                                try {
                                    socket = device.createRfcommSocketToServiceRecord(mUuid)
                                    socket.connect()
                                    socket.close()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                break
                            }
                        }
                    } else {
                        Looper.prepare()
                        Toast.makeText(
                            applicationContext, R.string.str_bluetooth_prompt, Toast.LENGTH_LONG
                        ).show()
                        Looper.loop()
                    }
                }
            }
        } catch (_: Exception) {
        }
    }

    private fun showFloatView() {
        floatView = ScannerFloatView.getInstance()
        floatView?.showFloatView()
        floatView?.setOnFloatClickListener(object : OnFloatClickListener {
            override fun onCancelClick(view: View) {
                if ("Async".equals(scanModel, ignoreCase = true)) keySwitchToTrigger(
                    triggerCancelValue
                )
            }

            override fun onClick() {
                if (isEnable) {
                    keySwitchToTrigger(triggerCancelValue)
                    if ("Async".equals(scanModel, ignoreCase = true)) {
                        handleMotoSend(true)
                        keySwitchToTrigger(triggerValue)
                    } else if ("Sync".equals(scanModel, ignoreCase = true)) {
                        handleMotoSend(true)
                        handler.removeCallbacks(runnable1)
                        keySwitchToTrigger(triggerValue)
                        handler.postDelayed(runnable1, 3000)
                    } else if ("Continuous".equals(scanModel, ignoreCase = true)) {
                        continuous()
                    }
                } else {
                    isContinuous = false
                }
            }
        })
    }

    private fun hideFloatView() {
        floatView?.hideFloatView()
    }

    private fun share2Third(data2: String) {
        var data = data2
        handler.removeCallbacks(runnable1)
        handleMotoSend(false)
        keySwitchToTrigger(triggerCancelValue)
        if (isScanVoice) SoundPlayUtil.play()
        if (isScanVibrate) vibrate()
        //去除首尾空格
        if (isFilterSpace) data = data.trim()
        // 字符串截取
        if (positionMode) {   //位置模式
            try {
                data = data.substring(deletePrefix - 1, deleteSuffix.coerceAtMost(data.length))
            } catch (e: Exception) {
                data = ""
                "删除数据越界了".logE()
            }
        }
        if (characterMode) {   //字符模式
            try {
                val startIndex = data.indexOf(deletePrefixChar)
                var stopIndex = data.indexOf(deleteSuffixChar, startIndex + 1)
                if (stopIndex == -1 && deletePrefixChar == deleteSuffixChar) stopIndex = startIndex
                data = data.substring(startIndex, stopIndex + 1)
            } catch (ignored: Exception) {
                data = ""
                "删除数据越界了".logE()
            }
        }
        //添加前后缀
        data = addPrefix + data
        data += addSuffix
        if (isAutoCleanEditText) sendBroadcast(Intent(actionHIDClean))
        if ((isHIDChoose || isWidgetChoose) && !isOurApp) {
            synchronized(this) { BackgroundInputTask().execute(data) }
        }
        sendDateToUser(data)
        if (isClipBoardChoose) clipboardManager?.setPrimaryClip(ClipData.newPlainText("text", data))
        resultChange = data
        jwss?.updateResult(data)
        scannerData = data
        handler.post(saveRunnable)
    }

    private val saveRunnable = Runnable { insertData() }
    private fun insertData() {
        val userName = if (isDebug()) userManager?.userName + ":" else ""
        MyApplication.dao.insertData(ScannerData(0, userName + scannerData))
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(longArrayOf(100, 400), -1)
    }

    private fun writeCmd(data: ByteArray) {
        "发送命令:${data.toHexString()}".logD()
        readType = ReadType.CMD
        SerialPortIO.write(data)
    }

    private fun write(data: ByteArray) {
        "唤醒命令:${data.toHexString()}".logD()
        SerialPortIO.write(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
        SoundPlayUtil.release()
        unregisterReceiver(receiver)
        ThreadPoolManager.getInstance().remove(mRB)
        stopForeground(true)
    }
}