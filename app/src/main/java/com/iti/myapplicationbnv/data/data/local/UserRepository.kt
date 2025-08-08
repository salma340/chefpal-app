package com.iti.myapplicationbnv.data.data.local

import com.iti.myapplicationbnv.data.local.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun register(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }
}