package com.example.androidcourse.data.mapper

import com.example.androidcourse.data.entity.YarnEntity
import com.example.androidcourse.model.Yarn

fun YarnEntity.toModel(): Yarn = Yarn(
    id = id,
    brand = brand,
    composition = composition,
    skeinLength = skeinLength,
    thickness = thickness,
    hookSize = hookSize,
    needleSize = needleSize
)

fun Yarn.toEntity(): YarnEntity = YarnEntity(
    id = id,
    brand = brand,
    composition = composition,
    skeinLength = skeinLength,
    thickness = thickness,
    hookSize = hookSize,
    needleSize = needleSize
)