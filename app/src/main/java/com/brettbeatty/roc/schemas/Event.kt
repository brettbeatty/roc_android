package com.brettbeatty.roc.schemas

import android.database.sqlite.SQLiteDatabase
import com.brettbeatty.roc.utilities.DatabaseConnection
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

data class Event(var id: Long, var opponent: Opponent, var sport: Sport, var start: LocalDateTime, var venue: Venue) {

    companion object {
        val TAG: String = Pass::class.java.simpleName
    }

    class Schema(private val connection: DatabaseConnection) {
        private val database: SQLiteDatabase by lazy { connection.database }

        fun addEvent(id: Long, opponent: Long, sport: Long, start: String, venue: Long): Long {
            val statement = database.compileStatement("INSERT OR IGNORE INTO events (id, opponent, sport, start, venue) VALUES (?, ?, ?, ?, ?);")

            statement.bindLong(1, id)
            statement.bindLong(2, opponent)
            statement.bindLong(3, sport)
            statement.bindString(4, start)
            statement.bindLong(5, venue)
            return statement.executeInsert()
        }

        fun deleteEvent(id: Long): Int {
            val statement = database.compileStatement("DELETE FROM events WHERE id=?;")

            statement.bindLong(1, id)
            return statement.executeUpdateDelete()
        }

        fun listEvents(): Array<Event> {
            val cursor = database.rawQuery("SELECT * FROM passes;", arrayOf<String>())
            val idColumn = cursor.getColumnIndexOrThrow("id")
            val opponentColumn = cursor.getColumnIndexOrThrow("opponent")
            val sportColumn = cursor.getColumnIndexOrThrow("sport")
            val startColumn = cursor.getColumnIndexOrThrow("start")
            val venueColumn = cursor.getColumnIndexOrThrow("venue")
            val events = arrayListOf<Event>()
            val opponents = connection.opponents.listOpponents().associateBy({ it.id }, { it })
            val sports = connection.sports.listSports().associateBy({ it.id }, { it })
            val venues = connection.venues.listVenues().associateBy({ it.id }, { it })
            val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(idColumn)
                val opponentID = cursor.getLong(opponentColumn)
                val sportID = cursor.getLong(sportColumn)
                val startString = cursor.getString(startColumn)
                val venueID = cursor.getLong(venueColumn)
                val opponent = opponents.getValue(opponentID)
                val sport = sports.getValue(sportID)
                val start = formatter.parseLocalDateTime(startString)
                val venue = venues.getValue(venueID)
                val event = Event(id, opponent, sport, start, venue)

                events.add(event)
                cursor.moveToNext()
            }
            cursor.close()
            return events.toTypedArray()
        }
    }
}