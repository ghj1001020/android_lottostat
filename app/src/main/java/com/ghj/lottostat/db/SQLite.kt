package com.ghj.lottostat.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import java.lang.Exception

object SQLite {
    val SQLITE_VERSION = 2
    val DB_FILE_NAME = "lotto.db"
    val DB_VERSION = 1

    private var helper : SQLiteHelper? = null
    private var readDB : SQLiteDatabase? = null
    private var writeDB : SQLiteDatabase? = null

    // database 경로가져오기
    fun databaseFolderPath(context: Context) : String {
        val parent : String = context.filesDir.parent ?: context.filesDir.absolutePath
        return "${parent}/databases"
    }


    fun init( context: Context) {
        if( helper == null ) {
            helper = SQLiteHelper(context)
            readDB = helper?.readableDatabase
            writeDB = helper?.writableDatabase
        }
    }

    fun close() {
        readDB?.close()
        writeDB?.close()
        helper?.close()
        helper = null
    }

    // SELECT
    fun select( sql: String, params: Array<String>?=null, listener: (cursor: Cursor)->Unit ) {
        synchronized(this) {
            try {
                if( readDB == null ) return

                val cursor: Cursor = readDB!!.rawQuery( sql, params )
                listener(cursor)
                cursor.close()
            }
            catch( e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // INSERT, UPDATE, DELETE
    fun execSQL( sql: String, params: Array<String>?=null ) : Boolean {
        synchronized(this) {
            writeDB?.beginTransaction()

            try {
                if( writeDB == null ) return false

                if( params == null ) {
                    writeDB!!.execSQL(sql)
                }
                else {
                    writeDB!!.execSQL(sql, params)
                }
                return true
            }
            catch( e: Exception) {
                e.printStackTrace()
                return false
            }
            finally {
                writeDB?.setTransactionSuccessful()
                writeDB?.endTransaction()
            }
        }
    }
}