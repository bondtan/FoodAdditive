package com.tbondarenko.foodadd.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.tbondarenko.foodadd.R
import com.tbondarenko.foodadd.data.FoodAdd
import com.tbondarenko.foodadd.databinding.ItemFoodAddBinding

class FoodAddOrdinaryViewHolder(private val binding: ItemFoodAddBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, foodAdd: FoodAdd) {
        with(binding) {
            foodAddNumberText.text =
                context.getString(R.string.e_number, foodAdd.number.toString())
            foodAddNameText.text = foodAdd.name
            foodAddOriginText.text = foodAdd.origin
        }
    }
}