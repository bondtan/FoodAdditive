package com.tbondarenko.foodadd.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_add")
data class FoodAdd(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(name = "e_number") val number: Int,
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "type") val type: String,
    @NonNull @ColumnInfo(name = "danger") val danger: Int,
    @NonNull @ColumnInfo(name = "origin") val origin: String,
    @NonNull @ColumnInfo(name = "description") val description: String,
    @NonNull @ColumnInfo(name = "benefit") val benefitToHealth: String,
    @NonNull @ColumnInfo(name = "harm") val harmToHealth: String,
    @NonNull @ColumnInfo(name = "use_country") val useCountry: String,
    @NonNull @ColumnInfo(name = "food") val food: String,
    @NonNull @ColumnInfo(name = "favorites") val favorites: Int
)

@JvmInline
value class DangerNumber(val number: Int)
val NoDangerNumber = DangerNumber(-1)