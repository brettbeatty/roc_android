package com.brettbeatty.roc.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.brettbeatty.roc.R

class EditPassActivity : AppCompatActivity() {
    val cancel: Button by lazy { findViewById<Button>(R.id.cancel) }
    val delete: Button by lazy { findViewById<Button>(R.id.delete_pass) }
    val passName: EditText by lazy { findViewById<EditText>(R.id.pass_name) }
    val update: Button by lazy { findViewById<Button>(R.id.save_pass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pass)

        cancel.setOnClickListener {
            finish()
        }
        delete.setOnClickListener {
            setResult(DELETE, intent)
            finish()
        }
        passName.setText(intent.getStringExtra(ALIAS))
        update.setOnClickListener {
            intent.putExtra(ALIAS, passName.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        val ALIAS = "alias"
        val BARCODE = "barcode"
        val DELETE = 1
        val EDIT_PASS = 1
        val ID = "id"
        val TAG: String = EditPassActivity::class.java.simpleName
    }
}
