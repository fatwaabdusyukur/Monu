package com.capstone.monu.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.databinding.AddFoodFragmentBinding
import com.capstone.monu.utils.TimePickerFragment

class AddFoodFragment(private val data : DailyEntity) : DialogFragment() {

    private var _binding : AddFoodFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        _binding = AddFoodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = DailyViewModelFactory.createFactory(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[DailyViewModel::class.java]

        with(binding.rvFoodDialog) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.btnTimepicker.setOnClickListener {
            val timePicker = TimePickerFragment()
            timePicker.show(childFragmentManager, TAG_TIME)
        }

    }

    companion object {
        private const val TAG_TIME = "time1"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}