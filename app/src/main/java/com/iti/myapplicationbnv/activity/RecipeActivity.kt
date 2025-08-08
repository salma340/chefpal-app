package com.iti.myapplicationbnv.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private var backPressedTime: Long = 0
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.recipe_nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.toolbar.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)


    }
}