// IRemoteCallback.aidl
package com.scanner.hardware;

// Declare any non-default types here with import statements

interface IRemoteCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void valueChanged(in byte[] data);
}