package com.capstone.monu.ui.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.monu.databinding.FragmentDailyBinding

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
        val adapter = DailyAdapter()
        val factory = DailyViewModelFactory.createFactory(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[DailyViewModel::class.java]

        with(binding.rvDaily) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.fabAdd.setOnClickListener {
            val fragment = AddDailyDialogFragment()
            fragment.show(childFragmentManager, AddDailyDialogFragment::class.java.simpleName)
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
        binding.fabAdd.visibility = if (isAvailable) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}