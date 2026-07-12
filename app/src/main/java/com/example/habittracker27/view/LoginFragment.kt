package com.example.habittracker27.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.habittracker27.databinding.LoginFragmentBinding
import com.example.habittracker27.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.checkLoginStatus()

        binding.btnLogin.setOnClickListener {
            val username = binding.txtName.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.login(username, password)
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = LoginFragmentDirections.actionHabitListFragment()
                requireView().findNavController().navigate(action)
            } else {
                binding.txtError.visibility = View.VISIBLE
            }
        })

        viewModel.activeUserLD.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                val action = LoginFragmentDirections.actionHabitListFragment()
                requireView().findNavController().navigate(action)
            }
        })
    }
}
