package com.brettbeatty.roc.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.brettbeatty.roc.R
import com.brettbeatty.roc.activities.MainActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator

class PassListFragment : Fragment() {

    fun createPass(barcode: String) {
        Toast.makeText(context, "Scanned: $barcode", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                ?: return super.onActivityResult(requestCode, resultCode, data)
        if (result.contents != null) createPass(result.contents)
        else Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val font = ResourcesCompat.getFont(context, R.font.fontawesome_webfont)
        val view = inflater!!.inflate(R.layout.fragment_pass_list, container, false)
        val addAnotherPass = view.findViewById<Button>(R.id.add_another_pass)
        view.findViewById<Button>(R.id.next).setOnClickListener {
            (activity as MainActivity).pager!!.currentItem++
        }
        addAnotherPass.typeface = font
        addAnotherPass.setOnClickListener {
            val scanner = IntentIntegrator.forSupportFragment(this)
            scanner.setDesiredBarcodeFormats(listOf(BarcodeFormat.CODE_39.name))
            scanner.initiateScan()
        }
        return view
    }

    companion object {
        val TAG: String = PassListFragment::class.java.simpleName

        fun newInstance(): PassListFragment {
            val fragment = PassListFragment()
            val arguments = Bundle()
            fragment.arguments = arguments
            return fragment
        }
    }
}
