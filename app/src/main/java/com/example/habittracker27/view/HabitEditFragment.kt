package com.example.habittracker27.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker27.R
import com.example.habittracker27.databinding.FragmentHabitEditBinding
import com.example.habittracker27.viewmodel.DetailHabitViewModel

class HabitEditFragment : Fragment() {
    private lateinit var binding: FragmentHabitEditBinding
    private lateinit var viewModel: DetailHabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailHabitViewModel::class.java]

        val habitId = HabitEditFragmentArgs.fromBundle(requireArguments()).habitId
        viewModel.fetch(habitId)

        setupSpinner()
        observeViewModel()

        binding.toolbarEdit.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSubmit.setOnClickListener {
            onButtonSubmitClick()
        }
    }

    private fun setupSpinner() {
        val iconList = listOf(
            "Water",
            "Walking",
            "Sleep",
            "Reading",
            "Workout",
            "Meditation",
            "Study",
            "Food")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            iconList)
        binding.spinnerIcon.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner) { habit ->
            binding.habit = habit

            val iconName = getIconName(habit.icon)
            val iconList = listOf(
                "Water",
                "Walking",
                "Sleep",
                "Reading",
                "Workout",
                "Meditation",
                "Study",
                "Food")
            val index = iconList.indexOf(iconName)
            if (index >= 0) {
                binding.spinnerIcon.setSelection(index)
            }
        }
    }

    private fun onButtonSubmitClick() {
        val habit = binding.habit
        if (habit != null) {
            val goalStr = binding.txtGoal.text.toString()
            val newGoal = goalStr.toIntOrNull() ?: habit.goal

            if (newGoal <= 0) {
                Toast.makeText(context, "Goal must be greater than 0", Toast.LENGTH_SHORT).show()
                return
            }

            if (newGoal < habit.progress) {
                Toast.makeText(
                    context,
                    "Goal cannot be less than current progress (${habit.progress})",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            habit.goal = newGoal
            
            val selectedIconName = binding.spinnerIcon.selectedItem.toString()
            habit.icon = getIconResource(selectedIconName)

            viewModel.updateHabit(habit)
            Toast.makeText(context,
                "Habit updated successfully",
                Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun getIconResource(iconKey: String): Int {
        return when (iconKey) {
            "Water" -> R.drawable.baseline_water_drop_24
            "Walking" -> R.drawable.baseline_directions_walk_24
            "Sleep" -> R.drawable.baseline_bed_24
            "Reading" -> R.drawable.baseline_menu_book_24
            "Workout" -> R.drawable.baseline_fitness_center_24
            "Meditation" -> R.drawable.baseline_self_improvement_24
            "Study" -> R.drawable.baseline_school_24
            "Food" -> R.drawable.baseline_restaurant_24
            else -> R.drawable.ic_launcher_background
        }
    }

    private fun getIconName(resourceId: Int): String {
        return when (resourceId) {
            R.drawable.baseline_water_drop_24 -> "Water"
            R.drawable.baseline_directions_walk_24 -> "Walking"
            R.drawable.baseline_bed_24 -> "Sleep"
            R.drawable.baseline_menu_book_24 -> "Reading"
            R.drawable.baseline_fitness_center_24 -> "Workout"
            R.drawable.baseline_self_improvement_24 -> "Meditation"
            R.drawable.baseline_school_24 -> "Study"
            R.drawable.baseline_restaurant_24 -> "Food"
            else -> "Water"
        }
    }
}
