package com.hana.radargraphsample

data class ObjectData(val data: List<RadarData>)
data class RadarData(val label: String, val value: Float)