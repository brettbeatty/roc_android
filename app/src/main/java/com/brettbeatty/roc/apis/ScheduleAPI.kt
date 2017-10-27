package com.brettbeatty.roc.apis

import android.os.AsyncTask
import com.brettbeatty.roc.schemas.Event
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class ScheduleAPI {

    fun lookupSchedule(callback: (List<Event>) -> Unit): Unit {
        LookupTask(callback).execute()
    }

    class ResponseWrapper(val data: List<Event>)

    class LookupTask(private val callback: (List<Event>) -> Unit): AsyncTask<Unit, Unit, List<Event>>() {

        companion object {
            val SCHEDULE_URL = "https://rocpass.com/schedule.json"
        }

        override fun doInBackground(vararg args: Unit): List<Event> {
            val gson = Gson()
            val raw = InputStreamReader(URL(SCHEDULE_URL).openConnection().getInputStream())
            val response = gson.fromJson(raw, ResponseWrapper::class.java)

            return response.data
        }

        override fun onPostExecute(result: List<Event>) {

            super.onPostExecute(result)
            callback(result)
        }
    }
}