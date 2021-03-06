package com.capstone.monu.ui.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.databinding.ItemsFoodBinding
import com.capstone.monu.utils.MonuConverter

class FoodAdapter(
    private val onClick : (FoodEntity) -> Unit
) : PagedListAdapter<FoodEntity, FoodAdapter.FoodViewHolder>(DIFF_CALLBACK) {

    inner class FoodViewHolder(private val binding : ItemsFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food : FoodEntity) {
            binding.itemFoodLabel.text = food.label
            val calories = itemView.context.getString(R.string.food_calories, MonuConverter.doubleToFloor(food.calories).toString())
            binding.itemCalories.text = calories
            Glide.with(itemView.context)
                .load(food.image)
                .into(binding.itemFoodImg)
            itemView.setOnClickListener {
                onClick(food)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemBinding = ItemsFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        if (food != null) holder.bind(food)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodEntity>() {
            override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}