package com.scanner.hardware.util;

/**
 * <a href="https://supportcommunity.zebra.cn/s/article/Calculate-the-checksum-for-SSI-command">计算SSI指令的校验和</a>
 * <a href="https://www.zebra.com/content/dam/zebra_new_ia/en-us/manuals/barcode-scanners/software/ssi-corded-pg-en.pdf">串口命令指南</a>
 */
public class DataUtil {
    //计算SSI指令的校验和
    public static String makeChecksum(String data) {
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        StringBuilder str = new StringBuilder(Integer.toHexString(total));
        while (str.length() < 4) str.insert(0, "0");
        byte[] bytes = parseHexStr2Byte(str.toString());
        byte temp;
        for (int i = 0; i < bytes.length; i++) {
//            System.out.println(bytes[i]);
            temp = bytes[i];
            bytes[i] = (byte) (~temp);
        }
        String bths = parseByte2HexStr(bytes);
        System.err.println("按位取反后的结果:" + bths);
        long i = Long.parseLong(bths, 16) + Long.parseLong("0001", 16);
        String string = Long.toHexString(i);
        System.err.println("按位取反后加1的结果:" + string);
        return string;
    }

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) hex = '0' + hex;
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
