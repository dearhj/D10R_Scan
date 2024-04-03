package com.scanner.d10r.hardware.enums

enum class ConfigEnum {
    model,

    //enable
    Enable,

    //声音开关
    ScanVoice,

    //开机声音
    StartVoice,

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


    //条码编码
    DataEncoding,

    //扫码开机自启动
    openServiceReboot,

    //扫码模式 Async or Sync or Continuous
    ScanModel,

    //自定义广播Action
    BroadcastAction,

    //自定义广播data
    BroadcastData,

    //hid写入节点
    inputPath,

    //提示音音量
    VoiceValue,

    //外部照明灯
    OutLight,

    //瞄准灯
    AimLight,

    //识读偏好
    ScanSp,

    //一次识读超时时间
    OneTime,

    //重读延迟
    ReTime,

    //扫描模块
    ScanModule,

    //感应模式灵敏度
    SenseScanValue
}