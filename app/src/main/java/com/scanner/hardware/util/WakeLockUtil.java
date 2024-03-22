package com.scanner.hardware.util;

import android.content.Context;
import android.os.PowerManager;

import com.github.h4de5ing.baseui.LogKt;
import com.scanner.hardware.MyApplication;


/**
 * 扫码中会自动休眠? 给你锁了
 */
public class WakeLockUtil {
    private static WakeLockUtil instance = null;

    private static WakeLockUtil getInstance() {
        if (instance == null) instance = new WakeLockUtil();
        return instance;
    }

    private PowerManager.WakeLock wakeLock = null;
    private final Object obj = new Object();

    public static void acquire() {
        WakeLockUtil wlu = WakeLockUtil.getInstance();
        if (wlu.wakeLock == null) {
            PowerManager pm = (PowerManager) MyApplication.application.getSystemService(Context.POWER_SERVICE);
            wlu.wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "scanWakeLock:liveTAG");
        }
        synchronized (wlu.obj) {
            if (wlu.wakeLock != null) {
                if (!wlu.wakeLock.isHeld())
                    wlu.wakeLock.acquire();
            } else
               LogKt.logE("wakeLock is NULL");
        }
    }

    public static void release() {
        WakeLockUtil wlu = WakeLockUtil.getInstance();
        synchronized (wlu.obj) {
            if (wlu.wakeLock != null && wlu.wakeLock.isHeld()) {
                wlu.wakeLock.release();
                wlu.wakeLock = null;
            }
        }
    }
}
