package com.iti.myapplicationbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.activity.RecipeActivity
import com.iti.myapplicationbnv.data.data.sharedpref.sharedpreferences
import com.iti.myapplicationbnv.databinding.FragmentSpalshBinding



class SplashFragment : Fragment() {

    private var binding: FragmentSpalshBinding?= null
    private val binds get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSpalshBinding.inflate(inflater,container,false)
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




    }



    /*private fun animateDots(){

        val scaleUp1 = ObjectAnimator.ofPropertyValuesHolder(
            binds.dot1,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        ).apply {
            duration = 300
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            startDelay = 0
        }

        val scaleUp2 = scaleUp1.clone().apply {
            setTarget(binds.dot2)
            startDelay = 150
        }

        val scaleUp3 = scaleUp1.clone().apply {
            setTarget(binds.dot3)
            startDelay = 300
        }

        scaleUp1.start()
        scaleUp2.start()
        scaleUp3.start()
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}