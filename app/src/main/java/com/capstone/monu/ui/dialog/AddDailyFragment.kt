package com.capstone.monu.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.monu.R
import com.capstone.monu.databinding.AddDailyFragmentBinding
import com.capstone.monu.ui.daily.DailyFragment

class AddDailyFragment : DialogFragment() {

    private var _binding : AddDailyFragmentBinding? = null
    private val binding get() = _binding!!
    private var dialogListener : OnDialogListener? = null

    interface OnDialogListener {
        fun addDaily(weight : Int, height : Int, age : Int, gender : String, activity : String, day : Int)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddDailyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            val weight = binding.weight.text.toString()
            val height = binding.height.text.toString()
            val age = binding.age.text.toString()

            dialogListener?.addDaily(weight.toInt(), height.toInt(), age.toInt(), gender, activity, day)
            dialog?.dismiss()

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

        if (fragment is DailyFragment) {
            this.dialogListener = fragment.onDailyDialogListener
        }

    }

    override fun onDetach() {
        super.onDetach()
        this.dialogListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}