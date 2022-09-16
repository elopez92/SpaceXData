package com.elopez.spacexdata

data class LaunchFailureDetails(
    val altitude: Int?,
    val reason: String,
    val time: Int
)