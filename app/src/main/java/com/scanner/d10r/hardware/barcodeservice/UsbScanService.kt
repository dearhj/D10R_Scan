package com.scanner.d10r.hardware.barcodeservice

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.UserManager
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.nlscan.nlsdk.NLDevice
import com.nlscan.nlsdk.NLDeviceStream
import com.scanner.d10r.hardware.MyApplication
import com.scanner.d10r.hardware.db.ScannerData
import com.scanner.d10r.hardware.util.BackgroundInputTask
import com.scanner.d10r.hardware.util.actionHIDClean
import com.scanner.d10r.hardware.util.actionSettings
import com.scanner.d10r.hardware.util.addPrefix
import com.scanner.d10r.hardware.util.addSuffix
import com.scanner.d10r.hardware.util.characterMode
import com.scanner.d10r.hardware.util.dataEncoding
import com.scanner.d10r.hardware.util.deletePrefix
import com.scanner.d10r.hardware.util.deletePrefixChar
import com.scanner.d10r.hardware.util.deleteSuffix
import com.scanner.d10r.hardware.util.deleteSuffixChar
import com.scanner.d10r.hardware.util.deviceChange
import com.scanner.d10r.hardware.util.isAutoCleanEditText
import com.scanner.d10r.hardware.util.isClipBoardChoose
import com.scanner.d10r.hardware.util.isFilterSpace
import com.scanner.d10r.hardware.util.isHIDChoose
import com.scanner.d10r.hardware.util.isOurApp
import com.scanner.d10r.hardware.util.isReplaceInvisibleChar
import com.scanner.d10r.hardware.util.isWidgetChoose
import com.scanner.d10r.hardware.util.positionMode
import com.scanner.d10r.hardware.util.replaceChar
import com.scanner.d10r.hardware.util.replaceInvisibleChar
import com.scanner.d10r.hardware.util.resultChange
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.sendDateToUser
import com.scanner.d10r.hardware.util.setSPChange
import com.scannerd.d10r.hardware.R
import com.tsingtengms.scanmodule.libhidpos.HIDManager
import com.tsingtengms.scanmodule.libhidpos.callback.HIDDataListener
import com.tsingtengms.scanmodule.libhidpos.callback.HIDOpenListener
import com.tsingtengms.scanmodule.libhidpos.constant.ConnectionStatus
import com.tsingtengms.scanmodule.libhidpos.util.HexUtil
import com.tsingtengms.scanmodule.libhidpos.util.StringFormat
import java.nio.charset.Charset

class UsbScanService : LifecycleService() {

    private var clipboardManager: ClipboardManager? = null
    private val handler = Handler(Looper.getMainLooper())
    private var mKeyguardManager: KeyguardManager? = null
    private var powerManager: PowerManager? = null
    private var scannerData: String? = null
    private var userManager: UserManager? = null

    private var mHIDOpenListener: HIDOpenListener? = null
    private var mHidDataListener: HIDDataListener? = null

    companion object {
        val ds: NLDeviceStream = NLDevice(NLDeviceStream.DevClass.DEV_COMPOSITE)
        var usbOpenChecked = false
        var ydOpenChecked = false
    }

    private var deviceFlag = false

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        "扫码模块:$scanModule  isWidget:$isWidgetChoose isHid:$isHIDChoose".logD()

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

        register()
        if (scanModule == 1 || scanModule == 2) onOpenDevice()
        else if (scanModule == 3) openYdScanModule()
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        mKeyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        userManager = getSystemService(USER_SERVICE) as UserManager

        //监听到模块改变了
        setSPChange {
            //26f1:8803   易度模块
            println("模块改变了，，， 切换到了模块 $it")
            if (it != "3") {
                closeYdScanModule()
                onOpenDevice()
            } else {
                onCloseDevice()
                openYdScanModule()
            }
        }
    }

    private fun openYdScanModule() {
        if (!ydOpenChecked) {
            initListener()
            HIDManager.getInstance().openHID(this, mHIDOpenListener, mHidDataListener)
        }
    }

    private fun closeYdScanModule() {
        if (ydOpenChecked) {
            HIDManager.getInstance().closeHID()
        }
    }


    private fun initListener() {
        //回调数据有关页面展示的请在主线程进行。
        mHIDOpenListener = object : HIDOpenListener {
            override fun onSuccess(status: ConnectionStatus) {
                if (status == ConnectionStatus.USB_CONNECT)
                    println("连接usb成功")
                else if (status == ConnectionStatus.COMMUNICATION_OPEN) {
                    ydOpenChecked = true
                    println("通讯建立成功")
                    //建立连接后，应该修改连续模式识读间隔时长为0ms
                    HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MARR000"))
                }
            }

            override fun onError(status: ConnectionStatus) {
                if (status == ConnectionStatus.USB_DISCONNECT) {
                    //USB断开
                    ydOpenChecked = false
                    println("断开USB")
                } else if (status == ConnectionStatus.COMMUNICATION_CLOSE) {
                    //服务销毁
                    ydOpenChecked = false
                    println("通讯断开")
                }
            }
        }
        mHidDataListener = HIDDataListener { a: Byte, bytes: ByteArray?, length: Int ->
            //去掉默认的0x0D后缀
            println("这里获取到的数据是？？？？   $a  $length   ${bytes?.get(0)}   ${bytes?.get(1)}")
            if (bytes != null) {
                if (!(length == 2 && bytes[0] == (-112).toByte() && bytes[1] == 0.toByte()))
                    share2Third(bytes.copyOfRange(0, length - 1))
            }
            val dataMessage =
                StringFormat.bytes2String(bytes, "UTF-8")
            if (!TextUtils.isEmpty(dataMessage)) {
                println("这里收到了扫描数据，结果是？   ￥￥ $dataMessage")
            }
        }
    }

    private fun onOpenDevice() {
        if (!usbOpenChecked) {
            if (!ds.open(this, object : NLDeviceStream.NLUsbListener {
                    @SuppressLint("CheckResult")
                    override fun actionUsbPlug(event: Int) {
                        if (event == 1) {
                            //设备插入
                        }
                    }

                    override fun actionUsbRecv(recvBuff: ByteArray, len: Int) {
                        if (usbOpenChecked) {
                            if (recvBuff[0] != 2.toByte() && recvBuff[1] != 1.toByte() && recvBuff[2] != 48.toByte()) {
                                //去掉默认的0x0D后缀
                                share2Third(recvBuff.copyOfRange(0, len - 1))
                            }
                        }
                    }
                })) {
                usbOpenChecked = false
                return
            }
            usbOpenChecked = true
            try {
                //默认打开重读延迟100ms
                val reScanTimeStatusOff = ds.getConfig("RRDENA*").contains("0")
                println("设备打开   $reScanTimeStatusOff")
                if (reScanTimeStatusOff) {
                    ds.setConfig("@RRDENA1")
                    ds.setConfig("@RRDDUR100")
                }
            } catch (_: Exception) {
            }
        }
    }

    fun onCloseDevice() {
        if (usbOpenChecked) {
            ds.close()
            println("设备关闭")
            usbOpenChecked = false
        }
    }


    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            "收到广播:$action".logD()
            if (action != null) {
                when (action) {
                    actionSettings -> actionSettings(intent.getStringExtra("settings"))

                    Intent.ACTION_SCREEN_ON -> "亮屏".logD()

                    Intent.ACTION_SCREEN_OFF -> "息屏".logD()

                    Intent.ACTION_USER_PRESENT -> "解锁".logD()

                    UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                        val device =
                            intent.getParcelableExtra(UsbManager.EXTRA_DEVICE) as UsbDevice?
                        if (device != null) {
                            if (device.vendorId == 7851 && (device.productId == 6690 || device.productId == 34) && (scanModule == 1 || scanModule == 2)) {
                                onOpenDevice()
                                deviceFlag = false
                            }
                            if (device.vendorId == 9969 && device.productId == 34819 && scanModule == 3) {
                                openYdScanModule()
                                deviceFlag = false
                            }
                            println("这里USB设备信息的vid是》》》   ${device.vendorId}    ${device.productId}")
                        }
                    }

                    UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                        val device =
                            intent.getParcelableExtra(UsbManager.EXTRA_DEVICE) as UsbDevice?
                        println("这里USB设备信息的vid是》》》   ${device?.vendorId}    ${device?.productId}") // 9969    34819
                        if (device != null) {
                            if (device.vendorId == 7851 && (device.productId == 6690 || device.productId == 34) && scanModule != 3) {
                                if (!deviceFlag) deviceChange =
                                    System.currentTimeMillis().toString() //通知设备已经被拔出
                                deviceFlag = true
                                onCloseDevice()
                            }
                            if (scanModule == 3 && device.vendorId == 9969 && device.productId == 34819) {
                                ////关闭连接
                                if (!deviceFlag) deviceChange =
                                    System.currentTimeMillis().toString() //通知设备已经被拔出
                                deviceFlag = true
                                closeYdScanModule()
                                ydOpenChecked = false
                            }
                        }
                    }

                }
            }
        }
    }

    private fun register() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_USER_PRESENT)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        intentFilter.addAction(actionSettings)

        val listenToBroadcastsFromOtherApps = false
        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
            ContextCompat.RECEIVER_EXPORTED
        } else {
            ContextCompat.RECEIVER_NOT_EXPORTED
        }
        registerReceiver(receiver, intentFilter, receiverFlags)
    }


    private fun share2Third(dataByte: ByteArray) {
        //下面这一行作用是实现字符替换功能。
        val codeData = replaceChar(dataByte)
        //下面这一行的作用是实现不可见字符替换。
        val visibleCodeData: ByteArray =
            if (isReplaceInvisibleChar) replaceInvisibleChar(codeData) else codeData
        var data = String(visibleCodeData, Charset.forName(dataEncoding))
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
        scannerData = data
        handler.post(saveRunnable)
    }

    private val saveRunnable = Runnable { insertData() }
    private fun insertData() {
        MyApplication.dao.insertData(ScannerData(0, scannerData ?: ""))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        stopForeground(true)
    }
}