package com.viacheslav.movieguide.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.viacheslav.movieguide.R

// Set of Material typography styles to start with
private val fontFamilyRoboto = FontFamily(
    listOf(
        Font(
            resId = R.font.roboto_light_300
        ),
        Font(
            resId = R.font.roboto_black_900,
            weight = FontWeight.Black
        )
    )
)
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Black,
        fontSize = 40.sp,
        lineHeight = 40.sp,
    ),
      titleMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Black,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color.White.copy(alpha = 0.75f)
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Black,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

)
