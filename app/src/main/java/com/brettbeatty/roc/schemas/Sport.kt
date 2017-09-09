package com.brettbeatty.roc.schemas

import android.database.sqlite.SQLiteDatabase
import com.brettbeatty.roc.utilities.DatabaseConnection

data class Sport(var id: Long, var name: String) {

    companion object {
        val TAG: String = Pass::class.java.simpleName
    }

    class Schema(connection: DatabaseConnection) {
        private val database: SQLiteDatabase by lazy { connection.database }

        fun addSport(id: Long, name: String): Long {
            val statement = database.compileStatement("INSERT OR IGNORE INTO sports (id, name) VALUES (?, ?);")

            statement.bindLong(1, id)
            statement.bindString(2, name)
            return statement.executeInsert()
        }

        fun deleteSport(id: Long): Int {
            val statement = database.compileStatement("DELETE FROM sports WHERE id=?;")

            statement.bindLong(1, id)
            return statement.executeUpdateDelete()
        }

        fun listSports(): Array<Sport> {
            val cursor = database.rawQuery("SELECT * FROM sports;", arrayOf<String>())
            val idColumn = cursor.getColumnIndexOrThrow("id")
            val nameColumn = cursor.getColumnIndexOrThrow("name")
            val sports = arrayListOf<Sport>()

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val sport = Sport(id, name)

                sports.add(sport)
                cursor.moveToNext()
            }
            cursor.close()
            return sports.toTypedArray()
        }
    }
}