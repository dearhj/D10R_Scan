package com.scanner.d10r.hardware.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BarCodeDao {
    @Query("SELECT * FROM config order by id")
    fun selectAllConfig(): MutableList<Config>

    @Query("SELECT * FROM config order by id")
    fun observerConfigChange(): LiveData<List<Config>>

    @Query("SELECT * FROM config order by id")
    fun observerConfig(): LiveData<List<Config>>

    @Query("SELECT * FROM config where `key`=(:key)")
    fun observerConfig2(key: String): LiveData<Config>

    @Query("SELECT * FROM config")
    fun getAll(): Flow<List<Config>>

    @Query("SELECT value FROM config where `key`=(:key) ")
    fun observerReplaceCharChange(key: String): LiveData<String>

    @Query("SELECT * FROM config where `key`=(:key)")
    fun selectConfigFromKey(key: String): MutableList<Config>

    @Query("select 1 from config where `key` =:key  limit 1")
    fun isExits(key: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(vararg config: Config)

    @Update
    fun updateConfig(config: Config)

    @Query("DELETE FROM config")
    fun deleteAllConfig()

    @Query("SELECT * FROM data ORDER BY id DESC")
    fun selectAllData(): PagingSource<Int, ScannerData>

    @Query("SELECT data FROM data ORDER BY id DESC")
    fun selectAllData2(): List<String>

    @Query("SELECT data FROM data WHERE id BETWEEN :n AND :m")
    fun selectAllData3(n: Long, m: Long): List<String>

    @Query("select count(*) from data")
    fun selectData(): Long

    @Insert
    fun insertData(vararg data: ScannerData)

    @Query("DELETE FROM data")
    fun delAllData()

    @Query("SELECT * FROM data")
    fun loadConfigUri(): Cursor

    @Query("SELECT data FROM data ")
    fun observeDataChange(): LiveData<List<String>>

    @Query("SELECT id FROM data Limit 1")
    fun getFirstID(): Long

    @Query("SELECT id FROM data ORDER BY id DESC Limit 1")
    fun getLastID(): Long

    @Query("DELETE FROM me11SymData")
    fun delAllSymbologyData()

    @Delete
    fun deleteSymbologyData(me11SymbologyData: ME11SymbologyData)

    @Update
    fun updateSymbologyData(me11SymbologyData: ME11SymbologyData)

    @Query("SELECT * FROM me11SymData where `symbologyData`=(:symbologyData) AND `symbologyItemInfo`=(:symbologyItemInfo)")
    fun selectSymbologyDataFromSymbologyData(
        symbologyData: String,
        symbologyItemInfo: String
    ): MutableList<ME11SymbologyData>

    @Query("select 1 from me11SymData where `symbologyData`=(:symbologyData) AND `symbologyItemInfo`=(:symbologyItemInfo)  limit 1")
    fun symbologyHasExits(symbologyData: String, symbologyItemInfo: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSymbologyData(vararg me11SymbologyData: ME11SymbologyData)
}