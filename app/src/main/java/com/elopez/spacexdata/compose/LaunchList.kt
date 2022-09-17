package com.elopez.spacexdata.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.elopez.spacexdata.model.LaunchData

@Composable
fun LaunchList(
    list: LaunchData,
    modifier: Modifier = Modifier
){
    LazyColumn{
        itemsIndexed(items = list){ _, item ->
            LaunchItem(
                missionName = item.mission_name,
                rocketName = item.rocket.rocket_name,
                siteName = item.launch_site.site_name,
                launchDate = item.launch_date_utc,
                imageString = item.links.mission_patch_small ?: ""
            )
        }
    }
}