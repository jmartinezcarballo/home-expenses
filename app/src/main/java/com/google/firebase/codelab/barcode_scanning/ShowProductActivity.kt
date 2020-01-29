package com.google.firebase.codelab.barcode_scanning

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.net.URL
import android.os.StrictMode
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.xml.sax.Parser


class ShowProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val barcode = intent.getStringExtra("barcode")

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val result = URL("https://world.openfoodfacts.org/api/v0/product/" + barcode).readText()


        Toast.makeText(this, result, Toast.LENGTH_LONG)
    }
}