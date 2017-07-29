package com.brettbeatty.roc.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.brettbeatty.roc.R

class NavigationHeaderView : LinearLayout {
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
    var iconNext:Drawable? = null
        set(value) {
            field = value
            next?.setImageDrawable(value)
        }
    var iconPrevious:Drawable? = null
        set(value) {
            field = value
            previous?.setImageDrawable(value)
        }
    private var next:ImageButton? = null
        get() = findViewById(R.id.next)
    var pageTitle:String = ""
        set(value) {
            field = value
            titleView?.text = value
        }
    private var previous:ImageButton? = null
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
        inflate(context, R.layout.navigation_header_view, this)
        // Get custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.NavigationHeaderView, defStyle, 0)
        if (attributes.hasValue(R.styleable.NavigationHeaderView_has_next))
            hasNext = attributes.getBoolean(R.styleable.NavigationHeaderView_has_next, false)
        if (attributes.hasValue(R.styleable.NavigationHeaderView_has_previous))
            hasPrevious = attributes.getBoolean(R.styleable.NavigationHeaderView_has_previous, false)
        if (attributes.hasValue(R.styleable.NavigationHeaderView_icon_next)) {
            val id = attributes.getResourceId(R.styleable.NavigationHeaderView_icon_next, R.mipmap.ic_launcher)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                iconNext = resources.getDrawable(id, context.theme)
            else
                iconNext = resources.getDrawable(id)
        }
        if (attributes.hasValue(R.styleable.NavigationHeaderView_icon_previous)) {
            val id = attributes.getResourceId(R.styleable.NavigationHeaderView_icon_previous, R.mipmap.ic_launcher)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                iconPrevious = resources.getDrawable(id, context.theme)
            else
                iconPrevious = resources.getDrawable(id)
        }
        if (attributes.hasValue(R.styleable.NavigationHeaderView_title))
            pageTitle = attributes.getString(R.styleable.NavigationHeaderView_title)
        attributes.recycle()
    }

}