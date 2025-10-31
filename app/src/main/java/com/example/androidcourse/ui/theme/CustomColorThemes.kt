package com.example.androidcourse.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColorThemes (
    val borderColor: Color,
    val backgroundColor: Color,
    val errorColor: Color,
    val inputTextColor: Color,
    val buttonTextColor: Color,
    val fieldColor: Color
    )

val PumpkinTheme = CustomColorThemes(
    borderColor = Color(0xFFA65521),
    backgroundColor = Color(0xFFDFCAA0),
    errorColor = Color(0xFF591902),
    inputTextColor = Color(0x99BF5B04),
    buttonTextColor = Color(0xFFFFFFFF),
    fieldColor = Color(0xCCFCF5E1)
)

val WinterStormTheme = CustomColorThemes(
    borderColor = Color(0xFF021740),
    backgroundColor = Color(0xFF6BD9F2),
    errorColor = Color(0xFF010D26),
    inputTextColor = Color(0x99021740),
    buttonTextColor = Color(0xFFFFFFFF),
    fieldColor = Color(0xCCB5ECF9)
)

val GrassTheme = CustomColorThemes(
    borderColor = Color(0xFF07730E),
    backgroundColor = Color(0xFF9BD97E),
    errorColor = Color(0xFF044008),
    inputTextColor = Color(0x9907730E),
    buttonTextColor = Color(0xFFFFFFFF),
    fieldColor = Color(0xCCE6F2BB)
)

val LocalAppColorScheme = staticCompositionLocalOf {
    CustomColorThemes(
        borderColor = PumpkinTheme.borderColor,
        backgroundColor = PumpkinTheme.backgroundColor,
        errorColor = PumpkinTheme.errorColor,
        inputTextColor = PumpkinTheme.inputTextColor,
        buttonTextColor = PumpkinTheme.buttonTextColor,
        fieldColor = PumpkinTheme.fieldColor,
    )
}

@Composable
fun CustomTheme(
    theme: ThemeEnum,
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        ThemeEnum.PUMPKIN -> PumpkinTheme
        ThemeEnum.WINTERSTORM -> WinterStormTheme
        ThemeEnum.GRASS -> GrassTheme
    }

    CompositionLocalProvider(LocalAppColorScheme provides colors) {
        MaterialTheme {
            content()
        }
    }
}