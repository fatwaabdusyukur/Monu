package com.capstone.monu.ui.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.monu.R
import com.capstone.monu.databinding.FragmentFoodBinding
import com.capstone.monu.utils.ViewModelFactory

class FoodFragment : Fragment() {

    private var _binding : FragmentFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[FoodViewModel::class.java]
        val adapter = FoodAdapter()
        viewModel.getFoods().observe(viewLifecycleOwner) {
            with(binding.rvFood) {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2)
                this.adapter = adapter
            }
            adapter.submitList(it.data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}