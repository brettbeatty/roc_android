package com.brettbeatty.roc.views

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.brettbeatty.roc.R
import com.brettbeatty.roc.activities.EditPassActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class PassView: LinearLayout {
    var alias: String = ""
        set(value) {
            field = value
            aliasView.text = value
        }
    val aliasView: TextView by lazy { findViewById<TextView>(R.id.alias) }
    var barcode: String = ""
        set(value) {
            field = value
            barcodeValueView.text = value
            BarcodeDrawingTask { barcodeView.setImageBitmap(it) }.execute(value)
        }
    val barcodeValueView: TextView by lazy { findViewById<TextView>(R.id.barcode_value) }
    val barcodeView: ImageView by lazy { findViewById<ImageView>(R.id.barcode) }
    var barcodeID: Long = 0
    val edit: Button by lazy { findViewById<Button>(R.id.edit) }
    var editPassActivityLauncher: ((Intent, Int) -> Unit)? = null

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
        inflate(context, R.layout.view_pass, this)
        // Prepare edit button
        edit.typeface = ResourcesCompat.getFont(context, R.font.fontawesome_webfont)
        edit.setOnClickListener {
            val intent = Intent(context, EditPassActivity::class.java)
                    .putExtra(EditPassActivity.ALIAS, alias)
                    .putExtra(EditPassActivity.BARCODE, barcode)
                    .putExtra(EditPassActivity.ID, barcodeID)
            editPassActivityLauncher?.invoke(intent, EditPassActivity.EDIT_PASS)
        }
        // Get custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PassView, defStyle, 0)
        if (attributes.hasValue(R.styleable.PassView_alias))
            alias = attributes.getString(R.styleable.PassView_alias)
        if (attributes.hasValue(R.styleable.PassView_barcode))
            barcode = attributes.getString(R.styleable.PassView_barcode)
        attributes.recycle()
    }

    companion object {
        val TAG: String = PassView::class.java.simpleName
    }

    class BarcodeDrawingTask(val callback: (Bitmap) -> Unit): AsyncTask<String, Unit, Bitmap>() {

        override fun doInBackground(vararg args: String): Bitmap {

            val barcode = args.first()
            val matrix = MultiFormatWriter().encode(barcode, BarcodeFormat.CODE_39, 800, 120)
            val image = Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.ARGB_8888)

            for (x in 0..matrix.width - 1) for (y in 0..matrix.height-1)
                image.setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
            return image
        }

        override fun onPostExecute(result: Bitmap) {

            callback(result)
        }
    }
}
