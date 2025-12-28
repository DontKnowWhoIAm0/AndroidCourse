package com.example.androidcourse.ui.navigation.yarn.catalog.sort

import androidx.annotation.StringRes
import com.example.androidcourse.R

enum class SortOption(@StringRes val titleRes: Int) {
    NONE(R.string.sort_none),
    BRAND_ASC(R.string.sort_brand_asc),
    BRAND_DESC(R.string.sort_brand_desc),
    WEIGHT_ASC(R.string.sort_weight_asc),
    WEIGHT_DESC(R.string.sort_weight_desc),
    LENGTH_ASC(R.string.sort_length_asc),
    LENGTH_DESC(R.string.sort_length_desc)
}