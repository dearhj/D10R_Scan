package com.andorid.scannerlib

enum class ConfigEnum {
    //Android系统平台的系统，T50
    model,

    //是否是第一次打开
//    isFirst,

    //声音开关
    scanVoice,

    //声音文件的索引
    VoiceIndex,

    //屏蔽音量按键
    BlockVolumeKeys,

    //震动开关
    scanVibrate,


    //蓝牙手柄
    BLUETOOTH,

    //自动清空
    AUTO_CLEAN,


//    //废弃这种存4个值的方式，采用存模式索引
//    //广播模式
//    BroadcastReceiver,
//
//    //剪切板模式
//    ClipBoardChoose,
//
//    //控件模式
//    WidgetChoose,
//
//    //HID模式
//    HIDChoose,

    //输出模式
    InputMode,//0 广播模式 1 剪贴板模式 2控件模式 3HID模式

    //ge分组符号  修改成用GS2这个字符去替换GS这个字符
    GS,
    GS2,

    //添加前缀
    AddPrefix,
    AddPrefixEnter,

    //添加后缀
    AddSuffix,
    AddSuffixEnter,

    //添加后缀
    //删除前缀字符个数
    DeletePrefix,

    //删除后缀字符个数
    DeleteSuffix,

    //Tab后缀
    TabSuffix,

    //enter后缀 并响应Enter动作
    EnterChoose,

    //条码编码
    DataEncoding,

    //扫码开机自启动
    openServiceReboot,
    enableScann,

//    //悬浮按钮
//    FloatButton,
//
//    //悬浮按钮尺寸大小
//    FloatSize,

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

    //超时时间
    overTime
}