package com.google.firebase.codelab.barcode_scanning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        scan_button.setOnClickListener {
            val intent = Intent(this@HomeActivity, BarcodeScannerActivity::class.java)
            startActivity(intent)
        }
    }
}