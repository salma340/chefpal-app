package com.iti.myapplicationbnv.data.data.sharedpref

import android.accounts.AccountManager.KEY_PASSWORD
import android.content.Context

class sharedpreferences(context: Context){

    private  val pref = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    companion object{
        private const val LoggedIn = "isLoggedIn"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }

    fun setLoggedIn(isLoggedIn: Boolean){

        pref.edit().putBoolean(LoggedIn,isLoggedIn).apply()

    }

    fun isLoggedIn():Boolean{

        return pref.getBoolean(LoggedIn,false)
    }
    fun saveUser(name: String, email: String, password: String) {
        pref.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun logout(){

        pref.edit().clear().apply()

    }
    fun isFirstTime(): Boolean {
        return pref.getBoolean("isFirstTime", true)
    }

    fun setFirstTime(value: Boolean) {
        pref.edit().putBoolean("isFirstTime", value).apply()
    }

}