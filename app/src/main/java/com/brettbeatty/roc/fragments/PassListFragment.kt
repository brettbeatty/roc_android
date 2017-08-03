package com.brettbeatty.roc.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

import com.brettbeatty.roc.R
import com.brettbeatty.roc.activities.MainActivity
import com.brettbeatty.roc.schemas.Pass
import com.brettbeatty.roc.views.PassView
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator

class PassListFragment : Fragment() {
    val passSchema: Pass.Schema by lazy { Pass.Schema(context) }

    fun createPass(barcode: String) {
        val id = passSchema.addPass("New Pass", barcode)
        val passView = PassView(context)
        passView.alias = "New Pass"
        passView.barcode = barcode
        passView.barcodeID = id
        view!!.findViewById<LinearLayout>(R.id.container).addView(passView)
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
        val passContainer: LinearLayout = view.findViewById(R.id.container)
        view.findViewById<Button>(R.id.next).setOnClickListener {
            (activity as MainActivity).pager!!.currentItem++
        }
        addAnotherPass.typeface = font
        addAnotherPass.setOnClickListener {
            val scanner = IntentIntegrator.forSupportFragment(this)
            scanner.setDesiredBarcodeFormats(listOf(BarcodeFormat.CODE_39.name))
            scanner.initiateScan()
        }
        for (pass in passSchema.listPasses()) {
            val passView = PassView(context)
            passView.alias = pass.alias
            passView.barcode = pass.barcode
            passView.barcodeID = pass.id
            passContainer.addView(passView)
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
