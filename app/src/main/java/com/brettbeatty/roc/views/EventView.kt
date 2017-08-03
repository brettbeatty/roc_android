package com.brettbeatty.roc.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

import com.brettbeatty.roc.R

class EventView : LinearLayout {
    var eventID: String = ""
    var isAtHome: Boolean = false
        set(value) {
            field = value
            updateSportAndOpponent()
            updateTimeAndLocation()
        }
    var location: String = ""
        set(value) {
            field = value
            if (isAtHome) updateTimeAndLocation()
        }
    var opponent: String = ""
        set(value) {
            field = value
            updateSportAndOpponent()
        }
    var sport: String = ""
        set(value) {
            field = value
            updateSportAndOpponent()
        }
    private var sportAndOpponent: TextView? = null
        get() = findViewById(R.id.sport_and_opponent)
    var startTime: String = ""
        set(value) {
            field = value
            updateTimeAndLocation()
        }
    var station: String = ""
        set(value) {
            field = value
            if (!isAtHome) updateTimeAndLocation()
        }
    private var timeAndLocation: TextView? = null
        get() = findViewById(R.id.time_and_location)

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Get custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.EventView, defStyle, 0)
        if (attributes.hasValue(R.styleable.EventView_event_id))
            eventID = attributes.getString(R.styleable.EventView_event_id)
        if (attributes.hasValue(R.styleable.EventView_is_at_home))
            isAtHome = attributes.getBoolean(R.styleable.EventView_is_at_home, false)
        if (attributes.hasValue(R.styleable.EventView_location))
            location = attributes.getString(R.styleable.EventView_location)
        if (attributes.hasValue(R.styleable.EventView_opponent))
            opponent = attributes.getString(R.styleable.EventView_opponent)
        if (attributes.hasValue(R.styleable.EventView_sport))
            sport = attributes.getString(R.styleable.EventView_sport)
        if (attributes.hasValue(R.styleable.EventView_start_time))
            startTime = attributes.getString(R.styleable.EventView_start_time)
        if (attributes.hasValue(R.styleable.EventView_station))
            station = attributes.getString(R.styleable.EventView_station)
        attributes.recycle()
        // Inflate view
        inflate(context, R.layout.view_event, this)
        updateSportAndOpponent()
        updateTimeAndLocation()
    }

    private fun updateSportAndOpponent() {
        sportAndOpponent?.text = if (isAtHome) "$sport vs $opponent" else "$sport @ $opponent"
    }

    private fun updateTimeAndLocation() {
        timeAndLocation?.text = if (isAtHome) "$startTime at $location" else "$startTime on $station"
    }
}
