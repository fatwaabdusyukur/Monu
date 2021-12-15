package com.capstone.monu.ui.daily

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.monu.R
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.databinding.AddFoodFragmentBinding
import com.capstone.monu.ui.food.FoodAdapter
import com.capstone.monu.utils.ViewModelFactory
import com.capstone.monu.utils.vo.Status
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class AddFoodFragment(private val data : DailyEntity) : DialogFragment() {

    private var _binding : AddFoodFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddFoodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[DailyViewModel::class.java]


        var hour = binding.hour.value.toString()
        var minute = binding.minute.value.toString()

        binding.foodSearch.setOnEditorActionListener { tv, action, _ ->
            return@setOnEditorActionListener when(action) {
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

        binding.hour.setOnValueChangedListener { _, _, newVal ->
           hour = String.format(Locale.US, "%d", newVal)
        }

        binding.minute.setOnValueChangedListener { _, _, newVal ->
            minute = String.format(Locale.US, "%d", newVal)
        }

        val adapter = FoodAdapter {
            val time = hour.plus(":").plus(minute)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Adding Food")
                .setMessage("Did you eat ${it.label} at $time ?")
                .setPositiveButton("Add") { d , _ ->
                    viewModel.setDailyFood(it, data, time)
                    d.dismiss()
                }
                .setNegativeButton("Cancel") { d, _ ->
                    d.cancel()
                }
                .show()
        }

        viewModel.foods.observe(viewLifecycleOwner) { foods ->
            with(binding.rvFoodDialog) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }

            when(foods.status) {
                Status.LOADING -> showLoading(true)
                Status.SUCCESS -> {
                    showLoading(false)
                    adapter.submitList(foods.data)
                    adapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showLoading(isLoading : Boolean) {
        binding.apply {
            foodSearch.visibility = if (isLoading) View.GONE else View.VISIBLE
            loading.visibility = if (isLoading) View.VISIBLE else View.GONE
            hour.visibility = if (isLoading) View.GONE else View.VISIBLE
            minute.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}