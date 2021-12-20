package com.capstone.monu.ui.daily

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.monu.R
import com.capstone.monu.databinding.DailyCalendarDayBinding
import com.capstone.monu.databinding.FragmentDailyBinding
import com.capstone.monu.ui.dialog.AddDailyFragment
import com.capstone.monu.ui.dialog.AddFoodFragment
import com.capstone.monu.utils.MonuConverter
import com.capstone.monu.utils.TAG_DAILY
import com.capstone.monu.utils.ViewModelFactory
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class DailyFragment : Fragment() {

    private var _binding : FragmentDailyBinding? = null
    private val binding get() = _binding!!

    private var selectedDate = LocalDate.now()
    private lateinit var viewModel: DailyViewModel
    private lateinit var date : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val factory = ViewModelFactory.getInstance(requireActivity())
        val adapter = DailyMealAdapter()
        viewModel = ViewModelProvider(this, factory)[DailyViewModel::class.java]

        val currentMonth = YearMonth.now()
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.dailyCalendar.apply {
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = Size(dayWidth, dayHeight)
            setup(currentMonth, currentMonth.plusMonths(3), DayOfWeek.values().random())
            scrollToDate(LocalDate.now())
        }

        date = SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(
            Calendar.getInstance().time).toString()

        binding.fabAddDaily.setOnClickListener {
            val fragment = AddDailyFragment()
            fragment.show(parentFragmentManager, "ADD DAILY")
        }

        viewModel.getDailyByDate().observe(viewLifecycleOwner) { daily ->
            if (daily != null) {
                binding.detailDaily.detailTargetCalories.text = resources.getString(R.string.calories_value, daily.calories.toInt().toString(), daily.targetCalories.toInt().toString())

                binding.detailDaily.progressCalories.apply {
                    this.progressMax = daily.targetCalories
                    this.progress =   daily.calories
                }

                binding.detailDaily.cardNutrients.apply {
                    showProperty(false)
                    barProtein.max = daily.targetProtein
                    barFat.max = daily.targetProtein
                    barCarbs.max = daily.targetCarbs

                    barProtein.progress = daily.protein
                    barFat.progress = daily.fat
                    barCarbs.progress = daily.carbs

                    dailyProtein.text = resources.getString(R.string.daily_nut, MonuConverter.doubleToFloor(daily.protein.toDouble()).toString(), MonuConverter.doubleToFloor(daily.targetProtein.toDouble()).toString())
                    dailyFat.text = resources.getString(R.string.daily_nut, MonuConverter.doubleToFloor(daily.fat.toDouble()).toString(), MonuConverter.doubleToFloor(daily.targetFat.toDouble()).toString())
                    dailyCarbs.text = resources.getString(R.string.daily_nut, MonuConverter.doubleToFloor(daily.carbs.toDouble()).toString(), MonuConverter.doubleToFloor(daily.targetCarbs.toDouble()).toString())
                }

                val list = MonuConverter.changeToList(daily.food)

                viewModel.getDailyMeals(list).observe(viewLifecycleOwner) {
                    if (it != null) {
                        with(binding.detailDaily.rvDailyMeals) {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(context)
                            this.adapter = adapter
                        }

                        val times = MonuConverter.changeToList(daily.eatTime)
                        adapter.setData(it, times)
                        adapter.notifyDataSetChanged()
                    }
                }

                binding.detailDaily.btnAddFood.setOnClickListener {
                    val fragment = AddFoodFragment(daily)
                    fragment.show(parentFragmentManager, TAG_DAILY)
                }

            } else showProperty(true)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = DailyCalendarDayBinding.bind(view)
            lateinit var day : CalendarDay

            init {

                view.setOnClickListener {
                    val firstDay = binding.dailyCalendar.findFirstVisibleDay()
                    val lastDay = binding.dailyCalendar.findLastVisibleDay()
                    if (day == firstDay) binding.dailyCalendar.smoothScrollToDate(day.date)
                    else if (day == lastDay) binding.dailyCalendar.smoothScrollToDate(day.date.minusDays(4))

                    selectDate(day.date)

                }

            }

            fun bind(day : CalendarDay) {
                this.day = day
                bind.dateText.text = DateTimeFormatter.ofPattern("dd").format(day.date)
                bind.monthText.text = DateTimeFormatter.ofPattern("MMM").format(day.date)
                bind.dayText.text = DateTimeFormatter.ofPattern("EEE").format(day.date)

                bind.exSevenSelectedView.isVisible = day.date == selectedDate
            }

        }

        binding.dailyCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }


    }

    fun selectDate(date : LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.dailyCalendar.notifyDateChanged(it) }
            binding.dailyCalendar.notifyDateChanged(date)
            viewModel.setDate(date.toString())
            this.date = date.toString()
        }
    }

    private fun showProperty(isVisible : Boolean) {
        binding.fabAddDaily.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.detailDaily.dailyContainer.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}