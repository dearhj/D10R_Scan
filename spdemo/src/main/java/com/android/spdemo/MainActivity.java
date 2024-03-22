package com.android.spdemo;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.van.uart.LastError;
import com.van.uart.UartManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {
    private Button sp0, sp1, start, send;
    private CheckBox hex, still;
    private EditText input;
    private TextView tv;
    private TextView tvCount;
    private final String name = "ttyMSM1";
    private final int baud = 9600;
    private final static String path = "/sys/class/EMDEBUG/gpio_ctrl";
    private final String on = "10";
    private final String off = "11";
    private long rX = 0L;
    private long tX = 0L;
    private boolean isStill = false;
    private boolean isHex = false;

    @Override
    protected void onResume() {
        super.onResume();
//        write("7");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        write("8");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.clear).setOnClickListener(v -> {
            tv.setText("");
            rX = 0L;
            tX = 0L;
            tvCount.setText("Rx:" + rX + " Tx:" + tX);
        });
        sp0 = findViewById(R.id.sp0);
        sp0.setOnClickListener(v -> {
            if (mSerialPort != null) {
                stop0();
                sp0.setText("打开0");
                start.setEnabled(false);
                sp1.setEnabled(true);
            } else {
                initSP0();
                sp0.setText("关闭0");
                start.setEnabled(true);
                sp1.setEnabled(false);
            }
        });
        sp1 = findViewById(R.id.sp1);
        sp1.setOnClickListener(v -> {
            if (manager != null) {
                stop1();
                sp1.setText("打开1");
                start.setEnabled(false);
                sp0.setEnabled(true);
            } else {
                initSP1();
                sp1.setText("关闭1");
                start.setEnabled(true);
                sp0.setEnabled(false);
            }
        });
        start = findViewById(R.id.start);
        findViewById(R.id.start1).setOnClickListener(v -> {
            write(off);
            SystemClock.sleep(10);
            write(on);
        });
        start.setOnClickListener(v -> {
            if (mSerialPort != null || manager != null) {
                scan = !scan;
                start.setText(scan ? "停止" : "开始");
            }
        });
        tv = findViewById(R.id.tv);
        tvCount = findViewById(R.id.count);
        tv.setMovementMethod(new ScrollingMovementMethod());
        send = findViewById(R.id.send);
        input = findViewById(R.id.input);
        hex = findViewById(R.id.hex);
        still = findViewById(R.id.still);
        hex.setOnCheckedChangeListener((buttonView, isChecked) -> isHex = isChecked);
        still.setOnCheckedChangeListener((buttonView, isChecked) -> isStill = isChecked);
        send.setOnClickListener(v -> sendOne());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (scan) {
                        write(off);
                        SystemClock.sleep(10);
                        write(on);
                    }
                    if (isStill) sendOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 300);
    }

    private void sendOne() {
        try {
            String inputStr = input.getText().toString();
            if (!TextUtils.isEmpty(inputStr)) {
                byte[] data;
                if (isHex) data = DataUtil.int2bytes2(inputStr);
                else data = inputStr.getBytes();
                tX += data.length;
                if (mOutputStream != null) {
                    try {
                        mOutputStream.write(data);
                    } catch (IOException error) {
                        updateTv("g 发送错误:" + error.getMessage());
                    }
                }
                if (manager != null) {
                    try {
                        manager.write(data, data.length);
                    } catch (LastError error) {
                        updateTv("v 发送错误:" + error.getMessage());
                    }
                }
            } else updateTv("请输入内容");
            tvCount.setText("Rx:" + rX + " Tx:" + tX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean scan = false;
    private SerialPort mSerialPort = null;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    void initSP0() {
        if (mSerialPort == null) {
            try {
                mSerialPort = new SerialPort(new File("/dev/" + name), baud, 0, 8, 1, 0);
                mInputStream = mSerialPort.getInputStream();
                mOutputStream = mSerialPort.getOutputStream();
                mReadThread0 = new ReadThread0();
                mReadThread0.start();
            } catch (Exception e) {
                updateTv("发生异常:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private ReadThread0 mReadThread0 = null;

    class ReadThread0 extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                while (!isInterrupted()) {
                    run0();
//                    run1();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void run0() {
            try {
                byte[] buffer = new byte[2048];
                int length = readTimeout(mInputStream, buffer, 100);
                System.out.println("G串口运行中... [" + length + "]" + DataUtil.bytes2HexString(buffer, length));
                rX += length;
                if (length > 1) updateTv("gr:" + new String(buffer));
            } catch (Exception e) {
                //e.printStackTrace();
            }
            SystemClock.sleep(50);
        }

        byte[] readBytes = null;

        void run1() {
            int size = -1;
            byte[] buffer = new byte[2048];
            if (mInputStream == null) return;
            try {
                int i = mInputStream.available();
                if (i == 0) size = 0;
                else size = mInputStream.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (size > 0) {
                readBytes = DataUtil.arrayAppend(readBytes, buffer, size);
            } else {
                if (readBytes != null) {
                    byte sum = 0x00;
                    for (byte readByte : readBytes) sum += readByte;
                    if (sum != 0) updateTv("gr:" + new String(readBytes));
                }
                readBytes = null;
            }
            SystemClock.sleep(10);
        }

        int readTimeout(InputStream is, byte[] b, int timeoutMillis) throws IOException {
            int bufferOffset = 0;
            long maxTimeMillis = System.currentTimeMillis() + timeoutMillis;
            while (System.currentTimeMillis() < maxTimeMillis && bufferOffset < b.length) {
                int readLength = Math.min(is.available(), b.length - bufferOffset);
                // can alternatively use bufferedReader, guarded by isReady():
                int readResult = is.read(b, bufferOffset, readLength);
                if (readResult == -1) break;
                bufferOffset += readResult;
            }
            return bufferOffset;
        }
    }

    void stop0() {
        try {
            if (mSerialPort != null) {
                mSerialPort.close();
                if (mInputStream != null) mInputStream.close();
                if (mOutputStream != null) mOutputStream.close();
                if (mReadThread0 != null && mReadThread0.isAlive()) mReadThread0.interrupt();
                mReadThread0 = null;
                mSerialPort = null;
            }
        } catch (Exception e) {
            updateTv("关闭发生异常:" + e.getMessage());
            e.printStackTrace();
        }
    }

    UartManager manager;
    ReadThread1 readThread1;

    void initSP1() {
        if (manager == null) {
            try {
                manager = new UartManager();
                manager.open(name, UartManager.getBaudRate(baud));
                readThread1 = new ReadThread1();
                readThread1.start();
            } catch (LastError e) {
                updateTv("发生异常:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private class ReadThread1 implements Runnable {
        private Thread thread;

        public void start() {
            stop();
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }

        public void stop() {
            try {
                if (thread != null && thread.isAlive()) thread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            thread = null;
        }

        @Override
        public void run() {
            try {
                while (manager.isOpen()) {
                    try {
                        byte[] buffer = new byte[2048];
                        int length = manager.read(buffer, buffer.length, 50, 1);
                        System.out.println("Van串口运行中... [" + length + "]" + DataUtil.bytes2HexString(buffer, length));
                        if (length > 1) {
                            rX += length;
                            byte[] data = new byte[length];
                            System.arraycopy(buffer, 0, data, 0, length);
                            updateTv("vr:" + new String(data));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SystemClock.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void stop1() {
        if (readThread1 != null) readThread1.stop();
        if (manager != null) manager.close();
        readThread1 = null;
        manager = null;
    }

    void power(String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/devices/soc/10003000.keypad/scantrigger_enable"));
            writer.write(value);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            updateTv("触发异常:" + e.getMessage());
            e.printStackTrace();
        }
    }

    void write(String value) {
        try {
            File writePath = new File(path);
            if (writePath.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(writePath));
                writer.write(value);
//            writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            updateTv("触发异常:" + e.getMessage());
            e.printStackTrace();
        }
    }

    void updateTv(String message) {
        runOnUiThread(() -> {
            tvCount.setText("Rx:" + rX + " Tx:" + tX);
            tv.append("\n" + message);
            int offset = tv.getLineCount() * tv.getLineHeight() - tv.getHeight() + 10;
            if (offset > 10000) tv.setText("");
            else tv.scrollTo(0, offset);
        });
    }
}