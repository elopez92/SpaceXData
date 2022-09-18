package com.elopez.spacexdata.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elopez.spacexdata.AssetParamType
import com.elopez.spacexdata.Screen
import com.elopez.spacexdata.model.LaunchDataItem
import com.elopez.spacexdata.viewModel.LaunchViewModel

@Composable
fun Navigation(viewModel: LaunchViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            BaseScreen(
                viewModel = viewModel,
                launchList = viewModel.launchListResponse,
                loading = viewModel.loading,
                navController
            )
        }
        composable(
            route = Screen.DetailScreen.route + "/{launch}",
            arguments = listOf(navArgument("launch")
        {
            type = AssetParamType()
            nullable = false
        }
        )) { launch ->
            LaunchDetails(launchData = launch.arguments?.getParcelable<LaunchDataItem>("launch")!!)
        }
    }
}