package com.scanner.hardware

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.widget.Spinner
import com.github.h4de5ing.base.toHexByteArray
import com.github.h4de5ing.base.write
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logI
import com.github.h4de5ing.document.getMyIp
import com.github.h4de5ing.filepicker.DialogUtils.selectDir
import com.google.zxing.client.android.ZXingUtils
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.bean.Constants
import com.scanner.hardware.databinding.ActivitySettingBinding
import com.scanner.hardware.enums.ConfigEnum
import com.scanner.hardware.ui.ActionFragment
import com.scanner.hardware.ui.BroadcastDialogFragment
import com.scanner.hardware.ui.CharReplaceDialog
import com.scanner.hardware.ui.DeleteCharDialogFragment
import com.scanner.hardware.ui.InputDataScanType
import com.scanner.hardware.ui.ScannerFloatView
import com.scanner.hardware.util.DataUtil
import com.scanner.hardware.util.SC_PREFIX
import com.scanner.hardware.util.SC_SUFFIX
import com.scanner.hardware.util.SoundPlayUtil
import com.scanner.hardware.util.actionPrefixKeyCode
import com.scanner.hardware.util.actionPrefixToggle
import com.scanner.hardware.util.actionSuffixKeyCode
import com.scanner.hardware.util.actionSuffixToggle
import com.scanner.hardware.util.addPrefix
import com.scanner.hardware.util.addSuffix
import com.scanner.hardware.util.baudRate
import com.scanner.hardware.util.choseSingleDialog
import com.scanner.hardware.util.confirmDialog
import com.scanner.hardware.util.dataEncoding
import com.scanner.hardware.util.editDialog
import com.scanner.hardware.util.floatSize
import com.scanner.hardware.util.imageDialog
import com.scanner.hardware.util.importConfig
import com.scanner.hardware.util.is195T
import com.scanner.hardware.util.isAutoCleanEditText
import com.scanner.hardware.util.isBlockVolumeKeys
import com.scanner.hardware.util.isBlueToothScan
import com.scanner.hardware.util.isClipBoardChoose
import com.scanner.hardware.util.isEnterChoose
import com.scanner.hardware.util.isFilterSpace
import com.scanner.hardware.util.isFloatButton
import com.scanner.hardware.util.isHIDChoose
import com.scanner.hardware.util.isOpenServiceReboot
import com.scanner.hardware.util.isReplaceInvisibleChar
import com.scanner.hardware.util.isScanVibrate
import com.scanner.hardware.util.isScanVoice
import com.scanner.hardware.util.isWidgetChoose
import com.scanner.hardware.util.overTime
import com.scanner.hardware.util.parserXML
import com.scanner.hardware.util.scanModel
import com.scanner.hardware.util.scanModule
import com.scanner.hardware.util.send2Newland
import com.scanner.hardware.util.sendByteArray2SP0x00
import com.scanner.hardware.util.sendString2SP
import com.scanner.hardware.util.setIsFirstOpen
import com.scanner.hardware.util.tabChoose
import com.scanner.hardware.util.updateFloatView
import com.scanner.hardware.util.updateKT
import com.scanner.hardware.util.voiceChooseIndex
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class SettingActivity : BaseBackActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initData() {
        binding.sbHidChoose.isChecked = isHIDChoose
        binding.sbEnterChoose.isChecked = isEnterChoose
        binding.spaceFilterBtn.isChecked = isFilterSpace
        binding.replaceInvisibleCharBtn.isChecked = isReplaceInvisibleChar
        binding.sbVoice.isChecked = isScanVoice
        binding.sbOpenBoot.isChecked = isOpenServiceReboot
        binding.sbZdChoose.isChecked = isScanVibrate
        binding.sbClipboardChoose.isChecked = isClipBoardChoose
        binding.sbOpenWidget.isChecked = isWidgetChoose
        binding.sbSuspensionBtn.isChecked = isFloatButton
        binding.sbBlueToothChoose.isChecked = isBlueToothScan
        binding.sbCleanEdittext.isChecked = isAutoCleanEditText
        binding.sbBlockVolumeKeys.isChecked = isBlockVolumeKeys
        binding.tvTabSummaryText.setText(
            when (tabChoose) {
                "close" -> R.string.close
                "ahead" -> R.string.add_qian
                "behind" -> R.string.add_hou
                else -> R.string.close
            }
        )
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
    }

    private fun initView() {
        //提示音
        binding.sbVoice.changed { update(ConfigEnum.scanVoice.name, "$it") }
        if (is195T()) binding.rlZd.visibility = View.GONE
        //震动
        binding.sbZdChoose.changed { update(ConfigEnum.scanVibrate.name, "$it") }
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
        //蓝牙
        binding.sbBlueToothChoose.changed { update(ConfigEnum.BLUETOOTH.name, "$it") }
        //剪切板
        binding.sbClipboardChoose.changed { update(ConfigEnum.ClipBoardChoose.name, "$it") }
        binding.sbCleanEdittext.changed { update(ConfigEnum.AUTO_CLEAN.name, "$it") }
        binding.sbBlockVolumeKeys.changed { update(ConfigEnum.BlockVolumeKeys.name, "$it") }
        //恢复默认参数
        binding.btnSetFactory.setOnClickListener {
            confirmDialog(this, getString(R.string.str_restore_factory)) {
                val deviceType = scanModule
                "恢复出厂设置:${deviceType}".logD()
                when (deviceType) {
                    Constants.DTHONEYWELL -> {
                        sendString2SP("defalt.")
                        //setEXT()  //此处代码作用是使霍尼扫码头输出数据后增加后缀，用来识别断行数据。
                    }

                    Constants.DTMoToSE655, Constants.DTMoTOSE2707 -> {
                        MyApplication.dao.deleteAllMoto()
                        val resetDefaultCode = "04C80408"
                        sendByteArray2SP0x00(
                            (resetDefaultCode + DataUtil.makeChecksum(
                                resetDefaultCode
                            )).toHexByteArray()
                        )
                        MyApplication.application?.allMotoSetting()
                    }

                    Constants.DTTotinfo -> sendByteArray2SP0x00("08C6040800F2FF00FD35".toHexByteArray())
                    Constants.DTNewland -> send2Newland("FACDEF")
                }
                rereadConfigurationItems()
            }
        }
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
        binding.rlAddTab.setOnClickListener {
            val select = when (tabChoose) {
                "close" -> 0
                "ahead" -> 1
                "behind" -> 2
                else -> 0
            }
            val list = arrayOf(
                getString(R.string.close), getString(R.string.add_qian), getString(R.string.add_hou)
            )
            choseSingleDialog(this, getString(R.string.table_set), list, select) { _, witch ->
                val model = when (witch) {
                    0 -> "close"
                    1 -> "ahead"
                    2 -> "behind"
                    else -> "close"
                }
                tabChoose = model
                updateKT(ConfigEnum.TabChoose.name, tabChoose)
                binding.tvTabSummaryText.setText(
                    when (tabChoose) {
                        "close" -> R.string.close
                        "ahead" -> R.string.add_qian
                        "behind" -> R.string.add_hou
                        else -> R.string.close
                    }
                )
            }
        }
        binding.sbEnterChoose.changed { update(ConfigEnum.EnterChoose.name, "$it") }
        binding.rlScanEncoding.setOnClickListener { showScanEncodingDialog() }
        binding.sbOpenBoot.changed { update(ConfigEnum.openServiceReboot.name, "$it") }
        binding.sbSuspensionBtn.changed { if (it) requestFloatSystem() else sendBroadcastClose() }
        binding.rlFloatSize.setOnClickListener { showFloatSizeDialog() }
        binding.rlChooseVoice.setOnClickListener { showSingleAlertDialog() }
        binding.rlChooseScanMode.setOnClickListener {
            val select = when (scanModel) {
                "Async" -> 0
                "Sync" -> 1
                "Continuous" -> 2
                "Induction" -> 3
                else -> 0
            }
            val list = if (scanModule == 6) arrayOf(
                getString(R.string.get_result_close),
                getString(R.string.song_shou_close),
                getString(R.string.continuous_close),
                getString(R.string.Induction_close)
            ) else arrayOf(
                getString(R.string.get_result_close),
                getString(R.string.song_shou_close),
                getString(R.string.continuous_close),
            )
            choseSingleDialog(
                this, getString(R.string.scan_mode_choose), list, select
            ) { _, witch ->
                val model = when (witch) {
                    0 -> "Async"
                    1 -> "Sync"
                    2 -> "Continuous"
                    3 -> "Induction"
                    else -> "Async"
                }
                scanModel = model
                updateKT(ConfigEnum.ScanModel.name, scanModel)
                if (scanModule == 6) {
                    if (scanModel == "Induction") {
                        send2Newland("SCNMOD2")
                    } else send2Newland("SCNMOD0")
                }
            }
        }
        //频繁打开关闭串口会导致无响应
        binding.rlSelectBaudrate.setOnClickListener {
            val baudRate = baudRate
            val i = Constants.baudRate.indexOf(baudRate)
            choseSingleDialog(
                this,
                getString(R.string.choose_baud_rate),
                Constants.baudRate.map { "$it" }.toList().toTypedArray(),
                i
            ) { _, position ->
                "选择的位置=$position；选择的波特率=" + Constants.baudRate[position].logI()
                update(ConfigEnum.BaudRate.name, "${Constants.baudRate[position]}")
            }
        }
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
                val list = MyApplication.dao.selectAllConfig().map { mapOf(it.key to it.value) }
                val result = SC_PREFIX + Json.encodeToString(list) + SC_SUFFIX
                imageDialog(
                    this,
                    getString(R.string.save_config_to_file),
                    ZXingUtils.createQRCode(result, 400, 400)
                ) {
                    selectDir(
                        this, getString(R.string.save_config_to_file), true
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
            } catch (e: Exception) {
                showToast("${e.message}")
                e.printStackTrace()
            }
        }
        //连续扫码间隔时间
        binding.overtime.setOnClickListener { showOverTimeDialog() }
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

    private fun rereadConfigurationItems() {
        setIsFirstOpen(true)
        MyApplication.dao.deleteAllConfig()
        showToast(getString(R.string.reset_success))
        parserXML(File(Constants.configPath))
        updateFloatView(false)
        SoundPlayUtil.init(this, 0)
        rebootAPP()
    }

    private fun showOverTimeDialog() {
        val overTimes = resources.getStringArray(R.array.overtime)
        choseSingleDialog(
            this, getString(R.string.set_over_time), overTimes, overTime
        ) { _, index ->
            update(
                ConfigEnum.overTime.name, "$index"
            )
        }
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

    private fun showFloatSizeDialog() {
        val floatSizes = resources.getStringArray(R.array.float_button_size)
        choseSingleDialog(
            this, getString(R.string.float_btn_size), floatSizes, floatSize
        ) { _, index ->
            update(ConfigEnum.FloatSize.name, "$index")
            floatSize = index
            ScannerFloatView.resize()
        }
    }

    private fun showSingleAlertDialog() {
        choseSingleDialog(
            this, getString(R.string.voice_choose), Constants.voices, voiceChooseIndex
        ) { _, index ->
            voiceChooseIndex = index
            update(ConfigEnum.VoiceIndex.name, "$voiceChooseIndex")
            SoundPlayUtil.init(this, voiceChooseIndex)
        }
    }

    private fun requestFloatSystem() {
        if (!Settings.canDrawOverlays(this)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, 10)
        } else sendBroadcastOpen()
    }

    private fun sendBroadcastOpen() {
        update(ConfigEnum.FloatButton.name, "true")
        updateFloatView(true)
    }

    private fun sendBroadcastClose() {
        update(ConfigEnum.FloatButton.name, "false")
        updateFloatView(false)
    }
}