package com.iti.myapplicationbnv.presentation.recipe.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.data.data.local.FavoriteMeal
import com.iti.myapplicationbnv.data.data.local.FavoriteMealDao
import com.iti.myapplicationbnv.data.local.AppDatabase
import com.iti.myapplicationbnv.databinding.FragmentRecipeDetailBinding
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {
    private var binding: FragmentRecipeDetailBinding? = null
    private val bind get() = binding!!

    private lateinit var dao: FavoriteMealDao



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

        val videoUrl = args.mealYoutubeUrl
        if (videoUrl.isNullOrEmpty()) {
            bind.btnWatchVideo.visibility = View.GONE
        } else {
            bind.btnWatchVideo.visibility = View.VISIBLE
            bind.btnWatchVideo.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        }


        val db = AppDatabase.Companion.getInstance(requireContext())
        dao = db.favoriteMealDao()


        dao.getMealById(args.mealId).observe(viewLifecycleOwner) { meal ->
            if (meal != null) {
                bind.btnFavorite.setImageResource(R.drawable.star_filled)
            } else {
                bind.btnFavorite.setImageResource(R.drawable.ic_star)
            }
        }

        // لما يضغط على النجمة
        bind.btnFavorite.setOnClickListener {
            lifecycleScope.launch {
                val meal =
                    dao.getMealById(args.mealId).value // لكن getMealById دي LiveData، مش هتقدر تجيبي القيمة كده
                // عشان كده الأفضل تخلي dao.getMealById suspend وترجع FavoriteMeal? مش LiveData

                val favoriteMeal = dao.getMealByIdSuspend(args.mealId) // لو ضفتِ دالة suspend

                if (favoriteMeal != null) {
                    dao.delete(favoriteMeal)
                    bind.btnFavorite.setImageResource(R.drawable.ic_star)
                } else {
                    dao.insert(
                        FavoriteMeal(
                            id = args.mealId,
                            name = args.mealName,
                            imageUrl = args.mealImageUrl ?: "",
                            instructions = args.mealInstructions,
                            category = ""
                        )
                    )
                    bind.btnFavorite.setImageResource(R.drawable.star_filled)
                }
            }
        }
    }

        override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}