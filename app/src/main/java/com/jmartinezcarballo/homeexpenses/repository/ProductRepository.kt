package com.jmartinezcarballo.homeexpenses.repository

import androidx.lifecycle.LiveData
import com.jmartinezcarballo.homeexpenses.model.Product
import com.jmartinezcarballo.homeexpenses.model.ProductDao

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: List<Product> = productDao.findAll()

    suspend fun insert(product: Product) {
        productDao.add(product)
    }
}