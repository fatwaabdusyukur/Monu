package com.capstone.monu.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.databinding.ActivityDetailFoodBinding
import com.capstone.monu.utils.*
import com.capstone.monu.utils.adapter.IngredientAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import java.util.*

class DetailFoodActivity : AppCompatActivity() {

    private var _binding : ActivityDetailFoodBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapterIngredient : IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterIngredient = IngredientAdapter()
        with(binding.rvIngredient) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailFoodActivity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapterIngredient
        }
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val id = intent.getStringExtra(FOOD_ID)
        viewModel.getFood(id!!).observe(this, Observer(this::showDetailFood))
    }

    private fun showDetailFood(food : FoodEntity) {
        with(binding) {
            tvDetailFoodName.text = food.label
            tvDetailCalories.text = resources.getString(R.string.nutrients, MonuConverter.doubleToFloor(food.calories).toString())
            tvDetailFat.text = resources.getString(R.string.nutrients, MonuConverter.doubleToFloor(food.fat).toString())
            tvDetailProtein.text = resources.getString(R.string.nutrients, MonuConverter.doubleToFloor(food.protein).toString())
            tvDetailCarbo.text = resources.getString(R.string.nutrients, MonuConverter.doubleToFloor(food.carbohydrate).toString())

            Glide.with(this@DetailFoodActivity)
                .load(food.image)
                .centerCrop()
                .circleCrop()
                .into(imgDetailFood)

            tvDetailIngredientLines.text = MonuConverter.toLines(food.ingredientLines)
            adapterIngredient.notifyDataSetChanged()
            adapterIngredient.setData(MonuConverter.toList(food.ingredientImage))
            btnAddFoodDaily.setOnClickListener { showAddDialog(food) }
        }
    }

    private fun showAddDialog(food: FoodEntity) {
        val pref = this.getSharedPreferences(PREF_DAILY_KEY, Context.MODE_PRIVATE)
        val date = pref.getString(PREF_DATE_KEY, "")
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.dialog_add_food)
        val hour = dialog.findViewById<EditText>(R.id.et_eat_hour)?.text.toString()
        val minute = dialog.findViewById<EditText>(R.id.et_eat_minute)?.text.toString()
        val chip = dialog.findViewById<Chip>(R.id.chip_food)
        chip?.text = food.label
        dialog.findViewById<Button>(R.id.btn_add_food)?.setOnClickListener {
            val eatTime = hour.plus(":").plus(minute)
            if (date != null) viewModel.addDailyMeal(
                food = food.id,
                eatTime = eatTime,
                date = date,
                calories = food.calories.toInt()
            )
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_dialog_cancel)?.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}