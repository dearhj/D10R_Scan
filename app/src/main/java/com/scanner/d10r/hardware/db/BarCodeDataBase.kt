package com.scanner.d10r.hardware.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Config::class,ScannerData::class],
    version = 7,
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