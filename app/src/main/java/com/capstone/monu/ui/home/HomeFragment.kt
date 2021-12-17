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
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.ArrayList

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

        val colorChart = ArrayList<Int>()

        colorChart.add(resources.getColor(R.color.crimson))
        colorChart.add(resources.getColor(R.color.colorPrimaryVariant))
        colorChart.add(resources.getColor(R.color.colorSecondary))
        colorChart.add(resources.getColor(R.color.macha))


        viewModel.daily.observe(viewLifecycleOwner) {
            if (it != null) {

                binding.apply {
                    detailTargetCalories.text = resources.getString(R.string.calories_value, it.calories.toInt().toString(), it.targetCalories.toInt().toString())
                    progressCalories.progressMax = it.targetCalories
                    progressCalories.progress = it.calories
                    barProtein.max = it.targetProtein
                    barProtein.progress = it.protein
                    barProtein.progressText = if (it.protein == 0F) "0" else MonuConverter.doubleToFloor(it.protein.toDouble()).toString()
                    barFat.max = it.targetFat
                    barFat.progress = it.fat
                    barFat.progressText = if (it.fat == 0F) "0" else MonuConverter.doubleToFloor(it.fat.toDouble()).toString()
                    barCarbs.max = it.targetCarbs
                    barCarbs.progress = it.carbs
                    barCarbs.progressText = if (it.carbs == 0F) "0" else MonuConverter.doubleToFloor(it.carbs.toDouble()).toString()

                    val entries = ArrayList<PieEntry>()
                    entries.add(PieEntry(it.protein * 4, "Protein"))
                    entries.add(PieEntry(it.fat * 9, "Fat"))
                    entries.add(PieEntry(it.carbs * 4, "Carbs"))
                    entries.add(PieEntry(it.calories, "Other"))

                    val dataSet = PieDataSet(entries, "Nutrition Percent")
                    dataSet.colors = colorChart
                    val data = PieData(dataSet)

                    with(data) {
                        setValueFormatter(PercentFormatter())
                        setValueTextSize(14F)
                        setValueTextColor(resources.getColor(R.color.white))
                    }

                    binding.pieChart.apply {
                        if (it.calories != 0F) visibility = View.VISIBLE
                        description.isEnabled = false
                        setCenterTextSize(19F)
                        centerText = "Macro Nutrition"
                        setEntryLabelColor(resources.getColor(R.color.white))
                        setEntryLabelTextSize(17F)
                        animateXY(1000, 1000)
                        setUsePercentValues(true)
                        this.data = data
                        invalidate()
                    }
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}