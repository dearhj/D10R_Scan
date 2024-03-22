package com.van.uart;


public class LastError extends Exception {
    private final int errno;

    public LastError(int errno, String msg) {
        super(msg);
        this.errno = errno;
    }

    public int getNumber() {
        return errno;
    }

    @Override
    public String toString() {
        return "errno = " + errno + ", " + getMessage();
    }
}
