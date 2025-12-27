package com.example.androidcourse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    val salt: String,
    val isDeleted: Boolean,
    val deleteDate: Long
)