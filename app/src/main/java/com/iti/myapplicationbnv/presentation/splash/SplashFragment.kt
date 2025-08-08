package com.iti.myapplicationbnv.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.presentation.recipe.recipes.RecipeActivity
import com.iti.myapplicationbnv.data.data.sharedpref.sharedpreferences
import com.iti.myapplicationbnv.databinding.FragmentSpalshBinding

class SplashFragment : Fragment() {

    private var binding: FragmentSpalshBinding? = null
    private val binds get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpalshBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shared = sharedpreferences(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            if (shared.isLoggedIn()) {
                val intent = Intent(requireContext(), RecipeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else if (shared.isFirstTime()) {
                shared.setFirstTime(false)
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 3000)

        animateDots(view)
    }

    private fun animateDots(view: View) {
        val dots = listOf(R.id.dot1, R.id.dot2, R.id.dot3).map { view.findViewById<View>(it) }

        val anim = AlphaAnimation(1f, 0.3f).apply {
            duration = 500
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }

        dots.forEachIndexed { i, dot ->
            dot.postDelayed({ dot.startAnimation(anim) }, i * 200L)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
