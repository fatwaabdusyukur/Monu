package com.capstone.monu.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.databinding.ActivityDetailDailyBinding
import com.capstone.monu.ui.daily.AddFoodFragment
import com.capstone.monu.utils.DAILY_ID
import com.capstone.monu.utils.ViewModelFactory

class DetailDailyActivity : AppCompatActivity() {

    private var _binding : ActivityDetailDailyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailDailyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val id = intent.getIntExtra(DAILY_ID, 0)
        viewModel.getDaily(id).observe(this, Observer(this::showDaily))

    }

    private fun showDaily(daily : DailyEntity) {

        binding.cardCalories.apply {
            detailTargetCalories.text = resources.getString(R.string.calories_value, daily.calories.toString(), daily.targetCalories.toString())
            progressCalories.progressMax = daily.targetCalories
            progressCalories.progress = daily.calories
        }

        binding.cardNutrients.apply {
            barProtein.max = daily.targetProtein
            barFat.max = daily.fat
            barCarbs.max = daily.carbs

            barProtein.progress = daily.protein
            barFat.progress = daily.fat
            barCarbs.progress = daily.carbs

            dailyProtein.text = resources.getString(R.string.daily_nut, daily.protein.toString(), daily.targetProtein.toString())
            dailyFat.text = resources.getString(R.string.daily_nut, daily.fat.toString(), daily.targetFat.toString())
            dailyCarbs.text = resources.getString(R.string.daily_nut, daily.carbs.toString(), daily.targetCarbs.toString())

        }

        binding.btnAddFood.setOnClickListener {
            val fragment = AddFoodFragment(daily)
            fragment.show(supportFragmentManager, AddFoodFragment::class.java.simpleName)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}