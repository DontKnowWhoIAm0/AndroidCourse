package com.example.androidcourse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.data.entity.YarnEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(yarn: YarnEntity)

    @Update
    suspend fun update(yarn: YarnEntity)

    @Delete
    suspend fun delete(yarn: YarnEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailExists(email: String): Int
}