package com.andorid.scandemo.utils;

public class DataUtil {
    //数组拼接 {0x01} {0x02 0x03} -> {0x01,0x02,0x03}
    public static byte[] byteArrayAddByteArray(byte[] id, byte[] data) {
        byte[] resultData = new byte[id.length + data.length];
        System.arraycopy(id, 0, resultData, 0, id.length);
        resultData[id.length] = (byte) data.length;
        System.arraycopy(data, 0, resultData, id.length, data.length);
        return resultData;
    }

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
}
