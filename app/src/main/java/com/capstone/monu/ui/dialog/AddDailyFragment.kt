package com.capstone.monu.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.monu.R
import com.capstone.monu.databinding.AddDailyFragmentBinding
import com.capstone.monu.utils.ViewModelFactory

class AddDailyFragment : DialogFragment() {

    private var _binding : AddDailyFragmentBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddDailyFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[DialogViewModel::class.java]
        var day = 1

        binding.day.setOnValueChangedListener { _, _, newVal ->
            day = newVal
        }

        binding.btnAddDailySchedule.setOnClickListener {

            var gender = ""
            var activity = ""

            if (binding.weight.text.isEmpty()) {
                binding.weight.error = "Weight is Required!"
                binding.weight.requestFocus()
                return@setOnClickListener
            }

            if (binding.height.text.isEmpty()) {
                binding.height.error = "Height is Required!"
                binding.height.requestFocus()
                return@setOnClickListener
            }

            if (binding.age.text.isEmpty()) {
                binding.age.error = "Age is Required!"
                binding.age.requestFocus()
                return@setOnClickListener
            }

            if (binding.rgGender.checkedRadioButtonId > 0) {
                when (binding.rgGender.checkedRadioButtonId) {
                    R.id.male -> gender = "Male"
                    R.id.female -> gender = "Female"
                    else -> return@setOnClickListener
                }
            }

            if (binding.rgActivity.checkedRadioButtonId > 0) {
                when (binding.rgActivity.checkedRadioButtonId) {
                    R.id.light_activity -> activity = "Lightly Activity"
                    R.id.moderate_activity -> activity = "Moderate Activity"
                    R.id.heavy_activity -> activity = "Heavy Activity"
                    else -> return@setOnClickListener
                }
            }

            val weight = binding.weight.text
            val height = binding.height.text
            val age = binding.age.text

            viewModel.addDaily(
                day,
                weight.toString().toInt(),
                height.toString().toInt(),
                age.toString().toInt(),
                gender,
                activity
            )

            findNavController().run {
                popBackStack()
                navigate(R.id.navigation_daily)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}