package com.iti.myapplicationbnv.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.presentation.recipe.recipes.RecipeActivity
import com.iti.myapplicationbnv.data.data.sharedpref.sharedpreferences
import com.iti.myapplicationbnv.data.local.AppDatabase
import com.iti.myapplicationbnv.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val bind get() = binding!!

    private lateinit var sessionManager: sharedpreferences

    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        sessionManager = sharedpreferences(requireContext())
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime < 2000) {
                    backToast.cancel()
                    requireActivity().finish()
                } else {
                    backToast = Toast.makeText(requireContext(), "Click again to exit", Toast.LENGTH_SHORT)
                    backToast.show()
                    backPressedTime = currentTime
                }
            }
        })

        bind.loginButton.setOnClickListener {
            val email = bind.emailEditText.text.toString().trim()
            val password = bind.passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check user in database
            lifecycleScope.launch {
                val userDao = AppDatabase.Companion.getInstance(requireContext()).userDao()
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserByEmailAndPassword(email, password)
                }

                if (user != null) {
                    sessionManager.setLoggedIn(true)
                    sessionManager.saveUser(user.fullName, user.email, user.password) // حفظ بيانات المستخدم

                    val intent = Intent(requireContext(), RecipeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                }

            }
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