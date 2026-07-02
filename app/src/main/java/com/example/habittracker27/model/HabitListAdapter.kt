package com.example.habittracker27.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker27.R
import com.example.habittracker27.databinding.HabitCardItemBinding
import com.example.habittracker27.view.HabitListFragmentDirections
import com.example.habittracker27.viewmodel.ListViewModel

class HabitListAdapter(val habitList: ArrayList<Habit>, val viewModel: ListViewModel) :
    RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    class HabitViewHolder(val binding: HabitCardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitCardItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.binding.txtNameHabit.text = habit.name
        holder.binding.tvDesc.text = habit.description
        holder.binding.txtProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"
        holder.binding.progressBar.max = habit.goal
        holder.binding.progressBar.progress = habit.progress

        if (habit.progress >= habit.goal) {
            holder.binding.txtStatus.text = "Completed"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_status_compleate)
            holder.binding.btnPlus.isEnabled = false
            holder.binding.btnMinus.isEnabled = false
            holder.binding.imgVerified.visibility = android.view.View.VISIBLE

        } else {
            holder.binding.txtStatus.text = "In Progress"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_status)
            holder.binding.btnPlus.isEnabled = true
            holder.binding.btnMinus.isEnabled = true
            holder.binding.imgVerified.visibility = android.view.View.INVISIBLE
        }
        holder.binding.imgIcon.setImageResource(habit.icon)

        holder.binding.btnPlus.setOnClickListener {
            viewModel.increaseProgress(habit)
        }

        holder.binding.btnMinus.setOnClickListener {
            viewModel.decreaseProgress(habit)
        }

        holder.binding.root.setOnClickListener {
            if (habit.progress < habit.goal) {
                val action = HabitListFragmentDirections.actionHabitEditFragment(habit.id)
                it.findNavController().navigate(action)
            } else {
                android.widget.Toast.makeText(
                    it.context,
                    "Habit is already completed and cannot be edited",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount() = habitList.size

    fun updateHabitList(newList: List<Habit>) {
        habitList.clear()
        habitList.addAll(newList)
        notifyDataSetChanged()
    }


}