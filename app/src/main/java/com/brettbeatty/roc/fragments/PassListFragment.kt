package com.brettbeatty.roc.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brettbeatty.roc.R

class PassListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_pass_list, container, false)
    }

    companion object {
        fun newInstance(): PassListFragment {
            val fragment = PassListFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }
}
