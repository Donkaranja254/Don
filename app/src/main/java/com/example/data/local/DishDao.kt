package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Query("SELECT * FROM dishes ORDER BY isPopular DESC, name ASC")
    fun getAllDishes(): Flow<List<DishEntity>>

    @Query("SELECT * FROM dishes WHERE category = :category ORDER BY isPopular DESC, name ASC")
    fun getDishesByCategory(category: String): Flow<List<DishEntity>>

    @Query("SELECT * FROM dishes WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY isPopular DESC, name ASC")
    fun searchDishes(query: String): Flow<List<DishEntity>>

    @Query("SELECT * FROM dishes WHERE id = :id LIMIT 1")
    suspend fun getDishById(id: String): DishEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<DishEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishEntity)

    @Update
    suspend fun updateDish(dish: DishEntity)

    @Query("DELETE FROM dishes WHERE id = :id")
    suspend fun deleteDishById(id: String)

    @Query("SELECT COUNT(*) FROM dishes")
    suspend fun getDishCount(): Int
}
