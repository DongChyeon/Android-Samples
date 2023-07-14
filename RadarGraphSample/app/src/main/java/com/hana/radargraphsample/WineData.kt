package com.hana.radargraphsample

import androidx.compose.ui.graphics.Color

data class RadarData(val data: List<WineData>, val color: Color)
data class WineData(val label: String, val value: Float)