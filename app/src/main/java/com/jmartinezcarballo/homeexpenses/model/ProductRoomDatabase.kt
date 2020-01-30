package com.jmartinezcarballo.homeexpenses.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductRoomDatabase {

    @Database(entities = arrayOf(DatabaseProduct::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun databaseProductDao(): DatabaseProductDao
    }

    private class ProductDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.databaseProductDao())
                }
            }
        }

        suspend fun populateDatabase(productDao: DatabaseProductDao) {
            // Delete all content here.
//            productDao.deleteAll()

            // Add sample words.
            var product = DatabaseProduct(1, "Product 1", "image1")
            productDao.add(product)
            product = DatabaseProduct(2, "Product 2", "image2")
            productDao.add(product)


        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "products"
                )
                .addCallback(ProductDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}