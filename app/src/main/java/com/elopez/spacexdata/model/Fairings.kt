package com.elopez.spacexdata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fairings(
    val recovered: Boolean?,
    val recovery_attempt: Boolean?,
    val reused: Boolean?,
    val ship: String?
): Parcelable