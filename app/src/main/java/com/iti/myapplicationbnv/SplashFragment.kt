package com.iti.myapplicationbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.databinding.FragmentSpalshBinding



class SplashFragment : Fragment() {

    private var binding: FragmentSpalshBinding?= null
    private val bind get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSpalshBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateDots()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.register_fragment)
        }, 3000)
    }

    private fun animateDots(){

        val scaleUp1 = ObjectAnimator.ofPropertyValuesHolder(
            bind.dot1,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        ).apply {
            duration = 300
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            startDelay = 0
        }

        val scaleUp2 = scaleUp1.clone().apply {
            setTarget(bind.dot2)
            startDelay = 150
        }

        val scaleUp3 = scaleUp1.clone().apply {
            setTarget(bind.dot3)
            startDelay = 300
        }

        scaleUp1.start()
        scaleUp2.start()
        scaleUp3.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}