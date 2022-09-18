package com.elopez.spacexdata

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.elopez.spacexdata.compose.Navigation
import com.elopez.spacexdata.ui.theme.SpaceXDataTheme
import com.elopez.spacexdata.viewModel.LaunchViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val launchViewModel by viewModels<LaunchViewModel>()
        if(savedInstanceState == null || launchViewModel.launchListResponse.isEmpty())
            launchViewModel.getLaunchData()
        setContent{
            Navigation(viewModel = launchViewModel)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("STATE_SAVED", 1)
    }
}