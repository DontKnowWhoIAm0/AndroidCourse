package com.example.androidcourse.data.repository

import com.example.androidcourse.data.dao.UserDao
import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.utils.PasswordHasher
import java.util.Date

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun registration(
        email: String,
        password: String,
        salt: String
    ): Result<Unit> {
        if (userDao.isEmailExists(email) > 0) {
            return Result.failure(Exception("Email already exists"))
        }

        val user = UserEntity(
            email = email,
            password = password,
            salt = salt,
            isDeleted = false,
            deleteDate = Date().time
        )

        userDao.insert(user)
        return Result.success(Unit)
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<UserEntity> {
        val user = userDao.getUserByEmail(email) ?: return Result.failure(Exception("User not found"))
        val hashedInput = PasswordHasher.hash(password, user.salt)

        return if (hashedInput == user.password) {
            Result.success(user)
        } else {
            Result.failure(Exception("Wrong password"))
        }
    }
}