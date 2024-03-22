package com.scanner.hardware.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "data", indices = [Index(
        value = ["id"],
        unique = true
    )]
)
class ScannerData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "data")
    var data: String

) {
    override fun toString(): String {
        return "ScannerData(id=$id, data=$data)"
    }
}