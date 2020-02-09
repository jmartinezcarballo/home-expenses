package com.jmartinezcarballo.homeexpenses.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.codelab.barcode_scanning.R
import com.jmartinezcarballo.homeexpenses.model.Product

class ProductListAdapter (val products: List<Product>) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun getItemCount() = products.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage = view.findViewById<ImageView>(R.id.product_image)
        val productName = view.findViewById<TextView>(R.id.product_name)

        fun bind(product: Product) {
            productName.text = product.name
            productImage.setImageURI(Uri.parse(product.image))
//          Picasso.with(this).load(product.image).into(productImage);
        }
    }

}

