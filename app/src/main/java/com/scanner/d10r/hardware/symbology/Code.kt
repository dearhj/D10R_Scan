package com.scanner.d10r.hardware.symbology

data class CodeObj(
    val codeName: String,
    val functions: List<CodeFunction>,
)

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