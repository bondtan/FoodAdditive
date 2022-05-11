package com.tbondarenko.foodadd.repository

import com.tbondarenko.foodadd.data.DangerNumber
import com.tbondarenko.foodadd.data.FoodAdd
import com.tbondarenko.foodadd.data.FoodAddDao

class FoodAddRepository(private val foodAddDao: FoodAddDao) {

    fun getFoodAdd (id: Int) =
        foodAddDao.getFoodAdd(id)

    fun getFoodAdds() =
        foodAddDao.getFoodAdds()

    fun getFoodAddsWithDanger (dangerNumber: DangerNumber) =
        foodAddDao.getFoodAddsWithDanger(dangerNumber.number)

    fun getFoodAddsFavorites (favorites: Int) =
        foodAddDao.getFoodAddFavorites(favorites)

    suspend fun updateFoodAdd (foodAdd: FoodAdd) =
        foodAddDao.updateFoodAdd(foodAdd)
}