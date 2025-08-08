package com.iti.myapplicationbnv.presentation.recipe.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.myapplicationbnv.data.local.AppDatabase
import com.iti.myapplicationbnv.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private val bind get() = binding!!

    private lateinit var adapter: FavoriteMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.Companion.getInstance(requireContext()).favoriteMealDao()

        adapter = FavoriteMealAdapter(emptyList(), onItemClick = { meal ->
            val action = FavoriteFragmentDirections
                .actionFavFragmentToRecipeDetailFragment(
                    mealId = meal.id,
                    mealName = meal.name,
                    mealImageUrl = meal.imageUrl,
                    mealInstructions = meal.instructions

                )
            findNavController().navigate(action)
        }, dao)

        bind.favRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.favRecyclerView.adapter = adapter

        dao.getAll().observe(viewLifecycleOwner) { favorites ->
            if (favorites.isEmpty()) {
                bind.tvNoFavorites.visibility = View.VISIBLE
                bind.favRecyclerView.visibility = View.GONE
            } else {
                bind.tvNoFavorites.visibility = View.GONE
                bind.favRecyclerView.visibility = View.VISIBLE
                adapter.updateData(favorites)
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}