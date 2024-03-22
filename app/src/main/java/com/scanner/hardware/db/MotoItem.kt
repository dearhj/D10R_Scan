package com.scanner.hardware.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "motobar", indices = [Index(
        value = ["id"],
        unique = true
    )]
)
class MotoItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "type") val type: Int,//标记用的是哪一种厂商的哪一种类型,新增厂商，请增加BarCodeEnum
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "openValue") val openValue: ByteArray,
    @ColumnInfo(name = "closeValue") val closeValue: ByteArray,
    @ColumnInfo(name = "state") var state: Boolean
) {
    override fun toString(): String {
        return "MotoBarcodeDBItem(id=$id, name='$name', state=$state)"
    }
}