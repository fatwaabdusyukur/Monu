package com.capstone.monu.ui.daily

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.monu.R
import com.capstone.monu.databinding.FragmentDailyBinding
import com.capstone.monu.ui.detail.DetailDailyActivity
import com.capstone.monu.utils.DAILY_ID
import com.capstone.monu.utils.PREF_DAILY_KEY
import com.capstone.monu.utils.PREF_DATE_KEY
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class DailyFragment : Fragment() {

    private var _binding : FragmentDailyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DailyAdapter {
            val i = Intent(context, DetailDailyActivity::class.java)
            i.putExtra(DAILY_ID, it.id)
            startActivity(i)
        }
        val factory = DailyViewModelFactory.createFactory(requireActivity())
        val sharedPref = requireActivity().getSharedPreferences(PREF_DAILY_KEY, Context.MODE_PRIVATE)
        val viewModel = ViewModelProvider(this, factory)[DailyViewModel::class.java]

        with(binding.rvDaily) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.fabAdd.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            dialog.setContentView(R.layout.dialog_add_daily)
            val editText = dialog.findViewById<TextInputEditText>(R.id.add_target_calories)
            val btnAdd = dialog.findViewById<Button>(R.id.btn_add_daily_schedule)
            val btnCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)
            btnAdd?.setOnClickListener {
                val targetCalories = editText?.text.toString()
                viewModel.addDailyMeals(targetCalories = targetCalories.toFloat())
                sharedPref.edit().putString(
                    PREF_DATE_KEY, SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault()).format(
                        Calendar.getInstance().time).toString()).apply()
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

        viewModel.getAllDailySchedule().observe(viewLifecycleOwner) {
            binding.rvDaily.adapter = adapter
            adapter.submitList(it)
        }

        viewModel.isAvailable().observe(viewLifecycleOwner) {
            updateCardAdd(it)
        }

    }

    private fun updateCardAdd(isAvailable : Boolean) {
        binding.fabAdd.isEnabled = isAvailable
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}