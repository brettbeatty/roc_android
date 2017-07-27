package com.brettbeatty.roc.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brettbeatty.roc.R

class ScheduleFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_schedule, container, false)
    }

    companion object {
        fun newInstance(): ScheduleFragment {
            val fragment = ScheduleFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }
}
