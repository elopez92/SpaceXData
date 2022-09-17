package com.elopez.spacexdata.compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.elopez.spacexdata.RetrofitInstance
import com.elopez.spacexdata.SpaceXService
import com.elopez.spacexdata.model.LaunchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun BaseScreen(
    launchList: LaunchData,
    modifier: Modifier = Modifier,
){


    LaunchList(launchList)
}