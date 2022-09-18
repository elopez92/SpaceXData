package com.elopez.spacexdata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirstStage(
    val cores: List<Core>
): Parcelable