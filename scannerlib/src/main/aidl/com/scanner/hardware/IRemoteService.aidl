// IRemoteService.aidl
package com.scanner.hardware;
import com.scanner.hardware.IRemoteCallback;
// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void registerCallback(IRemoteCallback callback);
    void unregisterCallback(IRemoteCallback callback);
    void setSettings(in String key,in String value);
    String getSettings(in String key);
    void setCode(in byte[] data);
}