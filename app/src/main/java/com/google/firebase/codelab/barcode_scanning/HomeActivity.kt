package com.google.firebase.codelab.barcode_scanning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmartinezcarballo.homeexpenses.adapter.ProductListAdapter
import com.jmartinezcarballo.homeexpenses.model.DatabaseProduct
import com.jmartinezcarballo.homeexpenses.model.Product
import com.jmartinezcarballo.homeexpenses.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private val newProductActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ProductListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        productViewModel.allProducts.observe(this, Observer { products ->
            // Update the cached copy of the words in the adapter.
            products?.let { adapter.setProducts(it) }
        })

        scan_button.setOnClickListener {
            val intent = Intent(this@HomeActivity, BarcodeScannerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newProductActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(BarcodeScannerActivity.EXTRA_REPLY)?.let {
                val product = DatabaseProduct(1, it, "image")
                productViewModel.insert(product)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}