package com.example.androidcourse.domain.usecase

class ValidateCityUseCase {
    operator fun invoke(city: String): String? {
        return city.trim().replace(Regex("[\\n\\t]"), "").lowercase().ifEmpty { null }
    }
}