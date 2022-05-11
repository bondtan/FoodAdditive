package com.tbondarenko.foodadd.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodAdd::class], version = 1, exportSchema = true)
abstract class FoodAddDataBase: RoomDatabase() {

    abstract fun foodAddDao(): FoodAddDao

    companion object{
        @Volatile
        private var INSTANSE: FoodAddDataBase? =null

        fun getDatabase(context: Context): FoodAddDataBase{
            return INSTANSE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodAddDataBase::class.java,
                    "food_add_database"
                )
                    .createFromAsset("databases/e_full.db")
                    .build()
                INSTANSE = instance
                instance
            }
        }
    }
}