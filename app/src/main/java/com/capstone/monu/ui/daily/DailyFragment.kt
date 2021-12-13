package com.capstone.monu.ui.daily

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.monu.R
import com.capstone.monu.databinding.DailyCalendarDayBinding
import com.capstone.monu.databinding.FragmentDailyBinding
import com.capstone.monu.utils.MonuConverter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DailyFragment : Fragment() {

    private var _binding : FragmentDailyBinding? = null
    private val binding get() = _binding!!

    private var selectedDate = LocalDate.now()
    private lateinit var viewModel: DailyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val factory = DailyViewModelFactory.createFactory(requireActivity())
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
                    barFat.max = daily.fat
                    barCarbs.max = daily.carbs

                    barProtein.progress = daily.protein
                    barFat.progress = daily.fat
                    barCarbs.progress = daily.carbs

                    dailyProtein.text = resources.getString(R.string.daily_nut, MonuConverter.doubleToFloor(daily.protein.toDouble()).toString(), MonuConverter.doubleToFloor(daily.targetProtein.toDouble()).toString())
                    dailyFat.text = resources.getString(R.string.daily_nut, MonuConverter.doubleToFloor(daily.fat.toDouble()).toString(), MonuConverter.doubleToFloor(daily.targetFat.toDouble()).toString())
                    dailyCarbs.text = resources.getString(R.string.daily_nut, MonuConverter.doubleToFloor(daily.carbs.toDouble()).toString(), MonuConverter.doubleToFloor(daily.targetCarbs.toDouble()).toString())
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

            binding.fabAddDaily.setOnClickListener {
                val dialog = BottomSheetDialog(requireActivity())
                dialog.setContentView(R.layout.dialog_add_daily)
                val editText = dialog.findViewById<TextInputEditText>(R.id.add_target_calories)
                val btnAdd = dialog.findViewById<Button>(R.id.btn_add_daily_schedule)
                val btnCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)
                btnAdd?.setOnClickListener {
                    val targetCalories = editText?.text.toString()
                    viewModel.addDailyMeals(date = date.toString(),targetCalories = targetCalories.toFloat())
                    dialog.dismiss()
                    findNavController().run {
                        popBackStack()
                        navigate(R.id.navigation_daily)
                    }
                }

                btnCancel?.setOnClickListener {
                    dialog.cancel()
                }
                dialog.show()
            }

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