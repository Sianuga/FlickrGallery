package com.example.flickrgallery

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class ListItem(
    val height: Dp,
    val color: Color,
    val imageUrl: String
)