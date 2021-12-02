package com.capstone.monu.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.monu.databinding.ItemsIngredientBinding

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private val listData = ArrayList<String>()

    fun setData(newData : List<String>) {
        if (listData.isNotEmpty())  {
            listData.clear()
        }
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    class IngredientViewHolder(private val binding : ItemsIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image : String) {
            Glide.with(itemView.context)
                .load(image)
                .into(binding.itemIngredientImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(ItemsIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = listData[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int = listData.size
}