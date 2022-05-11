package com.tbondarenko.foodadd

import android.app.Application
import com.tbondarenko.foodadd.data.FoodAddDataBase
import com.tbondarenko.foodadd.repository.FoodAddRepository

class Application: Application() {

    private val dataBase: FoodAddDataBase by lazy {FoodAddDataBase.getDatabase(this)}
    val repository: FoodAddRepository by lazy {FoodAddRepository(dataBase.foodAddDao())}

}