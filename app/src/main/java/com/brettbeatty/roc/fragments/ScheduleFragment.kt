package com.brettbeatty.roc.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView

import com.brettbeatty.roc.R
import com.brettbeatty.roc.activities.MainActivity
import com.brettbeatty.roc.apis.ScheduleAPI
import com.brettbeatty.roc.schemas.Event
import com.brettbeatty.roc.utilities.DatabaseConnection
import com.brettbeatty.roc.views.EventView

class ScheduleFragment : Fragment() {
    private val connection: DatabaseConnection by lazy { DatabaseConnection(context) }
    private val container: LinearLayout by lazy { view!!.findViewById<LinearLayout>(R.id.container) }

    private fun cacheSchedule() {
        ScheduleAPI().lookupSchedule {
            for (event in it) {
                connection.events.addEvent(event.id, event.opponent.id, event.sport.id, event.start, event.venue.id)
                connection.opponents.addOpponent(event.opponent.id, event.opponent.name, event.opponent.mascot)
                connection.sports.addSport(event.sport.id, event.sport.name)
                connection.venues.addVenue(event.venue.id, event.venue.name, event.venue.latitude, event.venue.longitude, event.venue.address, event.venue.city, event.venue.state, event.venue.zip_code, event.venue.photo_key)
            }
            view!!.findViewById<SwipeRefreshLayout>(R.id.refresh_layout).isRefreshing = false
            displayEvents()
        }
    }

    private fun displayEvents() {
        container.removeAllViews()
        connection.events.listEvents().map {
            val eventView = EventView(context)
            eventView.opponent = it.opponent.name
            eventView.sport = it.sport.name
            eventView.eventID = it.id.toString()
            eventView.isAtHome = true
            eventView.location = it.venue.name
            eventView.startTime = it.start
            eventView.station = "unknown"
            eventView
        }.groupBy {
            it.date
        }.forEach {
            val dateView = TextView(context)
            val horizontalMargin = resources.getDimension(R.dimen.schedule_fragment_date_horizontal_margin).toInt()
            val verticalMargin = resources.getDimension(R.dimen.schedule_fragment_date_vertical_margin).toInt()
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(horizontalMargin, verticalMargin, 0, verticalMargin)
            dateView.setTextAppearance(context, android.R.style.TextAppearance_Small_Inverse)
            dateView.text = it.key
            dateView.layoutParams = layoutParams
            container.addView(dateView)
            it.value.forEach {
                container.addView(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_schedule, container, false)
        view.findViewById<Button>(R.id.previous).setOnClickListener {
            (activity as MainActivity).pager.currentItem--
        }
        view.findViewById<Button>(R.id.next).setOnClickListener {
            (activity as MainActivity).pager.currentItem++
        }
        view.findViewById<SwipeRefreshLayout>(R.id.refresh_layout).setOnRefreshListener {
            cacheSchedule()
        }
        return view
    }

    companion object {
        val TAG:String = ScheduleFragment::class.java.simpleName

        fun newInstance(): ScheduleFragment {
            val fragment = ScheduleFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }
}
