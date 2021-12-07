package com.capstone.monu.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.monu.R
import com.capstone.monu.databinding.ItemsIngredientBinding

class DishTypeAdapter(private val onclick : (String) -> Unit) : RecyclerView.Adapter<DishTypeAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(val binding : ItemsIngredientBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(ItemsIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val listData = holder.itemView.resources.getStringArray(R.array.dish_type)
        val ingredient = listData[position]
        holder.binding.chipDishType.text = ingredient
        holder.binding.chipDishType.setOnClickListener {
            onclick
        }
    }

    override fun getItemCount(): Int = 10
}