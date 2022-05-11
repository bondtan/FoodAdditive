package com.tbondarenko.foodadd.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tbondarenko.foodadd.repository.FoodAddRepository

class FoodAddViewModelFactory (private val repository: FoodAddRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodAddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}