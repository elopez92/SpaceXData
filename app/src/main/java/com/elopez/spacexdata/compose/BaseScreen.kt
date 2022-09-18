package com.elopez.spacexdata.compose

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.elopez.spacexdata.Screen
import com.elopez.spacexdata.model.LaunchData
import com.elopez.spacexdata.viewModel.LaunchViewModel
import com.google.gson.Gson

@Composable
fun BaseScreen(
    viewModel: LaunchViewModel,
    launchList: LaunchData,
    loading: State<Boolean>,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val windowInfo = rememberWindowInfo()
    val configuration = LocalConfiguration.current
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded
        && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    ){
        Row{
            Column(Modifier.weight(1f)) {
                LaunchList(
                    list = launchList, loading = loading, onLaunchSelected = { item ->
                        viewModel.selectedLaunch.value = item
                    }
                )
            }
            Column(Modifier.weight(3f)) {
                LaunchDetails(launchData = viewModel.selectedLaunch.value, modifier = Modifier.weight(2f))
            }
        }
    }else
        LaunchList(
            launchList,
            loading,
            onLaunchSelected = {item ->
                val json = Uri.encode(Gson().toJson(item))
                navController.navigate(Screen.DetailScreen.withArgs(json))
            }, modifier = modifier.fillMaxWidth())

}