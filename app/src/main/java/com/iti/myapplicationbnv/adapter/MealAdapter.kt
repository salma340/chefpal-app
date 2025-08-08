package com.iti.myapplicationbnv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.data.data.local.FavoriteMeal
import com.iti.myapplicationbnv.data.data.local.FavoriteMealDao
import com.iti.myapplicationbnv.data.data.local.Meal
import com.iti.myapplicationbnv.databinding.ItemMealBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealAdapter(private var meals: List<Meal>, private val onItemClick: (Meal) -> Unit, private val dao: FavoriteMealDao) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    inner class MealViewHolder(val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.binding.mealName.text = meal.name
        holder.binding.mealCategory.text = meal.category
        Glide.with(holder.itemView.context)
            .load(meal.imageUrl)
            .into(holder.binding.mealImage)

        val starIcon = if (meal.isFavorite) R.drawable.star_filled else R.drawable.ic_star
        holder.binding.favoriteButton.setImageResource(starIcon)

        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }


        holder.binding.favoriteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (meal.isFavorite) {

                    dao.delete(FavoriteMeal(meal.id, meal.name, meal.imageUrl,meal.category))
                    meal.isFavorite = false
                } else {
                    dao.insert(FavoriteMeal(meal.id, meal.name, meal.imageUrl,meal.category))
                    meal.isFavorite = true
                }

                CoroutineScope(Dispatchers.Main).launch {// احدث الicons
                    notifyItemChanged(position)
                    val message =
                        if (meal.isFavorite) "Added to favorites" else "Removed from favorites"
                    Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun getItemCount() = meals.size

    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}