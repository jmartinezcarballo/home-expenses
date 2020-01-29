package com.jmartinezcarballo.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DatabaseProductDao {

    @Query("SELECT * FROM DatabaseProduct WHERE barcode = :barcode")
    fun findByBarcode(barcode: String) : LiveData<DatabaseProduct>

    @Query("SELECT * FROM DatabaseProduct")
    fun findAll() : LiveData<List<DatabaseProduct>>

    @Insert
    fun add(databaseProduct: DatabaseProduct)
}