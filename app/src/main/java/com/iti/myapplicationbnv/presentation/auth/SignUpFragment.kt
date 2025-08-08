package com.iti.myapplicationbnv.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.presentation.recipe.recipes.RecipeActivity
import com.iti.myapplicationbnv.data.data.local.UserEntity
import com.iti.myapplicationbnv.data.data.sharedpref.sharedpreferences
import com.iti.myapplicationbnv.data.local.AppDatabase
import com.iti.myapplicationbnv.databinding.FragmentSignupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment() {
    private var binding: FragmentSignupBinding? = null
    private val bind get() = binding!!

    private lateinit var sessionManager: sharedpreferences  // إضافة متغير sessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        sessionManager = sharedpreferences(requireContext())  // تهيئة sessionManager
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDao = AppDatabase.Companion.getInstance(requireContext()).userDao()

        bind.signupButton.setOnClickListener {
            val name = bind.usernameEditText.text.toString().trim()
            val email = bind.emailEditText.text.toString().trim()
            val password = bind.passwordEditText.text.toString().trim()
            val confirmPassword = bind.confirmPasswordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val existingUser = userDao.getUserByEmail(email)
                    if (existingUser != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Email already registered",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val newUser =
                            UserEntity(fullName = name, email = email, password = password)
                        userDao.insertUser(newUser)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Account created!", Toast.LENGTH_SHORT)
                                .show()
                            // حفظ حالة تسجيل الدخول:
                            sessionManager.setLoggedIn(true)
                            sessionManager.saveUser(name, email, password)

                            val intent = Intent(requireContext(), RecipeActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                }
            }
        }

        bind.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }
}