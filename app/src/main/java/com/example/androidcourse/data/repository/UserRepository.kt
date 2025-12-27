package com.example.androidcourse.data.repository

import com.example.androidcourse.data.dao.UserDao
import com.example.androidcourse.data.entity.UserEntity
import java.util.Date

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {
        if (userDao.isEmailExists(email) > 0) {
            return Result.failure(Exception("Email already exists"))
        }

        val user = UserEntity(
            email = email,
            password = password,
            isDeleted = false,
            deleteDate = Date()
        )

        userDao.insert(user)
        return Result.success(Unit)
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<UserEntity> {
        val user = userDao.getUserByEmail(email) ?: return Result.failure(Exception("User not found"))

        return if (user.password == password) {
            Result.success(user)
        } else {
            Result.failure(Exception("Wrong password"))
        }
    }
}