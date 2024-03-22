package com.andorid.scannerlib

import com.scanner.hardware.IRemoteService

/**
 * 基类,抽象一些公用方法
 */
abstract class BaseReader {
    private var remoteService: IRemoteService? = null
    fun init(remoteService: IRemoteService?) {
        this.remoteService = remoteService
    }

    /**
     * 通用设置
     * @param key 设置选项
     * @param value 设置选项的值
     */
    fun setSettings(key: String, value: String) {
        remoteService?.setSettings(key, value)
    }

    /**
     * 获取通用设置的制
     * @return value 有值返回值，无值返回null
     */
    fun getSettings(key: String): String? {
        return remoteService?.getSettings(key)
    }

    /**
     * 设置码制
     * @param data 码制命令
     * 本方法无返回值，码值设置的返回值与扫码结果返回值在同一个回调中返回
     * 部分命令设置过后也没有返回值
     */
    fun setCode(data: ByteArray) {
        remoteService?.setCode(data)
    }
}