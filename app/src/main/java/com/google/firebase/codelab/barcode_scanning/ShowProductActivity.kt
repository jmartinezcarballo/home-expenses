package com.google.firebase.codelab.barcode_scanning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import android.os.StrictMode
import android.net.Uri
import androidx.room.Room
import com.google.gson.Gson
import com.jmartinezcarballo.model.AppDatabase.AppDatabase
import com.jmartinezcarballo.model.ProductResponse
import kotlinx.android.synthetic.main.activity_product.*


class ShowProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val barcode = intent.getStringExtra("barcode")

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val result = URL("https://world.openfoodfacts.org/api/v0/product/" + barcode).readText()

        val productResponse: ProductResponse = Gson().fromJson(result, ProductResponse::class.java)

        if(productResponse.status == 1) {
            product_name.text = productResponse.product.product_name
            product_image.setImageURI(Uri.parse(productResponse.product.image_front_url))


            //TODO: database insert
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()

        } else {
            Toast.makeText(this, "The product does not exist", Toast.LENGTH_SHORT)
        }
    }
}