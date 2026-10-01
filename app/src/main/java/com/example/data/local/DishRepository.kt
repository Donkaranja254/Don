package com.example.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DishRepository(private val dishDao: DishDao) {

    val allDishes: Flow<List<DishEntity>> = dishDao.getAllDishes()

    fun getDishesByCategory(category: String): Flow<List<DishEntity>> {
        return if (category.equals("ALL", ignoreCase = true)) {
            dishDao.getAllDishes()
        } else {
            dishDao.getDishesByCategory(category)
        }
    }

    fun searchDishes(query: String): Flow<List<DishEntity>> {
        return dishDao.searchDishes(query)
    }

    suspend fun addDish(dish: DishEntity) = withContext(Dispatchers.IO) {
        dishDao.insertDish(dish)
    }

    suspend fun updateDish(dish: DishEntity) = withContext(Dispatchers.IO) {
        dishDao.updateDish(dish)
    }

    suspend fun deleteDish(id: String) = withContext(Dispatchers.IO) {
        dishDao.deleteDishById(id)
    }

    suspend fun checkAndSeedIfEmpty() = withContext(Dispatchers.IO) {
        val count = dishDao.getDishCount()
        if (count == 0) {
            TruceDatabase.seedDefaultDishes(dishDao)
        } else {
            // Ensure dishes have the latest distinct pictures and drawables
            TruceDatabase.seedDefaultDishes(dishDao)
        }
    }
}
