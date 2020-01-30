package com.jmartinezcarballo.homeexpenses.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.codelab.barcode_scanning.R
import com.jmartinezcarballo.homeexpenses.model.DatabaseProduct

class ProductListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var products = emptyList<DatabaseProduct>() // Cached copy of products

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = products[position]
        holder.productItemView.text = current.name
    }

    internal fun setProducts(productsDao: List<DatabaseProduct>) {
        this.products = productsDao
        notifyDataSetChanged()
    }

    override fun getItemCount() = products.size
}