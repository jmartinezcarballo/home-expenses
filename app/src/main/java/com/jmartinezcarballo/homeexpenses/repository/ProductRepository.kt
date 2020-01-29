package com.jmartinezcarballo.homeexpenses.repository

import androidx.lifecycle.LiveData
import com.jmartinezcarballo.model.DatabaseProduct
import com.jmartinezcarballo.model.DatabaseProductDao

class ProductRepository(private val productDao: DatabaseProductDao) {

    val allProducts: LiveData<List<DatabaseProduct>> = productDao.findAll()

    suspend fun insert(databaseProduct: DatabaseProduct) {
        productDao.add(databaseProduct)
    }
}