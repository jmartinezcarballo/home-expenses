package com.jmartinezcarballo.homeexpenses.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Product::class), version = 1) abstract class ProductDatabase : RoomDatabase() {
        abstract fun productDao(): ProductDao
    }

    private class ProductCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.productDao())
                }
            }
        }

        suspend fun populateDatabase(productDao: ProductDao) {
            // Delete all content here.
//            productDao.deleteAll()

            // Add sample words.
            var product = Product(1, "ProductData 1", "image1")
            productDao.add(product)
            product = Product(2, "ProductData 2", "image2")
            productDao.add(product)


        }

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: ProductDatabase? = null

            fun getDatabase(context: Context,
                            scope: CoroutineScope): ProductDatabase {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "products"
                    )
                        .addCallback(ProductCallback(scope))
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }