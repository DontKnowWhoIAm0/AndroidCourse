package com.example.androidcourse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidcourse.utils.Сonstants

@Entity(tableName = Сonstants.TABLE_YARNS)
data class YarnEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val composition: String,
    val skeinLength: Int,
    val weight: Int,
    val hookSize: Float,
    val needleSize: Float
)
