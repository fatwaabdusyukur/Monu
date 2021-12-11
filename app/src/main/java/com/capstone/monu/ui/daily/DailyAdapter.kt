package com.capstone.monu.ui.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.databinding.ItemDailyScheduleBinding
import com.capstone.monu.utils.DayName

class DailyAdapter(private val onclick : (DailyEntity) -> Unit) : PagedListAdapter<DailyEntity, DailyAdapter.DailyViewHolder>(DIFF_CALLBACK) {

    inner class DailyViewHolder(private val binding : ItemDailyScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(daily : DailyEntity) {
            val date = daily.date.split(",").toTypedArray()
            binding.itemDailyDay.text = DayName.getByTag(date[0])
            binding.itemDailyDate.text = date[1]
            itemView.setOnClickListener {
                onclick(daily)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(ItemDailyScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val daily = getItem(position)
        if (daily != null) holder.bind(daily)
//        when(daily?.date?.split(",")!!.toTypedArray()[0]) {
//            "Mon" -> DayName.MONDAY
//            "Tue" -> DayName.MONDAY
//            "Wed" -> DayName.MONDAY
//            "Thu" -> DayName.MONDAY
//            "Fri" -> DayName.MONDAY
//            "Sat" -> DayName.MONDAY
//            "Sun" -> DayName.MONDAY
//        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DailyEntity>() {
            override fun areItemsTheSame(oldItem: DailyEntity, newItem: DailyEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DailyEntity, newItem: DailyEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}