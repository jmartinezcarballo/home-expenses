package com.jmartinezcarballo.homeexpenses.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jmartinezcarballo.homeexpenses.repository.ProductRepository
import com.jmartinezcarballo.model.AppDatabase
import com.jmartinezcarballo.model.DatabaseProduct
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository

    val allProducts: LiveData<List<DatabaseProduct>>

    init {
        val productDao = AppDatabase.getDatabase(application).databaseProductDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
    }

    fun insert(databaseProduct: DatabaseProduct) = viewModelScope.launch {
        repository.insert(databaseProduct)
    }

}