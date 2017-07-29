package com.brettbeatty.roc.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.brettbeatty.roc.R
import com.brettbeatty.roc.activities.MainActivity

class PassListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_pass_list, container, false)
        view.findViewById<Button>(R.id.next).setOnClickListener {
            (activity as MainActivity).pager!!.currentItem++
        }
        return view
    }

    companion object {
        val TAG = PassListFragment::class.java.simpleName

        fun newInstance(): PassListFragment {
            val fragment = PassListFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }
}
