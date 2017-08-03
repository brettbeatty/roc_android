package com.brettbeatty.roc.schemas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask

data class Pass(var alias: String, var barcode: String, var id: Long) {

    companion object {
        val DATABASE = "pass.db"
        val SCHEMA_VERSION = 1
        val TAG = Pass::class.java.simpleName
    }

    class Schema(context: Context): SQLiteOpenHelper(context, DATABASE, null, SCHEMA_VERSION) {
        private val databaseConnectionTask = SchemaLoader().execute(this)
        private val database: SQLiteDatabase by lazy { databaseConnectionTask.get() }
//        private var database: SQLiteDatabase = SchemaLoader().execute(this).get()

        fun addPass(alias: String, barcode: String): Long {
            val statement = database.compileStatement("INSERT OR IGNORE INTO passes (barcode, alias) VALUES (?, ?);")
            statement.bindString(1, barcode)
            statement.bindString(2, alias)
            return statement.executeInsert()
        }

        fun deletePass(id: Long): Int {
            val statement = database.compileStatement("DELETE FROM passes WHERE rowid=?;")
            statement.bindLong(1, id)
            return statement.executeUpdateDelete()
        }

        fun deletePass(pass: Pass): Int {
            return deletePass(pass.id)
        }

        fun listPasses(): Array<Pass> {
            val cursor = database.rawQuery("SELECT rowid, * FROM passes;", arrayOf<String>())
            val aliasColumn = cursor.getColumnIndexOrThrow("alias")
            val barcodeColumn = cursor.getColumnIndexOrThrow("barcode")
            val idColumn = cursor.getColumnIndexOrThrow("rowid")
            val passes = arrayListOf<Pass>()

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val alias = cursor.getString(aliasColumn)
                val barcode = cursor.getString(barcodeColumn)
                val id = cursor.getLong(idColumn)
                val pass = Pass(alias, barcode, id)

                passes.add(pass)
                cursor.moveToNext()
            }
            cursor.close()
            return passes.toTypedArray()
        }

        fun renamePass(pass: Pass): Int {
            val statement = database.compileStatement("UPDATE passes SET alias=? WHERE rowid=?;")
            statement.bindString(1, pass.alias)
            statement.bindLong(2, pass.id)
            return statement.executeUpdateDelete()
        }

        override fun onCreate(database: SQLiteDatabase) {

            database.execSQL("CREATE TABLE IF NOT EXISTS passes (barcode TEXT PRIMARY KEY NOT NULL, alias TEXT);")
        }

        override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

            // For now do nothing
        }
    }

    class SchemaLoader: AsyncTask<Schema, Unit, SQLiteDatabase>() {

        override fun doInBackground(vararg schema: Schema): SQLiteDatabase {

            return schema.first().writableDatabase
        }
    }
}