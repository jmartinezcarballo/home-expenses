package com.jmartinezcarballo.homeexpenses.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.codelab.barcode_scanning.BarcodeScannerActivity
import com.google.firebase.codelab.barcode_scanning.R
import com.jmartinezcarballo.homeexpenses.adapter.ProductListAdapter
import com.jmartinezcarballo.homeexpenses.model.Product
import com.jmartinezcarballo.homeexpenses.model.ProductDatabase
import kotlinx.android.synthetic.main.activity_home.*

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeActivity : AppCompatActivity() {

    private val newProductActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Init database
        database =  Room.databaseBuilder(this, ProductDatabase::class.java, "products-db").build()

        products = ArrayList()
        getProducts()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ProductListAdapter(products)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        scan_button.setOnClickListener {
            val intent = Intent(this@HomeActivity, BarcodeScannerActivity::class.java)
            startActivityForResult(intent, newProductActivityRequestCode)
        }
    }

    //uiThread: when doAsync finishes do something in main thread
    private fun getProducts() {
        doAsync {
            products = HomeActivity.database.productDao().findAll()
            uiThread {
                setUpRecyclerView(products)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newProductActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(BarcodeScannerActivity.BARCODE)?.let {
                val product = Product(1, it, "image")
//                productViewModel.insert(productData)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "ERROR",
                Toast.LENGTH_LONG).show()
        }
    }

    fun setUpRecyclerView(products: List<Product>) {
//        adapter = ProductListAdapter(products)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductListAdapter(products)
    }

    companion object {
        lateinit var database: ProductDatabase
        lateinit var recyclerView: RecyclerView
        lateinit var products: MutableList<Product>
    }
}
