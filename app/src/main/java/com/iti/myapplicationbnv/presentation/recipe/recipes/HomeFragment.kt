package com.iti.myapplicationbnv.presentation.recipe.recipes

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.data.data.local.FavoriteMealDao
import com.iti.myapplicationbnv.data.data.sharedpref.sharedpreferences
import com.iti.myapplicationbnv.data.local.AppDatabase
import com.iti.myapplicationbnv.data.remote.ApiClient
import com.iti.myapplicationbnv.data.remote.MealResponse
import com.iti.myapplicationbnv.presentation.auth.AuthActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var mealAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var dao: FavoriteMealDao
    private var lastQuery: String = ""

    private lateinit var placeholderText: TextView

    private lateinit var progressBar: ProgressBar

    private var favoriteIds: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        recyclerView = view.findViewById(R.id.mealRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        placeholderText = view.findViewById(R.id.placeholderText)


        searchBar = view.findViewById(R.id.search_bar)

        progressBar = view.findViewById(R.id.progressBar)

        val db = AppDatabase.Companion.getInstance(requireContext())
        dao = db.favoriteMealDao()

        mealAdapter = MealAdapter(emptyList(), onItemClick = { meal ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToRecipeDetailFragment(
                    mealId = meal.id,
                    mealName = meal.name,
                    mealImageUrl = meal.imageUrl,
                    mealInstructions = meal.instructions,
                    mealCategory = meal.category,
                    mealYoutubeUrl = meal.youtubeUrl ?: ""


                )
            findNavController().navigate(action)
        }, dao = dao)

        recyclerView.adapter = mealAdapter


        dao.getAll().observe(viewLifecycleOwner) { favoriteMeals ->
            favoriteIds = favoriteMeals.map { it.id }
            mealAdapter.updateFavorites(favoriteIds)
        }


        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                lastQuery = s.toString()
                fetchMeals(lastQuery)
            }
        })


        fetchMeals("")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"

        val menuHost = requireActivity() as MenuHost

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_sign_out -> {
                        val shared = sharedpreferences(requireContext())
                        shared.logout()
                        val intent = Intent(requireContext(), AuthActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("openLoginDirect", true)
                        startActivity(intent)

                        true
                    }

                    R.id.menu_about_creator -> {
                        findNavController().navigate(R.id.action_homeFragment_to_aboutCreatorFragment)
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner)
    }



    private fun fetchMeals(query: String) {
        if (query.isEmpty()) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }

        ApiClient.apiService.searchMeals(query)
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: Response<MealResponse>
                ) {

                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val meals = response.body()?.meals ?: emptyList()

                        meals.forEach { meal ->
                            meal.isFavorite = favoriteIds.contains(meal.id)
                        }
                        mealAdapter.updateData(meals)

                        placeholderText.visibility = if (meals.isEmpty()) View.VISIBLE else View.GONE
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Failed to load meals", Toast.LENGTH_SHORT).show()
                }
            })
    }
}