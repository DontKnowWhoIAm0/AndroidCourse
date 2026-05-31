package com.example.androidcourse.domain.usecase

import junit.framework.TestCase.assertNull
import org.junit.Test

class ValidateCityUseCaseTest {

    private val useCase = ValidateCityUseCase()

    @Test
    fun `invoke returns null for blank city input`() {
        assertNull(useCase(""))
        assertNull(useCase("   "))
        assertNull(useCase("\t\t"))
    }
}