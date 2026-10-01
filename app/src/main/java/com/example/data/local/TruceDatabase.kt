package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.TruceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [DishEntity::class], version = 2, exportSchema = false)
abstract class TruceDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile
        private var INSTANCE: TruceDatabase? = null

        fun getInstance(context: Context): TruceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TruceDatabase::class.java,
                    "truce_lounge_menu.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Pre-seed local database with dishes
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = getInstance(context).dishDao()
                                seedDefaultDishes(dao)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun seedDefaultDishes(dao: DishDao) {
            val entities = TruceRepository.menuItems.map { item ->
                DishEntity(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    priceKes = item.priceKes,
                    category = item.category.name,
                    tag = item.tag,
                    isPopular = item.isPopular,
                    portionInfo = item.portionInfo,
                    drawableRes = item.drawableRes,
                    rating = item.rating,
                    isAvailable = true
                )
            }
            dao.insertDishes(entities)
        }
    }
}
