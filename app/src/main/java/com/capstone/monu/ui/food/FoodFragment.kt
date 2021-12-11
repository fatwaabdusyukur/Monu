package com.capstone.monu.ui.food

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.monu.R
import com.capstone.monu.databinding.FragmentFoodBinding
import com.capstone.monu.ui.detail.DetailFoodActivity
import com.capstone.monu.utils.FOOD_ID
import com.capstone.monu.utils.ViewModelFactory
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
        val foodAdapter = FoodAdapter { food ->
            val i = Intent(context, DetailFoodActivity::class.java)
            i.putExtra(FOOD_ID, food.id)
            startActivity(i)
        }


        viewModel.food.observe(viewLifecycleOwner) { foods ->
            with(binding.rvFood) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = foodAdapter
            }
            when(foods.status) {
                Status.LOADING -> showLoading(true)
                Status.SUCCESS -> {
                    showLoading(false)
                    foodAdapter.submitList(foods.data)
                    foodAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.foodSearch.setOnEditorActionListener { tv, id, _ ->
            return@setOnEditorActionListener when(id) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    tv.clearFocus()
                    tv.clearComposingText()
                    val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(tv.windowToken, 0)
                    viewModel.setFood(tv.text.toString())
                    tv.text = ""
                    true
                }
                else -> false
            }
        }

    }

    private fun showLoading(isLoading : Boolean) {
        binding.apply {
            loading.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (!isLoading) Glide.with(this@FoodFragment).load(R.drawable.healthy_food_banner).into(imgFoodBanner)
            textFood.visibility = if (isLoading) View.GONE else View.VISIBLE
            foodSearch.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}