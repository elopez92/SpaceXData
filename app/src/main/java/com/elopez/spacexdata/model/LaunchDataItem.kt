package com.elopez.spacexdata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LaunchDataItem(
    var crew: @RawValue List<Any>? = null,
    val details: String? = null,
    val flight_number: Int? = null,
    val is_tentative: Boolean = false,
    val last_date_update: String? = null,
    val last_ll_launch_date: String? = null,
    val last_ll_update: String? = null,
    val last_wiki_launch_date: String? = null,
    val last_wiki_revision: String? = null,
    val last_wiki_update: String? = null,
    val launch_date_local: String? = null,
    val launch_date_source: String? = null,
    val launch_date_unix: Int? = null,
    val launch_date_utc: String? = null,
    val launch_failure_details: LaunchFailureDetails? = null,
    val launch_site: LaunchSite? = null,
    val launch_success: Boolean? = null,
    val launch_window: Int? = null,
    val launch_year: String? = null,
    val links: Links? = null,
    val mission_id: List<String>? = emptyList(),
    val mission_name: String? = null,
    val rocket: Rocket? = null,
    val ships: List<String>? = emptyList(),
    val static_fire_date_unix: Int? = null,
    val static_fire_date_utc: String? = null,
    val tbd: Boolean = false,
    val telemetry: Telemetry? = null,
    val tentative_max_precision: String? = null,
    val timeline: Timeline? = null,
    val upcoming: Boolean = false
): Parcelable {
    
}