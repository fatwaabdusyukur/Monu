package com.capstone.monu.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.databinding.ActivityDetailFoodBinding
import com.capstone.monu.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*

class DetailFoodActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private var _binding : ActivityDetailFoodBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private lateinit var eatTime : TextView

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

            btnAddFood.setOnClickListener { showAddDialog(food) }
        }
    }

    private fun showAddDialog(food: FoodEntity) {
        val pref = applicationContext.getSharedPreferences(PREF_DAILY_KEY, Context.MODE_PRIVATE)
        val date = pref.getString(PREF_DATE_KEY, "")
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.dialog_add_food)
        val chip = dialog.findViewById<Chip>(R.id.chip_food)
        eatTime = dialog.findViewById(R.id.tv_eat_time)!!
        chip?.text = food.label

        dialog.findViewById<ImageButton>(R.id.btn_timepicker)?.setOnClickListener {
            val timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, TAG_TIME)
        }

        dialog.findViewById<Button>(R.id.btn_add_food)?.setOnClickListener {
            date?.let {
                viewModel.addDailyMeal(
                    food = food,
                    eatTime = eatTime.text.toString(),
                    date = it,
                )
            }
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_dialog_cancel)?.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        eatTime.text = dateFormat.format(calendar.time)
    }

    companion object {
        private const val TAG_TIME = "timepicker"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}