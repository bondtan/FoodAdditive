package com.tbondarenko.foodadd.viewmodel

import androidx.lifecycle.*
import com.tbondarenko.foodadd.data.DangerNumber
import com.tbondarenko.foodadd.data.FoodAdd
import com.tbondarenko.foodadd.data.NoDangerNumber
import com.tbondarenko.foodadd.repository.FoodAddRepository
import kotlinx.coroutines.launch

class FoodAddViewModel (private val repository: FoodAddRepository) : ViewModel() {

    private val dangerNumber = MutableLiveData(NoDangerNumber)

    val allFoodAdd: LiveData<List<FoodAdd>> = dangerNumber.switchMap { dangerNumber ->
        if (dangerNumber == NoDangerNumber) repository.getFoodAdds()
        else repository.getFoodAddsWithDanger(dangerNumber)
    }

    val favoritesFoodAdd: LiveData<List<FoodAdd>> =
        repository.getFoodAddsFavorites(FAVORITES_ADD)

    init {
        clearDangerNumber()
        repository.getFoodAdds()
    }

    fun setDangerNumber(num: Int) {
        this.dangerNumber.value = DangerNumber(num)
    }

    fun retrieveFoodAdd(id: Int): LiveData<FoodAdd> {
        return repository.getFoodAdd(id)
    }

    fun addFavorites(foodAdd: FoodAdd) {
        val newFoodAdd = foodAdd.copy(favorites = FAVORITES_ADD)
        updateFoodAdd(newFoodAdd)
    }

    fun deleteFavorites(foodAdd: FoodAdd) {
        val newFoodAdd = foodAdd.copy(favorites = FAVORITES_DELETE)
        updateFoodAdd(newFoodAdd)
    }

    private fun updateFoodAdd(foodAdd: FoodAdd) {
        viewModelScope.launch {
            repository.updateFoodAdd(foodAdd)
        }
    }

    private fun clearDangerNumber() {
        dangerNumber.value = NoDangerNumber
    }

    companion object {
        // constants for add or delete items from the favorites list
        const val FAVORITES_ADD = 1
        const val FAVORITES_DELETE = 0
    }
}