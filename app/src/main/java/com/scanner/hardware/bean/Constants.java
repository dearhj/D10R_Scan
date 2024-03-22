package com.scanner.hardware.bean;

public class Constants {
    //0 索引表示默认没有值
    public static final int DTMoToSE655 = 1;//摩托一维码
    public static final int DTMoTOSE2707 = 2;//摩托二维码
    public static final int DTHONEYWELLONE = 3;//honeywell一维码
    public static final int DTHONEYWELL = 4;//代表honeywell的二维码类型
    public static final int DTTotinfo = 5;  // 图腾
    public static final int DTNewland = 6;  // 新大陆
    public static final String[] voices = {"voice0", "voice1", "voice2"};
    public static final Integer[] baudRate = {115200, 9600};
    public static final String configPath = "vendor/etc/scanner_config.xml";

    public enum ReadType {SCAN, CMD}

    /******************霍尼韦尔一维码设置**************************/
    public static String[] symbolsSig = {//一维码制名称
            "Straight 2 of 5 IATA", "China Post", "codebar", "code 11",
            "Code 128", "code 32 Pharmaceutical", "Code 39", "code 93",
            "EAN/JAN-13", "EAN/JAN-8", "GS1 DataBar Expanded", "GS1 DataBar Limited",
            "GS1 DataBar Omnidirectional", "GS1-128", "Interleaved 2 of 5", "ISBT 128",
            "Matrix 2 of 5", "MSI", "NEC 2 of 5", "Plessey Code",
            "Postal Codes", "Straight 2 of 5 Industrial", "Telepen", "Trioptic Code",
            "UPC-A/EAN-13(Allow Concatenation)", "UPC-A/EAN-13(Require Concatenation)", "UPC-E0", "GS1-128 Emulation",
            "GS1 DataBar Emulation", "GS1 Code Expansion Off", "EAN8 to EAN13 Conversion"
    };
    public static String[] commandsSig = {//一维码制命令
            "a25ena1.", "cpcena1.", "cbrena1.", "c11ena1.",
            "128ena1.", "c39b321.", "c39ena1.", "c93ena1.",
            "e13ena1.", "ea8ena1.", "rseena1.", "rslena1.",
            "rssena1.", "gs1ena1.", "i25ena1.", "isbena1.",
            "x25ena1.", "msiena1.", "n25ena1.", "plsena1.",
            "cpcena1.", "r25ena1.", "telena1.", "triena1.",
            "cpnena1.", "cpnena2.", "upeen01.", "eanemu1.",
            "eanemu2.", "eanemu3.", "eanemu4."
    };
    public static String[] commandsSig_default = {    //一维默认码制
            "a25ena0.", "cpcena0.", "cbrena1.", "c11ena0.",
            "128ena1.", "c39b320.", "c39ena1.", "c93ena1.",
            "e13ena1.", "ea8ena1.", "rseena1.", "rslena1.",
            "rssena1.", "gs1ena1.", "i25ena1.", "isbena0.",
            "x25ena0.", "msiena0.", "n25ena0.", "plsena0.",
            "cpcena0.", "r25ena0.", "telena0.", "triena0.",
            "cpnena0.", "cpnena0.", "upeen01.", "eanemu0.",
            "eanemu0.", "eanemu0.", "eanemu0."
    };
    /******************摩托罗拉一维码设置**************************/
    //摩托一维码名称
    public static String[] motoSigName = {
            "UPC A",
            "UPC E",
            "UPC E1",
            "EAN-8/JAN 8",
            "Code 128",
            "Code 39",
            "Code 93",
            "Code 11",
            "Interleaved 2 of 5",
            "Codabar",
            "GS1 DataBar-14"
    };
    //摩托一维码默认值
    public static int[] motoSig_default = {1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1};

    //摩托一维码码制（开）
    public static byte[][] motoSigValueOpen = {
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x01, 0x01, (byte) 0xFE, 0x2E},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x02, 0x01, (byte) 0xFE, 0x2D},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x0C, 0x01, (byte) 0xFE, 0x23},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x04, 0x01, (byte) 0xFE, 0x2B},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x08, 0x01, (byte) 0xFE, 0x27},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x00, 0x01, (byte) 0xFE, 0x2F},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x09, 0x01, (byte) 0xFE, 0x26},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x0A, 0x01, (byte) 0xFE, 0x25},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x06, 0x01, (byte) 0xFE, 0x29},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x07, 0x01, (byte) 0xFE, 0x28},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x52, 0x01, (byte) 0xFD, (byte) 0xDD}
    };
    //摩托一维码码制（关）
    public static byte[][] motoSigValueClose = {
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x01, 0x00, (byte) 0xFE, 0x2F},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x02, 0x00, (byte) 0xFE, 0x2E},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x0C, 0x00, (byte) 0xFE, 0x24},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x04, 0x00, (byte) 0xFE, 0x2C},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x08, 0x00, (byte) 0xFE, 0x28},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x00, 0x00, (byte) 0xFE, 0x30},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x09, 0x00, (byte) 0xFE, 0x27},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x0A, 0x00, (byte) 0xFE, 0x26},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x06, 0x00, (byte) 0xFE, 0x2A},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x07, 0x00, (byte) 0xFE, 0x29},
            {0x07, (byte) 0xC6, 0x04, 0x00, (byte) 0xFF, 0x52, 0x00, (byte) 0xFD, (byte) 0xDE}
    };
}
