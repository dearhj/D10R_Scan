package com.scanner.hardware.barcodereceiver

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.scanner.hardware.BuildConfig
import com.scanner.hardware.MyApplication


class SettingsProvider : ContentProvider() {
    private val table = "data"
    private val item = 1
    private val itemId = 2
    private val authority = BuildConfig.APPLICATION_ID
    private val contentURI = Uri.parse("content://${authority}/$table")
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val contentType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/data"
    private val contentItemType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/data"

    init {
        sUriMatcher.addURI(authority, table, item)
        sUriMatcher.addURI(authority, "${table}/*", itemId)
    }

    override fun onCreate(): Boolean {
        return true
    }


    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            item -> contentType
            itemId -> contentItemType
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var uri2: Uri? = null
        try {
            val supportDB = MyApplication.database.openHelper.writableDatabase
            supportDB.beginTransaction()
            try {
                val raw = supportDB.insert(table, SQLiteDatabase.CONFLICT_NONE, values!!)
                if (raw > 0) {
                    uri2 = ContentUris.withAppendedId(contentURI, raw)
                    MyApplication.application?.contentResolver?.notifyChange(uri, null)
                }
                supportDB.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                supportDB.endTransaction()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return uri2
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return MyApplication.database.openHelper.writableDatabase.delete(
            table, selection, selectionArgs
        )
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            item -> MyApplication.database.query(
                SupportSQLiteQueryBuilder.builder(table).selection(selection, selectionArgs)
                    .columns(projection).orderBy(sortOrder).create()
            )

            itemId -> MyApplication.database.query(
                SupportSQLiteQueryBuilder.builder(table)
                    .selection("id=${uri.lastPathSegment}", selectionArgs).columns(projection)
                    .orderBy(sortOrder).create()
            )

            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?
    ): Int {
        return MyApplication.database.openHelper.writableDatabase.update(
                table,
                0,
                values!!,
                selection,
                selectionArgs
            )
    }
}