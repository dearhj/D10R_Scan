package com.scanner.d10r.hardware.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "me11SymData", indices = [Index(
        value = ["id"],
        unique = true
    )]
)
class ME11SymbologyData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "symbologyData")
    var symbologyData: String,
    @ColumnInfo(name = "symbologyItemInfo")
    var symbologyItemInfo: String,
    @ColumnInfo(name = "symbologyItemValue")
    var symbologyItemValue: String

) {
    override fun toString(): String {
        return "ScannerData(id=$id, symbologyData=$symbologyData, symbologyItemInfo=$symbologyItemInfo, symbologyItemValue=$symbologyItemValue)"
    }
}