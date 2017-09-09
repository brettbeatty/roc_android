package com.brettbeatty.roc.utilities

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import com.brettbeatty.roc.schemas.Event
import com.brettbeatty.roc.schemas.Opponent
import com.brettbeatty.roc.schemas.Sport
import com.brettbeatty.roc.schemas.Venue

class DatabaseConnection(context: Context): SQLiteOpenHelper(context, DATABASE, null, SCHEMA_VERSION) {
    private val databaseLoader = DatabaseLoader().execute(this)
    val database: SQLiteDatabase by lazy { databaseLoader.get() }
    val events = Event.Schema(this)
    val opponents = Opponent.Schema(this)
    val sports = Sport.Schema(this)
    val venues = Venue.Schema(this)

    companion object {
        val DATABASE = "roc.db"
        val SCHEMA_VERSION = 1
        val TAG: String = DatabaseConnection::class.java.simpleName
    }

    override fun onCreate(database: SQLiteDatabase) {

        database.execSQL("CREATE TABLE IF NOT EXISTS passes (barcode TEXT PRIMARY KEY NOT NULL, alias TEXT);")
        database.execSQL("CREATE TABLE IF NOT EXISTS events (id INTEGER PRIMARY KEY, opponent INTEGER, sport INTEGER, start TEXT, venue INTEGER);")
        database.execSQL("CREATE TABLE IF NOT EXISTS opponents (id INTEGER PRIMARY KEY, name TEXT, mascot TEXT, logo_key TEXT);")
        database.execSQL("CREATE TABLE IF NOT EXISTS sports (id INTEGER PRIMARY KEY, name TEXT);")
        database.execSQL("CREATE TABLE IF NOT EXISTS venues (id INTEGER PRIMARY KEY, name TEXT, latitude TEXT, longitude TEXT, address TEXT, city TEXT, state TEXT, zip TEXT, photo_key TEXT);")
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class DatabaseLoader: AsyncTask<DatabaseConnection, Unit, SQLiteDatabase>() {

        override fun doInBackground(vararg args: DatabaseConnection): SQLiteDatabase = args.first().writableDatabase
    }
}