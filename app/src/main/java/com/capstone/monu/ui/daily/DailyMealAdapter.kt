package com.capstone.monu.ui.daily

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.databinding.ItemsDailyMealBinding
import com.capstone.monu.ui.detail.DetailFoodActivity
import com.capstone.monu.utils.FOOD_ID

data class DailyMeals(
    val id : String,
    val label : String,
    val image : String?,
    val time : String
)

class DailyMealAdapter :  RecyclerView.Adapter<DailyMealAdapter.MealViewHolder>() {

    private val list = ArrayList<DailyMeals>()

    inner class MealViewHolder(private val binding : ItemsDailyMealBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : DailyMeals) {
            Glide.with(itemView.context)
                .load(item.image)
                .centerCrop()
                .into(binding.imgMeal)

            binding.apply {
                tvMealLabel.text = item.label
                tvMealTime.text = item.time
            }

            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailFoodActivity::class.java)
                i.putExtra(FOOD_ID, item.id)
                itemView.context.startActivity(i)
            }

        }
    }

    fun setData(array : List<FoodEntity>, list : List<String>) {
        val newData = ArrayList<DailyMeals>()
        for (i in array.indices) {
            newData.add(
                DailyMeals(
                    id = array[i].id,
                    label = array[i].label,
                    image = array[i].image,
                    time = list[i]
                )
            )
        }
        this.list.clear()
        this.list.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder =
        MealViewHolder(ItemsDailyMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val items = list[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int = list.size

}
