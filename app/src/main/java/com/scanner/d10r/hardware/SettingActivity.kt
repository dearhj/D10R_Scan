package com.scanner.d10r.hardware

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.Spinner
import com.github.h4de5ing.base.write
import com.github.h4de5ing.baseui.logI
import com.github.h4de5ing.document.getMyIp
import com.github.h4de5ing.filepicker.DialogUtils.selectDir
import com.scanner.d10r.hardware.barcodeservice.UsbScanService
import com.scanner.d10r.hardware.barcodeservice.UsbScanService.Companion.ds
import com.scanner.d10r.hardware.base.BaseBackActivity
import com.scanner.d10r.hardware.bean.KeyValue
import com.scanner.d10r.hardware.enums.ConfigEnum
import com.scanner.d10r.hardware.ui.ActionFragment
import com.scanner.d10r.hardware.ui.BroadcastDialogFragment
import com.scanner.d10r.hardware.ui.CharReplaceDialog
import com.scanner.d10r.hardware.ui.DeleteCharDialogFragment
import com.scanner.d10r.hardware.ui.InputDataScanType
import com.scanner.d10r.hardware.util.actionPrefixKeyCode
import com.scanner.d10r.hardware.util.actionPrefixToggle
import com.scanner.d10r.hardware.util.actionSuffixKeyCode
import com.scanner.d10r.hardware.util.actionSuffixToggle
import com.scanner.d10r.hardware.util.addPrefix
import com.scanner.d10r.hardware.util.addSuffix
import com.scanner.d10r.hardware.util.aimLight
import com.scanner.d10r.hardware.util.choseSingleDialog
import com.scanner.d10r.hardware.util.confirmDialog
import com.scanner.d10r.hardware.util.dataEncoding
import com.scanner.d10r.hardware.util.editDialog
import com.scanner.d10r.hardware.util.externalLighting
import com.scanner.d10r.hardware.util.firstUpdate
import com.scanner.d10r.hardware.util.importConfig
import com.scanner.d10r.hardware.util.isAutoCleanEditText
import com.scanner.d10r.hardware.util.isClipBoardChoose
import com.scanner.d10r.hardware.util.isEnable
import com.scanner.d10r.hardware.util.isFilterSpace
import com.scanner.d10r.hardware.util.isHIDChoose
import com.scanner.d10r.hardware.util.isOpenServiceReboot
import com.scanner.d10r.hardware.util.isReplaceInvisibleChar
import com.scanner.d10r.hardware.util.isScanModel
import com.scanner.d10r.hardware.util.isScanVoice
import com.scanner.d10r.hardware.util.isStartVoice
import com.scanner.d10r.hardware.util.isWidgetChoose
import com.scanner.d10r.hardware.util.oneScanOverTime
import com.scanner.d10r.hardware.util.parserXML
import com.scanner.d10r.hardware.util.reScanTime
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.scanSp
import com.scanner.d10r.hardware.util.senseModeValue
import com.scanner.d10r.hardware.util.setIsFirstOpen
import com.scanner.d10r.hardware.util.setOnChangeUsb
import com.scanner.d10r.hardware.util.voiceValue
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.ActivitySettingBinding
import com.tsingtengms.scanmodule.libhidpos.HIDManager
import com.tsingtengms.scanmodule.libhidpos.util.HexUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class SettingActivity : BaseBackActivity() {
    private lateinit var binding: ActivitySettingBinding
    private var activityFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (scanModule == 2 || scanModule == 3) binding.rlScanPre.visibility = View.GONE
        if (scanModule == 3) {
            binding.rlCouScan.visibility = View.VISIBLE
            binding.enableScan.visibility = View.GONE
        }
        initData1()
        initView1()

        if (scanModule != 3) initData()
        setOnChangeUsb {
            if (activityFlag) {
                showToast(getString(R.string.connectError))
                Handler(Looper.getMainLooper()).postDelayed({ finish() }, 1000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        activityFlag = false
    }

    override fun onResume() {
        super.onResume()
        activityFlag = true
    }

    private fun initData1() {
        binding.sbHidChoose.isChecked = isHIDChoose
        binding.sbOpenWidget.isChecked = isWidgetChoose
        binding.sbClipboardChoose.isChecked = isClipBoardChoose
        binding.sbCleanEdittext.isChecked = isAutoCleanEditText
        binding.sbOpenBoot.isChecked = isOpenServiceReboot
        binding.spaceFilterBtn.isChecked = isFilterSpace
        binding.replaceInvisibleCharBtn.isChecked = isReplaceInvisibleChar

        binding.tvScanEncodingSummaryText.text = dataEncoding
        binding.tvPrefixSummaryText.text = addPrefix
        binding.tvSuffixSummaryText.text = addSuffix

        try {
            if (actionPrefixKeyCode != 0 && actionPrefixToggle) binding.tvActionPrefixSummaryText.text =
                KeyEvent.keyCodeToString(actionPrefixKeyCode)
            else binding.tvActionPrefixSummaryText.text = getString(R.string.no_setting)
            if (actionSuffixKeyCode != 0 && actionSuffixToggle) binding.tvActionSuffixSummaryText.text =
                KeyEvent.keyCodeToString(actionSuffixKeyCode)
            else binding.tvActionSuffixSummaryText.text = getString(R.string.no_setting)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (scanModule == 3) {
            binding.sbEnable.isChecked = isEnable
            binding.sbVoice.isChecked = isScanVoice
            binding.sbStartVoice.isChecked = isStartVoice
        }
    }

    private fun initData() {
        MainScope().launch(Dispatchers.IO) {
            if (UsbScanService.usbOpenChecked) {
                try {
                    isEnable = ds.getConfig("SCNENA*").contains("1")
                    isScanVoice = ds.getConfig("GRBENA*").contains("1")
                    isStartVoice = ds.getConfig("PWBENA*").contains("1")
                    withContext(Dispatchers.Main) {
                        binding.sbEnable.isChecked = isEnable
                        binding.sbVoice.isChecked = isScanVoice
                        binding.sbStartVoice.isChecked = isStartVoice
                    }
                    isScanModel = ds.getConfig("SCNMOD*")
                    reScanTime = ds.getConfig("RRDDUR*").substring(6)
                    oneScanOverTime = ds.getConfig("ORTSET*").substring(6)
                    if (scanModule == 1) scanSp = ds.getConfig("EXPLVL*")
                    voiceValue = ds.getConfig("GRBVLL*")
                    aimLight = ds.getConfig("AMLENA*")
                    externalLighting = ds.getConfig("ILLSCN*")
                    withContext(Dispatchers.Main) { initView() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast(getString(R.string.connectError))
                    Handler(Looper.getMainLooper()).postDelayed({ finish() }, 1000)
                }
            }
        }
    }


    private fun initView() {
        //解码提示音
        binding.sbVoice.changed { isChecked ->
            if (isChecked) {
                if (!ds.setConfig("@GRBENA1")) {
                    showToast(getString(R.string.TextConfigErr))
                } else isScanVoice = true
            } else {
                if (!ds.setConfig("@GRBENA0")) {
                    showToast(getString(R.string.TextConfigErr))
                } else isScanVoice = false
            }
        }
        //启用扫码
        binding.sbEnable.changed { isChecked ->
            if (isChecked) {
                if (!ds.setConfig("@SCNENA1")) {
                    showToast(getString(R.string.TextConfigErr))
                } else isEnable = true
            } else {
                if (!ds.setConfig("@SCNENA0")) {
                    showToast(getString(R.string.TextConfigErr))
                } else isEnable = false
            }
        }
        //开机提示音
        binding.sbStartVoice.changed { isChecked ->
            if (isChecked) {
                if (!ds.setConfig("@PWBENA1")) {
                    showToast(getString(R.string.TextConfigErr))
                }
            } else {
                if (!ds.setConfig("@PWBENA0")) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }


        //恢复默认参数  FACDEF
        binding.btnSetFactory.setOnClickListener {
            confirmDialog(this, getString(R.string.str_restore_factory)) {
                if (!ds.setConfig("@FACDEF")) {
                    showToast(getString(R.string.TextConfigErr))
                } else {
                    //重读延迟打开100ms
                    ds.setConfig("@RRDENA1")
                    ds.setConfig("@RRDDUR100")
                    if (!isEnable) {
                        ds.setConfig("@SCNENA1")
                    } //这里很奇怪，恢复出厂设置时，没有办法将启用扫码恢复，这里只能手动恢复。
                    reViewAfterReset()
                    rereadConfigurationItems()
                    firstUpdate(MyApplication.dao.selectAllConfig())
                    initData1()
                }
            }
        }

        binding.rlReScan.setOnClickListener {
            val select = when (reScanTime) {
                "100" -> 0
                "200" -> 1
                "500" -> 2
                "1000" -> 3
                "2000" -> 4
                "3000" -> 5
                else -> 0
            }
            val list = arrayOf(
                "100ms", "200ms", "500ms", "1000ms", "2000ms", "3000ms"
            )
            choseSingleDialog(this, getString(R.string.re_scan_mode), list, select) { _, witch ->
                val model = when (witch) {
                    0 -> "@RRDDUR100"
                    1 -> "@RRDDUR200"
                    2 -> "@RRDDUR500"
                    3 -> "@RRDDUR1000"
                    4 -> "@RRDDUR2000"
                    5 -> "@RRDDUR3000"
                    else -> "@RRDDUR100"
                }
                reScanTime = model.substring(7)
                if (!ds.setConfig(model)) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }
        //扫描模式选择
        binding.rlChooseScanMode.setOnClickListener {
            val select = if (scanModule == 1) {
                when (isScanModel) {
                    "SCNMOD0" -> 0
                    "SCNMOD2" -> 1
                    "SCNMOD3" -> 2
                    "SCNMOD4" -> 3
                    else -> 0
                }
            } else {
                when (isScanModel) {
                    "SCNMOD0" -> 0
                    "SCNMOD2" -> 1
                    "SCNMOD3" -> 2
                    else -> 0
                }
            }
            val list = if (scanModule == 1) {
                arrayOf(
                    getString(R.string.TextLevelTriggerMode),
                    getString(R.string.TextSenseMode),
                    getString(R.string.TextContinuousMode),
                    getString(R.string.TextPulseMode),
                )
            } else {
                arrayOf(
                    getString(R.string.TextLevelTriggerMode),
                    getString(R.string.TextSenseMode),
                    getString(R.string.TextContinuousMode)
                )
            }
            choseSingleDialog(
                this, getString(R.string.scan_mode_choose), list, select
            ) { _, witch ->
                val mode = when (witch) {
                    0 -> "@SCNMOD0"
                    1 -> "@SCNMOD2"
                    2 -> "@SCNMOD3"
                    3 -> "@SCNMOD4"
                    else -> "@SCNMOD0"
                }
                isScanModel = mode.substring(1)
                if (!ds.setConfig(mode)) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }

        //一次读码超时
        binding.rlOneScanTimeout.setOnClickListener {
            val select = when (oneScanOverTime) {
                "3000" -> 0
                "5000" -> 1
                "10000" -> 2
                "20000" -> 3
                else -> 0
            }
            val list = arrayOf("3000ms", "5000ms", "10000ms", "20000ms")
            choseSingleDialog(
                this, getString(R.string.one_scan_timeout), list, select
            ) { _, witch ->
                val mode = when (witch) {
                    0 -> "@ORTSET3000"
                    1 -> "@ORTSET5000"
                    2 -> "@ORTSET10000"
                    3 -> "@ORTSET20000"
                    else -> "@ORTSET3000"
                }
                oneScanOverTime = mode.substring(7)
                if (!ds.setConfig(mode)) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }

        //识读偏好
        binding.rlScanPre.setOnClickListener {
            val select = when (scanSp) {
                "EXPLVL0" -> 0
                "EXPLVL2" -> 1
                else -> 0
            }
            val list = arrayOf(getString(R.string.normal), getString(R.string.screen))
            choseSingleDialog(
                this, getString(R.string.scan_sp), list, select
            ) { _, witch ->
                val mode = when (witch) {
                    0 -> "@EXPLVL0"
                    1 -> "@EXPLVL2"
                    else -> "@EXPLVL0"
                }
                scanSp = mode.substring(1)
                if (!ds.setConfig(mode)) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }

        //解码提示音音量
        binding.rlScanVoice.setOnClickListener {
            if (isScanVoice) {
                val select = when (voiceValue) {
                    "GRBVLL20" -> 0
                    "GRBVLL10" -> 1
                    "GRBVLL2" -> 2
                    else -> 0
                }
                val list = arrayOf(
                    getString(R.string.scan_voice_h),
                    getString(R.string.scan_voice_m),
                    getString(R.string.scan_voice_l)
                )
                choseSingleDialog(
                    this, getString(R.string.scan_voice_value), list, select
                ) { _, witch ->
                    val mode = when (witch) {
                        0 -> "@GRBVLL20"
                        1 -> "@GRBVLL10"
                        2 -> "@GRBVLL2"
                        else -> "@GRBVLL20"
                    }
                    voiceValue = mode.substring(1)
                    if (!ds.setConfig(mode)) {
                        showToast(getString(R.string.TextConfigErr))
                    }
                }
            } else showToast(getString(R.string.voice_off))
        }

        //瞄准灯设置
        binding.rlLightAim.setOnClickListener {
            val select = when (aimLight) {
                "AMLENA1" -> 0
                "AMLENA0" -> 1
                else -> 0
            }
            val list = arrayOf(
                getString(R.string.open),
                getString(R.string.close)
            )
            choseSingleDialog(
                this, getString(R.string.light_am), list, select
            ) { _, witch ->
                val mode = when (witch) {
                    0 -> "@AMLENA1"
                    1 -> "@AMLENA0"
                    else -> "@AMLENA1"
                }
                aimLight = mode.substring(1)
                if (!ds.setConfig(mode)) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }

        //外部照明灯设置
        binding.rlLightBt.setOnClickListener {
            val select = when (externalLighting) {
                "ILLSCN1" -> 0
                "ILLSCN0" -> 1
                else -> 0
            }
            val list = arrayOf(
                getString(R.string.open),
                getString(R.string.close)
            )
            choseSingleDialog(
                this, getString(R.string.light_bt), list, select
            ) { _, witch ->
                val mode = when (witch) {
                    0 -> "@ILLSCN1"
                    1 -> "@ILLSCN0"
                    else -> "@ILLSCN1"
                }
                externalLighting = mode.substring(1)
                if (!ds.setConfig(mode)) {
                    showToast(getString(R.string.TextConfigErr))
                }
            }
        }
    }

    private fun reViewAfterReset() {
        MainScope().launch(Dispatchers.Main) { updateView() }
    }

    private fun updateView() {
        if (!isEnable) isEnable = true
        if (!isScanVoice) isScanVoice = true
        if (!isStartVoice) isStartVoice = true
        binding.sbEnable.isChecked = isEnable
        binding.sbVoice.isChecked = isScanVoice
        binding.sbStartVoice.isChecked = isStartVoice
        isScanModel = "SCNMOD0"
        reScanTime = "100"
        oneScanOverTime = "3000"
        scanSp = "EXPLVL0"
        voiceValue = "GRBVLL20"
        aimLight = "AMLENA1"
        externalLighting = "ILLSCN1"
    }

    private fun initView1() {
        if (scanModule == 3) {
            binding.sbVoice.changed { isChecked ->
                if (isChecked) HIDManager.getInstance()
                    .sendData(HexUtil.stringToAscii("S_CMD_0403"))
                else HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_0402"))
                update(ConfigEnum.ScanVoice.name, "$isChecked")
            }
            binding.sbStartVoice.changed { isChecked ->
                if (isChecked) HIDManager.getInstance()
                    .sendData(HexUtil.stringToAscii("S_CMD_0401"))
                else HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_0400"))
                update(ConfigEnum.StartVoice.name, "$isChecked")
            }
            binding.rlChooseScanMode.setOnClickListener {
                val select =
                    when (isScanModel) {
                        "SCNMOD0" -> 0
                        "SCNMOD2" -> 1
                        "SCNMOD3" -> 2
                        else -> 0
                    }
                val list =
                    arrayOf(
                        getString(R.string.TextLevelTriggerMode),
                        getString(R.string.TextSenseMode),
                        getString(R.string.TextContinuousMode)
                    )
                choseSingleDialog(
                    this, getString(R.string.scan_mode_choose), list, select
                ) { _, witch ->
                    when (witch) {
                        0 -> {
                            isScanModel = "SCNMOD0"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MT00"))
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MT10"))
                        }

                        1 -> {
                            isScanModel = "SCNMOD2"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_020F"))
                        }

                        2 -> {
                            isScanModel = "SCNMOD3"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_020E"))
                        }

                        else -> {
                            isScanModel = "SCNMOD0"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MT00"))
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MT10"))
                        }
                    }
                    update(ConfigEnum.ScanModel.name, isScanModel)
                }
            }
            binding.rlOneScanTimeout.setOnClickListener {
                val select = when (oneScanOverTime) {
                    "3000" -> 0
                    "5000" -> 1
                    "10000" -> 2
                    "20000" -> 3
                    else -> 0
                }
                val list = arrayOf("3000ms", "5000ms", "10000ms", "20000ms")
                choseSingleDialog(
                    this, getString(R.string.one_scan_timeout), list, select
                ) { _, witch ->
                    oneScanOverTime = when (witch) {
                        0 -> "3000"
                        1 -> "5000"
                        2 -> "10000"
                        3 -> "20000"
                        else -> "3000"
                    }
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MTRS$oneScanOverTime"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MSRS$oneScanOverTime"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MARS$oneScanOverTime"))
                    update(ConfigEnum.OneTime.name, oneScanOverTime)
                }
            }
            binding.rlCouScan.setOnClickListener {
                val select = when (senseModeValue) {
                    "S_CMD_MS51" -> 0
                    "S_CMD_MS52" -> 1
                    "S_CMD_MS53" -> 2
                    "S_CMD_MS54" -> 3
                    else -> 0
                }
                val list = arrayOf(
                    getString(R.string.scan_voice_l),
                    getString(R.string.scan_voice_m),
                    getString(R.string.scan_voice_h),
                    getString(R.string.scan_voice_vh)
                )
                choseSingleDialog(
                    this, getString(R.string.senseModeValue), list, select
                ) { _, witch ->
                    senseModeValue = when (witch) {
                        0 -> "S_CMD_MS51"
                        1 -> "S_CMD_MS52"
                        2 -> "S_CMD_MS53"
                        3 -> "S_CMD_MS54"
                        else -> "S_CMD_MS51"
                    }
                    HIDManager.getInstance().sendData(HexUtil.stringToAscii(senseModeValue))
                    update(ConfigEnum.SenseScanValue.name, senseModeValue)
                }
            }
            binding.rlReScan.setOnClickListener {
                val select = when (reScanTime) {
                    "100" -> 0
                    "200" -> 1
                    "500" -> 2
                    "1000" -> 3
                    "2000" -> 4
                    "5000" -> 5
                    else -> 0
                }
                val list = arrayOf(
                    "100ms", "200ms", "500ms", "1000ms", "2000ms", "5000ms"
                )
                choseSingleDialog(
                    this,
                    getString(R.string.re_scan_mode),
                    list,
                    select
                ) { _, witch ->
                    reScanTime = when (witch) {
                        0 -> "100"
                        1 -> "200"
                        2 -> "500"
                        3 -> "1000"
                        4 -> "2000"
                        5 -> "5000"
                        else -> "100"
                    }
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MA31"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MS31"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MT31"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MTRI$reScanTime"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MSRI$reScanTime"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MARI$reScanTime"))

                    update(ConfigEnum.ReTime.name, reScanTime)
                }
            }
            binding.rlLightAim.setOnClickListener {
                val select = when (aimLight) {
                    "AMLENA1" -> 0
                    "AMLENA0" -> 1
                    else -> 0
                }
                val list = arrayOf(
                    getString(R.string.open),
                    getString(R.string.close)
                )
                choseSingleDialog(
                    this, getString(R.string.light_am), list, select
                ) { _, witch ->
                    when (witch) {
                        0 -> {
                            aimLight = "AMLENA1"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_03A2"))
                        }

                        1 -> {
                            aimLight = "AMLENA0"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_03A0"))
                        }

                        else -> {
                            aimLight = "AMLENA1"
                            HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_03A2"))
                        }
                    }
                    update(ConfigEnum.AimLight.name, aimLight)
                }
            }
            binding.rlLightBt.setOnClickListener {
                val select = when (externalLighting) {
                    "ILLSCN1" -> 0
                    "ILLSCN0" -> 1
                    else -> 0
                }
                val list = arrayOf(
                    getString(R.string.open),
                    getString(R.string.close)
                )
                choseSingleDialog(
                    this, getString(R.string.light_bt), list, select
                ) { _, witch ->
                    when (witch) {
                        0 -> {
                            externalLighting = "ILLSCN1"
                            HIDManager.getInstance()
                                .sendData(HexUtil.stringToAscii("S_CMD_03L2"))
                        }

                        1 -> {
                            externalLighting = "ILLSCN0"
                            HIDManager.getInstance()
                                .sendData(HexUtil.stringToAscii("S_CMD_03L0"))
                        }

                        else -> {
                            externalLighting = "ILLSCN1"
                            HIDManager.getInstance()
                                .sendData(HexUtil.stringToAscii("S_CMD_03L2"))
                        }
                    }
                    update(ConfigEnum.OutLight.name, externalLighting)
                }
            }
            binding.rlScanVoice.setOnClickListener {
                if (isScanVoice) {
                    val select = when (voiceValue) {
                        "GRBVLL20" -> 0
                        "GRBVLL10" -> 1
                        "GRBVLL2" -> 2
                        else -> 0
                    }
                    val list = arrayOf(
                        getString(R.string.scan_voice_h),
                        getString(R.string.scan_voice_m),
                        getString(R.string.scan_voice_l)
                    )
                    choseSingleDialog(
                        this, getString(R.string.scan_voice_value), list, select
                    ) { _, witch ->
                        when (witch) {
                            0 -> {
                                voiceValue = "GRBVLL20"
                                HIDManager.getInstance()
                                    .sendData(HexUtil.stringToAscii("S_CMD_04V0"))
                            }

                            1 -> {
                                voiceValue = "GRBVLL10"
                                HIDManager.getInstance()
                                    .sendData(HexUtil.stringToAscii("S_CMD_04V1"))
                            }

                            2 -> {
                                voiceValue = "GRBVLL2"
                                HIDManager.getInstance()
                                    .sendData(HexUtil.stringToAscii("S_CMD_04V2"))
                            }

                            else -> {
                                voiceValue = "GRBVLL20"
                                HIDManager.getInstance()
                                    .sendData(HexUtil.stringToAscii("S_CMD_04V0"))
                            }
                        }
                        update(ConfigEnum.VoiceValue.name, voiceValue)
                    }
                } else showToast(getString(R.string.voice_off))
            }
            //恢复默认参数  S_CMD_FFFF
            binding.btnSetFactory.setOnClickListener {
                confirmDialog(this, getString(R.string.str_restore_factory)) {
                    HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_FFFF"))
                    //修改连续识读模式识读间隔0ms
                    HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MARR000"))
                    rereadConfigurationItems()
                    firstUpdate(MyApplication.dao.selectAllConfig())
                    initData1()
                }
            }
        }
        //hid模式
        binding.sbHidChoose.changed { isChecked ->
            if (isChecked && binding.sbOpenWidget.isChecked) {
                binding.sbOpenWidget.isChecked = false
                update(ConfigEnum.WidgetChoose.name, "false")
            }
            update(ConfigEnum.HIDChoose.name, "$isChecked")
        }
        //控件模式
        binding.sbOpenWidget.changed { isChecked ->
            if (isChecked && binding.sbHidChoose.isChecked) {
                binding.sbHidChoose.isChecked = false
                update(ConfigEnum.HIDChoose.name, "false")
            }
            update(ConfigEnum.WidgetChoose.name, "$isChecked")
        }
        //首尾空格过滤
        binding.spaceFilterBtn.changed { update(ConfigEnum.FilterSpace.name, "$it") }
        //转换不可见字符
        binding.replaceInvisibleCharBtn.changed {
            update(ConfigEnum.ReplaceInvisibleChar.name, "$it")
        }
        //剪切板
        binding.sbClipboardChoose.changed { update(ConfigEnum.ClipBoardChoose.name, "$it") }
        binding.sbCleanEdittext.changed { update(ConfigEnum.AUTO_CLEAN.name, "$it") }
        binding.rlActionPrefix.setOnClickListener {
            ActionFragment(1) {
                if (actionPrefixToggle) binding.tvActionPrefixSummaryText.text =
                    KeyEvent.keyCodeToString(it)
                else binding.tvActionPrefixSummaryText.text = getString(R.string.no_setting)
            }.show(supportFragmentManager, "actionPrefix")
        }
        binding.rlActionSuffix.setOnClickListener {
            ActionFragment(2) {
                if (actionSuffixToggle) binding.tvActionSuffixSummaryText.text =
                    KeyEvent.keyCodeToString(it)
                else binding.tvActionSuffixSummaryText.text = getString(R.string.no_setting)
            }.show(supportFragmentManager, "actionSuffix")
        }
        //字符替换
        binding.rlReplaceGs.setOnClickListener {
            CharReplaceDialog().show(supportFragmentManager, "replaceChar")
        }
        binding.rlAddPrefix.setOnClickListener {
            InputDataScanType(addPrefix, 1) {
                binding.tvPrefixSummaryText.text = it
            }.show(supportFragmentManager, "qz")
        }
        binding.rlAddSuffix.setOnClickListener {
            InputDataScanType(addSuffix, 2) {
                binding.tvSuffixSummaryText.text = it
            }.show(supportFragmentManager, "hz")
        }
        //字符串截取
        binding.rlDeleteSuffix.setOnClickListener {
            DeleteCharDialogFragment().show(
                supportFragmentManager, "del"
            )
        }
        binding.rlScanEncoding.setOnClickListener { showScanEncodingDialog() }
        binding.sbOpenBoot.changed { update(ConfigEnum.openServiceReboot.name, "$it") }
        //自定义广播
        binding.rlSettingBroadcast.setOnClickListener {
            BroadcastDialogFragment().show(supportFragmentManager, "broadcast")
        }
        //导入
        binding.rlLoadConfig.setOnClickListener {
            com.github.h4de5ing.filepicker.DialogUtils.selectFile(
                this, getString(R.string.load_config_from_file)
            ) {
                try {
                    val result = File(it[0]).readText()
                    "选择的文件:${it[0]} $result".logI()
                    importConfig(context = this,
                        data = result,
                        importScan = { list ->
                            list.forEach { item ->
                                if (scanModule != 3 && item.key != ConfigEnum.SenseScanValue.name)
                                    ds.setConfig(item.value)
                                else setYdScanModuleConfig(item)
                            }
                        },
                        success = { showToast(getString(R.string.import_success)) },
                        error = { showToast(getString(R.string.import_failed)) })
                } catch (e: Exception) {
                    showToast(getString(R.string.import_failed))
                    e.printStackTrace()
                }
            }
        }
        //导出
        binding.rlSaveConfig.setOnClickListener {
            try {
                MainScope().launch(Dispatchers.IO) {
                    val list = mutableListOf<Pair<String, String>>()
                    list.clear()
                    MyApplication.dao.selectAllConfig().forEach {
                        list.add(Pair(it.key, it.value))
                    }
                    if (scanModule != 3) {
                        list.add(
                            Pair(
                                ConfigEnum.Enable.name,
                                if (isEnable) "@SCNENA1" else "@SCNENA0"
                            )
                        )
                        list.add(
                            Pair(
                                ConfigEnum.ScanVoice.name,
                                if (isScanVoice) "@GRBENA1" else "@GRBENA0"
                            )
                        )
                        list.add(
                            Pair(
                                ConfigEnum.StartVoice.name,
                                if (isStartVoice) "@PWBENA1" else "@PWBENA0"
                            )
                        )
                        list.add(Pair(ConfigEnum.ScanModel.name, "@$isScanModel"))
                        list.add(Pair(ConfigEnum.ReTime.name, "@RRDDUR$reScanTime"))
                        list.add(Pair(ConfigEnum.OneTime.name, "@ORTSET$oneScanOverTime"))
                        if (scanModule == 1) list.add(Pair(ConfigEnum.ScanSp.name, "@$scanSp"))
                        list.add(Pair(ConfigEnum.VoiceValue.name, "@$voiceValue"))
                        list.add(Pair(ConfigEnum.AimLight.name, "@$aimLight"))
                        list.add(Pair(ConfigEnum.OutLight.name, "@$externalLighting"))
                    } else insertMe11Config(list)
                    val result = "CONFIG_P" + Json.encodeToString(list) + "CONFIG_S"
                    withContext(Dispatchers.Main) {
                        selectDir(
                            this@SettingActivity, getString(R.string.save_config_to_file), true
                        ) { files ->
                            try {
                                val writeResult =
                                    File(files[0] + File.separator + files[1]).write(result)
                                showToast(
                                    if (writeResult) getString(R.string.save_config_completed) else getString(
                                        R.string.save_config_failed
                                    )
                                )
                            } catch (e: Exception) {
                                showToast(getString(R.string.save_config_failed))
                                e.printStackTrace()
                            }
                        }

                    }
                }

            } catch (e: Exception) {
                showToast("${e.message}")
                e.printStackTrace()
            }
        }
        //开发者
        val fileName =
            if (resources.configuration.locales[0].language.lowercase() == "zh") "index.html" else "index_English.html"
        val summary = "http://${getMyIp()}:4680/${fileName}"
        binding.tvDevelopmentText.text = summary
        binding.development.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://${getMyIp()}:4680/${fileName}")
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun insertMe11Config(list: MutableList<Pair<String, String>>) {
        if (list.firstOrNull { it.first == ConfigEnum.ScanVoice.name } == null)
            list.add(Pair(ConfigEnum.ScanVoice.name, isScanVoice.toString()))
        if (list.firstOrNull { it.first == ConfigEnum.StartVoice.name } == null)
            list.add(Pair(ConfigEnum.StartVoice.name, isStartVoice.toString()))
        if (list.firstOrNull { it.first == ConfigEnum.ScanModel.name } == null)
            list.add(Pair(ConfigEnum.ScanModel.name, isScanModel))
        if (list.firstOrNull { it.first == ConfigEnum.OneTime.name } == null)
            list.add(Pair(ConfigEnum.OneTime.name, oneScanOverTime))
        if (list.firstOrNull { it.first == ConfigEnum.SenseScanValue.name } == null)
            list.add(Pair(ConfigEnum.SenseScanValue.name, senseModeValue))
        if (list.firstOrNull { it.first == ConfigEnum.ReTime.name } == null)
            list.add(Pair(ConfigEnum.ReTime.name, reScanTime))
        if (list.firstOrNull { it.first == ConfigEnum.AimLight.name } == null)
            list.add(Pair(ConfigEnum.AimLight.name, aimLight))
        if (list.firstOrNull { it.first == ConfigEnum.OutLight.name } == null)
            list.add(Pair(ConfigEnum.OutLight.name, externalLighting))
        if (list.firstOrNull { it.first == ConfigEnum.VoiceValue.name } == null)
            list.add(Pair(ConfigEnum.VoiceValue.name, voiceValue))
    }

    private fun setYdScanModuleConfig(keyValue: KeyValue) {
        when (keyValue.key) {
            ConfigEnum.ScanVoice.name -> {
                if (keyValue.value.toBoolean())
                    HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_0403"))
                else HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_0402"))
            }

            ConfigEnum.StartVoice.name -> {
                if (keyValue.value.toBoolean())
                    HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_0401"))
                else HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_0400"))
            }

            ConfigEnum.ScanModel.name -> {
                when (keyValue.value) {
                    "SCNMOD0" -> {
                        HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MT00"))
                        HIDManager.getInstance().sendData(HexUtil.stringToAscii("S_CMD_MT10"))
                    }

                    "SCNMOD2" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_020F"))

                    "SCNMOD3" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_020E"))
                }
            }

            ConfigEnum.OneTime.name -> {
                HIDManager.getInstance()
                    .sendData(HexUtil.stringToAscii("S_CMD_MTRS${keyValue.value}"))
                HIDManager.getInstance()
                    .sendData(HexUtil.stringToAscii("S_CMD_MSRS${keyValue.value}"))
                HIDManager.getInstance()
                    .sendData(HexUtil.stringToAscii("S_CMD_MARS${keyValue.value}"))
            }

            ConfigEnum.SenseScanValue.name -> HIDManager.getInstance()
                .sendData(HexUtil.stringToAscii(keyValue.value))


            ConfigEnum.ReTime.name -> {
                if (keyValue.value == "0") {
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MA30"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MS30"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MT30"))
                } else {
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MA31"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MS31"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MT31"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MTRI${keyValue.value}"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MSRI${keyValue.value}"))
                    HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_MARI${keyValue.value}"))
                }
            }

            ConfigEnum.AimLight.name -> {
                when (keyValue.value) {
                    "AMLENA1" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_03A2"))

                    "AMLENA0" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_03A0"))
                }
            }

            ConfigEnum.OutLight.name -> {
                when (keyValue.value) {
                    "ILLSCN1" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_03L2"))

                    "ILLSCN0" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_03L0"))
                }
            }

            ConfigEnum.VoiceValue.name -> {
                when (keyValue.value) {
                    "GRBVLL20" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_04V0"))

                    "GRBVLL10" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_04V1"))

                    "GRBVLL2" -> HIDManager.getInstance()
                        .sendData(HexUtil.stringToAscii("S_CMD_04V2"))
                }
            }
        }
    }

    private fun rereadConfigurationItems() {
        setIsFirstOpen(true)
        MyApplication.dao.deleteAllConfig()
        showToast(getString(R.string.reset_success))
        parserXML(File(com.scanner.d10r.hardware.bean.Constants.configPath))
        rebootAPP()
    }

    private fun showScanEncodingDialog() {
        val v = View.inflate(this, R.layout.dialog_scan_encoding, null)
        val sp = v.findViewById<Spinner>(R.id.sp_scan_encoding)
        try {
            sp.setSelection(
                resources.getStringArray(R.array.scan_encoding).indexOf(dataEncoding)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        editDialog(this, v, getString(R.string.scan_encoding)) {
            val scanEncodingStr =
                resources.getStringArray(R.array.scan_encoding)[sp.selectedItemPosition]
            update(ConfigEnum.DataEncoding.name, scanEncodingStr)
            binding.tvScanEncodingSummaryText.text = scanEncodingStr
        }
    }
}