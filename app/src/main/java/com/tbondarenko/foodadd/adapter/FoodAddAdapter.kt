package com.tbondarenko.foodadd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbondarenko.foodadd.adapter.viewholder.FoodAddOrdinaryViewHolder
import com.tbondarenko.foodadd.adapter.viewholder.FoodAddToxicViewHolder
import com.tbondarenko.foodadd.data.FoodAdd
import com.tbondarenko.foodadd.databinding.ItemFoodAddBinding
import com.tbondarenko.foodadd.databinding.ItemToxicFoodAddBinding
import java.util.*
import kotlin.collections.ArrayList

class FoodAddAdapter(private val onItemClicked: (FoodAdd) -> Unit) :
    ListAdapter<FoodAdd, RecyclerView.ViewHolder>(DiffCallBack),
    Filterable {

    private var foodAddList: ArrayList<FoodAdd> = ArrayList()
    private var foodAddListFiltered: ArrayList<FoodAdd> = ArrayList()

    fun addData(list: List<FoodAdd>) {
        foodAddList = list as ArrayList<FoodAdd>
        foodAddListFiltered = foodAddList
        submitList(foodAddListFiltered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create view holder with its current danger type
        val viewHolder = when (viewType) {
            TYPE_ORDINARY -> {
                FoodAddOrdinaryViewHolder(
                    ItemFoodAddBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            TYPE_TOXIC -> {
                FoodAddToxicViewHolder(
                    ItemToxicFoodAddBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid type")
        }
        // get detail position
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FoodAddOrdinaryViewHolder -> holder.bind(holder.itemView.context, getItem(position))
            is FoodAddToxicViewHolder -> holder.bind(holder.itemView.context, getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).danger) {
            4, 5 -> TYPE_TOXIC
            else -> TYPE_ORDINARY
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint?.toString() ?: ""
                foodAddListFiltered = if (charSearch.isEmpty()) {
                    foodAddList
                } else {
                    val resultList = ArrayList<FoodAdd>()
                    foodAddList.filter {
                        val mNumber = "E${it.number}"
                        val mName = it.name
                        (mNumber.lowercase(Locale.getDefault()).contains(constraint!!, true)) or
                                (it.origin.lowercase(Locale.getDefault()).contains(constraint, true)) or
                                (mName.lowercase(Locale.getDefault()).contains(constraint,true))
                    }
                        .forEach { resultList.add(it) }
                    resultList
                }
                return FilterResults().apply { values = foodAddListFiltered }
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                foodAddListFiltered = if (results?.values == null)
                    foodAddList
                else
                    results.values as ArrayList<FoodAdd>
                submitList(foodAddListFiltered)
            }
        }
    }

    companion object {
        // types view holders:
        private const val TYPE_ORDINARY = 0
        private const val TYPE_TOXIC = 1

        private val DiffCallBack = object : DiffUtil.ItemCallback<FoodAdd>() {
            override fun areItemsTheSame(oldItem: FoodAdd, newItem: FoodAdd): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodAdd, newItem: FoodAdd): Boolean {
                return oldItem == newItem
            }
        }
    }
}