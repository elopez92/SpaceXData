package com.elopez.spacexdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elopez.spacexdata.compose.BaseScreen
import com.elopez.spacexdata.compose.CircularIndeterminateProgressBar
import com.elopez.spacexdata.fragment.MasterLaunchListFragment
import com.elopez.spacexdata.ui.theme.SpaceXDataTheme
import com.elopez.spacexdata.viewModel.LaunchViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MasterLaunchListFragment()).commit()
        //val launchViewModel by viewModels<LaunchViewModel>()

        /*setContent {
            //val loading = launchViewModel.loading
            SpaceXDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        BaseScreen(launchList = launchViewModel.launchListResponse, loading)
                        launchViewModel.getLaunchData()
                    }
                    //Greeting("Android")
                }
            }
        }*/
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceXDataTheme {

    }
}