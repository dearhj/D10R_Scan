package com.android.spdemo;

import java.io.IOException;

public class DataUtil {
    public static String bytes2HexString(byte[] data) {
        return bytes2HexString(data, data.length);
    }

    public static String bytes2HexString(byte[] data, int length) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < length; i++) {
            int value = data[i] & 0xff;
            sb.append(HEX[value / 16]).append(HEX[value % 16]).append(" ");
        }
        return sb.toString();
    }

    /**
     * @param s input string like : 000102030405060708
     * @return byte[] b={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08}
     */
    public static byte[] int2bytes2(String s) {
        byte[] data;
        try {
            s = s.replace(" ", "");
            if (s.length() % 2 != 0) {
                s = s.substring(0, s.length() - 1) + "0" + s.substring(s.length() - 1, s.length());
            }
            data = new byte[s.length() / 2];
            for (int j = 0; j < data.length; j++) {
                data[j] = (byte) (Integer.valueOf(s.substring(j * 2, j * 2 + 2), 16) & 0xff);
            }
        } catch (Exception e) {
            e.printStackTrace();//NumberFormatException
            data = new byte[]{0x45, 0x52, 0x52, 0x4F, 0x52};
        }
        return data;
    }

    //数组拼接 {0x01} {0x02 0x03} -> {0x01,0x02,0x03}
    public static byte[] byteArrayAddByteArray(byte[] id, byte[] data) {
        byte[] resultData = new byte[id.length + data.length];
        System.arraycopy(id, 0, resultData, 0, id.length);
        resultData[id.length] = (byte) data.length;
        System.arraycopy(data, 0, resultData, id.length, data.length);
        return resultData;
    }

    /**
     * 将源数组追加到目标数组
     *
     * @param byte_1 Sou1原数组1
     * @param byte_2 Sou2原数组2
     * @param size   长度
     * @return bytestr 返回一个新的数组，包括了原数组1和原数组2
     */
    public static byte[] arrayAppend(byte[] byte_1, byte[] byte_2, int size) {
        // java 合并两个byte数组
        if (byte_1 == null && byte_2 == null) {
            return null;
        } else if (byte_1 == null) {
            byte[] byte_3 = new byte[size];
            System.arraycopy(byte_2, 0, byte_3, 0, size);
            return byte_3;
            //return byte_2;
        } else if (byte_2 == null) {
            byte[] byte_3 = new byte[byte_1.length];
            System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
            return byte_3;
            //return byte_1;
        } else {
            byte[] byte_3 = new byte[byte_1.length + size];
            System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
            System.arraycopy(byte_2, 0, byte_3, byte_1.length, size);
            return byte_3;
        }

    }

    public static void test(String cmd) {
//        String cmd = String.format("echo %s > %s\n", arg, mSwitchUsbFileTextEdit.getText().toString());
        try {
            Process exeEcho = Runtime.getRuntime().exec("sh");
            exeEcho.getOutputStream().write(cmd.getBytes());
            exeEcho.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
