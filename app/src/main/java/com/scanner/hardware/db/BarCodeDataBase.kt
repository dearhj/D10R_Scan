package com.scanner.hardware.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MotoItem::class, SymbologiesItem::class, Config::class,ScannerData::class],
    version = 10,
    exportSchema = false
)
abstract class BarCodeDataBase : RoomDatabase() {
    abstract fun barcodeDao(): BarCodeDao

    companion object {
        @Volatile
        var INSTANCE: BarCodeDataBase? = null
        fun create(context: Context): BarCodeDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BarCodeDataBase::class.java,
                "data.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}