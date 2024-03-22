package com.scanner.hardware.util

import com.scanner.hardware.bean.CodeFunction
import com.scanner.hardware.bean.LengthFunction

/**
 * CodeObj 码制对象
 * codeName 码制名称
 * functions 支持的功能列表
 */
data class CodeObj(
    val codeName: String,
    val functions: List<CodeFunction>
)

fun minLength(min: Int, max: Int, command: String): CodeFunction {
    return CodeFunction(
        "Minimum Message Length",
        listOf(),
        LengthFunction(min, max, "${command}min")
    )
}

fun maxLength(min: Int, max: Int, command: String): CodeFunction {
    return CodeFunction(
        "Maximum Message Length",
        listOf(),
        LengthFunction(min, max, "${command}max")
    )
}

//通过传入码制拼接查询命令
fun codePages(codeName: String): CodeFunction {
    return CodeFunction(
        "Code Page",
        listOf(
            Pair("United States(ISO/IEC 646-06)", "${codeName}dcp0."),
            Pair("Canada (ISO/IEC 646-121)", "${codeName}dcp54."),
            Pair("Canada (ISO/IEC 646-122)", "${codeName}dcp18."),
            Pair("Japan (ISO/IEC 646-14)", "${codeName}dcp28."),
            Pair("China (ISO/IEC 646-57)", "${codeName}dcp92."),
            Pair("Great Britain (UK) (ISO /IEC 646-04)", "${codeName}dcp7."),
            Pair("France (ISO /IEC 646-69)", "${codeName}dcp3."),
            Pair("Germany (ISO/IEC646-21)", "${codeName}dcp4."),
            Pair("Switzerland (ISO /IEC 646-CH)", "${codeName}dcp6."),
            Pair("Sweden / Finland (extended Annex C)(ISO/IEC 646-11)", "${codeName}dcp2."),
            Pair("Ireland (ISO /IEC 646-207)", "${codeName}dcp73."),
            Pair("Denmark (ISO/IEC 646-08)", "${codeName}dcp8."),
            Pair("Norway (ISO/IEC 646-60)", "${codeName}dcp9."),
            Pair("Italy (ISO/IEC 646-15)", "${codeName}dcp5."),
            Pair("Portugal (ISO/IEC 646-16)", "${codeName}dcp13."),
            Pair("Spain (ISO/IEC 646-17)", "${codeName}dcp10."),
            Pair("Spain (ISO/IEC 646-85)", "${codeName}dcp51."),
        ),
        "${codeName}dcp"
    )
}

/**
 * 霍尼韦尔二维码制设置项
 */
fun getHuoNi(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "Aztec Code", listOf(
                CodeFunction(
                    "Default All Aztec Code Settings",
                    listOf(Pair("Default Settings", "aztdft.")),
                    "aztdft"
                ),
                CodeFunction(
                    "Aztec Code On/Off",
                    listOf(Pair("On", "aztena1."), Pair("Off", "aztena0.")),
                    "aztena"
                ),
                minLength(1, 3832, "azt"),
                maxLength(1, 3832, "azt"),
                CodeFunction(
                    "Aztec Append",
                    listOf(Pair("On", "aztapp1."), Pair("Off", "aztapp0.")),
                    "aztapp"
                ),
                codePages("azt")
            )
        ),
        CodeObj(
            "China Post (Hong Kong 2 of 5)",
            listOf(
                CodeFunction(
                    "Default All China Post (Hong Kong 2 of 5) Settings",
                    listOf(Pair("Default Settings", "cpcdft.")), "cpcdft"
                ),
                CodeFunction(
                    "China Post (Hong Kong 2 of 5) On/Off",
                    listOf(Pair("On", "cpcena1."), Pair("Off", "cpcena0.")), "cpcena"
                ),
                //默认值4
                minLength(2, 80, "cpc"),
                maxLength(2, 80, "cpc"),
            )
        ),
        CodeObj(
            "Chinese Sensible (Han Xin) Code",
            listOf(
                CodeFunction(
                    "Default All Han Xin Settings",
                    listOf(Pair("Default Settings", "hx_dft.")), "hx_dft"
                ),
                //汉信码乱码可以设置这条命令后正常
                CodeFunction(
                    "Unreadable Code",
                    listOf(Pair("Off", "decset40013602:0."))
                ),
                CodeFunction(
                    "Han Xin Code On/Off",
                    listOf(Pair("On", "hx_ena1."), Pair("Off", "hx_ena0.")), "hx_ena"
                ),
                //默认值4
                minLength(1, 7833, "hx_"),
                maxLength(1, 7833, "hx_"),
            )
        ),
        CodeObj(
            "Codabar",
            listOf(
                CodeFunction(
                    "Default All Codabar Settings",
                    listOf(Pair("Default Settings", "cbrdft.")), "cbrdft"
                ),
                CodeFunction(
                    "Codabar On/Off",
                    listOf(Pair("On", "cbrena1."), Pair("Off", "cbrena0.")), "cbrena"
                ),
                CodeFunction(
                    "Codabar Start/Stop Characters",
                    listOf(Pair("Transmit", "cbrssx1."), Pair("Don’t Transmit", "cbrssx0.")),
                    "cbrssx"
                ),
                CodeFunction(
                    "Codabar Check Character",
                    listOf(
                        Pair("No Check Character", "cbrck20."),
                        Pair("Validate Modulo 16, but Don’t Transmit", "cbrck21."),
                        Pair("Validate Modulo 16 and Transmit", "cbrck22.")
                    ), "cbrck2"
                ),
                CodeFunction(
                    "Codabar Concatenation",
                    listOf(
                        Pair("On", "cbrcct1."),
                        Pair("Off", "cbrcct0."),
                        Pair("Require", "cbrcct2.")
                    ), "cbrcct"
                ),
                //默认值4
                minLength(2, 60, "cbr"),
                maxLength(2, 60, "cbr"),
            )
        ),
        CodeObj(
            "Codablock A", listOf(
//                CodeFunction(
//                    "Default All Codablock A Settings",
//                    listOf(Pair("Default Settings", "cbaft.")), "cbaft"
//                ),
                CodeFunction(
                    "Codablock A On/Off",
                    listOf(Pair("On", "cbaena1."), Pair("Off", "cbaena0.")), "cbaena"
                ),
                minLength(1, 600, "cba"),
                maxLength(1, 600, "cba"),
            )
        ),
        CodeObj(
            "Codablock F", listOf(
//                CodeFunction(
//                    "Default All Codablock F Settings",
//                    listOf(Pair("Default Settings", "cbfft.")), "cbfft"
//                ),
                CodeFunction(
                    "Codablock F On/Off",
                    listOf(Pair("On", "cbfena1."), Pair("Off", "cbfena0.")), "cbfena"
                ),
                minLength(1, 2048, "cbf"),
                maxLength(1, 2048, "cbf"),
            )
        ),
        CodeObj(
            "Code 11", listOf(
                CodeFunction(
                    "Default All Code 11 Settings",
                    listOf(Pair("Default Settings", "c11dft.")), "c11dft"
                ),
                CodeFunction(
                    "Code 11 On/Off",
                    listOf(Pair("On", "c11ena1."), Pair("Off", "c11ena0.")), "c11ena"
                ),
                CodeFunction(
                    "Check Digits Required",
                    listOf(
                        Pair("One Check Digit", "c11ck20."),
                        Pair("Two Check Digits", "c11ck21."),
                    ), "c11ck2"
                ),
                //默认值4
                minLength(1, 80, "c11"),
                maxLength(1, 80, "c11"),
            )
        ),
        CodeObj(
            "Code 128", listOf(
                CodeFunction(
                    "Default All Code 128 Settings",
                    listOf(Pair("Default Settings", "128dft.")), "128dft"
                ),
                CodeFunction(
                    "Code 128 On/Off",
                    listOf(Pair("On", "128ena1."), Pair("Off", "128ena0.")), "128ena"
                ),
                CodeFunction(
                    "ISBT 128 Concatenation",
                    listOf(
                        Pair("On", "isbena1."),
                        Pair("Off", "isbena0."),
                    ), "isbena"
                ),
                minLength(0, 80, "128"),
                maxLength(0, 80, "128"),
                CodeFunction(
                    "Code 128 Append",
                    listOf(Pair("On", "128app1."), Pair("Off", "128app0.")), "128app"
                ),
                codePages("128")
            )
        ),
        CodeObj(
            "Code 32 Pharmaceutical(PARAF)",
            listOf(
                CodeFunction(
                    "Code 32 On/Off",
                    listOf(Pair("On", "c39b321."), Pair("Off", "c39b320.")), "c39b32"
                ),
                CodeFunction(
                    "Full ASCII",
                    listOf(Pair("Full ASCII On", "c39asc1."), Pair("Full ASCII Off", "c39asc0.")),
                    "c39asc"
                )
            )
        ),
        CodeObj(
            "Code 39", listOf(
                CodeFunction(
                    "Default All Code 39 Settings",
                    listOf(Pair("Default Settings", "c39dft.")), "c39dft"
                ),
                CodeFunction(
                    "Code 39 On/Off",
                    listOf(Pair("On", "c39ena1."), Pair("Off", "c39ena0.")), "c39ena"
                ),
                CodeFunction(
                    "Code 39 Start/Stop Characters",
                    listOf(Pair("Transmit", "c39ssx1."), Pair("Don’t Transmit", "c39ssx0.")),
                    "c39ssx"
                ),
                CodeFunction(
                    "Code 39 Check Character",
                    listOf(
                        Pair("No Check Character", "c39ck20."),
                        Pair("Validate, but Don’t Transmit", "c39ck21."),
                        Pair("Validate and Transmit", "c39ck22.")
                    ), "c39ck2"
                ),
                minLength(0, 48, "c39"),
                maxLength(0, 48, "c39"),
                CodeFunction(
                    "Code 39 Append",
                    listOf(Pair("On", "c39app1."), Pair("Off", "c39app0.")), "c39app"
                ),
                codePages("c39")
            )
        ),
        CodeObj(
            "Code 93", listOf(
                CodeFunction(
                    "Default All Code 93 Settings",
                    listOf(Pair("Default Settings", "c93dft.")), "c93dft"
                ),
                CodeFunction(
                    "Code 93 On/Off",
                    listOf(Pair("On", "c93ena1."), Pair("Off", "c93ena0.")), "c93ena"
                ),
                minLength(0, 80, "c93"),
                maxLength(0, 80, "c93"),
                CodeFunction(
                    "Code 93 Append",
                    listOf(Pair("On", "c93app1."), Pair("Off", "c93app0.")), "c93app"
                ),
                codePages("c93")
            )
        ),
        CodeObj(
            "Data Matrix",
            listOf(
                CodeFunction(
                    "Default All Data Matrix Settings",
                    listOf(Pair("Default Settings", "idmdft.")), "idmdft"
                ),
                CodeFunction(
                    "Data Matrix On/Off",
                    listOf(Pair("On", "idmena1."), Pair("Off", "idmena0.")), "idmena"
                ),
                minLength(1, 3116, "idm"),
                maxLength(1, 3116, "idm"),
                CodeFunction(
                    "Data Matrix Append",
                    listOf(Pair("On", "idmapp1."), Pair("Off", "idmapp0.")), "idmapp"
                ),
                codePages("idm")
            )
        ),
        CodeObj(
            "EAN/JAN-13",
            listOf(
                CodeFunction(
                    "Default All EAN/JAN-13 Settings",
                    listOf(Pair("Default Settings", "e13dft.")), "e13dft"
                ),
                CodeFunction(
                    "EAN/JAN-13 On/Off",
                    listOf(Pair("On", "e13ena1."), Pair("Off", "e13ena0.")),
                    "e13ena"
                ),
                CodeFunction(
                    "Convert UPC-A to EAN-13",
                    listOf(
                        Pair("UPC-A Converted to EAN-13", "upaena0."),
                        Pair("Don't Convert UPC-A", "upaena1.")
                    ), "upaena"
                ),
                CodeFunction(
                    "EAN/JAN-13 Check Digit",
                    listOf(
                        Pair("On", "e13ckx1."),
                        Pair("Off", "e13ckx0.")
                    ), "e13ckx"
                ),
                CodeFunction(
                    "ENA/JAN-13 2 Digit Addenda",
                    listOf(
                        Pair("2 Digit Addenda On", "e13ad21."),
                        Pair("2 Digit Addenda Off", "e13ad20."),
                    ), "e13ad2"
                ),
                CodeFunction(
                    "ENA/JAN-13 5 Digit Addenda",
                    listOf(
                        Pair("5 Digit Addenda On", "e13ad51."),
                        Pair("5 Digit Addenda Off", "e13ad50.")
                    ), "e13ad5"
                ),
                CodeFunction(
                    "ENA/JAN-13 Addenda Required",
                    listOf(Pair("Required", "e13arq1."), Pair("Not Required", "e13arq0.")), "e13arq"
                ),
                CodeFunction(
                    "EAN/JAN-13 Addenda Separator",
                    listOf(Pair("On", "e13ads1."), Pair("Off", "e13ads0.")), "e13ads"
                ),
                CodeFunction(
                    "ISBN Translate",
                    listOf(Pair("On", "e13isb1."), Pair("Off", "e13isb0.")), "e13isb"
                ),
            )
        ),
        CodeObj(
            "EAN/JAN-8", listOf(
                CodeFunction(
                    "Default All EAN/JAN-8 Settings",
                    listOf(Pair("Default Settings", "ea8dft.")), "ea8dft"
                ),
                CodeFunction(
                    "On/Off",
                    listOf(Pair("On", "ea8ena1."), Pair("Off", "ea8ena0.")),
                    "ea8ena"
                ),
                CodeFunction(
                    "EAN/JAN-8 Check Digit",
                    listOf(Pair("On", "ea8ckx1."), Pair("Off", "ea8ckx0.")), "ea8ckx"
                ),
                CodeFunction(
                    "EAN/JAN-8 2 Digit Addenda",
                    listOf(
                        Pair("2 Digit Addenda On", "ea8ad21."),
                        Pair("2 Digit Addenda Off", "ea8ad20."),
                    ), "ea8ad2"
                ),
                CodeFunction(
                    "EAN/JAN-8 5 Digit Addenda",
                    listOf(
                        Pair("5 Digit Addenda On", "ea8ad51."),
                        Pair("5 Digit Addenda Off", "ea8ad50.")
                    ), "ea8ad5"
                ),
                CodeFunction(
                    "EAN/JAN-8 Addenda Required",
                    listOf(Pair("Required", "ea8arq1."), Pair("Not Required", "ea8arq0.")), "ea8arq"
                ),
                CodeFunction(
                    "EAN/JAN-8 Addenda Separator",
                    listOf(Pair("On", "ea8ads1."), Pair("Off", "ea8ads0.")), "ea8ads"
                ),
            )
        ),
        CodeObj(
            "GS1 Composite Codes", listOf(
                CodeFunction(
                    "GS1 DataBar Codes On/Off",
                    listOf(Pair("On", "comena1."), Pair("Off", "comena0.")), "comena"
                ),
                CodeFunction(
                    "UPC/EAN Version",
                    listOf(
                        Pair("UPC/EAN Version On", "comupc1."),
                        Pair("UPC/EAN Version Off", "comupc0.")
                    ), "comupc"
                ),
                minLength(1, 2435, "com"),
                maxLength(1, 2435, "com"),
                CodeFunction(
                    "GS1 Emulation", listOf(
                        Pair("GS1-128 Emulation", "eanemu1."),
                        Pair("GS1 DataBar Emulation", "eanemu2."),
                        Pair("GS1 Code Expansion Off", "eanemu3."),
                        Pair("EAN8 to EAN13 Conversion", "eanemu4."),
                        Pair("GS1 Emulation Off", "eanemu0.")
                    ), "eanemu"
                )
            )
        ),
        CodeObj(
            "GS1 DataBar Expanded", listOf(
                CodeFunction(
                    "Default All GS1 DataBar Expanded Settings",
                    listOf(Pair("Default Settings", "rsedft.")), "rsedft"
                ),
                CodeFunction(
                    "GS1 DataBar Limited On/Off",
                    listOf(Pair("On", "rseena1."), Pair("Off", "rseena0.")), "rseena"
                ),
                minLength(4, 74, "rse"),
                maxLength(4, 74, "rse"),
            )
        ),
        CodeObj(
            "GS1 DataBar Limited", listOf(
                CodeFunction(
                    "Default All GS1 DataBar Limited Settings",
                    listOf(Pair("Default Settings", "rsldft.")), "rsldft"
                ),
                CodeFunction(
                    "GS1 DataBar Limited On/Off",
                    listOf(Pair("On", "rslena1."), Pair("Off", "rslena0.")), "rslena"
                ),
            )
        ),
        CodeObj(
            "GS1 DataBar Omnidirectional", listOf(
                CodeFunction(
                    "Default All GS1 DataBar Omnidirectional Settings",
                    listOf(Pair("Default Settings", "rssdft.")), "rssdft"
                ),
                CodeFunction(
                    "GS1 DataBar Omnidirectional On/Off",
                    listOf(Pair("On", "rssena1."), Pair("Off", "rssena0.")), "rssena"
                ),
            )
        ),
        CodeObj(
            "Coupon GS1 DataBar Output",
            listOf(
                CodeFunction(
                    "On/Off",
                    listOf(Pair("Gs1 Output On", "cpngs11."), Pair("Gs1 Output Off", "cpngs10.")),
                    "cpngs1"
                )
            )
        ),
        CodeObj(
            "GS1-128", listOf(
                CodeFunction(
                    "Default All GS1-128 Settings",
                    listOf(Pair("Default Settings", "gs1dft.")), "gs1dft"
                ),
                CodeFunction(
                    "On/Off",
                    listOf(Pair("On", "gs1ena1."), Pair("Off", "gs1ena0.")),
                    "gs1ena"
                ),
                minLength(0, 80, "128"),
                maxLength(0, 90, "128"),
            )
        ),
        CodeObj(
            "Interleaved 2 of 5", listOf(
                CodeFunction(
                    "Default All Interleaved 2 of 5 Settings",
                    listOf(Pair("Default Settings", "i25dft.")), "i25dft"
                ),
                CodeFunction(
                    "On/Off",
                    listOf(Pair("On", "i25ena1."), Pair("Off", "i25ena0.")),
                    "i25ena"
                ),
                CodeFunction(
                    "Check Digit",
                    listOf(
                        Pair("No Check Digit", "i25ck20."),
                        Pair("Validate,but Don't Transmit", "i25ck21."),
                        Pair("Validate,Transmit", "i25ck22."),
                    ), "i25ck2"
                ),
                //默认值4
                minLength(2, 80, "i25"),
                maxLength(2, 80, "i25"),
            )
        ),
        CodeObj(
            "Korea Post",
            listOf(
                CodeFunction(
                    "Default All Korea Post Settings",
                    listOf(Pair("Default Settings", "kpcdft.")), "kpcdft"
                ),
                CodeFunction(
                    "Korea Post On/Off",
                    listOf(Pair("On", "kpcena1."), Pair("Off", "kpcena0.")), "kpcena"
                ),
                //默认值4
                minLength(2, 80, "kpc"),
                //默认值48
                maxLength(2, 80, "kpc"),
                CodeFunction(
                    "Korea Post Check Digit",
                    listOf(
                        Pair("Transmit Check Digit", "kpcchk1."),
                        Pair("Don’t Transmit Check Digit", "kpcchk0.")
                    ), "kpcchk"
                ),
            )
        ),
        CodeObj(
            "Matrix 2 of 5", listOf(
                CodeFunction(
                    "Default All Matrix 2 of 5 Setting",
                    listOf(Pair("Default Settings", "x25dft.")), "x25dft"
                ),
                CodeFunction(
                    "Matrix 2 of 5 On/Off",
                    listOf(Pair("On", "x25ena1."), Pair("Off", "x25ena0.")), "x25ena"
                ),
                //默认值4
                minLength(1, 80, "x25"),
                maxLength(1, 80, "x25"),
            )
        ),
        CodeObj(
            "MaxiCode", listOf(
                CodeFunction(
                    "Default All MaxiCode Settings",
                    listOf(Pair("Default Settings", "maxdft.")), "maxdft"
                ),
                CodeFunction(
                    "MaxiCode On/Off",
                    listOf(Pair("On", "maxena1."), Pair("Off", "maxena0.")), "maxena"
                ),
                minLength(1, 150, "max"),
                maxLength(1, 150, "max"),
            )
        ),
        CodeObj(
            "MSI", listOf(
                CodeFunction(
                    "Default All MSI Setting",
                    listOf(Pair("Default Settings", "msidft.")), "msidft"
                ),
                CodeFunction(
                    "MSI On/Off",
                    listOf(Pair("On", "msiena1."), Pair("Off", "msiena0.")),
                    "msiena"
                ),
                CodeFunction(
                    "MSI Check Character",
                    listOf(
                        Pair("Validata Type 10,but Don't Transmit", "msichk0."),
                        Pair("Validata Type 10 and Transmit", "msichk1."),
                        Pair("Validata 2 Type 10 Characters,but Don't Transmit", "msichk2."),
                        Pair("Validata 2 Type 10 Characters and Transmit", "msichk3."),
                        Pair(
                            "Validata Type 11 then Type 10 Characters,but Don't Transmit",
                            "msichk4."
                        ),
                        Pair("Validata Type 11 then Type 10 Characters and Transmit", "msichk5."),
                        Pair("Disable MSI Check Characters", "msichk6.")
                    ), "msichk"
                ),
                minLength(4, 48, "msi"),
                maxLength(4, 48, "msi"),
            )
        ),
        CodeObj(
            "NEC 2 of 5", listOf(
                CodeFunction(
                    "Default All NEC 2 of 5 Setting",
                    listOf(Pair("Default Settings", "n25dft.")), "n25dft"
                ),
                CodeFunction(
                    "On/Off",
                    listOf(Pair("On", "n25ena1."), Pair("Off", "n25ena0.")),
                    "n25ena"
                ),
                CodeFunction(
                    "Check Digit",
                    listOf(
                        Pair("No Check Digit", "n25ck20."),
                        Pair("Validate,but Don't Transmit", "n25ck21."),
                        Pair("Validate,Transmit", "n25ck22."),
                    ), "n25ck2"
                ),
                minLength(2, 80, "n25"),
                maxLength(2, 80, "n25"),
            )
        ),
        CodeObj(
            "PDF417", listOf(
                CodeFunction(
                    "Default All PDF417 Settings",
                    listOf(Pair("Default Settings", "pdfdft.")), "pdfdft"
                ),
                CodeFunction(
                    "PDF417 On/Off",
                    listOf(Pair("On", "pdfena1."), Pair("Off", "pdfena0.")), "pdfena"
                ),
                minLength(1, 2750, "pdf"),
                maxLength(1, 2750, "pdf"),
                CodeFunction(
                    "MacroPDF417",
                    listOf(Pair("On", "pdfmac1."), Pair("Off", "pdfmac0.")), "pdfmac"
                )
            )
        ),
        CodeObj(
            "MacroPDF417", listOf(
                CodeFunction(
                    "Default All MicroPDF417 Settings",
                    listOf(Pair("On", "mpddft.")),
                    "mpddft"
                ),
                CodeFunction(
                    "MicroPDF417 On/Off",
                    listOf(Pair("On", "mpdena1."), Pair("Off", "mpdena0.")), "mpdena"
                ),
                minLength(1, 366, "mpd"),
                maxLength(1, 366, "mpd"),
            )
        ),
        CodeObj(
            "Straight 2 of 5 IATA (two-bar start/stop)", listOf(
                CodeFunction(
                    "Default All Straight 2 of 5 IATA Setting",
                    listOf(Pair("Default Settings", "a25dft.")), "a25dft"
                ),
                CodeFunction(
                    "On/Off",
                    listOf(Pair("On", "a25ena1."), Pair("Off", "a25ena0.")),
                    "a25ena"
                ),
                minLength(1, 48, "a25"),
                maxLength(1, 48, "a25"),
            )
        ),
        CodeObj(
            "Straight 2 of 5 Industrial (three-bar start/stop)", listOf(
                CodeFunction(
                    "Default All Straight 2 of 5 Industrial Setting",
                    listOf(Pair("Default Settings", "r25dft.")), "r25dft"
                ),
                CodeFunction(
                    "Straight 2 of 5 Industrial On/Off",
                    listOf(Pair("On", "r25ena1."), Pair("Off", "r25ena0.")), "r25ena"
                ),
                minLength(1, 48, "r25"),
                maxLength(1, 48, "r25"),
            )
        ),
        CodeObj(
            "TCIF Linked Code 39 (TLC39)",
            listOf(
                CodeFunction(
                    "On/Off",
                    listOf(Pair("On", "t39ena1."), Pair("Off", "t39ena0.")),
                    "t39ena"
                )
            )
        ),
        CodeObj(
            "QR Code",
            listOf(
                CodeFunction(
                    "Default All QR Code Settings",
                    listOf(Pair("Default Settings", "r25dft.")), "r25dft"
                ),
                CodeFunction(
                    "QR Code On/Off",
                    listOf(Pair("On", "qrcena1."), Pair("Off", "qrcena0.")), "qrcena"
                ),
                minLength(1, 7089, "qrc"),
                maxLength(1, 7089, "qrc"),
                CodeFunction(
                    "QR Code Append",
                    listOf(Pair("On", "qrcapp1."), Pair("Max", "qrcapp0.")), "qrcapp"
                ),
                codePages("qrc")
            )
        ),
        CodeObj(
            "UPC-A", listOf(
                CodeFunction(
                    "Default All UPC-A Setting",
                    listOf(Pair("Default Settings", "upadft.")), "upadft"
                ),
                CodeFunction(
                    "UPC-A On/Off",
                    listOf(Pair("On", "upbena1."), Pair("Off", "upbena0.")), "upbena"
                ),
                CodeFunction(
                    "UPC-A Check Digit",
                    listOf(Pair("On", "upackx1."), Pair("Off", "upackx0.")), "upackx"
                ),
                CodeFunction(
                    "UPC-A Number System",
                    listOf(Pair("On", "upansx1."), Pair("Off", "upansx0.")), "upansx"
                ),
                CodeFunction(
                    "UPC-A 2 Digit Addenda",
                    listOf(
                        Pair("2 Digit Addenda On", "upaad21."),
                        Pair("2 Digit Addenda Off", "upaad20."),
                    ), "upaad2"
                ),
                CodeFunction(
                    "UPC-A 5 Digit Addenda",
                    listOf(
                        Pair("5 Digit Addenda On", "upaad51."),
                        Pair("5 Digit Addenda Off", "upaad50.")
                    ), "upaad5"
                ),
                CodeFunction(
                    "UPC-A Addenda Required",
                    listOf(Pair("Required", "upaarq1."), Pair("Not Required", "upaarq0.")), "upaarq"
                ),
                CodeFunction(
                    "UPC-A Addenda Separator",
                    listOf(Pair("On", "upaads1."), Pair("Off", "upaads0.")), "upaads"
                ),
            )
        ),
        CodeObj(
            "UPC-A/EAN-13 with Extended Coupon Code(Allow Concatenation)",
            listOf(
                CodeFunction(
                    "Default Settings", listOf(
                        Pair("Off", "cpnena0."),
                        Pair("All Concatenation", "cpnena1."),
                        Pair("Require Concatenation", "cpnena2.")
                    ), "cpnena"
                ),
            )
        ),
        CodeObj(
            "UPC-E0", listOf(
                CodeFunction(
                    "Default All UPC-E Setting",
                    listOf(Pair("Default Settings", "upedft.")), "upedft"
                ),
                CodeFunction(
                    "UPC-E0 On/Off",
                    listOf(Pair("On", "upeen01."), Pair("Off", "upeen00.")), "upeen0"
                ),
                CodeFunction(
                    "UPC-E0 Expand",
                    listOf(Pair("On", "upeexp1."), Pair("Off", "upeexp0.")), "upeexp"
                ),
                CodeFunction(
                    "UPC-E0 Addenda Required",
                    listOf(Pair("Required", "upearq1."), Pair("Not Required", "upearq0.")), "upearq"
                ),
                CodeFunction(
                    "UPC-E0 Addenda Separator",
                    listOf(Pair("On", "upeads1."), Pair("Off", "upeads0.")), "upeads"
                ),
                CodeFunction(
                    "UPC-E0 Check Digit",
                    listOf(Pair("On", "upeckx1."), Pair("Off", "upeckx0.")), "upeckx"
                ),
                CodeFunction(
                    "UPC-E0 Leading Zero",
                    listOf(Pair("On", "upensx1."), Pair("Off", "upensx0.")), "upensx"
                ),
                CodeFunction(
                    "UPC-E0 2 Digit Addenda",
                    listOf(
                        Pair("2 Digit Addenda On", "upead21."),
                        Pair("2 Digit Addenda Off", "upead20."),
                    ), "upead2"
                ),
                CodeFunction(
                    "UPC-E0 5 Digit Addenda",
                    listOf(
                        Pair("5 Digit Addenda On", "upead51."),
                        Pair("5 Digit Addenda Off", "upead50.")
                    ), "upead5"
                ),
            )
        ),
        CodeObj(
            "UPC-E1", listOf(
                CodeFunction(
                    "UPC-E1 On/Off",
                    listOf(Pair("On", "upeen11."), Pair("Off", "upeen10.")), "upeen1"
                ),
            )
        ),
        CodeObj(
            "Postal Codes - 2D ",
            listOf(
                CodeFunction(
                    "Single 2D Postal Codes",
                    listOf(
                        //关闭
                        Pair("Off", "postal0."),
                        //1D
                        Pair("Australian Post On", "postal1."),
                        Pair("InfoMail On", "postal2."),
                        Pair("Japanese Post On", "postal3."),
                        Pair("KIX Post On", "postal4."),
                        Pair("Planet On", "postal5."),
                        Pair("Postnet On", "postal6."),
                        Pair("British Post On", "postal7."),
                        Pair("Canadian Post On", "postal30."),
                        Pair("Intelligent Mail Bar Post On", "postal10."),
                        Pair("Postal-4i On", "postal9."),
                        Pair("Postnet with B and B’ Fields On", "postal11."),
                        //2D
                        Pair("InfoMail and British Post On", "postal8."),
                        Pair("Planet Code and Postnet On", "postal12."),
                        Pair("Planet Code and Postal-4i On", "postal13."),
                        Pair("Postnet and Postal-4i On", "postal14."),
                        Pair("Planet Code and Intelligent Mail Bar Code On", "postal15."),
                        Pair("Postnet and Intelligent Mail Bar Code On", "postal16."),
                        Pair("Postal-4i and Intelligent Mail Bar Code On", "postal17."),
                        Pair("Planet Code and Postnet with B and B’ Fields On", "postal18."),
                        Pair("Postal-4i and Postnet with B and B’ Fields On", "postal19."),
                        Pair(
                            "Intelligent Mail Bar Code and Postnet with B and B’ Fields On",
                            "postal20."
                        ),
                        Pair(
                            "Planet Code, Postnet,and Postal-4i On",
                            "postal21."
                        ),
                        Pair("Planet Code, Postnet, and Intelligent Mail Bar Code On", "postal22."),
                        Pair(
                            "Planet Code, Postal-4i, and Intelligent Mail Bar Code On",
                            "postal23."
                        ),
                        Pair("Postnet, Postal-4i, and Intelligent Mail Bar Code On", "postal24."),
                        Pair(
                            "Planet Code, Postal-4i, and Postnet with B and B’ Fields On",
                            "postal25."
                        ),
                        Pair(
                            "Planet Code, Intelligent Mail Bar Code, and Postnet with B and B’ Fields On",
                            "postal26."
                        ),
                        Pair(
                            "Postal-4i, Intelligent Mail Bar Code, and Postnet with B and B’ Fields On",
                            "postal27."
                        ),
                        Pair(
                            "Planet Code,Postal-4i,Intelligent Mail Bar Code, and Postnet On",
                            "postal28."
                        ),
                        Pair(
                            "Planet Code,Postal-4i,Intelligent Mail Bar Code, and Postnet with B and B’ Fields On",
                            "postal29."
                        ),
                    ), "postal"
                ),
            )
        ),
        CodeObj(
            "Mobile Phone Read Mode", listOf(
                CodeFunction(
                    "settings",
                    listOf(
                        Pair("Hand Held Scanning - Mobile Phone", "paphhc."),
                        Pair("Presentation Scanning - Mobile Phone", "pappsc.")
                    )
                ),
            )
        ),
        CodeObj(
            "Centering", listOf(
                CodeFunction(
                    "settings on/off",
                    listOf(Pair("Centering On", "decwin1."), Pair("Centering Off", "decwin0.")),
                    "decwin"
                ),
                CodeFunction(
                    "Top of Centering Window",
                    listOf(),
                    LengthFunction(0, 100, "dectop")
                ),
                CodeFunction(
                    "Bottom of Centering Window",
                    listOf(),
                    LengthFunction(0, 100, "decbot")
                ),
                CodeFunction(
                    "Left of Centering Window",
                    listOf(),
                    LengthFunction(0, 100, "declft")
                ),
                CodeFunction(
                    "Right of Centering Window",
                    listOf(),
                    LengthFunction(0, 100, "decrgt")
                )
            )
        ),
    )
}

//仅测试版本才有
fun addDebugCommand(): List<CodeObj> {
    return listOf(
        CodeObj(
            "other command", listOf(
                CodeFunction(
                    "other command",
                    listOf(
                        Pair(
                            "Add Code I.D. Prefix to All Symbologies (Temporary)",
                            "PRECA2,BK2995C80!"
                        ),
                        Pair("Show Decoder Revision", "rev_dr."),
                        Pair("Show Scan Driver Revision", "rev_sd."),
                        Pair("Show Software Revision", "revinf."),
                    )
                ),
            )
        )
    )
}


val addenda = CodeFunction(
    "Append",
    listOf(
        Pair("None(00)", "07 C6 04 08 00 10 00 FF 17"),
        Pair("Enable(01)", "07 C6 04 08 00 10 01 FF 16"),
        Pair("Auto(02)", "07 C6 04 08 00 10 02 FF 15"),
        Pair("Accurate model(03)", "07 C6 04 08 00 10 03 FF 14"),
        Pair("378/379 Append mode(04)", "07 C6 04 08 00 10 04 FF 13"),
        Pair("978 Append mode(05)", "07 C6 04 08 00 10 05 FF 12"),
    ), "05 C7 04 00 10 FF 20"
)

/**
 * 图腾码制设置
 */

fun getToinfoEN(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "Device Settings",
            listOf(
                CodeFunction(
                    "SCAN Read On/Off",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 90 00 FD A4"),
                        Pair("On", "08 C6 04 08 00 F2 90 01 FD A3"),
                    ), "06 C7 04 00 F2 90 FD AD"
                ),
                CodeFunction(
                    "1D Code Read On/Off",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 11 00 FE 23"),
                        Pair("On", "08 C6 04 08 00 F2 11 01 FE 22"),
                    ), "06 C7 04 00 F2 11 FE 2C"
                ),
                CodeFunction(
                    "2D Code Read On/Off",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 50 00 FD E4"),
                        Pair("On", "08 C6 04 08 00 F2 50 01 FD E3"),
                    ), "06 C7 04 00 F2 50 FD ED"
                ),
                CodeFunction(
                    "TimeOut Setting", listOf(
                        Pair("continuous", "08 C6 04 08 00 F2 CF 00 FD 65"),
                        Pair("3S", "08 C6 04 08 00 F2 CF 03 FD 62"),
                        Pair("5S", "08 C6 04 08 00 F2 CF 05 FD 60"),
                        Pair("10S", "08 C6 04 08 00 F2 CF 0A FD 5B"),
                        Pair("15S", "08 C6 04 08 00 F2 CF 0B FD 5A"),
                        Pair("20S", "08 C6 04 08 00 F2 CF 0C FD 59"),
                        Pair("30S", "08 C6 04 08 00 F2 CF 0D FD 58"),
                        Pair("60S", "08 C6 04 08 00 F2 CF 0E FD 57"),
                    ), "06 C7 04 00 F2 CF FD 6E"
                ),
                CodeFunction(
                    "Supports GS1 rules and uses parentheses to contain AI fields", listOf(
                        Pair("Off", "08 C6 04 08 00 F6 3C 00 FD F4"),
                        Pair("On", "08 C6 04 08 00 F6 3C 01 FD F3"),
                    ), "06 C7 04 00 F6 3C FD FD"
                ),
//                CodeFunction(
//                    "增值税发票自动识别输出", listOf(
//                        Pair("Off", "08 C6 04 08 00 F2 08 00 FE 2C"),
//                        Pair("On", "08 C6 04 08 00 F2 08 01 FE 2B"),
//                    ), "06 C7 04 00 F2 08 FE 35"
//                ),
//                CodeFunction(
//                    "发票类型", listOf(
//                        Pair("专用发票", "08 C6 04 08 00 F2 AA 00 FD 8A"),
//                        Pair("普通发票", "08 C6 04 08 00 F2 AA 01 FD 89"),
//                    ), "06 C7 04 00 F2 AA FD 93"
//                ),
                CodeFunction(
                    "Send encoding ID character", listOf(
                        Pair("Disable", "07 C6 04 08 00 2D 00 FE FA"),
                        Pair("AIM", "07 C6 04 08 00 2D 01 FE F9"),
                        Pair("Custom", "07 C6 04 08 00 2D 02 FE F8"),
                    ), "05 C7 04 00 2D FF 03"
                ),
                CodeFunction(
                    "Prefix/Suffix value", listOf(
                        Pair(
                            "prefix [0x31]/suffix [0x32,0x33]",
                            "0B C6 04 08 00 69 31 68 32 6A 33 FD 52"
                        ),
                        Pair(
                            "prefix [0x00]/suffix [0x0D,0x0A]",
                            "0B C6 04 08 00 69 00 68 0D 6A 0A FD D1"
                        ),
                    ), "05 C7 04 00 EB FE 45"
                ),
                CodeFunction(
                    "Data transmission format", listOf(
                        Pair("Code", "07 C6 04 08 00 EB 00 FE 3C"),
                        Pair("Code+[0x32,0x33]", "07 C6 04 08 00 EB 01 FE 3B"),
                        Pair("Code+[0x0D,0x0A]", "07 C6 04 08 00 EB 02 FE 3A"),
                        Pair("Code+[0x32,0x33,0x0D,0x0A]", "07 C6 04 08 00 EB 03 FE 39"),
                        Pair("Prefix+Code", "07 C6 04 08 00 EB 04 FE 38"),
                        Pair("Prefix+Code+[0x32,0x33]", "07 C6 04 08 00 EB 05 FE 37"),
                        Pair("Prefix+Code+[0x0D,0x0A]", "07 C6 04 08 00 EB 06 FE 36"),
                        Pair("Prefix+Code+[0x32,0x33,0x0D,0x0A]", "07 C6 04 08 00 EB 07 FE 35"),
                        Pair("Data+Multiple Suffix", "07 C6 04 08 00 EB 08 FE 34"),
                        Pair("Multiple Prefix+Data", "07 C6 04 08 00 EB 09 FE 33"),
                        Pair("Multiple Prefix+Data+Multiple Suffix", "07 C6 04 08 00 EB 0A FE 32"),
                    ), "07 C7 04 00 69 68 6A FD F3"
                ),
                CodeFunction(
                    "STX/ETX", listOf(
                        Pair("disable", "08 C6 04 08 00 F2 B7 00 FD 7D"),
                        Pair("STX(prefix)", "08 C6 04 08 00 F2 B7 01 FD 7C"),
                        Pair("ETX(suffix)", "08 C6 04 08 00 F2 B7 02 FD 7B"),
                        Pair("STX(prefix)+ETX(suffix)", "08 C6 04 08 00 F2 B7 03 FD 7A"),
                    ), "06 C7 04 00 F2 B7 FD 86"
                ),
                CodeFunction(
                    "terminator", listOf(
                        Pair("disable", "08 C6 04 08 00 F2 05 00 FE 2F"),
                        Pair("CR LF", "08 C6 04 08 00 F2 05 01 FE 2E"),
                        Pair("CR", "08 C6 04 08 00 F2 05 02 FE 2D"),
                        Pair("TAB", "08 C6 04 08 00 F2 05 03 FE 2C"),
                        Pair("CR CR", "08 C6 04 08 00 F2 05 04 FE 2B"),
                        Pair("CR LF CR LF", "08 C6 04 08 00 F2 05 05 FE 2A"),
                    ), "06 C7 04 00 F2 05 FE 38"
                ),
                CodeFunction(
                    "Output Encoding Character", listOf(
                        Pair("Original", "08 C6 04 08 00 F2 06 00 FE 2E"),
                        Pair("GBK(GB2312)", "08 C6 04 08 00 F2 06 01 FE 2D"),
                        Pair("UTF8", "08 C6 04 08 00 F2 06 02 FE 2C"),
                        Pair("EUC-KR", "08 C6 04 08 00 F2 06 03 FE 2B"),
                        Pair("DEC MCS", "08 C6 04 08 00 F2 06 04 FE 2A"),
                        Pair("BIG5", "08 C6 04 08 00 F2 06 05 FE 29"),
                    ), "06 C7 04 00 F2 06 FE 37"
                ),
                CodeFunction(
                    "Input Encoding Character", listOf(
                        Pair("AUTO", "08 C6 04 08 00 F2 AB 00 FD 89"),
                        Pair("GBK(GB2312)", "08 C6 04 08 00 F2 AB 01 FD 88"),
                        Pair("UTF8", "08 C6 04 08 00 F2 AB 02 FD 87"),
                        Pair("ASCII", "08 C6 04 08 00 F2 AB 03 FD 86"),
                        Pair("Japanese", "08 C6 04 08 00 F2 AB 04 FD 85"),
                        Pair("EUC-KR", "08 C6 04 08 00 F2 AB 05 FD 84"),
                        Pair("DEC MCS", "08 C6 04 08 00 F2 AB 06 FD 83"),
                        Pair("ISO8859-1", "08 C6 04 08 00 F2 AB 07 FD 82"),
                        Pair("Japanese word stanza", "08 C6 04 08 00 F2 AB 08 FD 81"),
                        Pair("Cyrillic(Windows 1251)", "08 C6 04 08 00 F2 AB 09 FD 80"),
                        Pair("KOI8-R", "08 C6 04 08 00 F2 AB 0A FD 7F"),
                        Pair("BIG5", "08 C6 04 08 00 F2 AB 0B FD 7E"),
                        Pair("ISO8859-2", "08 C6 04 08 00 F2 AB 0C FD 7D"),
                    ), "06 C7 04 00 F2 AB FD 92"
                ),

                )
        ),
        CodeObj(
            "UPC-A",
            listOf(
                CodeFunction(
                    "Read",
                    listOf(
                        Pair("Off", "07 C6 04 08 00 01 00 FF 26"),
                        Pair("On", "07 C6 04 08 00 01 01 FF 25"),
                    ), "05 C7 04 00 01 FF 2F"
                ),
                CodeFunction(
                    "Transmission Check Character",
                    listOf(
                        Pair("Off", "07 C6 04 08 00 28 00 FE FF"),
                        Pair("On", "07 C6 04 08 00 28 01 FE FE"),
                    ), "05 C7 04 00 28 FF 08"
                ),
                addenda,
                CodeFunction(
                    "Lead Code",
                    listOf(
                        Pair("None", "07 C6 04 08 00 22 00 FF 05"),
                        Pair("System Id(01)", "07 C6 04 08 00 22 01 FF 04"),
                        Pair("Region/System Id(02)", "07 C6 04 08 00 22 02 FF 03"),
                    ), "05 C7 04 00 22 FF 0E"
                ),
                CodeFunction(
                    "UPC-A 2 Digit Append",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 40 00 FD F4"),
                        Pair("On", "08 C6 04 08 00 F2 40 01 FD F3")
                    ), "06 C7 04 00 F2 40 FD FD"
                ),
                CodeFunction(
                    "UPC-A 5 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 41 00 FD F3"),
                        Pair("On", "08 C6 04 08 00 F2 41 01 FD F2")
                    ), "06 C7 04 00 F2 41 FD FC"
                ),
                CodeFunction(
                    "UPC-A Addenda Required",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 42 00 FD F2"),
                        Pair("On", "08 C6 04 08 00 F2 42 01 FD F1")
                    ), "06 C7 04 00 F2 42 FD FB"
                ),
            )
        ),
        CodeObj(
            "UPC-E",
            listOf(
                CodeFunction(
                    "On/Off",
                    listOf(
                        Pair("Off", "07 C6 04 08 00 02 00 FF 25"),
                        Pair("On", "07 C6 04 08 00 02 01 FF 24")
                    ), "05 C7 04 00 02 FF 2E"
                ),
                CodeFunction(
                    "Transmit Check Digit",
                    listOf(
                        Pair("Off", "07 C6 04 08 00 29 00 FE FE"),
                        Pair("On", "07 C6 04 08 00 29 01 FE FD")
                    ), "05 C7 04 00 29 FF 07"
                ),
                addenda,
                CodeFunction(
                    "Lead Code",
                    listOf(
                        Pair("None", "07 C6 04 08 00 23 00 FF 04"),
                        Pair("System Id(01)", "07 C6 04 08 00 23 01 FF 03"),
                        Pair("Region/System Id(02)", "07 C6 04 08 00 23 02 FF 02"),
                    ), "05 C7 04 00 23 FF 0D"
                ),
                CodeFunction(
                    "Convert UPC-E to UPC-A",
                    listOf(
                        Pair("Off", "07 C6 04 08 00 25 00 FF 02"),
                        Pair("On", "07 C6 04 08 00 25 01 FF 01")
                    ), "05 C7 04 00 25 FF 0B"
                ),
                CodeFunction(
                    "UPC-E 2 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 3D 00 FD F7"),
                        Pair("On", "08 C6 04 08 00 F2 3D 01 FD F6")
                    ), "06 C7 04 00 F2 3D FE 00"
                ),
                CodeFunction(
                    "UPC-E 5 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 3E 00 FD F6"),
                        Pair("On", "08 C6 04 08 00 F2 3E 01 FD F5")
                    ), "06 C7 04 00 F2 3E FD FF"
                ),
                CodeFunction(
                    "UPC-E Addenda Required",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 3F 00 FD F5"),
                        Pair("On", "08 C6 04 08 00 F2 3F 01 FD F4")
                    ), "06 C7 04 00 F2 3F FD FE"
                ),
                CodeFunction(
                    "UPC-E1",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 15 00 FE 1F"),
                        Pair("On", "08 C6 04 08 00 F2 15 01 FE 1E")
                    ), "06 C7 04 00 F2 15 FE 28"
                ),
            )
        ),
        CodeObj(
            "EAN-8", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 04 00 FF 23"),
                        Pair("On", "07 C6 04 08 00 04 01 FF 22")
                    ), "05 C7 04 00 04 FF 2C"
                ),
                addenda,
                CodeFunction(
                    "EAN-8 Zero Extend(EAN-8 Extend EAN-13)", listOf(
                        Pair("Off", "07 C6 04 08 00 27 00 FF 00"),
                        Pair("On", "07 C6 04 08 00 27 01 FE FF")
                    ), "05 C7 04 00 27 FF 09"
                ),
                CodeFunction(
                    "EAN-8 2 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 37 00 FD FD"),
                        Pair("On", "08 C6 04 08 00 F2 37 01 FD FC")
                    ), "06 C7 04 00 F2 37 FE 06"
                ),
                CodeFunction(
                    "EAN-8 5 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 38 00 FD FC"),
                        Pair("On", "08 C6 04 08 00 F2 38 01 FD FB")
                    ), "06 C7 04 00 F2 38 FE 05"
                ),
                CodeFunction(
                    "EAN-8 Addenda Required",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 39 00 FD FB"),
                        Pair("On", "08 C6 04 08 00 F2 39 01 FD FA")
                    ), "06 C7 04 00 F2 39 FE 04"
                ),
                CodeFunction(
                    "EAN-8 Send Check Digits",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 80 00 FD B4"),
                        Pair("On", "08 C6 04 08 00 F2 80 01 FD B3")
                    ), "06 C7 04 00 F2 80 FD BD"
                ),
            )
        ),
        CodeObj(
            "EAN-13", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 03 00 FF 24"),
                        Pair("On", "07 C6 04 08 00 03 01 FF 23")
                    ), "05 C7 04 00 03 FF 2D"
                ),
                CodeFunction(
                    "EAN-13 2 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 3A 00 FD FA"),
                        Pair("On", "08 C6 04 08 00 F2 3A 01 FD F9")
                    ), "06 C7 04 00 F2 3A FE 03"
                ),
                CodeFunction(
                    "EAN-13 5 Digit Addenda",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 3B 00 FD F9"),
                        Pair("On", "08 C6 04 08 00 F2 3B 01 FD F8")
                    ), "06 C7 04 00 F2 3B FE 02"
                ),
                CodeFunction(
                    "EAN-13 Addenda Required",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 3C 00 FD F8"),
                        Pair("On", "08 C6 04 08 00 F2 3C 01 FD F7")
                    ), "06 C7 04 00 F2 3C FE 01"
                ),
                CodeFunction(
                    "EAN-13 Send Check Digits",
                    listOf(
                        Pair("Off", "08 C6 04 08 00 F2 16 00 FE 1E"),
                        Pair("On", "08 C6 04 08 00 F2 16 01 FE 1D")
                    ), "06 C7 04 00 F2 16 FE 27"
                ),
                addenda
            )
        ),
        CodeObj(
            "Bookland EAN(ISBN)", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 53 00 FE D4"),
                        Pair("On", "07 C6 04 08 00 53 01 FE D3")
                    ), "05 C7 04 00 53 FE DD"
                ),
                CodeFunction(
                    "Format", listOf(
                        Pair("Output 10 Digit", "08 C6 04 08 00 F1 40 00 FD F5"),
                        Pair("Output 13 Digit", "08 C6 04 08 00 F1 40 01 FD F4")
                    ), "06 C7 04 00 F1 40 FD FE"
                ),
                CodeFunction(
                    "UPC/EAN Security level", listOf(
                        Pair("Level 1", "07 C6 04 08 00 4D 00 FE DA"),
                        Pair("Level 2", "07 C6 04 08 00 4D 01 FE D9"),
                        Pair("Level 3", "07 C6 04 08 00 4D 02 FE D8"),
                        Pair("Level 4", "07 C6 04 08 00 4D 03 FE D7"),
                    ), "05 C7 04 00 4D FE E3"
                )
            )
        ),
        CodeObj(
            "Code 128", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 08 00 FF 1F"),
                        Pair("On", "07 C6 04 08 00 08 01 FF 1E")
                    ), "05 C7 04 00 08 FF 28"
                ), CodeFunction(
                    "Send Check Character", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 35 00 FD FF"),
                        Pair("On", "08 C6 04 08 00 F2 35 01 FD FE")
                    ), "06 C7 04 00 F2 35 FE 08"
                ), CodeFunction(
                    "Message Length", listOf(
                        Pair("A Single Length(06)", "0B C6 04 08 00 F5 04 06 F5 05 00 FD 2A"),
                        Pair(
                            "Two Separate Lengths(04 and 06)",
                            "0B C6 04 08 00 F5 04 06 F5 05 04 FD 26"
                        ),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "0B C6 04 08 00 F5 04 04 F5 05 09 FD"
                        ),
                        Pair("Arbitrary length", "08 C6 04 08 00 F2 35 01 FD FE")
                    ), "08 C7 04 00 F5 04 F5 05 FD 3A"
                )
            )
        ),
        CodeObj(
            "GS1-128 (UCC/EAN-128)", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 0E 00 FF 19"),
                        Pair("On", "07 C6 04 08 00 0E 01 FF 18")
                    ), "05 C7 04 00 0E FF 22"
                ), CodeFunction(
                    "Message Length", listOf(
                        Pair("A Single Length(06)", "0B C6 04 08 00 F5 06 06 F5 07 00 FD 26"),
                        Pair(
                            "Two Separate Lengths(04 and 06)",
                            "0B C6 04 08 00 F5 06 06 F5 07 04 FD 22"
                        ),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "0B C6 04 08 00 F5 06 04 F5 07 09 FD 1F"
                        ),
                        Pair("Arbitrary length", "0B C6 04 08 00 F5 06 00 F5 07 00 FD 2C")
                    ), "08 C7 04 00 F5 06 F5 07 FD 36"
                )
            )
        ),
        CodeObj(
            "Code 39", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 00 00 FF 27"),
                        Pair("On", "07 C6 04 08 00 00 01 FF 26")
                    ), "05 C7 04 00 00 FF 30"
                ),
                CodeFunction(
                    "Message Length", listOf(
                        Pair("A Single Length(06)", "09 C6 04 08 00 12 06 13 00 FE FA"),
                        Pair("A Single Length(16)", "09 C6 04 08 00 12 10 13 00 FE F0"),
                        Pair("A Single Length(14)", "09 C6 04 08 00 12 0E 13 00 FE F2"),
                        Pair("Two Separate Lengths(02 and 04)", "09 C6 04 08 00 12 04 13 02 FE FA"),
                        Pair("Two Separate Lengths(16 and 14)", "09 C6 04 08 00 12 10 13 0E FE E2"),
                        Pair(
                            "Length within a specified range(02 until  09)",
                            "09 C6 04 08 00 12 02 13 09 FE F5"
                        ),
                        Pair(
                            "Length within a specified range(0x02 until  0x37(55),Default)",
                            "09 C6 04 08 00 12 02 13 37 FE C7"
                        ),
                        Pair(
                            "Length within a specified range(14 until  15)",
                            "09 C6 04 08 00 12 0E 13 0F FE E3"
                        ),
                        Pair(
                            "Length within a specified range(14 until  15 Temporary effect)",
                            "09 C6 04 00 00 12 0E 13 0F FE EB"
                        ),
                        Pair(
                            "Length within a specified range(15 until  16)",
                            "09 C6 04 08 00 12 0F 13 10 FE E1"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 12 00 13 00 FE F0")
                    ), "06 C7 04 00 12 13 FF 0A"
                ),
                CodeFunction(
                    "Code 39 Check Digit", listOf(
                        Pair("Off", "07 C6 04 08 00 30 00 FE F7"),
                        Pair("On", "07 C6 04 08 00 30 01 FE F6")
                    ), "05 C7 04 00 30 FF 00"
                ),
                CodeFunction(
                    "Transmit Code 39 Check Digit", listOf(
                        Pair("Off", "07 C6 04 08 00 2B 00 FE FC"),
                        Pair("On", "07 C6 04 08 00 30 01 FE F6")
                    ), "05 C7 04 00 2B FF 05"
                ),
                CodeFunction(
                    "Code 39 Full ASCII", listOf(
                        Pair("Full ASCII", "07 C6 04 08 00 2B 00 FE FC")
                    ), "05 C7 04 00 11 FF 1F"
                ),
                CodeFunction(
                    "Start/Stop Characters", listOf(
                        Pair("Don’t Transmit", "08 C6 04 08 00 F2 30 00 FE 04"),
                        Pair("Transmit", "08 C6 04 08 00 F2 30 01 FE 03")
                    ), "06 C7 04 00 F2 30 FE 0D"
                ),
                CodeFunction(
                    "Code 32 Prefix", listOf(
                        Pair("Off", "07 C6 04 08 00 E7 00 FE 40"),
                        Pair("On", "07 C6 04 08 00 E7 01 FE 3F")
                    ), "05 C7 04 00 E7 FE 49"
                ),
                CodeFunction(
                    "Code 32 Check Digit", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 19 00 FE 1B"),
                        Pair("On", "08 C6 04 08 00 F2 19 01 FE 1A")
                    ), "06 C7 04 00 F2 19 FE 24"
                ),
                CodeFunction(
                    "Transmit Code 32 Check Digit", listOf(
                        Pair("Transmit Check Digit", "08 C6 04 08 00 F2 1A 00 FE 1A"),
                        Pair(
                            "Transmit Start/Stop Characters/Check Digit",
                            "08 C6 04 08 00 F2 1A 01 FE 19"
                        )
                    ), "06 C7 04 00 F2 1A FE 23"
                )
            )
        ),
        CodeObj(
            "Code 93", listOf(
                CodeFunction(
                    "Code 93 On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 09 00 FF 1E"),
                        Pair("On", "07 C6 04 08 00 09 01 FF 1D")
                    ), "05 C7 04 00 09 FF 27"
                ), CodeFunction(
                    "Message Length", listOf(
                        Pair("A Single Length(04)", "09 C6 04 08 00 1A 041B 00 FE EC"),
                        Pair("Two Separate Lengths(04 and 06)", "09 C6 04 08 00 1A 06 1B 04 FE E6"),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "09 C6 04 08 00 1A 04 1B 09 FE E3"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 1A 00 1B 00 FE F0")
                    ), "06 C7 04 00 1A 1B FE FA"
                )
            )
        ),
        CodeObj(
            "Code 11", listOf(
                CodeFunction(
                    "Code 11 On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 0A 00 FF 1D"),
                        Pair("On", "07 C6 04 08 00 0A 01 FF 1C")
                    ), "05 C7 04 00 0A FF 26"
                ), CodeFunction(
                    "Code 11 Message Length", listOf(
                        Pair("A Single Length(06)", "09 C6 04 08 00 1C 06 1D 00 FE E6"),
                        Pair("Two Separate Lengths(04 and 06)", "09 C6 04 08 00 1C 06 1D 04 FE E2"),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "09 C6 04 08 00 1C 04 1D 09 FE DF"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 1C 00 1D 00 FE EC")
                    ), "06 C7 04 00 1C 1D FE F6"
                ), CodeFunction(
                    "Code 11 Check Digits Required", listOf(
                        Pair("None", "07 C6 04 08 00 34 00 FE F3"),
                        Pair("One Check Digit", "07 C6 04 08 00 34 01 FE F2"),
                        Pair("Two Check Digits", "07 C6 04 08 00 34 02 FE F1")
                    ), "05 C7 04 00 34 FE FC"
                ), CodeFunction(
                    "Transmit Code 11 Check Digit", listOf(
                        Pair("Off", "07 C6 04 08 00 2F 00 FE F8"),
                        Pair("On", "07 C6 04 08 00 2F 01 FE F7")
                    ), "05 C7 04 00 2F FF 01"
                )
            )
        ),
        CodeObj(
            "Interleaved 2 of 5/ITF", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 06 00 FF 21"),
                        Pair("On", "07 C6 04 08 00 06 01 FF 20")
                    ), "05 C7 04 00 06 FF 2A"
                ), CodeFunction(
                    "Messsage Length", listOf(
                        Pair("A Single Length(06)", "09 C6 04 08 00 16 06 17 00 FE F2"),
                        Pair("Two Separate Lengths(04 and 06)", "09 C6 04 08 00 16 06 17 04 FE EE"),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "09 C6 04 08 00 16 04 17 09 FE EB"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 16 00 17 00 FE F8")
                    ), "06 C7 04 00 16 17 FF 02"
                ), CodeFunction(
                    "Interleaved 2 of 5 Check Digit", listOf(
                        Pair("Off", "07 C6 04 08 00 31 00 FE F6"),
                        Pair("On", "07 C6 04 08 00 31 01 FE F5")
                    ), "05 C7 04 00 31 FE FF"
                ), CodeFunction(
                    "Send Check Digits", listOf(
                        Pair("Off", "07 C6 04 08 00 2C 00 FE FB"),
                        Pair("On", "07 C6 04 08 00 2C 01 FE FA")
                    ), "05 C7 04 00 2C FF 04"
                ), CodeFunction(
                    "ITF14 On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 43 00 FD F1"),
                        Pair("On", "08 C6 04 08 00 F2 43 01 FD F0")
                    ), "06 C7 04 00 F2 43 FD FA"
                ), CodeFunction(
                    "ITF14 Send Check Character", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 44 00 FD F0"),
                        Pair("On", "08 C6 04 08 00 F2 44 01 FD EF")
                    ), "06 C7 04 00 F2 44 FD F9"
                )
            )
        ),
        CodeObj(
            "Discrete 2 of 5 /Industrial 2 of 5/IND25", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 05 00 FF 22"),
                        Pair("On", "07 C6 04 08 00 05 01 FF 21")
                    ), "05 C7 04 00 05 FF 2B"
                ), CodeFunction(
                    "Message Length", listOf(
                        Pair("A Single Length(06)", "09 C6 04 08 00 14 06 15 00 FE F6"),
                        Pair("Two Separate Lengths(04 and 06)", "09 C6 04 08 00 14 06 15 04 FE F2"),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "09 C6 04 08 00 14 04 15 09 FE EF"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 14 00 15 00 FE FC")
                    ), "06 C7 04 00 14 15 FF 06"
                ), CodeFunction(
                    "Discrete 2 of 5 Check Digits", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 48 00 FD EC"),
                        Pair("On", "08 C6 04 08 00 F2 48 01 FD EB")
                    ), "06 C7 04 00 F2 48 FD F5"
                ), CodeFunction(
                    "Discrete 2 of 5 Send Check Digits", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 49 00 FD EB"),
                        Pair("On", "08 C6 04 08 00 F2 49 01 FD EA")
                    ), "06 C7 04 00 F2 49 FD F4"
                )
            )
        ),
        CodeObj(
            "Matrix 25", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 21 00 FE 13"),
                        Pair("On", "08 C6 04 08 00 F2 21 01 FE 12")
                    ), "06 C7 04 00 F2 21 FE 1C"
                ), CodeFunction(
                    "Matrix 25 Check Digit", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 20 00 FE 14"),
                        Pair("On", "08 C6 04 08 00 F2 20 01 FE 13")
                    ), "06 C7 04 00 F2 20 FE 1D"
                ),
                CodeFunction(
                    "Transmit Matrix 25 Check Character", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 22 00 FE 12"),
                        Pair("On", "08 C6 04 08 00 F2 22 01 FE 11")
                    ), "06 C7 04 00 F2 22 FE 1B"
                ), CodeFunction(
                    "Matrix 25 Message Length", listOf(
                        Pair("A Single Length(06)", "0B C6 04 08 00 F5 00 06 F5 01 00 FD 32"),
                        Pair(
                            "Two Separate Lengths(04 and 06)",
                            "0B C6 04 08 00 F5 00 06 F5 01 04 FD 2E"
                        ),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "0B C6 04 08 00 F5 00 04 F5 01 09 FD 2B"
                        ),
                        Pair("Arbitrary length", "0B C6 04 08 00 F5 00 00 F5 01 00 FD 38")
                    ), "08 C7 04 00 F5 00 F5 01 FD 42"
                )
            )
        ),
        CodeObj(
            "Standard 25 / IATA 25", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 23 00 FE 11"),
                        Pair("On", "08 C6 04 08 00 F2 23 01 FE 10")
                    ), "06 C7 04 00 F2 23 FE 1A"
                ),
                CodeFunction(
                    "Standard 25 Check Digit", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 24 00 FE 10"),
                        Pair("On", "08 C6 04 08 00 F2 24 01 FE 0F")
                    ), "06 C7 04 00 F2 24 FE 19"
                ), CodeFunction(
                    "Transmit Standard 25 Check Character", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 25 00 FE 0F"),
                        Pair("On", "08 C6 04 08 00 F2 25 01 FE 0E")
                    ), "06 C7 04 00 F2 25 FE 18"
                ), CodeFunction(
                    "Standard 25 Message Length", listOf(
                        Pair("A Single Length(06)", "09 C6 04 08 00 F5 02 06 F5 03 00 FD 2E"),
                        Pair(
                            "Two Separate Lengths(04 and 06)",
                            "09 C6 04 08 00 F5 02 06 F5 03 04 FD 2A"
                        ),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "09 C6 04 08 00 F5 02 04 F5 03 09 FD 27"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 F5 02 00 F5 03 00 FD 34")
                    ), "08 C7 04 00 F5 02 F5 03 FD 3E"
                )
            )
        ),
        CodeObj(
            "Codabar", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 07 00 FF 20"),
                        Pair("On", "07 C6 04 08 00 07 01 FF 1F")
                    ), "05 C7 04 00 07 FF 29"
                ), CodeFunction(
                    "Codabar Message Length", listOf(
                        Pair("A Single Length(04)", "09 C6 04 08 00 18 04 19 00 FE F0"),
                        Pair("Two Separate Lengths", "09 C6 04 08 00 18 05 19 04 FE EB"),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "09 C6 04 08 00 18 04 19 09 FE E7"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 18 00 19 00 FE F4")
                    ), "06 C7 04 00 18 19 FE FE"
                ),
                CodeFunction(
                    "Codabar Check Digit", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 4C 00 FD E8"),
                        Pair("On", "08 C6 04 08 00 F2 4C 01 FD E7")
                    ), "06 C7 04 00 F2 4C FD F1"
                ),
                CodeFunction(
                    "Codabar Send Check Digits", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 4D 00 FD E7"),
                        Pair("On", "08 C6 04 08 00 F2 4D 01 FD E6")
                    ), "06 C7 04 00 F2 4D FD F0"
                ),
                CodeFunction(
                    "NOTIS Transmit Format", listOf(
                        Pair("Off", "07 C6 04 08 00 37 00 FE F0"),
                        Pair("On", "07 C6 04 08 00 37 01 FE EF")
                    ), "05 C7 04 00 37 FE F9"
                ),
                CodeFunction(
                    "Start/Stop Characters Format", listOf(
                        Pair("ABCD/ABCD", "08 C6 04 08 00 F2 31 00 FE 03"),
                        Pair("ABCD/TN*E", "08 C6 04 08 00 F2 31 01 FE 02")
                    ), "06 C7 04 00 F2 31 FE 0C"
                ),
                CodeFunction(
                    "Start/Stop Characters  Capital and Small letter", listOf(
                        Pair("Capital Letter", "08 C6 04 08 00 F2 32 00 FE 02"),
                        Pair("Small Letter", "08 C6 04 08 00 F2 32 01 FE 01")
                    ), "06 C7 04 00 F2 32 FE 0B"
                )
            )
        ),
        CodeObj(
            "MSI /MSI PLESSEY", listOf(
                CodeFunction(
                    "MSI /MSI PLESSEY On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 0B 00 FF 1C"),
                        Pair("On", "07 C6 04 08 00 0B 01 FF 1B")
                    ), "05 C7 04 00 0B FF 25"
                ), CodeFunction(
                    "MSI Message Length", listOf(
                        Pair("A Single Length(04)", "09 C6 04 08 00 1E 04 1F 00 FE E4"),
                        Pair("Two Separate Lengths", "09 C6 04 08 00 1E 05 1F 04 FE DF"),
                        Pair(
                            "Length within a specified range(02 until  09)",
                            "09 C6 04 08 00 1E 02 1F 09 FE DD"
                        ),
                        Pair("Arbitrary length", "09 C6 04 08 00 1E 00 1F 00 FE E8")
                    ), "06 C7 04 00 1E 1F FE F2"
                ), CodeFunction(
                    "MSI Check Digits Required", listOf(
                        Pair("One Check Digit", "07 C6 04 08 00 32 00 FE F5"),
                        Pair("Two Check Digits", "07 C6 04 08 00 32 01 FE F4")
                    ), "05 C7 04 00 32 FE FE"
                ), CodeFunction(
                    "Transmit MSI Check Digit", listOf(
                        Pair("Off", "07 C6 04 08 00 2E 00 FE F9"),
                        Pair("On", "07 C6 04 08 00 2E 01 FE F8")
                    ), "05 C7 04 00 2E FF 02"
                ), CodeFunction(
                    "MSI Check Algorithm", listOf(
                        Pair("MOD10/11", "07 C6 04 08 00 33 00 FE F4"),
                        Pair("MOD10/10", "07 C6 04 08 00 33 01 FE F3")
                    ), "05 C7 04 00 33 FE FD"
                )
            )
        ),
        CodeObj(
            "GS1 DataBar(RSS)", listOf(
                CodeFunction(
                    "GS1 DataBar(RSS) 14 On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 52 00 FD E4"),
                        Pair("On", "08 C6 04 08 00 F0 52 01 FD E3")
                    ), "06 C7 04 00 F0 52 FD ED"
                ), CodeFunction(
                    "GS1 DataBar Limited On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 53 00 FD E3"),
                        Pair("On", "08 C6 04 08 00 F0 53 01 FD E2")
                    ), "06 C7 04 00 F0 53 FD EC"
                ), CodeFunction(
                    "GS1 DataBar Expanded On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 54 00 FD E2"),
                        Pair("On", "08 C6 04 08 00 F0 54 01 FD E1")
                    ), "06 C7 04 00 F0 54 FD EB"
                ), CodeFunction(
                    "RSS AI Character", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 26 00 FE 0E"),
                        Pair("On", "08 C6 04 08 00 F2 26 01 FE 0D")
                    ), "06 C7 04 00 F2 26 FE 17"
                )
            )
        ),
        //二维码相关
        CodeObj(
            "PDF417", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 0F 00 FF 18"),
                        Pair("On", "07 C6 04 08 00 0F 01 FF 17")
                    ), "05 C7 04 00 0F FF 21"
                ), CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Read Single Code", "08 C6 04 08 00 F2 60 00 FD D4"),
                        Pair("Read Double Code", "08 C6 04 08 00 F2 60 01 FD D3"),
                        Pair("Read Single/Double Code", "08 C6 04 08 00 F2 60 02 FD D2")
                    ), "06 C7 04 00 F2 60 FD DD"
                ), CodeFunction(
                    "Positive/Negative On/Off", listOf(
                        Pair("Only Positive", "08 C6 04 08 00 F2 61 00 FD D3"),
                        Pair("Only Negative", "08 C6 04 08 00 F2 61 01 FD D2"),
                        Pair("Positive and Negative", "08 C6 04 08 00 F2 61 02 FD D1")
                    ), "06 C7 04 00 F2 61 FD DC"
                )
            )
        ),
        CodeObj(
            "QRCode", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 25 00 FE 11"),
                        Pair("On", "08 C6 04 08 00 F0 25 01 FE 10")
                    ), "06 C7 04 00 F0 25 FE 1A"
                ), CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Read Single Code", "08 C6 04 08 00 F2 65 00 FD CF"),
                        Pair("Read Double Code", "08 C6 04 08 00 F2 65 01 FD CE"),
                        Pair("Read Single/Double Code", "08 C6 04 08 00 F2 65 02 FD CD")
                    ), "06 C7 04 00 F2 65 FD D8"
                ), CodeFunction(
                    "ECI Control", listOf(
                        Pair("Don't Output", "08 C6 04 08 00 F2 66 00 FD CE"),
                        Pair("Output", "08 C6 04 08 00 F2 66 01 FD CD")
                    ), "06 C7 04 00 F2 66 FD D7"
                ), CodeFunction(
                    "QR Positive/Negative On/Off", listOf(
                        Pair("Only Positive", "08 C6 04 08 00 F2 67 00 FD CD"),
                        Pair("Only Negative", "08 C6 04 08 00 F2 67 01 FD CC"),
                        Pair("Positive and Negative", "08 C6 04 08 00 F2 61 02 FD D1")
                    ), "06 C7 04 00 F2 67 FD D6"
                ), CodeFunction(
                    "Remove THE UTF-8 BOM format of the QR code", listOf(
                        Pair("Off", "08 C6 04 08 00 F6 3A 00 FD F6"),
                        Pair("On", "08 C6 04 08 00 F6 3A 01 FD F5")
                    ), "06 C7 04 00 F6 3A FD FF"
                )
            )
        ),
        CodeObj(
            "DataMatrix", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 24 00 FE 12"),
                        Pair("On", "08 C6 04 08 00 F0 24 01 FE 11")
                    ), "06 C7 04 00 F0 24 FE 1B"
                ),
                CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Read Single Code", "08 C6 04 08 00 F2 6A 00 FD CA"),
                        Pair("Read Double Code", "08 C6 04 08 00 F2 6A 01 FD C9"),
                        Pair("Read Single/Double Code", "08 C6 04 08 00 F2 6A 02 FD C8")
                    ), "06 C7 04 00 F2 6A FD D3"
                ), CodeFunction(
                    "Positive/Negative On/Off", listOf(
                        Pair("Only Positive", "08 C6 04 08 00 F2 6B 00 FD C9"),
                        Pair("Only Negative", "08 C6 04 08 00 F2 6B 01 FD C8"),
                        Pair("Positive and Negative", "08 C6 04 08 00 F2 6B 02 FD C7")
                    ), "06 C7 04 00 F2 6B FD D2"
                ), CodeFunction(
                    "ECI Control", listOf(
                        Pair("Don't Output", "08 C6 04 08 00 F2 6C 00 FD C8"),
                        Pair("Output", "08 C6 04 08 00 F2 6C 01 FD C7")
                    ), "06 C7 04 00 F2 6C FD D1"
                )
            )
        ),
        CodeObj(
            "MaxiCode", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 26 00 FE 10"),
                        Pair("On", "08 C6 04 08 00 F0 26 01 FE 0F")
                    ), "06 C7 04 00 F0 26 FE 19"
                )
            )
        ),
        CodeObj(
            "Aztec", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 28 00 FE 0E"),
                        Pair("On", "08 C6 04 08 00 F0 28 01 FE 0D")
                    ), "06 C7 04 00 F0 28 FE 17"
                )
            )
        ),
        CodeObj(
            "Han Xin Code", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F0 2F 00 FE 07"),
                        Pair("On", "08 C6 04 08 00 F0 2F 01 FE 06")
                    ), "06 C7 04 00 F0 2F FE 10"
                ), CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Only Single Code", "08 C6 04 08 00 F2 70 00 FD C4"),
                        Pair("Read Double Code", "08 C6 04 08 00 F2 70 01 FD C3"),
                        Pair("Read Single/Double Code", "08 C6 04 08 00 F2 70 02 FD C2")
                    ), "06 C7 04 00 F2 70 FD CD"
                ), CodeFunction(
                    "Positive/Negative On/Off", listOf(
                        Pair("Only Positive", "08 C6 04 08 00 F2 71 00 FD C3"),
                        Pair("Only Negative", "08 C6 04 08 00 F2 71 01 FD C2"),
                        Pair("Positive and Negative", "08 C6 04 08 00 F2 71 02 FD C1")
                    ), "06 C7 04 00 F2 71 FD CC"
                )
            )
        ),
        CodeObj(
            "ISSN", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "07 C6 04 08 00 0B 00 FF 1C"),
                        Pair("On", "07 C6 04 08 00 0B 01 FF 1B")
                    ), "06 C7 04 00 F2 33 FE 0A"
                )
            )
        ),
        CodeObj(
            "NEC-25", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 45 00 FD EF"),
                        Pair("On", "08 C6 04 08 00 F2 45 01 FD EE")
                    ), "06 C7 04 00 F2 45 FD F8"
                ), CodeFunction(
                    "NEC-25(COOP25) Check Digits", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 46 00 FD EE"),
                        Pair("On", "08 C6 04 08 00 F2 46 01 FD ED")
                    ), "06 C7 04 00 F2 46 FD F7"
                ), CodeFunction(
                    "NEC-25(COOP25) Send Check Digits", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 47 00 FD ED"),
                        Pair("On", "08 C6 04 08 00 F2 47 01 FD EC")
                    ), "06 C7 04 00 F2 47 FD F6"
                ), CodeFunction(
                    "NEC-25(COOP25)Message Length", listOf(
                        Pair("A Single Length(06)", "0B C6 04 08 00 F5 08 06 F5 09 00 FD 22"),
                        Pair(
                            "Two Separate Lengths(04 and 06)",
                            "0B C6 04 08 00 F5 08 06 F5 09 04 FD 1E"
                        ),
                        Pair(
                            "Length within a specified range(04 until  09)",
                            "0B C6 04 08 00 F5 08 04 F5 09 09 FD 1B"
                        ),
                        Pair("Arbitrary length", "0B C6 04 08 00 F5 08 00 F5 09 00 FD 28")
                    ), "08 C7 04 00 F5 08 F5 09 FD 32"
                ), CodeFunction(
                    "On/Off COMPOSITE", listOf(
                        Pair("Off", "08 C6 04 08 00 F2 17 00 FE 1D"),
                        Pair("On", "08 C6 04 08 00 F2 17 01 FE 1C")
                    ), "06 C7 04 00 F2 17 FE 26"
                )
            )
        ),
        CodeObj(
            "1D Code Security Level", listOf(
                CodeFunction(
                    "Level", listOf(
                        Pair("Level 1", "07 C6 04 08 00 4E 01 FE D8"),
                        Pair("Level 2", "07 C6 04 08 00 4E 02 FE D7"),
                        Pair("Level 3", "07 C6 04 08 00 4E 03 FE D6"),
                        Pair("Level 4", "07 C6 04 08 00 4E 04 FE D5")
                    ), "05 C7 04 00 4E FE E2"
                )
            )
        ),
        CodeObj(
            "Country/Language Keyboard layout",
            listOf(
                CodeFunction(
                    "Country", listOf(
                        Pair("General", "08 C6 04 08 00 F6 01 24 FE 0B"),
                        Pair("The United States", "08 C6 04 08 00 F6 01 01 FE 2E"),
                        Pair("Belgium", "08 C6 04 08 00 F6 01 02 FE 2D"),
                        Pair("Brazil(ABNT2)", "08 C6 04 08 00 F6 01 03 FE 2C"),
                        Pair("Denmark", "08 C6 04 08 00 F6 01 06 FE 29"),
                        Pair("Finland", "08 C6 04 08 00 F6 01 07 FE 28"),
                        Pair("French", "08 C6 04 08 00 F6 01 08 FE 27"),
                        Pair("Austria/Germany", "08 C6 04 08 00 F6 01 09 FE 26"),
                        Pair("Greece", "08 C6 04 08 00 F6 01 0A FE 25"),
                        Pair("Hungary", "08 C6 04 08 00 F6 01 0B FE 24"),
                        Pair("Italy", "08 C6 04 08 00 F6 01 0D FE 22"),
                        Pair("Holland", "08 C6 04 08 00 F6 01 0F FE 20"),
                        Pair("Norway", "08 C6 04 08 00 F6 01 10 FE 1F"),
                        Pair("Poland", "08 C6 04 08 00 F6 01 11 FE 1E"),
                        Pair("Portugal", "08 C6 04 08 00 F6 01 12 FE 1D"),
                        Pair("Romania(standard)", "08 C6 04 08 00 F6 01 13 FE 1C"),
                        Pair("Russia", "08 C6 04 08 00 F6 01 14 FE 1B"),
                        Pair("Slovakia", "08 C6 04 08 00 F6 01 15 FE 1A"),
                        Pair("Spain", "08 C6 04 08 00 F6 01 16 FE 19"),
                        Pair("Sweden", "08 C6 04 08 00 F6 01 17 FE 18"),
                        Pair("Turkey_F", "08 C6 04 08 00 F6 01 19 FE 16"),
                        Pair("Turkey_Q", "08 C6 04 08 00 F6 01 1A FE 15"),
                        Pair("Britain", "08 C6 04 08 00 F6 01 1B FE 14"),
                        Pair("Japan", "08 C6 04 08 00 F6 01 1C FE 13"),
                        Pair("The Czech republic", "08 C6 04 08 00 F6 01 1D FE 12"),
                        Pair("Thailand(Kedmanee)", "08 C6 04 08 00 F6 01 1E FE 11"),
                        Pair("Ukraine", "08 C6 04 08 00 F6 01 1F FE 10"),
                        Pair("Arabic_101", "08 C6 04 08 00 F6 01 20 FE 0F"),
                        Pair("Croatia", "08 C6 04 08 00 F6 01 21 FE 0E"),
                        Pair("Korea", "08 C6 04 08 00 F6 01 22 FE 0D"),
                        Pair("Bulgaria", "08 C6 04 08 00 F6 01 23 FE 0C"),
                        Pair("Vietnam", "08 C6 04 08 00 F6 01 25 FE 0A"),
                    ), "06 C7 04 00 F6 01 FE 38"
                )
            )
        ),
//        CodeObj(
//            "调试", listOf(
//                CodeFunction(
//                    "输出产品信息", listOf(
//                        Pair("Test", "07 C6 04 08 00 4E 04 FE D5")
//                    ), "06 C7 04 00 F4 01 FE 3A"
//                )
//            )
//        )
    )
}

//图腾的其他码制开关
//全部条码全局开关
val allCodeOpenClose =
    CodeFunction(
        "1D&2D toggle",
        listOf(
            Pair("enable", "08 C6 04 08 00 F2 90 01 FD A3"),
            Pair("disable", "08 C6 04 08 00 F2 90 00 FD A4")
        ),
        "06 C7 04 00 F2 90 FD AD"
    )

//一维码全局开关
val codeOpenClose = CodeFunction(
    "1D toggle",
    listOf(
        Pair("enable", "08 C6 04 08 00 F2 11 01 FE 22"),
        Pair("disable", "08 C6 04 08 00 F2 11 00 FE 23")
    ),
    "06 C7 04 00 F2 11 FE 2C"
)

//二维码全局开关
val qrcodeOpenClose = CodeFunction(
    "2D toggle",
    listOf(
        Pair("enable", "08 C6 04 08 00 F2 50 01 FD E3"),
        Pair("disable", "08 C6 04 08 00 F2 50 00 FD E4")
    ),
    "06 C7 04 00 F2 50 FD ED"
)

fun minLengthNew(min: Int, max: Int, command: String): CodeFunction {
    return CodeFunction(
        "Minimum Message Length",
        listOf(),
        LengthFunction(min, max, "${command}MIN")
    )
}

fun maxLengthNew(min: Int, max: Int, command: String): CodeFunction {
    return CodeFunction(
        "Maximum Message Length",
        listOf(),
        LengthFunction(min, max, "${command}MAX")
    )
}

//新大陆码制设置
fun getNewland(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "Device Settings",
            listOf(
//                CodeFunction(
//                    "GETIMG",
//                    listOf(
//                        Pair("GETIMG", "IMGGET0T0R0F")
//                    ), ""
//                ),
                //此码制屏蔽，存在问题，设为off以后，休眠唤醒后会自动启用，且和主页的开启扫码功能重复。
//                CodeFunction(
//                    "SCAN On/Off",
//                    listOf(
//                        Pair("Off", "SCNENA0"),
//                        Pair("On", "SCNENA1"),
//                    ), "SCNENA"
//                ),
                CodeFunction(
                    "All Code",
                    listOf(
                        Pair("Off", "ALLENA0"),
                        Pair("On", "ALLENA1"),
                    ), "ALLENA"
                ),
                CodeFunction(
                    "1D Code",
                    listOf(
                        Pair("Off", "ALL1DC0"),
                        Pair("On", "ALL1DC1"),
                    ), "ALL1DC"
                ),
                CodeFunction(
                    "2D Code",
                    listOf(
                        Pair("Off", "ALL2DC0"),
                        Pair("On", "ALL2DC1"),
                    ), "ALL2DC"
                ),
                CodeFunction(
                    "Inverting the code",
                    listOf(
                        Pair("Off", "ALLINV0"),
                        Pair("On", "ALLINV1"),
                    ), "ALLINV"
                ),
                CodeFunction(
                    "Centering",
                    listOf(
                        Pair("All", "CADENA0"),
                        Pair("Centering", "CADENA1"),
                        Pair("Aimed", "CADENA2"),
                    ), "CADENA"
                ),
                CodeFunction(
                    "Top of Centering",
                    listOf(),
                    LengthFunction(0, 100, "CADTOP")
                ),
                CodeFunction(
                    "Buttom of Centering",
                    listOf(),
                    LengthFunction(0, 100, "CADBOT")
                ),
                CodeFunction(
                    "Left of Centering",
                    listOf(),
                    LengthFunction(0, 100, "CADLEF")
                ),
                CodeFunction(
                    "Right of Centering",
                    listOf(),
                    LengthFunction(0, 100, "CADRIG")
                ),
                CodeFunction(
                    "Unknown character prompt tone",
                    listOf(
                        Pair("Off", "KBWBUC0"),
                        Pair("On", "KBWBUC1"),
                    ), "KBWBUC"
                ),
                CodeFunction(
                    "Unicode output",
                    listOf(
                        Pair("Off", "KBWCPU0"),
                        Pair("On", "KBWCPU1"),
                    ), "KBWCPU"
                ),
                CodeFunction(
                    "Prefix '0'",
                    listOf(
                        Pair("Off", "KBWALZ0"),
                        Pair("On", "KBWALZ1"),
                    ), "KBWALZ"
                ),
            ),
        ),
        CodeObj(
            "Code 128", listOf(
                CodeFunction(
                    "Default Settings", listOf(
                        Pair("Default Code128 Settings", "128DEF")
                    )
                ), CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "128ENA0"),
                        Pair("On", "128ENA1"),
                    ), "128ENA"
                ),
                minLengthNew(1, 127, "128"),
                maxLengthNew(1, 127, "128"),
                CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "128CHK1"),
                        Pair("Transmit", "128CHK2"),
                    ), "128CHK"
                ), CodeFunction(
                    "FNC1 Output", listOf(
                        Pair("On", "128FNC1"),
                        Pair("Off", "128FNC0"),
                    ), "128FNC"
                )
            )
        ),
        CodeObj(
            "EAN-8", listOf(
                CodeFunction(
                    "Default EAN-8 Settings", listOf(
                        Pair("Default Settings", "EA8DEF")
                    )
                ), CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "EA8ENA0"),
                        Pair("On", "EA8ENA1"),
                    ), "EA8ENA"
                ), CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "EA8CHK1"),
                        Pair("Transmit", "EA8CHK2"),
                    ), "EA8CHK"
                ), CodeFunction(
                    "2 Digit Addenda", listOf(
                        Pair("2 Digit Addenda Off", "EA8AD20"),
                        Pair("2 Digit Addenda On", "EA8AD21"),
                    ), "EA8AD2"
                ), CodeFunction(
                    "5 Digit Addenda", listOf(
                        Pair("5 Digit Addenda Off", "EA8AD50"),
                        Pair("5 Digit Addenda On", "EA8AD51"),
                    ), "EA8AD5"
                ), CodeFunction(
                    "Addenda Required", listOf(
                        Pair("Not Required", "EA8REQ0"),
                        Pair("Required", "EA8REQ1"),
                    ), "EA8REQ"
                ),
                CodeFunction(
                    "convert to EAN-13", listOf(
                        Pair("Don't convert", "EA8EXP0"),
                        Pair("Convert to EAN13", "EA8EXP1"),
                    ), "EA8EXP"
                )
            )
        ),
        CodeObj(
            "EAN-13", listOf(
                CodeFunction(
                    "Default EAN-13 Settings", listOf(
                        Pair("Default Settings", "E13DEF")
                    )
                ), CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "E13ENA0"),
                        Pair("On", "E13ENA1")
                    ), "E13ENA"
                ), CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "E13CHK1"),
                        Pair("Transmit", "E13CHK2"),
                    ), "E13CHK"
                ), CodeFunction(
                    "2 Digit Addenda", listOf(
                        Pair("2 Digit Addenda Off", "E13AD20"),
                        Pair("2 Digit Addenda On", "E13AD21"),
                    ), "E13AD2"
                ), CodeFunction(
                    "5 Digit Addenda", listOf(
                        Pair("5 Digit Addenda Off", "E13AD50"),
                        Pair("5 Digit Addenda On", "E13AD51"),
                    ), "E13AD5"
                ), CodeFunction(
                    "Addenda Required", listOf(
                        Pair("Not Required", "E13REQ0"),
                        Pair("Required", "E13REQ1"),
                    ), "E13REQ"
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 290", listOf(
                        Pair("Off", "E132900"),
                        Pair("On", "E132901")
                    ), "E13290"
                ), CodeFunction(
                    "Ean-13 must have an extension code starting with 378/379", listOf(
                        Pair("Off", "E133780"),
                        Pair("On", "E133781")
                    ), "E13378"
                ), CodeFunction(
                    "Ean-13 must have an extension code starting with 414/419", listOf(
                        Pair("Off", "E134140"),
                        Pair("On", "E134141")
                    ), "E13414"
                ), CodeFunction(
                    "Ean-13 must have an extension code starting with 434/439", listOf(
                        Pair("Off", "E134340"),
                        Pair("On", "E134341")
                    ), "E13434"
                ), CodeFunction(
                    "Ean-13 must have an extension code starting with 977", listOf(
                        Pair("Off", "E139770"),
                        Pair("On", "E139771")
                    ), "E13977"
                ), CodeFunction(
                    "Ean-13 must have an extension code starting with 978", listOf(
                        Pair("Off", "E139780"),
                        Pair("On", "E139781")
                    ), "E13978"
                ), CodeFunction(
                    "Ean-13 must have an extension code starting with 979", listOf(
                        Pair("Off", "E139790"),
                        Pair("On", "E139791")
                    ), "E13979"
                )
            )
        ),
        CodeObj(
            "UPC-E", listOf(
                CodeFunction(
                    "Default UPC-E Settings", listOf(
                        Pair("Default Settings", "UPEDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "UPEENA0"),
                        Pair("On", "UPEENA1")
                    ), "UPEENA"
                ),
                CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "UPECHK1"),
                        Pair("Transmit", "UPECHK2"),
                    ), "UPECHK"
                ),
                CodeFunction(
                    "2 Digit Addenda", listOf(
                        Pair("2 Digit Addenda Off", "UPEAD20"),
                        Pair("2 Digit Addenda On", "UPEAD21"),
                    ), "UPEAD2"
                ),
                CodeFunction(
                    "5 Digit Addenda", listOf(
                        Pair("5 Digit Addenda Off", "UPEAD50"),
                        Pair("5 Digit Addenda On", "UPEAD51"),
                    ), "UPEAD5"
                ),
                CodeFunction(
                    "Addenda Required", listOf(
                        Pair("Not Required", "UPEREQ0"),
                        Pair("Required", "UPEREQ1"),
                    ), "UPEREQ"
                ),
                CodeFunction(
                    "Transmits leading characters", listOf(
                        Pair(
                            "Do not transmit leading characters (do not transmit national code and system characters)",
                            "UPEPRE0"
                        ),
                        Pair("Transport system character", "UPEPRE1"),
                        Pair("Transmit country code and system characters", "UPEPRE2"),
                    ), "UPEPRE"
                ),
                CodeFunction(
                    "Converted to UPC-A", listOf(
                        Pair("Don't convert to UPC-A", "UPEEXP0"),
                        Pair("Converted to UPC-A", "UPEEXP1"),
                    ), "UPEEXP"
                ),
            )
        ),
        CodeObj(
            "UPC-A", listOf(
                CodeFunction(
                    "Default UPC-A Settings", listOf(
                        Pair("Default Settings", "UPADEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "UPAENA0"),
                        Pair("On", "UPAENA1")
                    ), "UPAENA"
                ),
                CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "UPACHK1"),
                        Pair("Transmit", "UPACHK2"),
                    ), "UPACHK"
                ),
                CodeFunction(
                    "2 Digit Addenda", listOf(
                        Pair("2 Digit Addenda Off", "UPAAD20"),
                        Pair("2 Digit Addenda On", "UPAAD21"),
                    ), "UPAAD2"
                ),
                CodeFunction(
                    "5 Digit Addenda", listOf(
                        Pair("5 Digit Addenda Off", "UPAAD50"),
                        Pair("5 Digit Addenda On", "UPAAD51"),
                    ), "UPAAD5"
                ),
                CodeFunction(
                    "Addenda Required", listOf(
                        Pair("Not Required", "UPAREQ0"),
                        Pair("Required", "UPAREQ1"),
                    ), "UPAREQ"
                ),
                CodeFunction(
                    "Transmits leading characters", listOf(
                        Pair(
                            "Do not transmit leading characters (do not transmit national code and system characters)",
                            "UPAPRE0"
                        ),
                        Pair("Transport system character", "UPAPRE1"),
                        Pair("Transmit country code and system characters", "UPAPRE2"),
                    ), "UPAPRE"
                ),
            )
        ),
        CodeObj(
            "Interleaved 2 of 5", listOf(
                CodeFunction(
                    "Default Interleaved 2 of 5 Settings", listOf(
                        Pair(" Default Settings", "I25DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "I25ENA0"),
                        Pair("On", "I25ENA1")
                    ), "I25ENA"
                ),
                minLengthNew(1, 127, "I25"),
                maxLengthNew(1, 127, "I25"),
                CodeFunction(
                    "Check Character", listOf(
                        Pair("Off", "I25CHK0"),
                        Pair("On,Don’t Transmit", "I25CHK1"),
                        Pair("On,Transmit", "I25CHK2"),
                    ), "I25CHK"
                ),
                CodeFunction(
                    "Febraban", listOf(
                        Pair("Off Febraban", "I25FBB0"),
                        Pair("On Febraban,No information expansion", "I25FBB1"),
                        Pair("On Febraban,Expand information", "I25FBB2"),
                    ), "I25FBB"
                ),
            )
        ),
        CodeObj(
            "ITF-14", listOf(
                CodeFunction(
                    "Default ITF-14 Settings", listOf(
                        Pair("Default Settings", "I14DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "I14ENA0"),
                        Pair("On,Don’t Transmit", "I14ENA1"),
                        Pair("On,Transmit", "I14ENA2")
                    ), "I14ENA"
                ),
            )
        ),
        CodeObj(
            "ITF-6", listOf(
                CodeFunction(
                    "Default ITF-6 Settings", listOf(
                        Pair("Default Settings", "IT6DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "IT6ENA0"),
                        Pair("On,Don’t Transmit", "IT6ENA1"),
                        Pair("On,Transmit Check Character", "IT6ENA2")
                    ), "IT6ENA"
                ),
            )
        ),
        CodeObj(
            "Matrix 2 of 5", listOf(
                CodeFunction(
                    "Default Matrix 2 of 5 Settings", listOf(
                        Pair("Default Settings", "M25DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "M25ENA0"),
                        Pair("On", "M25ENA1"),
                    ), "M25ENA"
                ),
                minLengthNew(1, 127, "M25"),
                maxLengthNew(1, 127, "M25"),
                CodeFunction(
                    "Check Character", listOf(
                        Pair("Off", "M25CHK0"),
                        Pair("On,Don’t Transmit", "M25CHK1"),
                        Pair("On,Transmit", "M25CHK2")
                    ), "M25CHK"
                ),
            )
        ),
        CodeObj(
            "Code 39", listOf(
                CodeFunction(
                    "Default Code 39 Settings", listOf(
                        Pair("Default Settings", "C39DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "C39ENA0"),
                        Pair("On", "C39ENA1"),
                    ), "C39ENA"
                ),
                minLengthNew(1, 127, "C39"),
                maxLengthNew(1, 127, "C39"),
                CodeFunction(
                    "Check Character", listOf(
                        Pair("Off", "C39CHK0"),
                        Pair("On,Don’t Transmit", "C39CHK1"),
                        Pair("On,Transmit", "C39CHK2")
                    ), "C39CHK"
                ),
                CodeFunction(
                    "Start/Stop Characters", listOf(
                        Pair("Don’t Transmit", "C39TSC0"),
                        Pair("Transmit,but don’t transmit check character ", "C39TSC1"),
                    ), "C39TSC"
                ),
                CodeFunction(
                    "Full ASCII", listOf(
                        Pair("Off Code 39 Full ASCII", "C39ASC0"),
                        Pair("On Code 39 Full ASCII", "C39ASC1"),
                    ), "C39ASC"
                ),
                CodeFunction(
                    "Code 32 Pharmaceutical (PARAF)", listOf(
                        Pair("Off Code 39 Full ASCII", "C39E320"),
                        Pair("On Code 39 Full ASCII", "C39E321"),
                    ), "C39E32"
                ),
                CodeFunction(
                    "Code 32 前缀", listOf(
                        Pair("Off", "C39S320"),
                        Pair("On", "C39S321"),
                    ), "C39S32"
                ),
                CodeFunction(
                    "Code 32 Start/Stop Characters", listOf(
                        Pair("Don’t Transmit", "C39T320"),
                        Pair("Transmit", "C39T321"),
                    ), "C39T32"
                ),
                CodeFunction(
                    "Code 32 Check Character", listOf(
                        Pair("Don’t Transmit", "C39C320"),
                        Pair("Transmit", "C39C321"),
                    ), "C39C32"
                ),
            )
        ),
        CodeObj(
            "Codabar", listOf(
                CodeFunction(
                    "Default Codabar Settings", listOf(
                        Pair("Default Settings", "CBADEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "CBAENA0"),
                        Pair("On", "CBAENA1"),
                    ), "CBAENA"
                ),
                minLengthNew(1, 127, "CBA"),
                maxLengthNew(1, 127, "CBA"),
                CodeFunction(
                    "Check Character", listOf(
                        Pair("Off", "CBACHK0"),
                        Pair("On,Don’t Transmit", "CBACHK1"),
                        Pair("On,Transmit", "CBACHK2")
                    ), "CBACHK"
                ),
                CodeFunction(
                    "Start/Stop Characters", listOf(
                        Pair("Don’t Transmit", "CBATSC0"),
                        Pair("Transmit", "CBATSC1"),
                    ), "CBATSC"
                ),
                CodeFunction(
                    "Start/Stop Characters format", listOf(
                        Pair("ABCD/ABCD", "CBASCF0"),
                        Pair("ABCD/TN*E", "CBASCF1"),
                        Pair("abcd/abcd", "CBASCF2"),
                        Pair("abcd/tn*e", "CBASCF3"),
                    ), "CBASCF"
                ),
            )
        ),
        CodeObj(
            "Code 93", listOf(
                CodeFunction(
                    "Default Code 93 Settings", listOf(
                        Pair("Default Settings", "C93DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "C93ENA0"),
                        Pair("On", "C93ENA1"),
                    ), "C93ENA"
                ),
                minLengthNew(1, 127, "C93"),
                maxLengthNew(1, 127, "C93"),
                CodeFunction(
                    "Check Character", listOf(
                        Pair("Off", "C93CHK0"),
                        Pair("On,Don’t Transmit", "C93CHK1"),
                        Pair("On,Transmit", "C93CHK2")
                    ), "C93CHK"
                ),
            )
        ),
        CodeObj(
            "GS1-128(UCC/EAN-128)", listOf(
                CodeFunction(
                    "Default GS1-128 Settings", listOf(
                        Pair("Default Settings", "GS1DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "GS1ENA0"),
                        Pair("On", "GS1ENA1")
                    ), "GS1ENA"
                ),
                minLengthNew(1, 127, "GS1"),
                maxLengthNew(1, 127, "GS1"),
                CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "GS1CHK1"),
                        Pair("Transmit", "GS1CHK2")
                    ), "GS1CHK"
                ),
                CodeFunction(
                    "FNC1 Output", listOf(
                        Pair("On", "GS1FNC1"),
                        Pair("Off", "GS1FNC0")
                    ), "GS1FNC"
                ),
                CodeFunction(
                    "AIS", listOf(
                        Pair("On", "GS1AIP1"),
                        Pair("Off", "GS1AIP0")
                    ), "GS1AIP"
                ),
            )
        ),
        CodeObj(
            "GS1 DataBar(RSS)", listOf(
                CodeFunction(
                    "Default GS1 DataBar Settings", listOf(
                        Pair("Default Settings", "RSSDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "RSSENA0"),
                        Pair("On", "RSSENA1")
                    ), "RSSENA"
                ),
                CodeFunction(
                    "AI(01) Character", listOf(
                        Pair("Don’t Transmit", "RSSTAI0"),
                        Pair("Transmit", "RSSTAI1")
                    ), "RSSTAI"
                ),
            )
        ),
        CodeObj(
            "Code 11", listOf(
                CodeFunction(
                    "Default Code 11 Settings", listOf(
                        Pair("Default Settings", "C11DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "C11ENA0"),
                        Pair("On", "C11ENA1")
                    ), "C11ENA"
                ),
                minLengthNew(1, 127, "C11"),
                maxLengthNew(1, 127, "C11"),
                CodeFunction(
                    "Check", listOf(
                        Pair("Off", "C11CHK0"),
                        Pair("One Check Digit,MOD11", "C11CHK1"),
                        Pair("Two Check Digits,MOD11/MOD11", "C11CHK2"),
                        Pair("Two Check Digits,MOD11/MOD9", "C11CHK3"),
                        Pair(
                            "MOD11 Single Check Digits (Len<=10),MOD11/MOD11 Double Check Digits(Len>10)",
                            "C11CHK4"
                        ),
                        Pair(
                            "MOD11 Single Check Digits (Len<=10),MOD11/MOD9 Double Check Digits(Len>10)",
                            "C11CHK5"
                        ),
                    ), "C11CHK"
                ),
                CodeFunction(
                    "Check Character", listOf(
                        Pair("Don’t Transmit", "C11TCK0"),
                        Pair("Transmit", "C11TCK1")
                    ), "C11TCK"
                ),
            )
        ),
        CodeObj(
            "ISBN", listOf(
                CodeFunction(
                    "Default ISBN Settings", listOf(
                        Pair("Default Settings", "ISBDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "ISBENA0"),
                        Pair("On", "ISBENA1")
                    ), "ISBENA"
                ),
                CodeFunction(
                    "ISBN format", listOf(
                        Pair("ISBN-13", "ISBT100"),
                        Pair("ISBN-10", "ISBT101")
                    ), "ISBT10"
                ),
                CodeFunction(
                    "2 Digit Addenda", listOf(
                        Pair("2 Digit Addenda Off", "ISBAD20"),
                        Pair("2 Digit Addenda On", "ISBAD21"),
                    ), "ISBAD2"
                ),
                CodeFunction(
                    "5 Digit Addenda", listOf(
                        Pair("5 Digit Addenda Off", "EA8AD50"),
                        Pair("5 Digit Addenda On", "EA8AD51"),
                    ), "EA8AD5"
                ),
                CodeFunction(
                    "Addenda Required", listOf(
                        Pair("Not Required", "ISBREQ0"),
                        Pair("Required", "ISBREQ1"),
                    ), "ISBREQ"
                ),
            )
        ),
        CodeObj(
            "Industrial 25", listOf(
                CodeFunction(
                    "Default Industrial 25 Settings", listOf(
                        Pair("Default Settings", "L25DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "L25ENA0"),
                        Pair("On", "L25ENA1")
                    ), "L25ENA"
                ),
                minLengthNew(1, 127, "L25"),
                maxLengthNew(1, 127, "L25"),
                CodeFunction(
                    "Check", listOf(
                        Pair("Off", "L25CHK0"),
                        Pair("On,Don’t Transmit", "L25CHK1"),
                        Pair("On,Transmit", "L25CHK2")
                    ), "L25CHK"
                ),
            )
        ),
        CodeObj(
            "Standard 25", listOf(
                CodeFunction(
                    "Default Standard 25 Settings", listOf(
                        Pair("Default Settings", "S25DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "S25ENA0"),
                        Pair("On", "S25ENA1")
                    ), "S25ENA"
                ),
                minLengthNew(1, 127, "S25"),
                maxLengthNew(1, 127, "S25"),
                CodeFunction(
                    "Check", listOf(
                        Pair("Off", "S25CHK0"),
                        Pair("On,Don’t Transmit", "S25CHK1"),
                        Pair("On,Transmit", "S25CHK2")
                    ), "S25CHK"
                ),
            )
        ),
        CodeObj(
            "Plessey", listOf(
                CodeFunction(
                    "Default Plessey Settings", listOf(
                        Pair("Default Settings", "PLYDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "PLYENA0"),
                        Pair("On", "PLYENA1")
                    ), "PLYENA"
                ),
                minLengthNew(1, 127, "PLY"),
                maxLengthNew(1, 127, "PLY"),
                CodeFunction(
                    "Check", listOf(
                        Pair("Off", "PLYCHK0"),
                        Pair("On,Don’t Transmit", "PLYCHK1"),
                        Pair("On,Transmit", "PLYCHK2")
                    ), "PLYCHK"
                ),
            )
        ),
        CodeObj(
            "MSI Plessey", listOf(
                CodeFunction(
                    "Default MSI-Plessey Settings", listOf(
                        Pair("Default Settings", "MSIDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "MSIENA0"),
                        Pair("On", "MSIENA1")
                    ), "MSIENA"
                ),
                minLengthNew(1, 127, "MSI"),
                maxLengthNew(1, 127, "MSI"),
                CodeFunction(
                    "Check", listOf(
                        Pair("Off", "MSICHK0"),
                        Pair("One Check Digit,MOD10", "MSICHK1"),
                        Pair("Two Check Digits,MOD10/MOD10", "MSICHK2"),
                        Pair("Two Check Digits,MOD10/MOD11", "MSICHK3")
                    ), "MSICHK"
                ),
                CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "MSITCK0"),
                        Pair("Transmit,MOD10", "MSITCK1"),
                    ), "MSITCK"
                ),
            )
        ),
        CodeObj(
            "ISSN", listOf(
                CodeFunction(
                    "Default ISSN Settings", listOf(
                        Pair("Default Settings", "ISSDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "ISSENA0"),
                        Pair("On", "ISSENA1")
                    ), "ISSENA"
                ),
                CodeFunction(
                    "2 Digit Addenda", listOf(
                        Pair("2 Digit Addenda Off", "ISSAD20"),
                        Pair("2 Digit Addenda On", "ISSAD21"),
                    ), "ISSAD2"
                ),
                CodeFunction(
                    "5 Digit Addenda", listOf(
                        Pair("5 Digit Addenda Off", "ISSAD50"),
                        Pair("5 Digit Addenda On", "ISSAD51"),
                    ), "ISSAD5"
                ),
                CodeFunction(
                    "Addenda Required", listOf(
                        Pair("Not Required", "ISSREQ0"),
                        Pair("Required", "ISSREQ1"),
                    ), "ISSREQ"
                ),
            )
        ),
        CodeObj(
            "AIM 128", listOf(
                CodeFunction(
                    "Default AIM 128 Settings", listOf(
                        Pair("Default Settings", "AIMDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "AIMENA0"),
                        Pair("On", "AIMENA1")
                    ), "AIMENA"
                ),
                minLengthNew(1, 127, "AIM"),
                maxLengthNew(1, 127, "AIM"),
                CodeFunction(
                    "Transmit Check Character", listOf(
                        Pair("Don’t Transmit", "AIMCHK1"),
                        Pair("Transmit", "AIMCHK2")
                    ), "AIMCHK"
                ),
                CodeFunction(
                    "FNC1 Output", listOf(
                        Pair("On", "AIMFNC1"),
                        Pair("Off", "AIMFNC0")
                    ), "AIMFNC"
                ),
            )
        ),
        //二维码
        CodeObj(
            "PDF417", listOf(
                CodeFunction(
                    "Default PDF 417Settings", listOf(
                        Pair("Default Settings", "128DEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "PDFENA0"),
                        Pair("On", "PDFENA1")
                    ), "PDFENA"
                ),
                minLengthNew(1, 127, "PDF"),
                maxLengthNew(1, 127, "PDF"),
                CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Read Single Code", "PDFDOU0"),
                        Pair("Read Double Code", "PDFDOU1"),
                        Pair("Read Single/Double Code", "PDFDOU2"),
                    ), "PDFDOU"
                ),
                CodeFunction(
                    "ECI Output", listOf(
                        Pair("Off", "PDFECI0"),
                        Pair("On", "PDFECI1"),
                    ), "PDFECI"
                ),
                CodeFunction(
                    "character encoding format ", listOf(
                        Pair("Default", "PDFENC0"),
                        Pair("UTF-8", "PDFENC1"),
                    ), "PDFENC"
                ),
            )
        ),
        CodeObj(
            "QR Code", listOf(
                CodeFunction(
                    "Default QR Settings", listOf(
                        Pair("Default Settings", "QRCDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "QRCENA0"),
                        Pair("On", "QRCENA1")
                    ), "QRCENA"
                ),
                minLengthNew(1, 127, "QRC"),
                maxLengthNew(1, 127, "QRC"),
                CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Read Single Code", "QRCDOU0"),
                        Pair("Read Double Code", "QRCDOU1"),
                        Pair("Read Single/Double Code", "QRCDOU2"),
                    ), "QRCDOU"
                ),
                CodeFunction(
                    "ECI Output", listOf(
                        Pair("Off", "QRCECI0"),
                        Pair("On", "QRCECI1"),
                    ), "QRCECI"
                ),
                CodeFunction(
                    "character format", listOf(
                        Pair("Default", "QRCENC0"),
                        Pair("UTF-8", "QRCENC1"),
                    ), "QRCENC"
                ),
            )
        ),
        CodeObj(
            "Data Matrix", listOf(
                CodeFunction(
                    "Default Data Matrix Settings", listOf(
                        Pair("Default Settings", "DMCDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "DMCENA0"),
                        Pair("On", "DMCENA1")
                    ), "DMCENA"
                ),
                minLengthNew(1, 127, "DMC"),
                maxLengthNew(1, 127, "DMC"),
                CodeFunction(
                    "Read multi-code", listOf(
                        Pair("Read Single Code", "DMCDOU0"),
                        Pair("Read Double Code", "DMCDOU1"),
                        Pair("Read Single/Double Code", "DMCDOU2")
                    ), "DMCDOU"
                ),
                CodeFunction(
                    "Matrix Code", listOf(
                        Pair("Off", "DMCREC0"),
                        Pair("On", "DMCREC1"),
                    ), "DMCREC"
                ),
                CodeFunction(
                    "ECI Output", listOf(
                        Pair("Off", "DMCECI0"),
                        Pair("On", "DMCECI1")
                    ), "DMCECI"
                ),
                CodeFunction(
                    "Character encoding format", listOf(
                        Pair("Default", "DMCENC0"),
                        Pair("UTF-8", "DMCENC1"),
                    ), "DMCENC"
                ),
            )
        ),
        CodeObj(
            "Micro QR", listOf(
                CodeFunction(
                    "Default Micro QR Settings", listOf(
                        Pair("Default Settings", "MQRDEF")
                    )
                ),
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "MQRENA0"),
                        Pair("On", "MQRENA1")
                    ), "MQRENA"
                ),
                minLengthNew(1, 127, "MQR"),
                maxLengthNew(1, 127, "MQR"),
            )
        ),
        CodeObj(
            "Prefix/Suffix Settings", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "APSENA0"),
                        Pair("On", "APSENA1")
                    )
                ),
                CodeFunction(
                    "The prefix order", listOf(
                        Pair("Code ID+Custom prefix+AIM ID", "PRESEQ0"),
                        Pair("Custom prefix+Code ID+AIM ID", "PRESEQ1")
                    ), "PRESEQ"
                ),
                CodeFunction(
                    "On/Off custom prefix", listOf(
                        Pair("Off", "CPRENA0"),
                        Pair("On", "CPRENA1"),
                    ), "CPRENA"
                ),
                CodeFunction(
                    "AIM ID prefix", listOf(
                        Pair("Off", "AIDENA0"),
                        Pair("On", "AIDENA1"),
                    ), "AIDENA"
                ), CodeFunction(
                    "Custom suffixes", listOf(
                        Pair("Off", "CSUENA0"),
                        Pair("On", "CSUENA1"),
                    ), "CSUENA"
                ), CodeFunction(
                    "End suffix on/off", listOf(
                        Pair("Off", "TSUENA0"),
                        Pair("On", "TSUENA1"),
                    ), "TSUENA"
                ),
                CodeFunction(
                    "End suffix", listOf(
                        Pair("<ETX>(0x03)", "TSUSET03"),
                        Pair("<CR>(0x0D)", "TSUSET0D"),
                        Pair("<ETX>(0x0A)", "TSUSET0A"),
                        Pair("<CR><LF>(0x0D,0x0A)", "TSUSET0D0A"),
                    ), "TSUSET"
                )
            )
        ),
        CodeObj(
            "Code ID Prefix", listOf(
                CodeFunction(
                    "Default Code ID Settings",
                    listOf(Pair("Default Settings", "CIDDEF"))
                ),
                CodeFunction(
                    "Code ID Prefix", listOf(
                        Pair("Off", "CIDENA0"),
                        Pair("On", "CIDENA1")
                    ), "CIDENA"
                ),
            )
        ),
        CodeObj(
            "Country/Language Keyboard layout", listOf(
                CodeFunction(
                    "Country", listOf(
                        Pair("The United States", "KBWCTY0"),
                        Pair("Belgium", "KBWCTY1"),
                        Pair("Brazil", "KBWCTY2"),
                        Pair("Canada", "KBWCTY3"),
                        Pair("Czechoslovakia", "KBWCTY4"),
                        Pair("Denmark", "KBWCTY5"),
                        Pair("Finland", "KBWCTY6"),
                        Pair("French", "KBWCTY7"),
                        Pair("Germany/Austria", "KBWCTY8"),
                        Pair("Greece", "KBWCTY9"),
                        Pair("Hungary", "KBWCTY10"),
                        Pair("Israel", "KBWCTY11"),
                        Pair("Italy", "KBWCTY12"),
                        Pair("Latin America", "KBWCTY13"),
                        Pair("Holland", "KBWCTY14"),
                        Pair("Norway", "KBWCTY15"),
                        Pair("Poland", "KBWCTY16"),
                        Pair("Portugal", "KBWCTY17"),
                        Pair("Romania", "KBWCTY18"),
                        Pair("Russia", "KBWCTY19"),
                        Pair("Slovakia", "KBWCTY21"),
                        Pair("Spain", "KBWCTY22"),
                        Pair("Sweden", "KBWCTY23"),
                        Pair("Switzerland", "KBWCTY24"),
                        Pair("Turkey F", "KBWCTY25"),
                        Pair("Turkey Q", "KBWCTY26"),
                        Pair("Britain", "KBWCTY27"),
                        Pair("Japan", "KBWCTY28"),
                        Pair("Poland(programmer)", "KBWCTY29"),
                        Pair("Czech(programmer)", "KBWCTY30"),
                        Pair("Germany(no Dead Key)", "KBWCTY31"),
                    ), "KBWCTY"
                )
            )
        ),
        CodeObj(
            "Keyboard simulation input characters", listOf(
                CodeFunction(
                    "On/Off", listOf(
                        Pair("Off", "KBWALT0"),
                        Pair("On", "KBWALT1")
                    ), "KBWALT"
                ),
                CodeFunction(
                    "Code Page", listOf(
                        Pair("Code Page 1252(Latin, Western Europe)", "KBWCPG0"),
                        Pair("Code Page 1251(Cyril)", "KBWCPG1"),
                        Pair("Code Page 1250 (Middle Europe)", "KBWCPG2"),
                        Pair("Code Page 1253 (Greek)", "KBWCPG3"),
                        Pair("Code Page 1254 (Turkish)", "KBWCPG4"),
                        Pair("Code Page 1255 (Hebrew)", "KBWCPG5"),
                        Pair("Code Page 1256 (Arabic)", "KBWCPG6"),
                        Pair("Code Page 1257 (The Baltic sea)", "KBWCPG7"),
                        Pair("Code Page 1258 (Vietnamese)", "KBWCPG8"),
                        Pair("Code Page 936 (Simplified Chinese,GB2312,GBK)", "KBWCPG9"),
                        Pair("Code Page 950 (Traditional Chinese,Big5)", "KBWCPG10"),
                        Pair("Code Page 874 (Thai)", "KBWCPG11"),
                        Pair("Code Page 932 (Japanese,Shift-JIS)", "KBWCPG12"),
                        Pair("Code Page 949 (Korean,Unified Hangul Code)", "KBWCPG13"),
                    ), "KBWCPG"
                )
            )
        ),
    )
}

//08永久生效
//00临时生效
var str = "08"

//摩托二维 2707
fun getMoTo(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "User Preferences",
            listOf(
                CodeFunction(
                    "Parameter Bar Code Scanning",
                    listOf(
                        Pair("*Enable Parameter Bar Code Scanning", "07 C6 04 $str 00 EC 01"),
                        Pair("Disable Parameter Bar Code Scanning", "07 C6 04 $str 00 EC 00"),
                    ), "07 C7 04 $str 00 EC 01"
                ),
                CodeFunction(
                    "User Parameter Pass Through",
                    listOf(
                        Pair("Enable User Parameter Pass Through", "08 C6 04 $str 00 F1 71 01"),
                        Pair("*Disable User Parameter Pass Through", "08 C6 04 $str 00 F1 71 00"),
                    ), "08 C7 04 $str 00 F1 71 00"
                ),
                CodeFunction(
                    "Validate Concatenated Parameter Bar Codes",
                    listOf(
                        Pair(
                            "*Disable Validate Concatenated Parameter Bar Codes",
                            "08 C6 04 $str 00 F1 B4 00"
                        ),
                        Pair(
                            "Enable Validate Concatenated Parameter Bar Codes",
                            "08 C6 04 $str 00 F1 B4 01"
                        ),
                    ), "08 C7 04 $str 00 F1 B4 00"
                ),
                CodeFunction(
                    "Beep After Good Decode",
                    listOf(
                        Pair("*Enable Beep After Good Decode", "07 C6 04 $str 00 38 01"),
                        Pair("Disable Beep After Good Decode", "07 C6 04 $str 00 38 00"),
                    ), "07 C7 04 $str 00 38 01"
                ),
                CodeFunction(
                    "Beeper Volume",
                    listOf(
                        Pair("Low Volume", "07 C6 04 $str 00 8C 02"),
                        Pair("Medium Volume", "07 C6 04 $str 00 8C 01"),
                        Pair("*High Volume", "07 C6 04 $str 00 8C 00"),
                    ), "07 C7 04 $str 00 8C 00"
                ),
                CodeFunction(
                    "Beeper Tone",
                    listOf(
                        Pair("Disable Tone", "07 C6 04 $str 00 91 03"),
                        Pair("Low Tone", "07 C6 04 $str 00 91 02"),
                        Pair("*Medium Tone", "07 C6 04 $str 00 91 01"),
                        Pair("High Tone", "07 C6 04 $str 00 91 00"),
                        Pair("Medium to High Tone (2-tone)", "07 C6 04 $str 00 91 04"),
                    ), "07 C7 04 $str 00 91 01"
                ),
                CodeFunction(
                    "Beeper Duration",
                    listOf(
                        Pair("Short Duration", "08 C6 04 $str 00 F1 74 00"),
                        Pair("*Medium Duration", "08 C6 04 $str 00 F1 74 01"),
                        Pair("Long Duration", "08 C6 04 $str 00 F1 74 02")
                    ), "08 C7 04 $str 00 F1 74 01"
                ),
                CodeFunction(
                    "Suppress Power Up Beeps",
                    listOf(
                        Pair("*Do Not Suppress Power Up Beeps", "08 C6 04 $str 00 F1 D1 00"),
                        Pair("Suppress Power Up Beeps", "08 C6 04 $str 00 F1 D1 01"),
                    ), "08 C7 04 $str 00 F1 D1 00"
                ),
                CodeFunction(
                    "LED on Good Decode",
                    listOf(
                        Pair("*Enable LED on Good Decode", "08 C6 04 $str 00 F1 E8 02"),
                        Pair("Disable LED on Good Decode", "08 C6 04 $str 00 F1 E8 00"),
                    ), "08 C7 04 $str 00 F1 E8 02"
                ),
                CodeFunction(
                    "Direct Decode Indicator",
                    listOf(
                        Pair("*Disable Direct Decode Indicator", "08 C6 04 $str 00 F2 5B 00"),
                        Pair("1 Blink", "08 C6 04 $str 00 F2 5B 01"),
                        Pair("2 Blinks", "08 C6 04 $str 00 F2 5B 02"),
                    ), "08 C7 04 $str 00 F2 5B 00"
                ),
                CodeFunction(
                    "Low Power Mode",
                    listOf(
                        Pair("*Enable Low Power Mode", "07 C6 04 $str 00 80 01"),
                        Pair("Disable Low Power Mode", "07 C6 04 $str 00 80 00"),
                    ), "07 C7 04 $str 00 80 01"
                ),
                CodeFunction(
                    "Time Delay to Low Power Mode",
                    listOf(
                        Pair("*1 Second", "07 C6 04 $str 00 92 11"),
                        Pair("10 Seconds", "07 C6 04 $str 00 92 1A"),
                        Pair("1 Minute", "07 C6 04 $str 00 92 21"),
                        Pair("5 Minutes", "07 C6 04 $str 00 92 25"),
                        Pair("15 Minutes", "07 C6 04 $str 00 92 2B"),
                        Pair("30 Minutes", "07 C6 04 $str 00 92 2D"),
                        Pair("45 Minutes", "07 C6 04 $str 00 92 2E"),
                        Pair("1 Hour", "07 C6 04 $str 00 92 31"),
                        Pair("3 Hours", "07 C6 04 $str 00 92 33"),
                        Pair("6 Hours", "07 C6 04 $str 00 92 36"),
                        Pair("9 Hours", "07 C6 04 $str 00 92 39"),
                    ), "07 C7 04 $str 00 92 11"
                ),
                CodeFunction(
                    "Trigger Mode",
                    listOf(
                        Pair("*Standard (Level)", "07 C6 04 $str 00 8A 00"),
                        Pair("Presentation (Blink)", "07 C6 04 $str 00 8A 07"),
                        Pair("Auto Aim", "07 C6 04 $str 00 8A 09"),
                        Pair("Auto Aim with Illumination", "07 C6 04 $str 00 8A 0A"),
                        Pair("Host and Hardware Trigger Mode", "07 C6 04 $str 00 8A 08"),
                    ), "07 C7 04 $str 00 8A 00"
                ),
                CodeFunction(
                    "Picklist Mode",
                    listOf(
                        Pair("Enable Picklist Mode Always", "08 C6 04 $str 00 F0 92 02"),
                        Pair("*Disable Picklist Mode Always", "08 C6 04 $str 00 F0 92 00"),
                    ), "08 C7 04 $str 00 F0 92 00"
                ),
                CodeFunction(
                    "Continuous Bar Code Read",
                    listOf(
                        Pair("Enable Continuous Bar Code Read", "08 C6 04 $str 00 F1 89 01"),
                        Pair("*Disable Continuous Bar Code Read", "08 C6 04 $str 00 F1 89 00"),
                    ), "08 C7 04 $str 00 F1 89 00"
                ),
                CodeFunction(
                    "Unique Bar Code Reporting",
                    listOf(
                        Pair("Enable Unique Bar Code Reporting", "08 C6 04 $str 00 F1 D3 01"),
                        Pair("*Disable Unique Bar Code Reporting", "08 C6 04 $str 00 F1 D3 00"),
                    ), "08 C7 04 $str 00 F1 D3 00"
                ),
                CodeFunction(
                    "Unique Bar Code Reporting",
                    listOf(
                        Pair("Enable Unique Bar Code Reporting", "08 C6 04 $str 00 F1 D3 01"),
                        Pair("*Disable Unique Bar Code Reporting", "08 C6 04 $str 00 F1 D3 00"),
                    ), "08 C7 04 $str 00 F1 D3 00"
                ),
                CodeFunction(
                    "Mirrored Image",
                    listOf(
                        Pair("*Disable Mirrored Image", "08 C6 04 $str 00 F1 70 00"),
                        Pair("Enable Mirrored Image", "08 C6 04 $str 00 F1 70 01"),
                    ), "08 C7 04 $str 00 F1 70 00"
                ),
                CodeFunction(
                    "Mobile Phone/Display Mode",
                    listOf(
                        Pair("*Enable Mobile Phone/Display Mode", "08 C6 04 $str 00 F1 CC 00"),
                        Pair("Disable Mobile Phone/Display Mode", "08 C6 04 $str 00 F1 CC 03"),
                    ), "08 C7 04 $str 00 F1 CC 00"
                ),
                CodeFunction(
                    "PDF Prioritization",
                    listOf(
                        Pair("Enable PDF Prioritization", "09 C6 04 $str 00 F4 F1 CF 01"),
                        Pair("*Disable PDF Prioritization", "09 C6 04 $str 00 F4 F1 CF 00"),
                    ), "09 C7 04 $str 00 F4 F1 CF 00"
                ),
                CodeFunction(
                    "Low Light Scene Detection",
                    listOf(
                        Pair("*No Low Light Assist Scene Detection", "08 C6 04 $str 00 F2 2A 00"),
                        Pair(
                            "Aiming Pattern Low Light Assist Scene Detection",
                            "08 C6 04 $str 00 F2 2A 01"
                        ),
                        Pair(
                            "Dim Illumination Low Light Assist Scene Detection",
                            "08 C6 04 $str 00 F2 2A 02"
                        ),
                    ), "08 C7 04 $str 00 F2 2A 00"
                ),
            )
        ),
        CodeObj(
            "Miscellaneous Scanner Parameters",
            listOf(
                CodeFunction(
                    "Transmit Code ID Character",
                    listOf(
                        Pair("Symbol Code ID Character", "07 C6 04 $str 00 2D 02"),
                        Pair("AIM Code ID Character", "07 C6 04 $str 00 2D 01"),
                        Pair("*None", "07 C6 04 $str 00 2D 00"),
                    ), "07 C7 04 $str 00 2D 00"
                ),
                CodeFunction(
                    "Scan Data Transmission Format",
                    listOf(
                        Pair("*Data As Is", "07 C6 04 $str 00 EB 00"),
                        Pair("<DATA> <SUFFIX 1>", "07 C6 04 $str 00 EB 01"),
                        Pair("<DATA> <SUFFIX 2>", "07 C6 04 $str 00 EB 02"),
                        Pair("<DATA> <SUFFIX 1> <SUFFIX 2>", "07 C6 04 $str 00 EB 03"),
                        Pair("<PREFIX> <DATA >", "07 C6 04 $str 00 EB 04"),
                        Pair("<PREFIX> <DATA> <SUFFIX 1>", "07 C6 04 $str 00 EB 05"),
                        Pair("<PREFIX> <DATA> <SUFFIX 2>", "07 C6 04 $str 00 EB 06"),
                        Pair("<PREFIX> <DATA> <SUFFIX 1> <SUFFIX 2>", "07 C6 04 $str 00 EB 07"),
                    ), "07 C7 04 $str 00 EB 00"
                ),
                CodeFunction(
                    "Transmit “No Read” Message",
                    listOf(
                        Pair("Enable No Read", "07 C6 04 $str 00 5E 01"),
                        Pair("*Disable No Read", "07 C6 04 $str 00 5E 00"),
                    ), "07 C7 04 $str 00 5E 00"
                ),
            )
        ),
        CodeObj(
            "Image Capture Preferences",
            listOf(
                CodeFunction(
                    "Aim Brightness", listOf(
                        Pair("Aim Brightness - Low", "08 C6 04 $str 00 F1 9C 00"),
                        Pair("Aim Brightness - Medium", "08 C6 04 $str 00 F1 9C 01"),
                        Pair("*Aim Brightness - High", "08 C6 04 $str 00 F1 9C 02"),
                    ), "08 C7 04 $str 00 F1 9C 02"
                ),
                CodeFunction(
                    "Decoding Autoexposure", listOf(
                        Pair("*Enable Decoding Autoexposure", "08 C6 04 $str 00 F0 29 01"),
                        Pair("Disable Decoding Autoexposure", "08 C6 04 $str 00 F0 29 00"),
                    ), "08 C7 04 $str 00 F0 29 01"
                ),
                CodeFunction(
                    "Decoding Illumination", listOf(
                        Pair("*Enable Decoding Illumination", "08 C6 04 $str 00 F0 2A 01"),
                        Pair("Disable Decoding Illumination", "08 C6 04 $str 00 F0 2A 00"),
                    ), "08 C7 04 $str 00 F0 2A 01"
                ),
                CodeFunction(
                    "Decode Aiming Pattern", listOf(
                        Pair("*Enable Decode Aiming Pattern", "08 C6 04 $str 00 F0 32 02"),
                        Pair("Disable Decode Aiming Pattern", "08 C6 04 $str 00 F0 32 00"),
                    ), "08 C7 04 $str 00 F0 32 02"
                ),
                CodeFunction(
                    "Image Capture Illumination", listOf(
                        Pair("*Enable Image Capture Illumination", "08 C6 04 $str 00 F0 69 01"),
                        Pair("Disable Image Capture Illumination", "08 C6 04 $str 00 F0 69 00"),
                    ), "08 C7 04 $str 00 F0 69 01"
                ),
                CodeFunction(
                    "Image Capture Autoexposure", listOf(
                        Pair("*Enable Image Capture Autoexposure", "08 C6 04 $str 00 F0 68 01"),
                        Pair("Disable Image Capture Autoexposure", "08 C6 04 $str 00 F0 68 00"),
                    ), "08 C7 04 $str 00 F0 68 01"
                ),
                CodeFunction(
                    "Analog Gain", listOf(
                        Pair("*Analog Gain 1", "08 C6 04 $str 00 F4 D0 01"),
                        Pair("Analog Gain 2", "08 C6 04 $str 00 F4 D0 02"),
                        Pair("Analog Gain 3", "08 C6 04 $str 00 F4 D0 03"),
                        Pair("Analog Gain 4", "08 C6 04 $str 00 F4 D0 04"),
                        Pair("Analog Gain 5", "08 C6 04 $str 00 F4 D0 05"),
                        Pair("Analog Gain 6", "08 C6 04 $str 00 F4 D0 06"),
                    ), "08 C7 04 $str 00 F4 D0 01"
                ),
                CodeFunction(
                    "Snapshot Aiming Pattern", listOf(
                        Pair("*Enable Snapshot Aiming Pattern", "08 C6 04 $str 00 F0 2C 01"),
                        Pair("Disable Snapshot Aiming Pattern", "08 C6 04 $str 00 F0 2C 00"),
                    ), "08 C7 04 $str 00 F0 2C 01"
                ),
                CodeFunction(
                    "Image Size (Number of Pixels)", listOf(
                        Pair("*Full Resolution", "08 C6 04 $str 00 F0 2E 00"),
                        Pair("1/2 Resolution", "08 C6 04 $str 00 F0 2E 01"),
                        Pair("1/4 Resolution", "08 C6 04 $str 00 F0 2E 03"),
                    ), "08 C7 04 $str 00 F0 2E 00"
                ),
                CodeFunction(
                    "Image File Format Selector", listOf(
                        Pair("BMP File Format", "08 C6 04 $str 00 F0 30 03"),
                        Pair("*JPEG File Format", "08 C6 04 $str 00 F0 30 01"),
                        Pair("TIFF File Format", "08 C6 04 $str 00 F0 30 04"),
                    ), "08 C7 04 $str 00 F0 30 01"
                ),
                CodeFunction(
                    "Image Rotation", listOf(
                        Pair("*Rotate 0", "08 C6 04 $str 00 F1 99 00"),
                        Pair("Rotate 90", "08 C6 04 $str 00 F1 99 01"),
                        Pair("Rotate 180", "08 C6 04 $str 00 F1 99 02"),
                        Pair("Rotate 270", "08 C6 04 $str 00 F1 99 03"),
                    ), "08 C7 04 $str 00 F1 99 00"
                ),
            )
        ),
        CodeObj(
            "SSI Host Parameters",
            listOf(
                //以下三项是高危操作，用户设置后可能会导致扫码头死机无法扫码问题，暂时屏蔽
//                CodeFunction(
//                    "Baud Rate",
//                    listOf(
//                        Pair("9600", "07 C6 04 $str 00 9C 06"),
//                        Pair("19200", "07 C6 04 $str 00 9C 07"),
//                        Pair("38400", "07 C6 04 $str 00 9C 08"),
//                        Pair("57600", "07 C6 04 $str 00 9C 0A"),
//                        Pair("115200", "07 C6 04 $str 00 9C 0B"),
//                    ), "07 C7 04 $str 00 9C 06"
//                ),
//                CodeFunction(
//                    "Parity",
//                    listOf(
//                        Pair("Odd", "07 C6 04 $str 00 9E 00"),
//                        Pair("Even", "07 C6 04 $str 00 9E 01"),
//                        Pair("*None", "07 C6 04 $str 00 9E 04")
//                    ), "07 C7 04 $str 00 9E 04"
//                ),
//                CodeFunction(
//                    "Check Parity",
//                    listOf(
//                        Pair("*Do Not Check Parity", "07 C6 04 $str 00 97 00"),
//                        Pair("Check Parity", "07 C6 04 $str 00 97 01")
//                    ), "07 C7 04 $str 00 97 00"
//                ),
                CodeFunction(
                    "Stop Bits", listOf(
                        Pair("*1 Stop Bit", "07 C6 04 $str 00 9D 01"),
                        Pair("2 Stop Bits", "07 C6 04 $str 00 9D 02")
                    ), "07 C7 04 $str 00 9D 01"
                ),
//                CodeFunction(
//                    "Software Handshaking", listOf(
//                        Pair("Disable ACK/NAK", "07 C6 04 $str 00 9F 00"),
//                        Pair("*Enable ACK/NAK", "07 C6 04 $str 00 9F 01")
//                    )
//                ),
                CodeFunction(
                    "Host RTS Line State", listOf(
                        Pair("*Low", "07 C6 04 $str 00 9A 00"),
                        Pair("High", "07 C6 04 $str 00 9A 01")
                    ), "07 C7 04 $str 00 9A 00"
                ),
                CodeFunction(
                    "Decode Data Packet Format", listOf(
                        Pair("*Send Raw Decode Data", "07 C6 04 $str 00 EE 00"),
                        Pair("Send Packeted Decode Data", "07 C6 04 $str 00 EE 01")
                    ), "07 C7 04 $str 00 EE 00"
                ),
                CodeFunction(
                    "Host Serial Response Timeout", listOf(
                        Pair("*Low - 2 Seconds", "07 C6 04 $str 00 9B 14"),
                        Pair("Medium - 5 Seconds", "07 C6 04 $str 00 9B 32"),
                        Pair("High - 7.5 Seconds", "07 C6 04 $str 00 9B 4B"),
                        Pair("Maximum - 9.9 Seconds", "07 C6 04 $str 00 9B 63")
                    ), "07 C7 04 $str 00 9B 14"
                ),
                CodeFunction(
                    "Host Character Timeout", listOf(
                        Pair("*Low - 200 msec", "07 C6 04 $str 00 EF 14"),
                        Pair("Medium - 500 msec", "07 C6 04 $str 00 EF 32"),
                        Pair("High - 750 msec", "07 C6 04 $str 00 EF 4B"),
                        Pair("Maximum - 990 msec", "07 C6 04 $str 00 EF 63")
                    ), "07 C7 04 $str 00 EF 14"
                ),
                CodeFunction(
                    "Multipacket Option", listOf(
                        Pair("*Multipacket Option 1", "08 C6 04 $str 00 F0 4E 00"),
                        Pair("Multipacket Option 2", "08 C6 04 $str 00 F0 4E 01"),
                        Pair("Multipacket Option 3", "08 C6 04 $str 00 F0 4E 02"),
                    ), "08 C7 04 $str 00 F0 4E 00"
                ),
                CodeFunction(
                    "Interpacket Delay", listOf(
                        Pair("*Minimum - 0 msec", "08 C6 04 $str 00 F0 4F 00"),
                        Pair("Low - 25 msec", "08 C6 04 $str 00 F0 4F 19"),
                        Pair("Medium - 50 msec", "08 C6 04 $str 00 F0 4F 32"),
                        Pair("High - 75 msec", "08 C6 04 $str 00 F0 4F 4B"),
                        Pair("Maximum - 99 msec", "08 C6 04 $str 00 F0 4F 63")
                    ), "08 C7 04 $str 00 F0 4F 00"
                )
            ),
        ),
        CodeObj(
            "Event Reporting",
            listOf(
                CodeFunction(
                    "Decode Event", listOf(
                        Pair("Enable Decode Event", "08 C6 04 $str 00 F0 00 01"),
                        Pair("*Disable Decode Event", "08 C6 04 $str 00 F0 00 00")
                    ), "08 C7 04 $str 00 F0 00 00"
                ),
                CodeFunction(
                    "Boot Up Event", listOf(
                        Pair("Enable Boot Up Event", "08 C6 04 $str 00 F0 02 01"),
                        Pair("*Disable Boot Up Event", "08 C6 04 $str 00 F0 02 00")
                    ), "08 C7 04 $str 00 F0 02 00"
                ),
                CodeFunction(
                    "Parameter Event", listOf(
                        Pair("Enable Parameter Event", "08 C6 04 $str 00 F0 03 01"),
                        Pair("*Disable Parameter Event", "08 C6 04 $str 00 F0 03 00")
                    ), "08 C7 04 $str 00 F0 03 00"
                )
            )
        ),
        CodeObj(
            "UPC/EAN/JAN", listOf(
                CodeFunction(
                    "UPC-A",
                    listOf(
                        Pair("*Enable UPC-A", "07 C6 04 $str 00 01 01"),
                        Pair("Disable UPC-A", "07 C6 04 $str 00 01 00"),
                    ), "07 C7 04 $str 00 01 01"
                ),
                CodeFunction(
                    "UPC-E",
                    listOf(
                        Pair("*Enable UPC-E", "07 C6 04 $str 00 02 01"),
                        Pair("Disable UPC-E", "07 C6 04 $str 00 02 00"),
                    ), "07 C7 04 $str 00 02 01"
                ),
                CodeFunction(
                    "UPC-E1",
                    listOf(
                        Pair("Enable UPC-E1", "07 C6 04 $str 00 0C 01"),
                        Pair("*Disable UPC-E1", "07 C6 04 $str 00 0C 00"),
                    ), "07 C7 04 $str 00 0C 00"
                ),
                CodeFunction(
                    "EAN-8/JAN-8",
                    listOf(
                        Pair("*Enable EAN-8/JAN-8", "07 C6 04 $str 00 04 01"),
                        Pair("Disable EAN-8/JAN-8", "07 C6 04 $str 00 04 00"),
                    ), "07 C7 04 $str 00 04 01"
                ),
                CodeFunction(
                    "EAN-13/JAN-13",
                    listOf(
                        Pair("*Enable EAN-13/JAN-13", "07 C6 04 $str 00 03 01"),
                        Pair("Disable EAN-13/JAN-13", "07 C6 04 $str 00 03 00"),
                    ), "07 C7 04 $str 00 03 01"
                ),
                CodeFunction(
                    "Bookland EAN",
                    listOf(
                        Pair("Enable Bookland EAN", "07 C6 04 $str 00 53 01"),
                        Pair("*Disable Bookland EAN", "07 C6 04 $str 00 53 00"),
                    ), "07 C7 04 $str 00 53 00"
                ),
                CodeFunction(
                    "Bookland ISBN Format",
                    listOf(
                        Pair("*Bookland ISBN-10", "08 C6 04 $str 00 F1 40 00"),
                        Pair("Bookland ISBN-13", "08 C6 04 $str 00 F1 40 01"),
                    ), "08 C7 04 $str 00 F1 40 00"
                ),
                CodeFunction(
                    "ISSN EAN",
                    listOf(
                        Pair("Enable ISSN EAN", "08 C6 04 $str 00 F1 69 01"),
                        Pair("*Disable ISSN EAN", "08 C6 04 $str 00 F1 69 00"),
                    ), "08 C7 04 $str 00 F1 69 00"
                ),
                CodeFunction(
                    "Decode UPC/EAN/JAN Supplementals",
                    listOf(
                        Pair(
                            "Decode UPC/EAN/JAN With Supplementals Only",
                            "07 C6 04 $str 00 10 01"
                        ),
                        Pair("*Ignore UPC/EAN/JAN Supplementals", "07 C6 04 $str 00 10 00"),
                        Pair(
                            "Autodiscriminate UPC/EAN/JAN with Supplementals",
                            "07 C6 04 $str 00 10 02"
                        ),
                        Pair("Enable 378/379 Supplemental Mode", "07 C6 04 $str 00 10 04"),
                        Pair("Enable 978/979 Supplemental Mode", "07 C6 04 $str 00 10 05"),
                        Pair("Enable 977 Supplemental Mode", "07 C6 04 $str 00 10 07"),
                        Pair("Enable 414/419/434/439 Supplemental Mode", "07 C6 04 $str 00 10 06"),
                        Pair("Enable 491 Supplemental Mode", "07 C6 04 $str 00 10 08"),
                        Pair("Enable Smart Supplemental Mode", "07 C6 04 $str 00 10 03"),
                        Pair("Supplemental User-Programmable Type 1", "07 C6 04 $str 00 10 09"),
                        Pair(
                            "Supplemental User-Programmable Type 1 and 2",
                            "07 C6 04 $str 00 10 0A"
                        ),
                        Pair(
                            "Smart Supplemental Plus User-Programmable 1",
                            "07 C6 04 $str 00 10 0B"
                        ),
                        Pair(
                            "Smart Supplemental Plus User-Programmable 1 and 2",
                            "07 C6 04 $str 00 10 0C"
                        ),
                    ), "07 C7 04 $str 00 10 00"
                ),
                CodeFunction(
                    "UPC/EAN/JAN Supplemental AIM ID Format",
                    listOf(
                        Pair("Separate", "08 C6 04 $str 00 F1 A0 00"),
                        Pair("*Combined", "08 C6 04 $str 00 F1 A0 01"),
                        Pair("Separate Transmissions", "08 C6 04 $str 00 F1 A0 02"),
                    ), "08 C7 04 $str 00 F1 A0 01"
                ),
                CodeFunction(
                    "Transmit UPC-A Check Digit",
                    listOf(
                        Pair("*Transmit UPC-A Check Digit", "07 C6 04 $str 00 28 01"),
                        Pair("Do Not Transmit UPC-A Check Digit", "07 C6 04 $str 00 28 00"),
                    ), "07 C7 04 $str 00 28 01"
                ),
                CodeFunction(
                    "Transmit UPC-E Check Digit",
                    listOf(
                        Pair("*Transmit UPC-E Check Digit", "07 C6 04 $str 00 29 01"),
                        Pair("Do Not Transmit UPC-E Check Digit", "07 C6 04 $str 00 29 00"),
                    ), "07 C7 04 $str 00 29 01"
                ),
                CodeFunction(
                    "Transmit UPC-E1 Check Digit",
                    listOf(
                        Pair("*Transmit UPC-E1 Check Digit", "07 C6 04 $str 00 2A 01"),
                        Pair("Do Not Transmit UPC-E1 Check Digit", "07 C6 04 $str 00 2A 00"),
                    ), "07 C7 04 $str 00 2A 01"
                ),
                CodeFunction(
                    "UPC-A Preamble",
                    listOf(
                        Pair("No Preamble (<DATA>)", "07 C6 04 $str 00 22 00"),
                        Pair(
                            "*System Character(<SYSTEM CHARACTER> <DATA>)",
                            "07 C6 04 $str 00 22 01"
                        ),
                        Pair(
                            "System Character & Country Code(<COUNTRY CODE><SYSTEM CHARACTER><DATA>)",
                            "07 C6 04 $str 00 22 02"
                        ),
                    ), "07 C7 04 $str 00 22 01"
                ),
                CodeFunction(
                    "UPC-E Preamble",
                    listOf(
                        Pair("No Preamble (<DATA>)", "07 C6 04 $str 00 23 00"),
                        Pair(
                            "*System Character(<SYSTEM CHARACTER> <DATA>)",
                            "07 C6 04 $str 00 23 01"
                        ),
                        Pair(
                            "System Character & Country Code(<COUNTRY CODE><SYSTEM CHARACTER><DATA>)",
                            "07 C6 04 $str 00 23 02"
                        ),
                    ), "07 C7 04 $str 00 23 01"
                ),
                CodeFunction(
                    "UPC-E1 Preamble",
                    listOf(
                        Pair("No Preamble (<DATA>)", "07 C6 04 $str 00 24 00"),
                        Pair(
                            "*System Character(<SYSTEM CHARACTER> <DATA>)",
                            "07 C6 04 $str 00 24 01"
                        ),
                        Pair(
                            "System Character & Country Code(<COUNTRY CODE><SYSTEM CHARACTER><DATA>)",
                            "07 C6 04 $str 00 24 02"
                        ),
                    ), "07 C7 04 $str 00 24 01"
                ),
                CodeFunction(
                    "Convert UPC-E to UPC-A",
                    listOf(
                        Pair("Convert UPC-E to UPC-A (Enable)", "07 C6 04 $str 00 25 01"),
                        Pair("*Do Not Convert UPC-E to UPC-A (Disable)", "07 C6 04 $str 00 25 00"),
                    ), "07 C7 04 $str 00 25 00"
                ),
                CodeFunction(
                    "Convert UPC-E1 to UPC-A",
                    listOf(
                        Pair("Convert UPC-E1 to UPC-A (Enable)", "07 C6 04 $str 00 26 01"),
                        Pair("*Do Not Convert UPC-E1 to UPC-A (Disable)", "07 C6 04 $str 00 26 00"),
                    ), "07 C7 04 $str 00 26 00"
                ),
                CodeFunction(
                    "EAN/JAN Zero Extend",
                    listOf(
                        Pair("Enable EAN/JAN Zero Extend", "07 C6 04 $str 00 27 01"),
                        Pair("*Disable EAN/JAN Zero Extend", "07 C6 04 $str 00 27 00"),
                    ), "07 C7 04 $str 00 27 00"
                ),
                CodeFunction(
                    "UCC Coupon Extended Code",
                    listOf(
                        Pair("Enable UCC Coupon Extended Code", "07 C6 04 $str 00 55 01"),
                        Pair("*Disable UCC Coupon Extended Code", "07 C6 04 $str 00 55 00"),
                    ), "07 C7 04 $str 00 55 00"
                ),
                CodeFunction(
                    "Coupon Report",
                    listOf(
                        Pair("Old Coupon Format", "08 C6 04 $str 00 F1 DA 00"),
                        Pair("*New Coupon Format", "08 C6 04 $str 00 F1 DA 01"),
                        Pair("Autodiscriminate Coupon Format", "08 C6 04 $str 00 F1 DA 02"),
                    ), "08 C7 04 $str 00 F1 DA 01"
                ),
                CodeFunction(
                    "UPC Reduced Quiet Zone",
                    listOf(
                        Pair("Enable UPC Reduced Quiet Zone", "09 C6 04 $str 00 F8 05 09 01"),
                        Pair("*Disable UPC Reduced Quiet Zone", "09 C6 04 $str 00 F8 05 09 00"),
                    ), "09 C7 04 $str 00 F8 05 09 01"
                ),
            )
        ),
        CodeObj(
            "Code 128", listOf(
                CodeFunction(
                    "Enable/Disable Code 128 Code 128",
                    listOf(
                        Pair("*Enable Code 128", "07 C6 04 $str 00 08 01"),
                        Pair("Disable Code 128", "07 C6 04 $str 00 08 00")
                    ), "07 C7 04 $str 00 08 01"
                ),
                CodeFunction(
                    "GS1-128 (formerly UCC/EAN-128)",
                    listOf(
                        Pair("*Enable GS1-128", "07 C6 04 $str 00 0E 01"),
                        Pair("Disable GS1-128", "07 C6 04 $str 00 0E 00")
                    ), "07 C7 04 $str 00 0E 01"
                ),
                CodeFunction(
                    "ISBT 128",
                    listOf(
                        Pair("*Enable ISBT 128", "07 C6 04 $str 00 54 01"),
                        Pair("Disable ISBT 128", "07 C6 04 $str 00 54 00")
                    ), "07 C7 04 $str 00 54 01"
                ),
                CodeFunction(
                    "ISBT Concatenation",
                    listOf(
                        Pair("Enable ISBT Concatenation", "08 C6 04 $str 00 F1 41 01"),
                        Pair("*Disable ISBT Concatenation", "08 C6 04 $str 00 F1 41 00"),
                        Pair("Autodiscriminate ISBT Concatenation", "08 C6 04 $str 00 F1 41 02")
                    ), "08 C7 04 $str 00 F1 41 00"
                ),
                CodeFunction(
                    "Check ISBT Table",
                    listOf(
                        Pair("*Enable Check ISBT Table", "08 C6 04 $str 00 F1 42 01"),
                        Pair("Disable Check ISBT Table", "08 C6 04 $str 00 F1 42 00")
                    ), "08 C7 04 $str 00 F1 42 01"
                ),
                CodeFunction(
                    "Code 128 <FNC4>",
                    listOf(
                        Pair("*Honor Code 128 <FNC4>", "09 C6 04 $str 00 F8 04 E6 00"),
                        Pair("Ignore Code 128 <FNC4>", "09 C6 04 $str 00 F8 04 E6 01")
                    ), "09 C7 04 $str 00 F8 04 E6 00"
                ),
                CodeFunction(
                    "Code 128 Security Level",
                    listOf(
                        Pair("Code 128Security Level 0", "08 C6 04 $str 00 F1 EF 00"),
                        Pair("*Code 128 Security Level 1", "08 C6 04 $str 00 F1 EF 01"),
                        Pair("Code 128 Security Level 2", "08 C6 04 $str 00 F1 EF 02"),
                        Pair("Code 128 Security Level 3", "08 C6 04 $str 00 F1 EF 03"),
                    ), "08 C7 04 $str 00 F1 EF 03"
                ),
                CodeFunction(
                    "Code 128 Reduced Quiet Zone",
                    listOf(
                        Pair("Enable Code 128 Reduced Quiet Zone", "09 C6 04 $str 00 F8 04 B8 01"),
                        Pair("*Disable Code 128 Reduced Quiet Zone", "09 C6 04 $str 00 F8 04 B8 00")
                    ), "09 C7 04 $str 00 F8 04 B8 00"
                ),
            )
        ),
        CodeObj(
            "Code 39", listOf(
                CodeFunction(
                    "Enable/Disable Code 39", listOf(
                        Pair("*Enable Code 39", "07 C6 04 $str 00 00 01"),
                        Pair("Disable Code 39", "07 C6 04 $str 00 00 00"),
                    ), "07 C7 04 $str 00 00 01"
                ),
                CodeFunction(
                    "Trioptic Code 39", listOf(
                        Pair("Enable Trioptic Code 39", "07 C6 04 $str 00 0D 01"),
                        Pair("*Disable Trioptic Code 39", "07 C6 04 $str 00 0D 00"),
                    ), "07 C7 04 $str 00 0D 00"
                ),
                CodeFunction(
                    "Convert Code 39 to Code 32", listOf(
                        Pair("Enable Convert Code 39 to Code 32", "07 C6 04 $str 00 56 01"),
                        Pair("*Disable Convert Code 39 to Code 32", "07 C6 04 $str 00 56 00"),
                    ), "07 C7 04 $str 00 56 00"
                ),
                CodeFunction(
                    "Code 32 Prefix", listOf(
                        Pair("Enable Code 32 Prefix", "07 C6 04 $str 00 E7 01"),
                        Pair("*Disable Code 32 Prefix", "07 C6 04 $str 00 E7 00"),
                    ), "07 C7 04 $str 00 E7 00"
                ),
                CodeFunction(
                    "Code 39 Check Digit Verification", listOf(
                        Pair("Enable Code 39 Check Digit", "07 C6 04 $str 00 30 01"),
                        Pair("*Disable Code 39 Check Digit", "07 C6 04 $str 00 30 00"),
                    ), "07 C7 04 $str 00 30 00"
                ),
                CodeFunction(
                    "Transmit Code 39 Check Digit", listOf(
                        Pair("Transmit Code 39 Check Digit (Enable)", "07 C6 04 $str 00 2B 01"),
                        Pair(
                            "*Do Not Transmit Code 39 Check Digit (Disable)",
                            "07 C6 04 $str 00 2B 00"
                        ),
                    ), "07 C7 04 $str 00 2B 00"
                ),
                CodeFunction(
                    "Code 39 Full ASCII Conversion", listOf(
                        Pair("Enable Code 39 Full ASCII", "07 C6 04 $str 00 11 01"),
                        Pair("*Disable Code 39 Full ASCII", "07 C6 04 $str 00 11 00"),
                    ), "07 C7 04 $str 00 11 00"
                ),
                CodeFunction(
                    "Code 39 Security Level", listOf(
                        Pair("Code 39 Security Level 0", "08 C6 04 $str 00 F1 EE 00"),
                        Pair("*Code 39 Security Level 1", "08 C6 04 $str 00 F1 EE 01"),
                        Pair("Code 39 Security Level 2", "08 C6 04 $str 00 F1 EE 02"),
                        Pair("Code 39 Security Level 3", "08 C6 04 $str 00 F1 EE 03"),
                    ), "08 C7 04 $str 00 F1 EE 01"
                ),
                CodeFunction(
                    "Code 39 Reduced Quiet Zone", listOf(
                        Pair("Enable Code 39 Reduced Quiet Zone", "09 C6 04 $str 00 F8 04 B9 01"),
                        Pair("*Disable Code 39 Reduced Quiet Zone", "09 C6 04 $str 00 F8 04 B9 00"),
                    ), "09 C7 04 $str 00 F8 04 B9 00"
                ),
            )
        ),
        CodeObj(
            "Code 93", listOf(
                CodeFunction(
                    "Enable/Disable Code 93", listOf(
                        Pair("*Enable Code 93", "07 C6 04 $str 00 09 01"),
                        Pair("Disable Code 93", "07 C6 04 $str 00 09 00"),
                    ), "07 C7 04 $str 00 09 01"
                ),
            )
        ),
        CodeObj(
            "Code 11", listOf(
                CodeFunction(
                    "Enable/Disable Code 11", listOf(
                        Pair("Enable Code 11", "07 C6 04 $str 00 0A 01"),
                        Pair("*Disable Code 11", "07 C6 04 $str 00 0A 00"),
                    ), "07 C7 04 $str 00 0A 00"
                ),
                CodeFunction(
                    "Code 11 Check Digit Verification", listOf(
                        Pair("*Disable", "07 C6 04 $str 00 34 00"),
                        Pair("One Check Digit", "07 C6 04 $str 00 34 01"),
                        Pair("Two Check Digits", "07 C6 04 $str 00 34 02"),
                    ), "07 C7 04 $str 00 34 00"
                ),
                CodeFunction(
                    "Code 11 Check Digit Verification", listOf(
                        Pair("*Disable", "07 C6 04 $str 00 34 00"),
                        Pair("One Check Digit", "07 C6 04 $str 00 34 01"),
                        Pair("Two Check Digits", "07 C6 04 $str 00 34 02"),
                    ), "07 C7 04 $str 00 34 00"
                ),
                CodeFunction(
                    "Transmit Code 11 Check Digits", listOf(
                        Pair("Transmit Code 11 Check Digit(s) (Enable)", "07 C6 04 $str 00 2F 01"),
                        Pair(
                            "*Do Not Transmit Code 11 Check Digit(s) (Disable)",
                            "07 C6 04 $str 00 2F 00"
                        ),
                    ), "07 C7 04 $str 00 2F 00"
                ),
            )
        ),
        CodeObj(
            "Interleaved 2 of 5 (ITF)", listOf(
                CodeFunction(
                    "Enable/Disable Interleaved 2 of 5", listOf(
                        Pair("Enable Interleaved 2 of 5", "07 C6 04 $str 00 06 01"),
                        Pair("*Disable Interleaved 2 of 5", "07 C6 04 $str 00 06 00"),
                    ), "07 C7 04 $str 00 06 00"
                ),
                CodeFunction(
                    "I 2 of 5 Check Digit Verification", listOf(
                        Pair("*Disable", "07 C6 04 $str 00 31 00"),
                        Pair("USS Check Digit", "07 C6 04 $str 00 31 01"),
                        Pair("OPCC Check Digit", "07 C6 04 $str 00 31 02"),
                    ), "07 C7 04 $str 00 31 00"
                ),
                CodeFunction(
                    "Transmit I 2 of 5 Check Digit", listOf(
                        Pair("Transmit I 2 of 5 Check Digit (Enable)", "07 C6 04 $str 00 2C 01"),
                        Pair(
                            "*Do Not Transmit I 2 of 5 Check Digit (Disable)",
                            "07 C6 04 $str 00 2C 00"
                        ),
                    ), "07 C7 04 $str 00 2C 00"
                ),
                CodeFunction(
                    "Convert I 2 of 5 to EAN-13", listOf(
                        Pair("Convert I 2 of 5 to EAN-13 (Enable)", "07 C6 04 $str 00 52 01"),
                        Pair(
                            "*Do Not Convert I 2 of 5 to EAN-13 (Disable)",
                            "07 C6 04 $str 00 52 00"
                        ),
                    ), "07 C7 04 $str 00 52 00"
                ),
                CodeFunction(
                    "I 2 of 5 Security Level", listOf(
                        Pair("I 2 of 5 Security Level 0", "09 C6 04 $str 00 F8 04 61 00"),
                        Pair("*I 2 of 5 Security Level 1", "09 C6 04 $str 00 F8 04 61 01"),
                        Pair("I 2 of 5 Security Level 2", "09 C6 04 $str 00 F8 04 61 02"),
                        Pair("I 2 of 5 Security Level 3", "09 C6 04 $str 00 F8 04 61 03"),
                    ), "09 C7 04 $str 00 F8 04 61 01"
                ),
                CodeFunction(
                    "I 2 of 5 Reduced Quiet Zone", listOf(
                        Pair("Enable I 2 of 5 Reduced Quiet Zone", "09 C6 04 $str 00 F8 04 BA 01"),
                        Pair(
                            "*Disable I 2 of 5 Reduced Quiet Zone",
                            "09 C6 04 $str 00 F8 04 BA 00"
                        ),
                    ), "09 C7 04 $str 00 F8 04 BA 00"
                ),
            )
        ),
        CodeObj(
            "Discrete 2 of 5 (DTF)", listOf(
                CodeFunction(
                    "Enable/Disable Discrete 2 of 5", listOf(
                        Pair("Enable Discrete 2 of 5", "07 C6 04 $str 00 05 01"),
                        Pair("*Disable Discrete 2 of 5", "07 C6 04 $str 00 05 00"),
                    ), "07 C7 04 $str 00 05 00"
                )
            )
        ),
        CodeObj(
            "Codabar (NW - 7)", listOf(
                CodeFunction(
                    "Enable/Disable Codabar", listOf(
                        Pair("*Enable Codabar", "07 C6 04 $str 00 07 01"),
                        Pair("Disable Codabar", "07 C6 04 $str 00 07 00"),
                    ), "07 C7 04 $str 00 07 01"
                ),
                CodeFunction(
                    "Enable/Disable CLSI Editing", listOf(
                        Pair("Enable CLSI Editing", "07 C6 04 $str 00 36 01"),
                        Pair("*Disable CLSI Editing", "07 C6 04 $str 00 36 00"),
                    ), "07 C7 04 $str 00 36 00"
                ),
                CodeFunction(
                    "Enable/Disable NOTIS Editing", listOf(
                        Pair("Enable NOTIS Editing", "07 C6 04 $str 00 37 01"),
                        Pair("*Disable NOTIS Editing", "07 C6 04 $str 00 37 00"),
                    ), "07 C7 04 $str 00 37 00"
                ),
                CodeFunction(
                    "Codabar Upper or Lower Case Start/Stop Characters", listOf(
                        Pair("Lower Case", "08 C6 04 $str 00 F2 57 01"),
                        Pair("*Upper Case", "08 C6 04 $str 00 F2 57 00"),
                    ), "08 C7 04 $str 00 F2 57 00"
                ),
            )
        ),
        CodeObj(
            "MSI", listOf(
                CodeFunction(
                    "Enable/Disable MSI", listOf(
                        Pair("Enable MSI", "07 C6 04 $str 00 0B 01"),
                        Pair("*Disable MSI", "07 C6 04 $str 00 0B 00"),
                    ), "07 C7 04 $str 00 0B 00"
                ),
                CodeFunction(
                    "MSI Check Digits", listOf(
                        Pair("*One MSI Check Digit", "07 C6 04 $str 00 32 00"),
                        Pair("Two MSI Check Digits", "07 C6 04 $str 00 32 01"),
                    ), "07 C7 04 $str 00 32 00"
                ),
                CodeFunction(
                    "Transmit MSI Check Digit(s)", listOf(
                        Pair("Transmit MSI Check Digit(s) (Enable)", "07 C6 04 $str 00 46 01"),
                        Pair(
                            "*Do Not Transmit MSI Check Digit(s) (Disable)",
                            "07 C6 04 $str 00 46 00"
                        ),
                    ), "07 C7 04 $str 00 46 00"
                ),
                CodeFunction(
                    "MSI Check Digit Algorithm", listOf(
                        Pair("MOD 11/MOD 10", "07 C6 04 $str 00 33 00"),
                        Pair("*MOD 10/MOD 10", "07 C6 04 $str 00 33 01"),
                    ), "07 C7 04 $str 00 33 01"
                ),
                CodeFunction(
                    "MSI Reduced Quiet Zone", listOf(
                        Pair("*Disable MSI Reduced Quiet Zone", "09 C6 04 $str 00 F8 05 70 00"),
                        Pair("Enable MSI Reduced Quiet Zone", "09 C6 04 $str 00 F8 05 70 01"),
                    ), "09 C7 04 $str 00 F8 05 70 00"
                ),
            )
        ),
        CodeObj(
            "Chinese 2 of 5", listOf(
                CodeFunction(
                    "Enable/Disable Chinese 2 of 5", listOf(
                        Pair("Enable Chinese 2 of 5", "08 C6 04 $str 00 F0 98 01"),
                        Pair("*Disable Chinese 2 of 5", "08 C6 04 $str 00 F0 98 00"),
                    ), "08 C7 04 $str 00 F0 98 00"
                ),
            )
        ),
        CodeObj(
            "Matrix 2 of 5", listOf(
                CodeFunction(
                    "Enable/Disable Matrix 2 of 5", listOf(
                        Pair("Enable Matrix 2 of 5", "08 C6 04 $str 00 F0 98 01"),
                        Pair("*Disable Matrix 2 of 5", "08 C6 04 $str 00 F0 98 00"),
                    ), "08 C7 04 $str 00 F0 98 00"
                ),
                CodeFunction(
                    "Matrix 2 of 5 Check Digit", listOf(
                        Pair("Enable Matrix 2 of 5 Check Digit", "08 C6 04 $str 00 F1 6E 01"),
                        Pair("*Disable Matrix 2 of 5 Check Digit", "08 C6 04 $str 00 F1 6E 00"),
                    ), "08 C7 04 $str 00 F1 6E 00"
                ),
                CodeFunction(
                    "Transmit Matrix 2 of 5 Check Digit", listOf(
                        Pair("Transmit Matrix 2 of 5 Check Digit", "08 C6 04 $str 00 F1 6F 01"),
                        Pair(
                            "*Do Not Transmit Matrix 2 of 5 Check Digit",
                            "08 C6 04 $str 00 F1 6F 00"
                        ),
                    ), "08 C7 04 $str 00 F1 6F 00"
                ),
            )
        ),
        CodeObj(
            "Inverse 1D", listOf(
                CodeFunction(
                    "Inverse 1D", listOf(
                        Pair("*Regular", "08 C6 04 $str 00 F1 4A 00"),
                        Pair("Inverse Only", "08 C6 04 $str 00 F1 4A 01"),
                        Pair("Inverse Autodetect", "08 C6 04 $str 00 F1 4A 02"),
                    ), "08 C7 04 $str 00 F1 4A 00"
                ),
            )
        ),
        CodeObj(
            "GS1 DataBar", listOf(
                CodeFunction(
                    "GS1 DataBar-14", listOf(
                        Pair("*Enable GS1 DataBar-14", "08 C6 04 $str 00 F0 52 01"),
                        Pair("Disable GS1 DataBar-14", "08 C6 04 $str 00 F0 52 00"),
                    ), "08 C7 04 $str 00 F0 52 01"
                ),
                CodeFunction(
                    "GS1 DataBar Limited", listOf(
                        Pair("*Enable GS1 DataBar Limited", "08 C6 04 $str 00 F0 53 01"),
                        Pair("Disable GS1 DataBar Limited", "08 C6 04 $str 00 F0 53 00"),
                    ), "08 C7 04 $str 00 F0 53 01"
                ),
                CodeFunction(
                    "GS1 DataBar Expanded", listOf(
                        Pair("*Enable GS1 DataBar Expanded", "08 C6 04 $str 00 F0 54 01"),
                        Pair("Disable GS1 DataBar Expanded", "08 C6 04 $str 00 F0 54 00"),
                    ), "08 C7 04 $str 00 F0 54 01"
                ),
                CodeFunction(
                    "Convert GS1 DataBar to UPC/EAN/JAN", listOf(
                        Pair(
                            "Enable Convert GS1 DataBar to UPC/EAN/JAN",
                            "08 C6 04 $str 00 F0 8D 01"
                        ),
                        Pair(
                            "*Disable Convert GS1 DataBar to UPC/EAN/JAN",
                            "08 C6 04 $str 00 F0 8D 00"
                        ),
                    ), "08 C7 04 $str 00 F0 8D 00"
                ),
                CodeFunction(
                    "GS1 DataBar Security Level", listOf(
                        Pair("GS1 DataBar Security Level 0", "09 C6 04 $str 00 F8 06 AA 00"),
                        Pair("*GS1 DataBar Security Level 1", "09 C6 04 $str 00 F8 06 AA 01"),
                        Pair("GS1 DataBar Security Level 2", "09 C6 04 $str 00 F8 06 AA 02"),
                        Pair("GS1 DataBar Security Level 3", "09 C6 04 $str 00 F8 06 AA 03"),
                    ), "09 C7 04 $str 00 F8 06 AA 01"
                ),
                CodeFunction(
                    "GS1 DataBar Limited Margin Check", listOf(
                        Pair(
                            "GS1 DataBar Limited Margin Check Level 1",
                            "08 C6 04 $str 00 F1 D8 00"
                        ),
                        Pair(
                            "GS1 DataBar Limited Margin Check Level 2",
                            "08 C6 04 $str 00 F1 D8 01"
                        ),
                        Pair(
                            "*GS1 DataBar Limited Margin Check Level 3",
                            "08 C6 04 $str 00 F1 D8 02"
                        ),
                        Pair(
                            "GS1 DataBar Limited Margin Check Level 4",
                            "08 C6 04 $str 00 F1 D8 03"
                        ),
                    ), "08 C7 04 $str 00 F1 D8 02"
                ),
                CodeFunction(
                    "GS1 DataBar Expanded Security Level", listOf(
                        Pair(
                            "GS1 DataBar Expanded Security Level 0",
                            "09 C6 04 $str 00 F8 06 AB 00"
                        ),
                        Pair(
                            "*GS1 DataBar Expanded Security Level 1",
                            "09 C6 04 $str 00 F8 06 AB 01"
                        ),
                        Pair(
                            "GS1 DataBar Expanded Security Level 2",
                            "09 C6 04 $str 00 F8 06 AB 02"
                        ),
                        Pair(
                            "GS1 DataBar Expanded Security Level 3",
                            "09 C6 04 $str 00 F8 06 AB 03"
                        ),
                    ), "09 C7 04 $str 00 F8 06 AB 01"
                ),
            )
        ),
        CodeObj(
            "Symbology-Specific Security Features", listOf(
                CodeFunction(
                    "Redundancy Level", listOf(
                        Pair("*Redundancy Level 1", "07 C6 04 $str 00 4E 01"),
                        Pair("Redundancy Level 2", "07 C6 04 $str 00 4E 02"),
                        Pair("Redundancy Level 3", "07 C6 04 $str 00 4E 03"),
                        Pair("Redundancy Level 4", "07 C6 04 $str 00 4E 04"),
                    ), "07 C7 04 $str 00 4E 01"
                ),
                CodeFunction(
                    "Security Level", listOf(
                        Pair("Security Level 0", "07 C6 04 $str 00 4D 00"),
                        Pair("*Security Level 1", "07 C6 04 $str 00 4D 01"),
                        Pair("Security Level 2", "07 C6 04 $str 00 4D 02"),
                        Pair("Security Level 3", "07 C6 04 $str 00 4D 03"),
                    ), "07 C7 04 $str 00 4D 01"
                ),
                CodeFunction(
                    "1D Quiet Zone Level", listOf(
                        Pair("1D Quiet Zone Level 0", "09 C6 04 $str 00 F8 05 08 00"),
                        Pair("*1D Quiet Zone Level 1", "09 C6 04 $str 00 F8 05 08 01"),
                        Pair("1D Quiet Zone Level 2", "09 C6 04 $str 00 F8 05 08 02"),
                        Pair("1D Quiet Zone Level 3", "09 C6 04 $str 00 F8 05 08 03"),
                    ), "09 C7 04 $str 00 F8 05 08 01"
                ),
                CodeFunction(
                    "Intercharacter Gap Size", listOf(
                        Pair("*Normal Intercharacter Gaps", "08 C6 04 $str 00 F0 7D 06"),
                        Pair("Large Intercharacter Gaps", "08 C6 04 $str 00 F0 7D 0A"),
                    ), "08 C7 04 $str 00 F0 7D 06"
                ),
            )
        ),
        CodeObj(
            "Composite", listOf(
                CodeFunction(
                    "Composite CC-C",
                    listOf(
                        Pair("Enable CC-C", "08 C6 04 $str 00 F0 55 01"),
                        Pair("*Disable CC-C", "08 C6 04 $str 00 F0 55 00"),
                    ), "08 C7 04 $str 00 F0 55 00"
                ),
                CodeFunction(
                    "Composite CC-A/B",
                    listOf(
                        Pair("Enable CC-A/B", "08 C6 04 $str 00 F0 56 01"),
                        Pair("*Disable CC-A/B", "08 C6 04 $str 00 F0 56 00"),
                    ), "08 C7 04 $str 00 F0 56 00"
                ),
                CodeFunction(
                    "Composite TLC-39",
                    listOf(
                        Pair("Enable TLC39", "08 C6 04 $str 00 F0 73 01"),
                        Pair("*Disable TLC39", "08 C6 04 $str 00 F0 73 00"),
                    ), "08 C7 04 $str 00 F0 73 00"
                ),
                CodeFunction(
                    "Composite Inverse",
                    listOf(
                        Pair("*Regular Only", "09 C6 04 $str 00 F8 04 59 01"),
                        Pair("Inverse Only", "09 C6 04 $str 00 F8 04 59 00"),
                    ), "09 C7 04 $str 00 F8 04 59 01"
                ),
                CodeFunction(
                    "UPC Composite Mode",
                    listOf(
                        Pair("*UPC Never Linked", "08 C6 04 $str 00 F0 58 00"),
                        Pair("UPC Always Linked", "08 C6 04 $str 00 F0 58 01"),
                        Pair("Autodiscriminate UPC Composites", "08 C6 04 $str 00 F0 58 02"),
                    ), "08 C7 04 $str 00 F0 58 00"
                ),
                CodeFunction(
                    "Composite Beep Mode",
                    listOf(
                        Pair("Single Beep After Both are Decoded", "08 C6 04 $str 00 F0 8E 00"),
                        Pair("*Beep as Each Code Type is Decoded", "08 C6 04 $str 00 F0 8E 01"),
                        Pair("Double Beep After Both are Decoded", "08 C6 04 $str 00 F0 8E 02"),
                    ), "08 C7 04 $str 00 F0 8E 01"
                ),
                CodeFunction(
                    "GS1-128 Emulation Mode for UCC/EAN Composite Codes",
                    listOf(
                        Pair(
                            "Enable GS1-128 Emulation Mode for UCC/EAN Composite Codes",
                            "08 C6 04 $str 00 F0 AB 01"
                        ),
                        Pair(
                            "*Disable GS1-128 Emulation Mode for UCC/EAN Composite Codes",
                            "08 C6 04 $str 00 F0 AB 00"
                        ),
                    ), "08 C7 04 $str 00 F0 AB 00"
                ),
            )
        ),
        CodeObj(
            "2D Symbologies", listOf(
                CodeFunction(
                    "PDF417",
                    listOf(
                        Pair("*Enable PDF417", "07 C6 04 $str 00 0F 01"),
                        Pair("Disable PDF417", "07 C6 04 $str 00 0F 00"),
                    ), "07 C7 04 $str 00 0F 01"
                ),
                CodeFunction(
                    "MicroPDF417",
                    listOf(
                        Pair("Enable MicroPDF417", "07 C6 04 $str 00 E3 01"),
                        Pair("*Disable MicroPDF417", "07 C6 04 $str 00 E3 00"),
                    ), "07 C7 04 $str 00 E3 00"
                ),
                CodeFunction(
                    "Code 128 Emulation",
                    listOf(
                        Pair("Enable Code 128 Emulation", "07 C6 04 $str 00 7B 01"),
                        Pair("*Disable Code 128 Emulation", "07 C6 04 $str 00 7B 00"),
                    ), "07 C7 04 $str 00 7B 00"
                ),
                CodeFunction(
                    "Data Matrix",
                    listOf(
                        Pair("*Enable Data Matrix", "08 C6 04 $str 00 F0 24 01"),
                        Pair("Disable Data Matrix", "08 C6 04 $str 00 F0 24 00"),
                    ), "08 C7 04 $str 00 F0 24 01"
                ),
                CodeFunction(
                    "Data Matrix Inverse",
                    listOf(
                        Pair("Regular Only", "08 C6 04 $str 00 F1 4C 00"),
                        Pair("Inverse Only", "08 C6 04 $str 00 F1 4C 01"),
                        Pair("*Inverse Autodetect", "08 C6 04 $str 00 F1 4C 02"),
                    ), "08 C7 04 $str 00 F1 4C 02"
                ),
                CodeFunction(
                    "Data Matrix Inverse",
                    listOf(
                        Pair("Regular Only", "08 C6 04 $str 00 F1 4C 00"),
                        Pair("Inverse Only", "08 C6 04 $str 00 F1 4C 01"),
                        Pair("*Inverse Autodetect", "08 C6 04 $str 00 F1 4C 02"),
                    ), "08 C7 04 $str 00 F1 4C 02"
                ),
                CodeFunction(
                    "Decode Data Matrix Mirror Images",
                    listOf(
                        Pair("Never", "08 C6 04 $str 00 F1 19 00"),
                        Pair("Always", "08 C6 04 $str 00 F1 19 01"),
                        Pair("*Auto", "08 C6 04 $str 00 F1 19 02"),
                    ), "08 C7 04 $str 00 F1 19 02"
                ),
            )
        ),
        CodeObj(
            "Maxicode", listOf(
                CodeFunction(
                    "Enable/Disable Maxicode",
                    listOf(
                        Pair("Enable Maxicode", "08 C6 04 $str 00 F0 26 01"),
                        Pair("*Disable Maxicode", "08 C6 04 $str 00 F0 26 00"),
                    ), "08 C7 04 $str 00 F0 26 00"
                )
            )
        ),
        CodeObj(
            "QR Code", listOf(
                CodeFunction(
                    "Enable/Disable QR Code",
                    listOf(
                        Pair("*Enable QR Code", "08 C6 04 $str 00 F0 25 01"),
                        Pair("Disable QR Code", "08 C6 04 $str 00 F0 25 00"),
                    ), "08 C7 04 $str 00 F0 25 01"
                )
            )
        ),
        CodeObj(
            "MicroQR", listOf(
                CodeFunction(
                    "Enable/Disable MicroQR",
                    listOf(
                        Pair("*Enable MicroQR", "08 C6 04 $str 00 F1 3D 01"),
                        Pair("Disable MicroQR", "08 C6 04 $str 00 F1 3D 00"),
                    ), "08 C7 04 $str 00 F1 3D 01"
                )
            )
        ),
        CodeObj(
            "Aztec", listOf(
                CodeFunction(
                    "Enable/Disable Aztec",
                    listOf(
                        Pair("*Enable Aztec", "08 C6 04 $str 00 F1 3E 01"),
                        Pair("Disable Aztec", "08 C6 04 $str 00 F1 3E 00"),
                    ), "08 C7 04 $str 00 F1 3E 01"
                ),
                CodeFunction(
                    "Aztec Inverse",
                    listOf(
                        Pair("Regular Only", "08 C6 04 $str 00 F1 4D 00"),
                        Pair("Inverse Only", "08 C6 04 $str 00 F1 4D 01"),
                        Pair("*Inverse Autodetect", "08 C6 04 $str 00 F1 4D 02"),
                    ), "08 C7 04 $str 00 F1 4D 02"
                ),

                )
        ),
        CodeObj(
            "Han Xin", listOf(
                CodeFunction(
                    "Enable/Disable Han Xin",
                    listOf(
                        Pair("Enable Han Xin", "09 C6 04 $str 00 F8 04 8F 01"),
                        Pair("*Disable Han Xin", "09 C6 04 $str 00 F8 04 8F 00"),
                    ), "09 C7 04 $str 00 F8 04 8F 00"
                ),
                CodeFunction(
                    "Han Xin Inverse",
                    listOf(
                        Pair("*Regular Only", "09 C6 04 $str 00 F8 04 90 00"),
                        Pair("Inverse Only", "09 C6 04 $str 00 F8 04 90 01"),
                        Pair("Inverse Autodetect", "09 C6 04 $str 00 F8 04 90 02"),
                    ), "09 C7 04 $str 00 F8 04 90 00"
                ),
            )
        ),
        CodeObj(
            "Postal Codes", listOf(
                CodeFunction(
                    "US Postnet",
                    listOf(
                        Pair("Enable US Postnet", "07 C6 04 $str 00 59 01"),
                        Pair("*Disable US Postnet", "07 C6 04 $str 00 59 00"),
                    ), "07 C7 04 $str 00 59 00"
                ),
                CodeFunction(
                    "US Planet",
                    listOf(
                        Pair("Enable US Postnet", "07 C6 04 $str 00 5A 01"),
                        Pair("*Disable US Postnet", "07 C6 04 $str 00 5A 00"),
                    ), "07 C7 04 $str 00 5A 00"
                ),
                CodeFunction(
                    "Transmit US Postal Check Digit",
                    listOf(
                        Pair("*Transmit US Postal Check Digit", "07 C6 04 $str 00 5F 01"),
                        Pair("Do Not Transmit US Postal Check Digit", "07 C6 04 $str 00 5F 00"),
                    ), "07 C7 04 $str 00 5F 01"
                ),
                CodeFunction(
                    "UK Postal",
                    listOf(
                        Pair("Enable UK Postal", "07 C6 04 $str 00 5B 01"),
                        Pair("*Disable UK Postal", "07 C6 04 $str 00 5B 00"),
                    ), "07 C7 04 $str 00 5B 00"
                ),
                CodeFunction(
                    "Transmit UK Postal Check Digit",
                    listOf(
                        Pair("*Transmit UK Postal Check Digit", "07 C6 04 $str 00 60 01"),
                        Pair("Do Not Transmit UK Postal Check Digit", "07 C6 04 $str 00 60 00"),
                    ), "07 C7 04 $str 00 60 01"
                ),
                CodeFunction(
                    "Japan Postal",
                    listOf(
                        Pair("Enable Japan Postal", "08 C6 04 $str 00 F0 22 01"),
                        Pair("*Disable Japan Postal", "08 C6 04 $str 00 F0 22 00"),
                    ), "08 C7 04 $str 00 F0 22 00"
                ),
                CodeFunction(
                    "Australia Post",
                    listOf(
                        Pair("Enable Australia Post", "08 C6 04 $str 00 F0 23 01"),
                        Pair("*Disable Australia Post", "08 C6 04 $str 00 F0 23 00"),
                    ), "08 C7 04 $str 00 F0 23 00"
                ),
                CodeFunction(
                    "Australia Post Format",
                    listOf(
                        Pair("*Autodiscriminate", "08 C6 04 $str 00 F1 CE 00"),
                        Pair("Raw Format", "08 C6 04 $str 00 F1 CE 01"),
                        Pair("Alphanumeric Encoding", "08 C6 04 $str 00 F1 CE 02"),
                        Pair("Numeric Encoding", "08 C6 04 $str 00 F1 CE 03"),
                    ), "08 C7 04 $str 00 F1 CE 00"
                ),
                CodeFunction(
                    "Netherlands KIX Code",
                    listOf(
                        Pair("Enable Netherlands KIX Code", "08 C6 04 $str 00 F0 46 01"),
                        Pair("*Disable Netherlands KIX Code", "08 C6 04 $str 00 F0 46 00"),
                    ), "08 C7 04 $str 00 F0 46 00"
                ),
                CodeFunction(
                    "USPS 4CB/One Code/Intelligent Mail",
                    listOf(
                        Pair(
                            "Enable USPS 4CB/One Code/Intelligent Mail",
                            "08 C6 04 $str 00 F1 50 01"
                        ),
                        Pair(
                            "*Disable USPS 4CB/One Code/Intelligent Mail",
                            "08 C6 04 $str 00 F1 50 00"
                        ),
                    ), "08 C7 04 $str 00 F1 50 00"
                ),
                CodeFunction(
                    "UPU FICS Postal",
                    listOf(
                        Pair("Enable UPU FICS Postal", "08 C6 04 $str 00 F1 63 01"),
                        Pair("*Disable UPU FICS Postal", "08 C6 04 $str 00 F1 63 00"),
                    ), "08 C7 04 $str 00 F1 63 00"
                ),
            )
        ),
    )
}
