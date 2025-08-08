package com.iti.myapplicationbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.iti.myapplicationbnv.databinding.FragmentRecipeDetailBinding
import com.iti.myapplicationbnv.databinding.FragmentSignupBinding

class RecipeDetailFragment: Fragment() {
    private var binding: FragmentRecipeDetailBinding? = null
    private val bind get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = RecipeDetailFragmentArgs.fromBundle(requireArguments())

        bind.mealTitle.text = args.mealName
        bind.mealInstructions.text = args.mealInstructions

        Glide.with(requireContext())
            .load(args.mealImageUrl)
            .into(bind.mealImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}