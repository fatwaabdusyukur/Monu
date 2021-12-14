package com.capstone.monu.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.databinding.ActivityDetailFoodBinding
import com.capstone.monu.utils.*
import java.util.*

class DetailFoodActivity : AppCompatActivity() {

    private var _binding : ActivityDetailFoodBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val id = intent.getStringExtra(FOOD_ID)
        viewModel.getFood(id!!).observe(this, Observer(this::showDetailFood))
    }

    private fun showDetailFood(food : FoodEntity) {

        Glide.with(this@DetailFoodActivity)
            .load(food.image)
            .into(binding.imgDetailFood)

        with(binding.cardFood) {
            tvDetailFoodName.text = food.label
            barProtein.max = if (food.protein < 10) 10F else 100F
            barCarbs.max = if (food.carbohydrate < 10) 10F else 100F
            barFat.max = if (food.fat < 10) 10F else 100F

            barProtein.progress = food.protein.toFloat()
            barFat.progress = food.fat.toFloat()
            barCarbs.progress = food.carbohydrate.toFloat()

            detailCalories.text = resources.getString(R.string.detail_calories, MonuConverter.doubleToFloor(food.calories).toString())

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}