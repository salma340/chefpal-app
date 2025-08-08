package com.iti.myapplicationbnv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.myapplicationbnv.adapter.FavoriteMealAdapter
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
        }, dao)

        bind.favRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bind.favRecyclerView.adapter = adapter

        dao.getAll().observe(viewLifecycleOwner) { favorites ->
            adapter.updateData(favorites)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}