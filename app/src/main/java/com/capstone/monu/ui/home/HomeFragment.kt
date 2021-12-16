package com.capstone.monu.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.capstone.monu.R
import com.capstone.monu.databinding.FragmentHomeBinding
import com.capstone.monu.utils.MonuConverter
import com.capstone.monu.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.daily.observe(viewLifecycleOwner) {
            if (it != null) {

                binding.apply {
                    detailTargetCalories.text = resources.getString(R.string.calories_value, it.calories.toInt().toString(), it.targetCalories.toInt().toString())
                    progressCalories.progressMax = it.targetCalories
                    progressCalories.progress = it.calories
                    barProtein.max = it.targetProtein
                    barProtein.progress = it.protein
                    barProtein.progressText = MonuConverter.doubleToFloor(it.protein.toDouble()).toString()
                    barFat.max = it.targetFat
                    barFat.progress = it.fat
                    barFat.progressText = MonuConverter.doubleToFloor(it.fat.toDouble()).toString()
                    barCarbs.max = it.targetCarbs
                    barCarbs.progress = it.carbs
                    barCarbs.progressText = MonuConverter.doubleToFloor(it.carbs.toDouble()).toString()
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}