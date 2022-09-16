package com.elopez.spacexdata.model

data class LaunchFailureDetails(
    val altitude: Int?,
    val reason: String,
    val time: Int
)