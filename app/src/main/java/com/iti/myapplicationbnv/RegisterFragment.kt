package com.iti.myapplicationbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.databinding.FragmentRegisterBinding
import com.iti.myapplicationbnv.databinding.FragmentSpalshBinding

class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding?= null
    private val bind get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_signupFragment)
        }

        bind.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}