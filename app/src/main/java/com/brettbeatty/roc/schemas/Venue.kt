package com.brettbeatty.roc.schemas

import android.database.sqlite.SQLiteDatabase
import com.brettbeatty.roc.utilities.DatabaseConnection

data class Venue(var id: Long, var name: String, var latitude: String, var longitude: String, var address: String, var city: String, var state: String, var zip_code: String, var photo_key: String) {

    companion object {
        val TAG: String = Pass::class.java.simpleName
    }

    class Schema(connection: DatabaseConnection) {
        private val database: SQLiteDatabase by lazy { connection.database }

        fun addVenue(id: Long, name: String, latitude: String, longitude: String, address: String, city: String, state: String, zip: String, photoKey: String): Long {
            val statement = database.compileStatement("INSERT OR IGNORE INTO venues (id, name, latitude, longitude, address, city, state, zip, photo_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")

            statement.bindLong(1, id)
            statement.bindString(2, name)
            statement.bindString(3, latitude)
            statement.bindString(4, longitude)
            statement.bindString(5, address)
            statement.bindString(6, city)
            statement.bindString(7, state)
            statement.bindString(8, zip)
            statement.bindString(9, photoKey)
            return statement.executeInsert()
        }

        fun deleteVenue(id: Long): Int {
            val statement = database.compileStatement("DELETE FROM venues WHERE id=?;")

            statement.bindLong(1, id)
            return statement.executeUpdateDelete()
        }

        fun listVenues(): Array<Venue> {
            val cursor = database.rawQuery("SELECT * FROM venues;", arrayOf<String>())
            val addressColumn = cursor.getColumnIndexOrThrow("address")
            val cityColumn = cursor.getColumnIndexOrThrow("city")
            val idColumn = cursor.getColumnIndexOrThrow("id")
            val latitudeColumn = cursor.getColumnIndexOrThrow("latitude")
            val longitudeColumn = cursor.getColumnIndexOrThrow("longitude")
            val nameColumn = cursor.getColumnIndexOrThrow("name")
            val photoKeyColumn = cursor.getColumnIndexOrThrow("photo_key")
            val stateColumn = cursor.getColumnIndexOrThrow("state")
            val zipColumn = cursor.getColumnIndexOrThrow("zip")
            val venues = arrayListOf<Venue>()

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val address = cursor.getString(addressColumn)
                val city = cursor.getString(cityColumn)
                val id = cursor.getLong(idColumn)
                val latitude = cursor.getString(latitudeColumn)
                val longitude = cursor.getString(longitudeColumn)
                val name = cursor.getString(nameColumn)
                val photoKey = cursor.getString(photoKeyColumn)
                val state = cursor.getString(stateColumn)
                val zip = cursor.getString(zipColumn)
                val venue = Venue(id, name, latitude, longitude, address, city, state, zip, photoKey)

                venues.add(venue)
                cursor.moveToNext()
            }
            cursor.close()
            return venues.toTypedArray()
        }
    }
}