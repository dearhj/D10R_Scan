package com.scanner.hardware.enums

enum class ConfigEnum {
    //Android系统平台的系统，T50
    model,

    //串口节点
    SerialPortName,

    //打开关闭节点  /sys/devices/soc/10003000.keypad/scan_leveltrigger_enable  /dev/gpio_ctl_drv
    FileNodeCloseOpen,

    //打开扫描的值 10
    OpenScanValue,

    //关闭扫描的值 11
    CloseScanValue,

    //打开二维码供电 7
    PowerOnValue,

    //关闭二维码供电 8
    PowerOffValue,
    SerialPortSwitch,
    FileNodeChannelSwitch,
    FileNodeChannelValue,

    //声音开关
    scanVoice,

    //震动开关
    scanVibrate,

    //声音文件的索引
    VoiceIndex,

    //屏蔽音量按键
    BlockVolumeKeys,

    //蓝牙手柄
    BLUETOOTH,

    //自动清空
    AUTO_CLEAN,

    //剪切板模式
    ClipBoardChoose,

    //控件模式
    WidgetChoose,

    //HID模式
    HIDChoose,

    //替换字符，采用字符串存储，例如0x12>0x22&0x31>0x32
    ReplaceChar,

    //添加前缀
    AddPrefix, AddPrefixEnter,

    //添加后缀
    AddSuffix, AddSuffixEnter,

    //添加前后缀动作
    ActionPrefixToggle, ActionSuffixToggle,
    ActionPrefix, ActionSuffix,

    //字符串截取位置模式
    PositionMode, DeletePrefix, DeleteSuffix,

    //字符串截取字符模式
    CharacterMode, DeletePrefixChar, DeleteSuffixChar,

    //过滤首尾空格
    FilterSpace,

    //转换不可见字符
    ReplaceInvisibleChar,

    //Tab后缀
    TabChoose,

    //enter后缀 并响应Enter动作
    EnterChoose,

    //条码编码
    DataEncoding,

    //扫码开机自启动
    openServiceReboot, enableScan,

    //悬浮按钮
    FloatButton,

    //悬浮按钮尺寸大小
    FloatSize,

    //扫码模式 Async or Sync or Continuous
    ScanModel,

    //波特率
    BaudRate,

    //自定义广播Action
    BroadcastAction,

    //自定义广播data
    BroadcastData,

    //扫码模块
    ScanModule,

    //hid写入节点
    inputPath, inputPathEnter,

    //超时时间
    overTime
}