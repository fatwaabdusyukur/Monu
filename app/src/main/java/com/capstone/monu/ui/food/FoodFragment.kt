package com.capstone.monu.ui.food

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.monu.databinding.FragmentFoodBinding
import com.capstone.monu.ui.detail.DetailFoodActivity
import com.capstone.monu.utils.FOOD_ID
import com.capstone.monu.utils.ViewModelFactory
import com.capstone.monu.utils.adapter.DishTypeAdapter
import com.capstone.monu.utils.vo.Status

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
        val adapter = FoodAdapter { food ->
            val i = Intent(context, DetailFoodActivity::class.java)
            i.putExtra(FOOD_ID, food.id)
            startActivity(i)
        }

        with(binding.rvDish) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = DishTypeAdapter {
                viewModel.setDish(it)
            }
        }

        viewModel.getFoods()?.observe(viewLifecycleOwner) {
            with(binding.rvFood) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }
            if (it != null) {
                when(it.status) {
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        showLoading(false)
                        adapter.submitList(it.data)
                        adapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading : Boolean) {
        binding.apply {
            loading.visibility = if (isLoading) View.VISIBLE else View.GONE
            textBrandFood.visibility = if (isLoading) View.GONE else View.VISIBLE
            foodSearch.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}