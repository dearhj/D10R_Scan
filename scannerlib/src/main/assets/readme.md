# SDK使用手册v1.0

1.如果仅仅是只获取数据并不需要控制扫码app，那么监听广播即可获取到数据 2.如果需要深度开发才需要使用SDK，SDK是始于【硬解扫描工具
v5.01.5】，大于这个版本号才能使用，在开发前请确认设备中安装的【硬解扫描工具】版本号

## 串口：如果出现无法扫码的情况，请检查串口通信是否正常，串口正常情况下会打印【sp=】日志，可以adb logcat -s gh0st 过滤此日志，如果扫码有返回值,会打印sp=30 31 32 等号后面为扫码的字符串【123】的16进制形式

## 大多数情况下只需要监听广播即可获取到数据
```java
    public static String BARCODEPORT_RECEIVEDDATA_ACTION = "com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION";
    public static String BARCODEPORT_RECEIVEDDATA_EXTRA_DATA = "DATA";
```

## 　SDK引入
在project build.gradle 增加仓库地址
```build.gradle
repositories {
maven { url 'https://gitee.com/lex1992/repository/raw/master/repository' }
}
```

在app的 build.gradle 增加对sdk 库的依赖，可以自行更新为最新版本
```build.gradle
implementation 'com.andorid.scannerlib:scannerlib:1.0-20220303'
```

## 绑定服务

```kotlin
val intent = Intent()
intent.action = "com.scanner.hardware.barcodeservice.SerialPortService"
intent.setPackage("com.scanner.hardware")
bindService(intent, connection, BIND_AUTO_CREATE)
private var remoteService: IRemoteService? = null
private var connection: ServiceConnection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        remoteService = IRemoteService.Stub.asInterface(service)
        try {
            remoteService?.asBinder()?.linkToDeath(mDeathRecipient, 0)
            remoteService?.registerCallback(callback)
            //String value = remoteService.getSettings(ConfigEnum.BaudRate.name());
            //一定要初始化以后才能调用Reader中的其他方法，也可以直接调用remoteService 的getSettings setSettings setCode方法
            Reader.init(remoteService)
            //获取设置选项
            val value = Reader.getSettings(ConfigEnum.BaudRate.name)
            //设置前缀为字符串a
            Reader.setSettings(ConfigEnum.AddPrefix.name, "a")
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun onServiceDisconnected(name: ComponentName) {
        remoteService = null
        updateTv("onServiceDisconnected$name")
    }
}
private val mDeathRecipient: IBinder.DeathRecipient = object : IBinder.DeathRecipient {
    override fun binderDied() {
        remoteService!!.asBinder().unlinkToDeath(this, 0)
        remoteService = null
        updateTv("binderDied")
    }
}
var callback: IRemoteCallback = object : IRemoteCallback.Stub() {
    @Throws(RemoteException::class)
    //串口数据回调，是串口的原始数据
    override fun valueChanged(data: ByteArray) {
    }
}
```

##　常用接口说明
Reader.init(remoteService) 初始化，让Reader获得远程服务对象
Reader.setSettings(key,value) 设置通用设置，key名称可以参考ConfigEnum枚举，value为当前硬解扫描工具的当前设置
Reader.getSettings(key) 获取通用设置，key名称可以参考ConfigEnum枚举
Reader.setCode(data) 发送数据到串口，通常用于码制设置，设置支持的常见码制设置常看Code类，

注意：Code.getHuoNi()  霍尼二维码制列表，Code.getToinfoEN 图腾码制列表 Code.getNewland 新大陆码制列表
具体码制格式可以参考scandemo中的symbologies目录