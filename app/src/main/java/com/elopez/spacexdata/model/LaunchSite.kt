package com.elopez.spacexdata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
): Parcelable