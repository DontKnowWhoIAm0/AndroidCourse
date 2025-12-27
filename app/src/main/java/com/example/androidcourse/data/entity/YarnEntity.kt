package com.example.androidcourse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "yarns")
data class YarnEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val composition: String,
    val skeinLength: Int,
    val thickness: Int,
    val hookSize: Float,
    val needleSize: Float
)
