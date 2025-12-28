package com.example.androidcourse.data.repository

import com.example.androidcourse.data.dao.UserDao
import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.utils.PasswordHasher
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun registration(email: String, password: String, salt: String) {
        val user = UserEntity(
            email = email,
            password = password,
            salt = salt,
            isDeleted = false,
            deleteDate = null
        )
        userDao.insert(user)
    }

    suspend fun login(email: String, password: String): Boolean {
        val userEntity = userDao.getUserByEmail(email) ?: return false
        val hashedInputPassword = PasswordHasher.hash(password, userEntity.salt)
        return hashedInputPassword == userEntity.password
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun isEmailExists(email: String): Boolean {
        return userDao.isEmailExists(email) > 0
    }

    fun observeUsers(): Flow<List<UserEntity>> {
        return userDao.observeUsers()
    }
}