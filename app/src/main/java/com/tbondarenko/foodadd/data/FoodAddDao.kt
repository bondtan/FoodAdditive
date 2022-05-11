package com.tbondarenko.foodadd.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodAddDao {

    @Query(value = "SELECT * FROM food_add WHERE id = :id")
    fun getFoodAdd (id: Int): LiveData<FoodAdd>

    @Query(value = "SELECT * FROM food_add ORDER BY e_number")
    fun getFoodAdds(): LiveData<List<FoodAdd>>

    @Query(value = "SELECT * FROM food_add WHERE danger = :dangerNumber ORDER BY e_number")
    fun getFoodAddsWithDanger (dangerNumber: Int): LiveData<List<FoodAdd>>

    @Query(value = "SELECT * FROM food_add WHERE favorites = :favorites ORDER BY e_number")
    fun getFoodAddFavorites (favorites: Int): LiveData<List<FoodAdd>>

    @Update
    suspend fun updateFoodAdd (foodAdd: FoodAdd)

}