package com.iti.myapplicationbnv.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.data.local.AppDatabase
import com.iti.myapplicationbnv.data.local.UserDao
import com.iti.myapplicationbnv.databinding.ActivityAuthBinding

class AuthActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val db = AppDatabase.Companion.getInstance(this)
        userDao = db.userDao()



        if (intent.getBooleanExtra("openLoginDirect", false)) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.login_fragment)
        }

    }
}