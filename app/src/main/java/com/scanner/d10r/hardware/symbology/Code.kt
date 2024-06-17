package com.scanner.d10r.hardware.symbology

data class CodeObj(
    val codeName: String,
    val functions: List<CodeFunction>,
)

//HR22P 扫描手柄
fun getAllConfig(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "Code 128",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "128DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "128ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("128ENA1", "128ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("128MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "128MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("128MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "128MAX")
                )

            )
        ),
        CodeObj(
            "EAN-8",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "EA8DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "EA8ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("EA8ENA1", "EA8ENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "EA8CHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("EA8CHK1", "EA8CHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "EA8AD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("EA8AD20", "EA8AD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "EA8AD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("EA8AD50", "EA8AD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "EA8REQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("EA8REQ0", "EA8REQ1")
                ),
                CodeFunction(
                    "convert to EAN-13",
                    "EA8EXP*",
                    mutableListOf("Don't convert", "Convert to EAN13"),
                    mutableListOf("EA8EXP0", "EA8EXP1")
                )
            )
        ),
        CodeObj(
            "EAN-13",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "E13DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "E13ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("E13ENA1", "E13ENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "E13CHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("E13CHK1", "E13CHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "E13AD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("E13AD20", "E13AD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "E13AD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("E13AD50", "E13AD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "E13REQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("E13REQ0", "E13REQ1")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 290",
                    "E13290*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E132900", "E132901")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 378/379",
                    "E13378*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E133780", "E133781")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 414/419",
                    "E13414*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E134140", "E134141")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 434/439",
                    "E13434*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E134340", "E134341")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 977",
                    "E13977*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E139770", "E139771")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 978",
                    "E13978*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E139780", "E139781")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 979",
                    "E13979*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E139790", "E139791")
                )
            )
        ),
        CodeObj(
            "UPC-E",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "UPEDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "UPEENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("UPEENA1", "UPEENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPECHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("UPECHK1", "UPECHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPEAD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("UPEAD20", "UPEAD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPEAD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("UPEAD50", "UPEAD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "UPEREQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("UPEREQ0", "UPEREQ1")
                ),
                CodeFunction(
                    "Transmits leading characters",
                    "UPEPRE*",
                    mutableListOf(
                        "Do not transmit leading characters (do not transmit national code and system characters)",
                        "Transport system character",
                        "Transmit country code and system characters"
                    ),
                    mutableListOf("UPEPRE0", "UPEPRE1", "UPEPRE2")
                ),
                CodeFunction(
                    "Converted to UPC-A",
                    "UPEEXP*",
                    mutableListOf("Don't convert to UPC-A", "Converted to UPC-A"),
                    mutableListOf("UPEEXP0", "UPEEXP1")
                ),
                CodeFunction(
                    "Reading UPC-E0",
                    "UPEEN0*",
                    mutableListOf("Off", "On"),
                    mutableListOf("UPEEN00", "UPEEN01")
                ),
                CodeFunction(
                    "Reading UPC-E1",
                    "UPEEN1*",
                    mutableListOf("Off", "On"),
                    mutableListOf("UPEEN10", "UPEEN11")
                )
            )
        ),
        CodeObj(
            "UPC-A",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "UPADEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "UPAENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("UPAENA1", "UPAENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPACHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("UPACHK1", "UPACHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPAAD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("UPAAD20", "UPAAD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPAAD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("UPAAD50", "UPAAD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "UPAREQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("UPAREQ0", "UPAREQ1")
                ),
                CodeFunction(
                    "Transmits leading characters",
                    "UPAPRE*",
                    mutableListOf(
                        "Do not transmit leading characters (do not transmit national code and system characters)",
                        "Transport system character",
                        "Transmit country code and system characters"
                    ),
                    mutableListOf("UPAPRE0", "UPAPRE1", "UPAPRE2")
                )
            )
        ),
        CodeObj(
            "Coupon",
            listOf(
                CodeFunction(
                    "UPC-A/EAN-13 comes with Coupon extension code",
                    "CPNENA*",
                    mutableListOf("Off", "Allow Connections", "Must Connections"),
                    mutableListOf("CPNENA0", "CPNENA1", "CPNENA2")
                ),
                CodeFunction(
                    "Output Coupon GS1 DataBar",
                    "CPNGS1*",
                    mutableListOf("Off", "On"),
                    mutableListOf("CPNGS10", "CPNGS11")
                )
            )
        ),
        CodeObj(
            "Interleaved 2 of 5",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "I25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "I25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("I25ENA1", "I25ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "I25CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("I25CHK0", "I25CHK1", "I25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("I25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "I25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("I25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "I25MAX")
                )
            )
        ),
        CodeObj(
            "ITF-14",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "I14DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "I14ENA*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("I14ENA0", "I14ENA1", "I14ENA2")
                )
            )
        ),
        CodeObj(
            "ITF-6",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "IT6DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "IT6ENA*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("IT6ENA0", "IT6ENA1", "IT6ENA2")
                )
            )
        ),
        CodeObj(
            "Matrix 2 of 5",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "M25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "M25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("M25ENA1", "M25ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "M25CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("M25CHK0", "M25CHK1", "M25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("M25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "M25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("M25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "M25MAX")
                )
            )
        ),
        CodeObj(
            "Code 39",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "C39DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "C39ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C39ENA1", "C39ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "C39CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C39CHK0", "C39CHK1", "C39CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("C39MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "C39MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("C39MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "C39MAX")
                ),
                CodeFunction(
                    "Start/Stop Characters",
                    "C39TSC*",
                    mutableListOf("Transmit", "Don’t Transmit"),
                    mutableListOf("C39TSC1", "C39TSC0")
                ),
                CodeFunction(
                    "Full ASCII",
                    "C39ASC*",
                    mutableListOf("Disable Code 39 Full ASCII", "Enable Code 39 Full ASCII"),
                    mutableListOf("C39ASC0", "C39ASC1")
                ),
                CodeFunction(
                    "Code 32 Pharmaceutical (PARAF)",
                    "C39E32*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C39E320", "C39E321")
                ),
                CodeFunction(
                    "Code 32 Prefix",
                    "C39S32*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C39S320", "C39S321")
                ),
                CodeFunction(
                    "Code 32 Start/Stop Characters",
                    "C39T32*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C39T320", "C39T321")
                ),
                CodeFunction(
                    "Code 32 Check Character",
                    "C39C32*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C39C320", "C39C321")
                )
            )
        ),
        CodeObj(
            "Codabar",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "CBADEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "CBAENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("CBAENA1", "CBAENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "CBACHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("CBACHK0", "CBACHK1", "CBACHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("CBAMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "CBAMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("CBAMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "CBAMAX")
                ),
                CodeFunction(
                    "Start/Stop Characters",
                    "CBATSC*",
                    mutableListOf("Transmit", "Don’t Transmit"),
                    mutableListOf("CBATSC1", "CBATSC0")
                ),
                CodeFunction(
                    "Format Of Start and Stop Characters",
                    "CBASCF*",
                    mutableListOf("ABCD/ABCD", "ABCD/TN*E", "abcd/abcd", "abcd/tn*e"),
                    mutableListOf("CBASCF0", "CBASCF1", "CBASCF2", "CBASCF3")
                )
            )
        ),
        CodeObj(
            "Code 93",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "C93DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "C93ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C93ENA1", "C93ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "C93CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C93CHK0", "C93CHK1", "C93CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("C93MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "C93MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("C93MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "C93MAX")
                )
            )
        ),
        CodeObj(
            "GS1-128(UCC/EAN-128)",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "GS1DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "GS1ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("GS1ENA1", "GS1ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("GS1MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "GS1MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("GS1MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "GS1MAX")
                )
            )
        ),
        CodeObj(
            "GS1 DataBar(RSS)",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "RSSDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "RSSENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("RSSENA1", "RSSENA0")
                ),
                CodeFunction(
                    "AI(01) Character",
                    "RSSTAI*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("RSSTAI0", "RSSTAI1")
                )
            )
        ),
        CodeObj(
            "GS1 Composite",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "CPTDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "CPTENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("CPTENA1", "CPTENA0")
                ),
                CodeFunction(
                    "UPC/EAN Version",
                    "CPTUPC*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("CPTUPC0", "CPTUPC1")
                )
            )
        ),
        CodeObj(
            "Code 11",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "C11DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "C11ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C11ENA1", "C11ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("C11MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "C11MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("C11MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "C11MAX")
                ),
                CodeFunction(
                    "Check",
                    "C11CHK*",
                    mutableListOf(
                        "Disable",
                        "One Check Digit,MOD11",
                        "Two Check Digits,MOD11/MOD11",
                        "Two Check Digits,MOD11/MOD9",
                        "MOD11 Single Check Digits (Len<=10),MOD11/MOD11 Double Check Digits(Len>10)",
                        "MOD11 Single Check Digits (Len<=10),MOD11/MOD9 Double Check Digits(Len>10)"
                    ),
                    mutableListOf("C11CHK0", "C11CHK1", "C11CHK2", "C11CHK3", "C11CHK4", "C11CHK5")
                ),
                CodeFunction(
                    "Check Character",
                    "C11TCK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C11TCK0", "C11TCK1")
                )
            )
        ),
        CodeObj(
            "ISBN",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "ISBDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "ISBENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("ISBENA1", "ISBENA0")
                ),
                CodeFunction(
                    "ISBN format",
                    "ISBT10*",
                    mutableListOf("ISBN-13", "ISBN-10"),
                    mutableListOf("ISBT100", "ISBT101")
                )
            )
        ),
        CodeObj(
            "Industrial 25",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "L25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "L25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("L25ENA1", "L25ENA0")
                ),
                CodeFunction(
                    "Check",
                    "L25CHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("L25CHK0", "L25CHK1", "L25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("L25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "L25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("L25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "L25MAX")
                )
            )
        ),
        CodeObj(
            "Standard 25",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "S25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "S25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("S25ENA1", "S25ENA0")
                ),
                CodeFunction(
                    "Check",
                    "S25CHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("S25CHK0", "S25CHK1", "S25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("S25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "S25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("S25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "S25MAX")
                )
            )
        ),
        CodeObj(
            "Plessey",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "PLYDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "PLYENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("PLYENA1", "PLYENA0")
                ),
                CodeFunction(
                    "Check",
                    "PLYCHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("PLYCHK0", "PLYCHK1", "PLYCHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("PLYMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "PLYMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("PLYMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "PLYMAX")
                )
            )
        ),
        CodeObj(
            "MSI Plessey",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "MSIDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "MSIENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("MSIENA1", "MSIENA0")
                ),
                CodeFunction(
                    "Check",
                    "MSICHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("MSICHK0", "MSICHK1", "MSICHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("MSIMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "MSIMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("MSIMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "MSIMAX")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "MSITCK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("MSITCK0", "MSITCK1")
                ),
            )
        ),
        CodeObj(
            "ISSN",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "ISSDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "ISSENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("ISSENA1", "ISSENA0")
                )
            )
        ),
        CodeObj(
            "China Post 25",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "CHPDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "CHPENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("CHPENA1", "CHPENA0")
                ),
                CodeFunction(
                    "Check",
                    "CHPCHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("CHPCHK0", "CHPCHK1", "CHPCHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("CHPMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "CHPMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("CHPMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "CHPMAX")
                )
            )
        ),
        CodeObj(
            "AIM 128",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "AIMDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "AIMENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("AIMENA1", "AIMENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("AIMMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "AIMMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("AIMMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "AIMMAX")
                )
            )
        ),
        CodeObj(
            "ISBT 128",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "IBTDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "IBTENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("IBTENA1", "IBTENA0")
                )
            )
        ),
        CodeObj(
            "PDF417",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "PDFDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "PDFENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("PDFENA1", "PDFENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("PDFMIN*", 1, 5000, 1, "Minimum Length 0f Code Reading", "PDFMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("PDFMAX*", 1, 5000, 1, "Maximum Length 0f Code Reading", "PDFMAX")
                ),
                CodeFunction(
                    "Read multi-code",
                    "PDFDOU*",
                    mutableListOf(
                        "Read Single Code",
                        "Read Double Code",
                        "Read Single/Double Code"
                    ),
                    mutableListOf("PDFDOU0", "PDFDOU1", "PDFDOU2")
                ),
                CodeFunction(
                    "PDF417 Antiphase",
                    "PDFINV*",
                    mutableListOf(
                        "Only recognize positive phase barcodes",
                        "Only recognize reverse barcodes",
                        "Both positive and negative barcodes are recognized"
                    ),
                    mutableListOf("PDFINV0", "PDFINV1", "PDFINV2")
                ),
                CodeFunction(
                    "ECI Output",
                    "PDFECI*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("PDFECI0", "PDFECI1")
                ),
                CodeFunction(
                    "Character Encoding Format",
                    "PDFENC*",
                    mutableListOf("Default", "UTF-8"),
                    mutableListOf("PDFENC0", "PDFENC1")
                )
            )
        ),
        CodeObj(
            "QR",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "QRCDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "QRCENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("QRCENA1", "QRCENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("QRCMIN*", 1, 8000, 1, "Minimum Length 0f Code Reading", "QRCMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("QRCMAX*", 1, 8000, 1, "Maximum Length 0f Code Reading", "QRCMAX")
                ),
                CodeFunction(
                    "Read multi-code",
                    "QRCDOU*",
                    mutableListOf(
                        "Read Single Code",
                        "Read Double Code",
                        "Read Single/Double Code"
                    ),
                    mutableListOf("QRCDOU0", "QRCDOU1", "QRCDOU2")
                ),
                CodeFunction(
                    "QR Antiphase",
                    "QRCINV*",
                    mutableListOf(
                        "Only recognize positive phase barcodes",
                        "Only recognize reverse barcodes",
                        "Both positive and negative barcodes are recognized"
                    ),
                    mutableListOf("QRCINV0", "QRCINV1", "QRCINV2")
                ),
                CodeFunction(
                    "ECI Output",
                    "QRCECI*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("QRCECI0", "QRCECI1")
                ),
                CodeFunction(
                    "Character Encoding Format",
                    "QRCENC*",
                    mutableListOf("Default", "UTF-8"),
                    mutableListOf("QRCENC0", "QRCENC1")
                )
            )
        ),
        CodeObj(
            "Data Matrix",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "DMCDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "DMCENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("DMCENA1", "DMCENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("DMCMIN*", 1, 5000, 1, "Minimum Length 0f Code Reading", "DMCMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("DMCMAX*", 1, 5000, 1, "Maximum Length 0f Code Reading", "DMCMAX")
                ),
                CodeFunction(
                    "Read multi-code",
                    "DMCDOU*",
                    mutableListOf(
                        "Read Single Code",
                        "Read Double Code",
                        "Read Single/Double Code"
                    ),
                    mutableListOf("DMCDOU0", "DMCDOU1", "DMCDOU2")
                ),
                CodeFunction(
                    "Data Matrix Antiphase",
                    "DMCINV*",
                    mutableListOf(
                        "Only recognize positive phase barcodes",
                        "Only recognize reverse barcodes",
                        "Both positive and negative barcodes are recognized"
                    ),
                    mutableListOf("DMCINV0", "DMCINV1", "DMCINV2")
                ),
                CodeFunction(
                    "Matrix Code",
                    "DMCREC*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("DMCREC1", "DMCREC0")
                ),
                CodeFunction(
                    "ECI Output",
                    "DMCECI*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("DMCECI0", "DMCECI1")
                ),
                CodeFunction(
                    "Character Encoding Format",
                    "DMCENC*",
                    mutableListOf("Default", "UTF-8"),
                    mutableListOf("DMCENC0", "DMCENC1")
                )
            )
        ),
        CodeObj(
            "Micro PDF417",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "MPDDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "MPDENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("MPDENA1", "MPDENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("MPDMIN*", 1, 5000, 1, "Minimum Length 0f Code Reading", "MPDMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("MPDMAX*", 1, 5000, 1, "Maximum Length 0f Code Reading", "MPDMAX")
                )
            )
        ),
        CodeObj(
            "Micro QR",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "MQRDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "MQRENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("MQRENA1", "MQRENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("MQRMIN*", 1, 5000, 1, "Minimum Length 0f Code Reading", "MQRMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("MQRMAX*", 1, 5000, 1, "Maximum Length 0f Code Reading", "MQRMAX")
                )
            )
        )
    )
}

//EM3100 扫描模块
fun getAllEM3100Config(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "Code 128",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "128DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "128ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("128ENA1", "128ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("128MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "128MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("128MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "128MAX")
                )

            )
        ),
        CodeObj(
            "EAN-8",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "EA8DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "EA8ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("EA8ENA1", "EA8ENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "EA8CHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("EA8CHK1", "EA8CHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "EA8AD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("EA8AD20", "EA8AD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "EA8AD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("EA8AD50", "EA8AD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "EA8REQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("EA8REQ0", "EA8REQ1")
                ),
                CodeFunction(
                    "convert to EAN-13",
                    "EA8EXP*",
                    mutableListOf("Don't convert", "Convert to EAN13"),
                    mutableListOf("EA8EXP0", "EA8EXP1")
                )
            )
        ),
        CodeObj(
            "EAN-13",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "E13DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "E13ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("E13ENA1", "E13ENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "E13CHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("E13CHK1", "E13CHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "E13AD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("E13AD20", "E13AD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "E13AD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("E13AD50", "E13AD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "E13REQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("E13REQ0", "E13REQ1")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 290",
                    "E13290*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E132900", "E132901")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 378/379",
                    "E13378*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E133780", "E133781")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 414/419",
                    "E13414*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E134140", "E134141")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 434/439",
                    "E13434*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E134340", "E134341")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 977",
                    "E13977*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E139770", "E139771")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 978",
                    "E13978*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E139780", "E139781")
                ),
                CodeFunction(
                    "Ean-13 must have an extension code starting with 979",
                    "E13979*",
                    mutableListOf("Off", "On"),
                    mutableListOf("E139790", "E139791")
                )
            )
        ),
        CodeObj(
            "UPC-E",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "UPEDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "UPEENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("UPEENA1", "UPEENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPECHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("UPECHK1", "UPECHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPEAD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("UPEAD20", "UPEAD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPEAD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("UPEAD50", "UPEAD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "UPEREQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("UPEREQ0", "UPEREQ1")
                ),
                CodeFunction(
                    "Transmits leading characters",
                    "UPEPRE*",
                    mutableListOf(
                        "Do not transmit leading characters (do not transmit national code and system characters)",
                        "Transport system character",
                        "Transmit country code and system characters"
                    ),
                    mutableListOf("UPEPRE0", "UPEPRE1", "UPEPRE2")
                ),
                CodeFunction(
                    "Converted to UPC-A",
                    "UPEEXP*",
                    mutableListOf("Don't convert to UPC-A", "Converted to UPC-A"),
                    mutableListOf("UPEEXP0", "UPEEXP1")
                )
            )
        ),
        CodeObj(
            "UPC-A",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "UPADEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "UPAENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("UPAENA1", "UPAENA0")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPACHK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("UPACHK1", "UPACHK2")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPAAD2*",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("UPAAD20", "UPAAD21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPAAD5*",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("UPAAD50", "UPAAD51")
                ),
                CodeFunction(
                    "Addenda Required",
                    "UPAREQ*",
                    mutableListOf("Not Required", "Required"),
                    mutableListOf("UPAREQ0", "UPAREQ1")
                ),
                CodeFunction(
                    "Transmits leading characters",
                    "UPAPRE*",
                    mutableListOf(
                        "Do not transmit leading characters (do not transmit national code and system characters)",
                        "Transport system character",
                        "Transmit country code and system characters"
                    ),
                    mutableListOf("UPAPRE0", "UPAPRE1", "UPAPRE2")
                )
            )
        ),
        CodeObj(
            "Interleaved 2 of 5",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "I25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "I25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("I25ENA1", "I25ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "I25CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("I25CHK0", "I25CHK1", "I25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("I25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "I25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("I25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "I25MAX")
                )
            )
        ),
        CodeObj(
            "ITF-14",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "I14DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "I14ENA*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("I14ENA0", "I14ENA1", "I14ENA2")
                )
            )
        ),
        CodeObj(
            "ITF-6",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "IT6DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "IT6ENA*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("IT6ENA0", "IT6ENA1", "IT6ENA2")
                )
            )
        ),
        CodeObj(
            "Matrix 2 of 5",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "M25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "M25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("M25ENA1", "M25ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "M25CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("M25CHK0", "M25CHK1", "M25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("M25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "M25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("M25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "M25MAX")
                )
            )
        ),
        CodeObj(
            "Code 39",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "C39DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "C39ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C39ENA1", "C39ENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "C39CHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C39CHK0", "C39CHK1", "C39CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("C39MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "C39MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("C39MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "C39MAX")
                ),
                CodeFunction(
                    "Start/Stop Characters",
                    "C39TSC*",
                    mutableListOf("Transmit", "Don’t Transmit"),
                    mutableListOf("C39TSC1", "C39TSC0")
                ),
                CodeFunction(
                    "Full ASCII",
                    "C39ASC*",
                    mutableListOf("Disable Code 39 Full ASCII", "Enable Code 39 Full ASCII"),
                    mutableListOf("C39ASC0", "C39ASC1")
                ),
                CodeFunction(
                    "Code 32 Pharmaceutical (PARAF)",
                    "C39E32*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C39E320", "C39E321")
                ),
                CodeFunction(
                    "Code 32 Prefix",
                    "C39S32*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C39S320", "C39S321")
                ),
                CodeFunction(
                    "Code 32 Start/Stop Characters",
                    "C39T32*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C39T320", "C39T321")
                ),
                CodeFunction(
                    "Code 32 Check Character",
                    "C39C32*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C39C320", "C39C321")
                )
            )
        ),
        CodeObj(
            "Codabar",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "CBADEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "CBAENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("CBAENA1", "CBAENA0")
                ),
                CodeFunction(
                    "Check Character",
                    "CBACHK*",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("CBACHK0", "CBACHK1", "CBACHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("CBAMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "CBAMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("CBAMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "CBAMAX")
                ),
                CodeFunction(
                    "Start/Stop Characters",
                    "CBATSC*",
                    mutableListOf("Transmit", "Don’t Transmit"),
                    mutableListOf("CBATSC1", "CBATSC0")
                ),
                CodeFunction(
                    "Format Of Start and Stop Characters",
                    "CBASCF*",
                    mutableListOf("ABCD/ABCD", "ABCD/TN*E", "abcd/abcd", "abcd/tn*e"),
                    mutableListOf("CBASCF0", "CBASCF1", "CBASCF2", "CBASCF3")
                )
            )
        ),
        CodeObj(
            "Code 93",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "C93DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "C93ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C93ENA1", "C93ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("C93MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "C93MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("C93MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "C93MAX")
                )
            )
        ),
        CodeObj(
            "GS1-128(UCC/EAN-128)",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "GS1DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "GS1ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("GS1ENA1", "GS1ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("GS1MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "GS1MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("GS1MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "GS1MAX")
                )
            )
        ),
        CodeObj(
            "GS1 DataBar(RSS)",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "RSSDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "RSSENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("RSSENA1", "RSSENA0")
                ),
                CodeFunction(
                    "AI(01) Character",
                    "RSSTAI*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("RSSTAI0", "RSSTAI1")
                )
            )
        ),
        CodeObj(
            "Code 11",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "C11DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "C11ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C11ENA1", "C11ENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("C11MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "C11MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("C11MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "C11MAX")
                ),
                CodeFunction(
                    "Check",
                    "C11CHK*",
                    mutableListOf(
                        "Disable",
                        "One Check Digit,MOD11",
                        "Two Check Digits,MOD11/MOD11",
                        "Two Check Digits,MOD11/MOD9",
                        "MOD11 Single Check Digits (Len<=10),MOD11/MOD11 Double Check Digits(Len>10)",
                        "MOD11 Single Check Digits (Len<=10),MOD11/MOD9 Double Check Digits(Len>10)"
                    ),
                    mutableListOf("C11CHK0", "C11CHK1", "C11CHK2", "C11CHK3", "C11CHK4", "C11CHK5")
                ),
                CodeFunction(
                    "Check Character",
                    "C11TCK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C11TCK0", "C11TCK1")
                )
            )
        ),
        CodeObj(
            "ISBN",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "ISBDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "ISBENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("ISBENA1", "ISBENA0")
                ),
                CodeFunction(
                    "ISBN format",
                    "ISBT10*",
                    mutableListOf("ISBN-13", "ISBN-10"),
                    mutableListOf("ISBT100", "ISBT101")
                )
            )
        ),
        CodeObj(
            "Industrial 25",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "L25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "L25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("L25ENA1", "L25ENA0")
                ),
                CodeFunction(
                    "Check",
                    "L25CHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("L25CHK0", "L25CHK1", "L25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("L25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "L25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("L25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "L25MAX")
                )
            )
        ),
        CodeObj(
            "Standard 25",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "S25DEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "S25ENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("S25ENA1", "S25ENA0")
                ),
                CodeFunction(
                    "Check",
                    "S25CHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("S25CHK0", "S25CHK1", "S25CHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("S25MIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "S25MIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("S25MAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "S25MAX")
                )
            )
        ),
        CodeObj(
            "Plessey",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "PLYDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "PLYENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("PLYENA1", "PLYENA0")
                ),
                CodeFunction(
                    "Check",
                    "PLYCHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("PLYCHK0", "PLYCHK1", "PLYCHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("PLYMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "PLYMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("PLYMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "PLYMAX")
                )
            )
        ),
        CodeObj(
            "MSI Plessey",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "MSIDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "MSIENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("MSIENA1", "MSIENA0")
                ),
                CodeFunction(
                    "Check",
                    "MSICHK*",
                    mutableListOf("Disable", "Enable,Don’t Transmit", "Enable,Transmit"),
                    mutableListOf("MSICHK0", "MSICHK1", "MSICHK2")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("MSIMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "MSIMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("MSIMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "MSIMAX")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "MSITCK*",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("MSITCK0", "MSITCK1")
                ),
            )
        ),
        CodeObj(
            "ISSN",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "ISSDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "ISSENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("ISSENA1", "ISSENA0")
                )
            )
        ),
        CodeObj(
            "AIM 128",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "AIMDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "AIMENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("AIMENA1", "AIMENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("AIMMIN*", 1, 127, 1, "Minimum Length 0f Code Reading", "AIMMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("AIMMAX*", 1, 127, 1, "Maximum Length 0f Code Reading", "AIMMAX")
                )
            )
        ),
        CodeObj(
            "PDF417",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "PDFDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "PDFENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("PDFENA1", "PDFENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("PDFMIN*", 1, 6144, 1, "Minimum Length 0f Code Reading", "PDFMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("PDFMAX*", 1, 6144, 1, "Maximum Length 0f Code Reading", "PDFMAX")
                ),
                CodeFunction(
                    "Read multi-code",
                    "PDFDOU*",
                    mutableListOf(
                        "Read Single Code",
                        "Read Double Code",
                        "Read Single/Double Code"
                    ),
                    mutableListOf("PDFDOU0", "PDFDOU1", "PDFDOU2")
                ),
                CodeFunction(
                    "ECI Output",
                    "PDFECI*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("PDFECI0", "PDFECI1")
                ),
                CodeFunction(
                    "Character Encoding Format",
                    "PDFENC*",
                    mutableListOf("Default", "UTF-8"),
                    mutableListOf("PDFENC0", "PDFENC1")
                )
            )
        ),
        CodeObj(
            "QR",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "QRCDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "QRCENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("QRCENA1", "QRCENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("QRCMIN*", 1, 6144, 1, "Minimum Length 0f Code Reading", "QRCMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("QRCMAX*", 1, 6144, 1, "Maximum Length 0f Code Reading", "QRCMAX")
                ),
                CodeFunction(
                    "Read multi-code",
                    "QRCDOU*",
                    mutableListOf(
                        "Read Single Code",
                        "Read Double Code",
                        "Read Single/Double Code"
                    ),
                    mutableListOf("QRCDOU0", "QRCDOU1", "QRCDOU2")
                ),
                CodeFunction(
                    "ECI Output",
                    "QRCECI*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("QRCECI0", "QRCECI1")
                ),
                CodeFunction(
                    "Character Encoding Format",
                    "QRCENC*",
                    mutableListOf("Default", "UTF-8"),
                    mutableListOf("QRCENC0", "QRCENC1")
                )
            )
        ),
        CodeObj(
            "Data Matrix",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "DMCDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "DMCENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("DMCENA1", "DMCENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("DMCMIN*", 1, 6144, 1, "Minimum Length 0f Code Reading", "DMCMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("DMCMAX*", 1, 6144, 1, "Maximum Length 0f Code Reading", "DMCMAX")
                ),
                CodeFunction(
                    "Read multi-code",
                    "DMCDOU*",
                    mutableListOf(
                        "Read Single Code",
                        "Read Double Code",
                        "Read Single/Double Code"
                    ),
                    mutableListOf("DMCDOU0", "DMCDOU1", "DMCDOU2")
                ),
                CodeFunction(
                    "Matrix Code",
                    "DMCREC*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("DMCREC1", "DMCREC0")
                ),
                CodeFunction(
                    "ECI Output",
                    "DMCECI*",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("DMCECI0", "DMCECI1")
                ),
                CodeFunction(
                    "Character Encoding Format",
                    "DMCENC*",
                    mutableListOf("Default", "UTF-8"),
                    mutableListOf("DMCENC0", "DMCENC1")
                )
            )
        ),
        CodeObj(
            "Micro QR",
            listOf(
                CodeFunction(
                    "Restore Default",
                    "MQRDEF"
                ),
                CodeFunction(
                    "Enable/Disable",
                    "MQRENA*",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("MQRENA1", "MQRENA0")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("MQRMIN*", 1, 6144, 1, "Minimum Length 0f Code Reading", "MQRMIN")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("MQRMAX*", 1, 6144, 1, "Maximum Length 0f Code Reading", "MQRMAX")
                )
            )
        )
    )
}

//ME11 扫描模块
fun getAllME11Config(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "Code 128",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Code 128",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_2801", "C_CMD_2800")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Code 128",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_280A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Code 128",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_280B"
                    )
                )

            )
        ),
        CodeObj(
            "EAN-8",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "EAN-8",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_0801", "C_CMD_0800")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "EAN-8",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_0802", "C_CMD_0803")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "EAN-8",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_0820", "C_CMD_0821")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "EAN-8",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_0850", "C_CMD_0851")
                )
            )
        ),
        CodeObj(
            "EAN-13",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "EAN-13",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_1301", "C_CMD_1300")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "EAN-13",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_1302", "C_CMD_1303")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "EAN-13",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_1320", "C_CMD_1321")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "EAN-13",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_1350", "C_CMD_1351")
                )
            )
        ),
        CodeObj(
            "UPC-E0",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "UPC-E0",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_E001", "C_CMD_E000")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPC-E0",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_E002", "C_CMD_E003")
                ),
                CodeFunction(
                    "Output System Characters",
                    "UPC-E0",
                    mutableListOf("Do not output", "Output"),
                    mutableListOf("C_CMD_E004", "C_CMD_E005")
                )
            )
        ),
        CodeObj(
            "UPC-E1",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "UPC-E1",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_E101", "C_CMD_E100")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPC-E1",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_E102", "C_CMD_E103")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPC-E1",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_E120", "C_CMD_E121")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPC-E1",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_E150", "C_CMD_E151")
                ),
                CodeFunction(
                    "Output System Characters",
                    "UPC-E1",
                    mutableListOf("Do not output", "Output"),
                    mutableListOf("C_CMD_E104", "C_CMD_E105")
                )
            )
        ),
        CodeObj(
            "UPC-A",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "UPC-A",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_CA01", "C_CMD_CA00")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPC-A",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_CA02", "C_CMD_CA03")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPC-A",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_CA20", "C_CMD_CA21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPC-A",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_CA50", "C_CMD_CA51")
                ),
                CodeFunction(
                    "Output System Characters",
                    "UPC-A",
                    mutableListOf("Do not output", "Output"),
                    mutableListOf("C_CMD_CA04", "C_CMD_CA05")
                )
            )
        ),
        CodeObj(
            "Interleaved 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Interleaved 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_IT01", "C_CMD_IT00")
                ),
                CodeFunction(
                    "Check Character",
                    "Interleaved 2 of 5",
                    mutableListOf(
                        "Off",
                        "USS On,Don’t Transmit",
                        "USS On,Transmit",
                        "OPCC On,Don’t Transmit",
                        "OPCC On,Transmit"
                    ),
                    mutableListOf(
                        "C_CMD_IT02",
                        "C_CMD_IT04",
                        "C_CMD_IT03",
                        "C_CMD_IT06",
                        "C_CMD_IT05"
                    )
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Interleaved 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_IT0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Interleaved 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_IT0B"
                    )
                )
            )
        ),
        CodeObj(
            "Matrix 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Matrix 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MA01", "C_CMD_MA00")
                ),
                CodeFunction(
                    "Check Character",
                    "Matrix 2 of 5",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_MA02", "C_CMD_MA04", "C_CMD_MA03")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Matrix 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_MA0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Matrix 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_MA0B"
                    )
                )
            )
        ),
        CodeObj(
            "Industrial 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Industrial 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_IN01", "C_CMD_IN00")
                ),
                CodeFunction(
                    "Check Character",
                    "Industrial 2 of 5",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_IN02", "C_CMD_IN04", "C_CMD_IN03")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Industrial 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_IN0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Industrial 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_IN0B"
                    )
                )
            )
        ),
        CodeObj(
            "IATA 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "IATA 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_IA01", "C_CMD_IA00")
                ),
                CodeFunction(
                    "Check Character",
                    "IATA 2 of 5",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_IA02", "C_CMD_IA04", "C_CMD_IA03")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "IATA 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_IA0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "IATA 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_IA0B"
                    )
                )
            )
        ),
        CodeObj(
            "Code 39",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Code 39",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_3901", "C_CMD_3900")
                ),
                CodeFunction(
                    "Start/Stop Characters",
                    "Code 39",
                    mutableListOf("Transmit", "Don’t Transmit"),
                    mutableListOf("C_CMD_3907", "C_CMD_3906")
                ),
                CodeFunction(
                    "Check Character",
                    "Code 39",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_3902", "C_CMD_3904", "C_CMD_3903")
                ),
                CodeFunction(
                    "Full ASCII",
                    "Code 39",
                    mutableListOf("Disable Code 39 Full ASCII", "Enable Code 39 Full ASCII"),
                    mutableListOf("C_CMD_39M2", "C_CMD_39M3")
                ),
                CodeFunction(
                    "Code 32",
                    "Code 39",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C_CMD_39M0", "C_CMD_39M1")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("Code 39", 1, 255, 1, "Minimum Length 0f Code Reading", "C_CMD_390A")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("Code 39", 1, 255, 1, "Maximum Length 0f Code Reading", "C_CMD_390B")
                )

            )
        ),
        CodeObj(
            "Code 93",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Code 93",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_9301", "C_CMD_9300")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("Code 93", 1, 255, 1, "Minimum Length 0f Code Reading", "C_CMD_930A")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("Code 93", 1, 255, 1, "Maximum Length 0f Code Reading", "C_CMD_930B")
                )

            )
        ),
        CodeObj(
            "CodeBar",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "CodeBar",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_BA01", "C_CMD_BA00")
                ),
                CodeFunction(
                    "Check Character",
                    "CodeBar",
                    mutableListOf(
                        "Off",
                        "Mod 10 Check,Don’t Transmit",
                        "Mod 10 Check,Transmit",
                        "Mod 16 Check,Don’t Transmit",
                        "Mod 16 Check,Transmit"
                    ),
                    mutableListOf(
                        "C_CMD_BA02",
                        "C_CMD_BA04",
                        "C_CMD_BA03",
                        "C_CMD_BA06",
                        "C_CMD_BA05"
                    )
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("CodeBar", 1, 255, 1, "Minimum Length 0f Code Reading", "C_CMD_BA0A")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("CodeBar", 1, 255, 1, "Maximum Length 0f Code Reading", "C_CMD_BA0B")
                ),
                CodeFunction(
                    "Output Of Start and Stop Characters",
                    "CodeBar",
                    mutableListOf("Off", "ABCD/ABCD", "ABCD/TN*E", "abcd/abcd", "abcd/tn*e"),
                    mutableListOf(
                        "C_CMD_BAS0",
                        "C_CMD_BAS2",
                        "C_CMD_BAS3",
                        "C_CMD_BAS4",
                        "C_CMD_BAS5"
                    )
                )
            )
        ),
        CodeObj(
            "PDF417",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "PDF417",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_PDF1", "C_CMD_PDF0")
                )
            )
        ),
        CodeObj(
            "QR",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "QR",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_QR01", "C_CMD_QR00")
                )
            )
        ),
        CodeObj(
            "DM",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "DM",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_DM01", "C_CMD_DM00")
                )
            )
        )
    )
}

//M1 扫描模块
fun getAllM1Config(): MutableList<CodeObj> {
    return mutableListOf(
        CodeObj(
            "GS1 AI Settings",
            listOf(
                CodeFunction(
                    "GS1 AI output rules",
                    "GS1 AI Settings",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_GSA1", "C_CMD_GSA0")
                )
            )
        ),
        CodeObj(
            "Inverse Color Code",
            listOf(
                CodeFunction(
                    "Reading inverse color codes",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_INV1", "C_CMD_INV0")
                ),
                CodeFunction(
                    "Reading one-dimensional inverse color codes",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_OIM1", "C_CMD_OIM0")
                ),
                CodeFunction(
                    "Read PDF417 reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_PIM1", "C_CMD_PIM0")
                ),
                CodeFunction(
                    "Read DM reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_DIM1", "C_CMD_DIM0")
                ),
                CodeFunction(
                    "Read QR reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_QIM1", "C_CMD_QIM0")
                ),
                CodeFunction(
                    "Read Micro PDF417 reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MIM1", "C_CMD_MIM0")
                ),
                CodeFunction(
                    "Read Aztec reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_AIM1", "C_CMD_AIM0")
                ),
                CodeFunction(
                    "Read Maxicode reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MAM1", "C_CMD_MAM0")
                ),
                CodeFunction(
                    "Read Chinese QR codes reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_HIM1", "C_CMD_HIM0")
                ),
                CodeFunction(
                    "Read DotCode reverse color code",
                    "Inverse Color Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_TIM1", "C_CMD_TIM0")
                )
            )
        ),
        CodeObj(
            "Code 128",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Code 128",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_2801", "C_CMD_2800")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Code 128",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_280A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Code 128",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_280B"
                    )
                )

            )
        ),
        CodeObj(
            "EAN-8",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "EAN-8",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_0801", "C_CMD_0800")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "EAN-8",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_0802", "C_CMD_0803")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "EAN-8",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_0820", "C_CMD_0821")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "EAN-8",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_0850", "C_CMD_0851")
                ),
                CodeFunction(
                    "EAN8 to EAN13 conversion",
                    "EAN-8",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_0831", "C_CMD_0830")
                )
            )
        ),
        CodeObj(
            "EAN-13",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "EAN-13",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_1301", "C_CMD_1300")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "EAN-13",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_1302", "C_CMD_1303")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "EAN-13",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_1320", "C_CMD_1321")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "EAN-13",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_1350", "C_CMD_1351")
                ),
                CodeFunction(
                    "EAN13 to ISBN conversion",
                    "EAN-13",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_BN01", "C_CMD_BN00")
                ),
                CodeFunction(
                    "EAN13 to ISSN conversion",
                    "EAN-13",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_SN01", "C_CMD_SN00")
                )
            )
        ),
        CodeObj(
            "UPC-E0",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "UPC-E0",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_E001", "C_CMD_E000")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPC-E0",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_E002", "C_CMD_E003")
                ),
                CodeFunction(
                    "Output System Characters",
                    "UPC-E0",
                    mutableListOf("Do not output", "Output"),
                    mutableListOf("C_CMD_E004", "C_CMD_E005")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPC-E0",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_E020", "C_CMD_E021")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPC-E0",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_E050", "C_CMD_E051")
                ),
                CodeFunction(
                    "Extension code",
                    "UPC-E0",
                    mutableListOf("Must include", "Not required to include"),
                    mutableListOf("C_CMD_E0A1", "C_CMD_E0A0")
                ),
                CodeFunction(
                    "UPCE0 to UPCA conversion",
                    "UPC-E0",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_E0T1", "C_CMD_E0T0")
                )
            )
        ),
        CodeObj(
            "UPC-E1",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "UPC-E1",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_E101", "C_CMD_E100")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPC-E1",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_E102", "C_CMD_E103")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPC-E1",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_E120", "C_CMD_E121")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPC-E1",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_E150", "C_CMD_E151")
                ),
                CodeFunction(
                    "Output System Characters",
                    "UPC-E1",
                    mutableListOf("Do not output", "Output"),
                    mutableListOf("C_CMD_E104", "C_CMD_E105")
                ),
                CodeFunction(
                    "Extension code",
                    "UPC-E1",
                    mutableListOf("Must include", "Not required to include"),
                    mutableListOf("C_CMD_E1A1", "C_CMD_E1A0")
                ),
                CodeFunction(
                    "UPCE1 to UPCA conversion",
                    "UPC-E1",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_E1T1", "C_CMD_E1T0")
                )
            )
        ),
        CodeObj(
            "UPC-A",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "UPC-A",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_CA01", "C_CMD_CA00")
                ),
                CodeFunction(
                    "Transmit Check Character",
                    "UPC-A",
                    mutableListOf("Don’t Transmit", "Transmit"),
                    mutableListOf("C_CMD_CA02", "C_CMD_CA03")
                ),
                CodeFunction(
                    "2 Digit Addenda",
                    "UPC-A",
                    mutableListOf("2 Digit Addenda Off", "2 Digit Addenda On"),
                    mutableListOf("C_CMD_CA20", "C_CMD_CA21")
                ),
                CodeFunction(
                    "5 Digit Addenda",
                    "UPC-A",
                    mutableListOf("5 Digit Addenda Off", "5 Digit Addenda On"),
                    mutableListOf("C_CMD_CA50", "C_CMD_CA51")
                ),
                CodeFunction(
                    "Output System Characters",
                    "UPC-A",
                    mutableListOf("Do not output", "Output"),
                    mutableListOf("C_CMD_CA04", "C_CMD_CA05")
                ),
                CodeFunction(
                    "Extension code",
                    "UPC-A",
                    mutableListOf("Must include", "Not required to include"),
                    mutableListOf("C_CMD_CAA1", "C_CMD_CAA0")
                ),
                CodeFunction(
                    "UPCA to EAN13 conversion",
                    "UPC-A",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_CAE1", "C_CMD_CAE0")
                )
            )
        ),
        CodeObj(
            "Interleaved 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Interleaved 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_IT01", "C_CMD_IT00")
                ),
                CodeFunction(
                    "Check Character",
                    "Interleaved 2 of 5",
                    mutableListOf(
                        "Off",
                        "USS On,Don’t Transmit",
                        "USS On,Transmit",
                        "OPCC On,Don’t Transmit",
                        "OPCC On,Transmit"
                    ),
                    mutableListOf(
                        "C_CMD_IT02",
                        "C_CMD_IT04",
                        "C_CMD_IT03",
                        "C_CMD_IT06",
                        "C_CMD_IT05"
                    )
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Interleaved 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_IT0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Interleaved 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_IT0B"
                    )
                )
            )
        ),
        CodeObj(
            "Matrix 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Matrix 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MA01", "C_CMD_MA00")
                ),
                CodeFunction(
                    "Check Character",
                    "Matrix 2 of 5",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_MA02", "C_CMD_MA04", "C_CMD_MA03")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Matrix 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_MA0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Matrix 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_MA0B"
                    )
                )
            )
        ),
        CodeObj(
            "Industrial 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Industrial 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_IN01", "C_CMD_IN00")
                ),
                CodeFunction(
                    "Check Character",
                    "Industrial 2 of 5",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_IN02", "C_CMD_IN04", "C_CMD_IN03")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Industrial 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_IN0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Industrial 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_IN0B"
                    )
                )
            )
        ),
        CodeObj(
            "IATA 2 of 5",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "IATA 2 of 5",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_IA01", "C_CMD_IA00")
                ),
                CodeFunction(
                    "Check Character",
                    "IATA 2 of 5",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_IA02", "C_CMD_IA04", "C_CMD_IA03")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "IATA 2 of 5",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_IA0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "IATA 2 of 5",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_IA0B"
                    )
                )
            )
        ),
        CodeObj(
            "Code 39",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Code 39",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_3901", "C_CMD_3900")
                ),
                CodeFunction(
                    "Start/Stop Characters",
                    "Code 39",
                    mutableListOf("Transmit", "Don’t Transmit"),
                    mutableListOf("C_CMD_3907", "C_CMD_3906")
                ),
                CodeFunction(
                    "Check Character",
                    "Code 39",
                    mutableListOf("Off", "On,Don’t Transmit", "On,Transmit"),
                    mutableListOf("C_CMD_3902", "C_CMD_3904", "C_CMD_3903")
                ),
                CodeFunction(
                    "Full ASCII",
                    "Code 39",
                    mutableListOf("Disable Code 39 Full ASCII", "Enable Code 39 Full ASCII"),
                    mutableListOf("C_CMD_39M2", "C_CMD_39M3")
                ),
                CodeFunction(
                    "Code 32",
                    "Code 39",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C_CMD_39M0", "C_CMD_39M1")
                ),
                CodeFunction(
                    "Output Code32 prefix",
                    "Code 39",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C_CMD_32A0", "C_CMD_32A1")
                ),
                CodeFunction(
                    "Code32 transmission checksum",
                    "Code 39",
                    mutableListOf("Disable", "Enable"),
                    mutableListOf("C_CMD_32P0", "C_CMD_32P1")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("Code 39", 1, 255, 1, "Minimum Length 0f Code Reading", "C_CMD_390A")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("Code 39", 1, 255, 1, "Maximum Length 0f Code Reading", "C_CMD_390B")
                )

            )
        ),
        CodeObj(
            "Code 93",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Code 93",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_9301", "C_CMD_9300")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("Code 93", 1, 255, 1, "Minimum Length 0f Code Reading", "C_CMD_930A")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("Code 93", 1, 255, 1, "Maximum Length 0f Code Reading", "C_CMD_930B")
                )

            )
        ),
        CodeObj(
            "CodeBar",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "CodeBar",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_BA01", "C_CMD_BA00")
                ),
                CodeFunction(
                    "Check Character",
                    "CodeBar",
                    mutableListOf(
                        "Off",
                        "Mod 10 Check,Don’t Transmit",
                        "Mod 10 Check,Transmit",
                        "Mod 16 Check,Don’t Transmit",
                        "Mod 16 Check,Transmit"
                    ),
                    mutableListOf(
                        "C_CMD_BA02",
                        "C_CMD_BA04",
                        "C_CMD_BA03",
                        "C_CMD_BA06",
                        "C_CMD_BA05"
                    )
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength("CodeBar", 1, 255, 1, "Minimum Length 0f Code Reading", "C_CMD_BA0A")
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength("CodeBar", 1, 255, 1, "Maximum Length 0f Code Reading", "C_CMD_BA0B")
                ),
                CodeFunction(
                    "Output Of Start and Stop Characters",
                    "CodeBar",
                    mutableListOf("Off", "ABCD/ABCD", "ABCD/TN*E", "abcd/abcd", "abcd/tn*e"),
                    mutableListOf(
                        "C_CMD_BAS0",
                        "C_CMD_BAS2",
                        "C_CMD_BAS3",
                        "C_CMD_BAS4",
                        "C_CMD_BAS5"
                    )
                )
            )
        ),
        CodeObj(
            "CODM1",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "CODM1",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_1101", "C_CMD_1100")
                ),
                CodeFunction(
                    "Check Character",
                    "CODM1",
                    mutableListOf(
                        "Off",
                        "When the data does not exceed 10 bits, a 1-bit check is performed. When the data exceeds 10 bits, a 2-bit check is performed. The transmission check is performed",
                        "When the data does not exceed 10 bits, a 1-bit check is performed. When the data exceeds 10 bits, a 2-bit check is performed, and no check bits are transmitted",
                        "Fixed 1-bit checksum, transmit checksum bit",
                        "Fixed 1-bit checksum, no checksum transmitted",
                        "Fixed 2-bit checksum, transmit checksum bit",
                        "Fixed 2-bit checksum, no checksum transmitted"
                    ),
                    mutableListOf(
                        "C_CMD_11P0",
                        "C_CMD_11P1",
                        "C_CMD_11P2",
                        "C_CMD_11P3",
                        "C_CMD_11P4",
                        "C_CMD_11P5",
                        "C_CMD_11P6"
                    )
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "CODM1",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_110A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "CODM1",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_110B"
                    )
                )
            )
        ),
        CodeObj(
            "MSI",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "MSI",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MS01", "C_CMD_MS00")
                ),
                CodeFunction(
                    "Check Character",
                    "MSI",
                    mutableListOf(
                        "Off",
                        "Mod 10 check, transmit check bit",
                        "Mod 10 check, no transfer of check bits",
                        "Mod 11 check, transmit check bit",
                        "Mod 11 check, no transfer of check bits",
                        "Mod 10、10 check, transmit check bit",
                        "Mod 10、10 check, no transfer of check bits",
                        "Mod 11、10 check, transmit check bit",
                        "Mod 11、10 check, no transfer of check bits"
                    ),
                    mutableListOf(
                        "C_CMD_MSP0",
                        "C_CMD_MSP1",
                        "C_CMD_MSP2",
                        "C_CMD_MSP3",
                        "C_CMD_MSP4",
                        "C_CMD_MSP5",
                        "C_CMD_MSP6",
                        "C_CMD_MSP7",
                        "C_CMD_MSP8"
                    )
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "MSI",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_MS0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "MSI",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_MS0B"
                    )
                )
            )
        ),
        CodeObj(
            "GS1 DataBar",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "GS1 DataBar",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_GS41", "C_CMD_GS40")
                )
            )
        ),
        CodeObj(
            "GS1 DataBar Limited",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "GS1 DataBar Limited",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_GSL1", "C_CMD_GSL0")
                )
            )
        ),
        CodeObj(
            "GS1 DataBar Expanded",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "GS1 DataBar Expanded",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_GSE1", "C_CMD_GSE0")
                )
            )
        ),
        CodeObj(
            "Plessey",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Plessey",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_PE01", "C_CMD_PE00")
                ),
                CodeFunction(
                    "Check Character",
                    "Plessey",
                    mutableListOf("Off", "On,Transmit"),
                    mutableListOf("C_CMD_PEP0", "C_CMD_PEP1")
                ),
                CodeFunction(
                    "Minimum Length 0f Code Reading",
                    CodeLength(
                        "Plessey",
                        1,
                        255,
                        1,
                        "Minimum Length 0f Code Reading",
                        "C_CMD_PE0A"
                    )
                ),
                CodeFunction(
                    "Maximum Length 0f Code Reading",
                    CodeLength(
                        "Plessey",
                        1,
                        255,
                        1,
                        "Maximum Length 0f Code Reading",
                        "C_CMD_PE0B"
                    )
                )
            )
        ),
        CodeObj(
            "Febraban",
            listOf(
                CodeFunction(
                    "Identify ITF25 types",
                    "Febraban",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_FEI1", "C_CMD_FEI0")
                ),
                CodeFunction(
                    "Identify Code128 types",
                    "Febraban",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_FE81", "C_CMD_FE80")
                ),
                CodeFunction(
                    "Febraban verification",
                    "Febraban",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_FEC1", "C_CMD_FEC0")
                )
            )
        ),
        CodeObj(
            "Composite",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Composite",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_CPO1", "C_CMD_CPO0")
                )
            )
        ),
        CodeObj(
            "PDF417",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "PDF417",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_PDF1", "C_CMD_PDF0")
                )
            )
        ),
        CodeObj(
            "QR",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "QR",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_QR01", "C_CMD_QR00")
                )
            )
        ),
        CodeObj(
            "DM",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "DM",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_DM01", "C_CMD_DM00")
                )
            )
        ),
        CodeObj(
            "Micro PDF417",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Micro PDF417",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MIP1", "C_CMD_MIP0")
                )
            )
        ),
        CodeObj(
            "Aztec",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Aztec",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_AZ01", "C_CMD_AZ00")
                )
            )
        ),
        CodeObj(
            "Maxicode",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Maxicode",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_MAX1", "C_CMD_MAX0")
                )
            )
        ),
        CodeObj(
            "Hanxin Code",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "Hanxin Code",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_HAN1", "C_CMD_HAN0")
                )
            )
        ),
        CodeObj(
            "DotCode",
            listOf(
                CodeFunction(
                    "Enable/Disable",
                    "DotCode",
                    mutableListOf("Enable", "Disable"),
                    mutableListOf("C_CMD_DOT1", "C_CMD_DOT0")
                )
            )
        )
    )
}