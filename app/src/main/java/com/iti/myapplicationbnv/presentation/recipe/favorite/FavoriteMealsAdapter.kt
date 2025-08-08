package com.iti.myapplicationbnv.presentation.recipe.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.data.data.local.FavoriteMeal
import com.iti.myapplicationbnv.data.data.local.FavoriteMealDao
import com.iti.myapplicationbnv.databinding.MealFavoriteItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMealAdapter(
    private var favoriteMeals: List<FavoriteMeal>,
    private val onItemClick: (FavoriteMeal) -> Unit,
    private val dao: FavoriteMealDao
) : RecyclerView.Adapter<FavoriteMealAdapter.FavoriteMealViewHolder>() {


    //امسك view واحد
    inner class FavoriteMealViewHolder(val binding: MealFavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    // لما recyclerview تحتاج عنصر جديد
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {
        val binding = MealFavoriteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
        val meal = favoriteMeals[position]

        holder.binding.mealName.text = meal.name
        holder.binding.mealCategory.text = meal.category
        Glide.with(holder.itemView.context)
            .load(meal.imageUrl)
            .into(holder.binding.mealImage)


        holder.binding.favoriteButton.setImageResource(R.drawable.star_filled)


        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }


        holder.binding.favoriteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                dao.delete(meal)

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(holder.itemView.context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount() = favoriteMeals.size

    fun updateData(newFavorites: List<FavoriteMeal>) {
        favoriteMeals = newFavorites
        notifyDataSetChanged()
    }
}
