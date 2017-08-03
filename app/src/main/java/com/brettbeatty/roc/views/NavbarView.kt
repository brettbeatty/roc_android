package com.brettbeatty.roc.views

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.brettbeatty.roc.R

class NavbarView : LinearLayout {
    var hasNext:Boolean = false
        set(value) {
            field = value
            next?.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }
    var hasPrevious:Boolean = false
        set(value) {
            field = value
            previous?.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }
    var iconNext:String = ""
        set(value) {
            field = value
            next?.text = value
        }
    var iconPrevious:String = ""
        set(value) {
            field = value
            previous?.text = value
        }
    private var next:Button? = null
        get() = findViewById(R.id.next)
    var pageTitle:String = ""
        set(value) {
            field = value
            titleView?.text = value
        }
    private var previous:Button? = null
        get() = findViewById(R.id.previous)
    private var titleView:TextView? = null
        get() = findViewById(R.id.title)


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
        // Inflate view
        inflate(context, R.layout.view_navbar, this)
        val font = ResourcesCompat.getFont(context, R.font.fontawesome_webfont)
        next?.typeface = font
        previous?.typeface = font
        // Get custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.NavbarView, defStyle, 0)
        if (attributes.hasValue(R.styleable.NavbarView_has_next))
            hasNext = attributes.getBoolean(R.styleable.NavbarView_has_next, false)
        if (attributes.hasValue(R.styleable.NavbarView_has_previous))
            hasPrevious = attributes.getBoolean(R.styleable.NavbarView_has_previous, false)
        if (attributes.hasValue(R.styleable.NavbarView_icon_next))
            iconNext = attributes.getString(R.styleable.NavbarView_icon_next)
        if (attributes.hasValue(R.styleable.NavbarView_icon_previous))
            iconPrevious = attributes.getString(R.styleable.NavbarView_icon_previous)
        if (attributes.hasValue(R.styleable.NavbarView_title))
            pageTitle = attributes.getString(R.styleable.NavbarView_title)
        attributes.recycle()
    }

}