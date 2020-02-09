package com.jmartinezcarballo.homeexpenses.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE barcode = :barcode")
    fun findByBarcode(barcode: String) : Product

    @Query("SELECT * FROM products")
    fun findAll() : MutableList<Product>

    @Insert
    fun add(product: Product)
}