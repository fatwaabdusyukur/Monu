package com.capstone.monu.ui.daily

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.monu.R
import com.capstone.monu.databinding.FragmentAddDailyDialogBinding
import com.capstone.monu.utils.PREF_DAILY_KEY
import com.capstone.monu.utils.PREF_DATE_KEY
import java.text.SimpleDateFormat
import java.util.*

class AddDailyDialogFragment : DialogFragment() {

    private var _binding : FragmentAddDailyDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDailyDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences(PREF_DAILY_KEY, Context.MODE_PRIVATE)
        val factory = DailyViewModelFactory.createFactory(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[DailyViewModel::class.java]

        binding.btnAddDailySchedule.setOnClickListener {
            val targetCalories = binding.addTargetCalories.text.toString()
            viewModel.addDailyMeals(targetCalories = targetCalories.toInt())
            sharedPref.edit().putString(
                PREF_DATE_KEY, SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault()).format(
                Calendar.getInstance().time)).apply()
            dialog?.dismiss()
            findNavController().run {
                popBackStack()
                navigate(R.id.navigation_daily)
            }
        }

        binding.btnDialogCancel.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}