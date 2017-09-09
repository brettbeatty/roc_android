package com.brettbeatty.roc.schemas

import android.database.sqlite.SQLiteDatabase
import com.brettbeatty.roc.utilities.DatabaseConnection

data class Opponent(var id: Long, var name: String, var mascot: String, var logoKey: String) {

    companion object {
        val TAG: String = Pass::class.java.simpleName
    }

    class Schema(connection: DatabaseConnection) {
        private val database: SQLiteDatabase by lazy { connection.database }

        fun addOpponent(id: Long, name: String, mascot: String): Long {
            val statement = database.compileStatement("INSERT OR IGNORE INTO opponents (id, name, mascot, logo_key) VALUES (?, ?, ?, ?);")

            statement.bindLong(1, id)
            statement.bindString(2, name)
            statement.bindString(3, mascot)
            statement.bindString(4, "")
            return statement.executeInsert()
        }

        fun deleteOpponent(id: Long): Int {
            val statement = database.compileStatement("DELETE FROM opponents WHERE id=?;")

            statement.bindLong(1, id)
            return statement.executeUpdateDelete()
        }

        fun listOpponents(): Array<Opponent> {
            val cursor = database.rawQuery("SELECT * FROM opponents;", arrayOf<String>())
            val idColumn = cursor.getColumnIndexOrThrow("id")
            val logoKeyColumn = cursor.getColumnIndexOrThrow("logo_key")
            val mascotColumn = cursor.getColumnIndexOrThrow("mascot")
            val nameColumn = cursor.getColumnIndexOrThrow("name")
            val opponents = arrayListOf<Opponent>()

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(idColumn)
                val logoKey = cursor.getString(logoKeyColumn)
                val mascot = cursor.getString(mascotColumn)
                val name = cursor.getString(nameColumn)
                val opponent = Opponent(id, name, mascot, logoKey)

                opponents.add(opponent)
                cursor.moveToNext()
            }
            cursor.close()
            return opponents.toTypedArray()
        }
    }
}