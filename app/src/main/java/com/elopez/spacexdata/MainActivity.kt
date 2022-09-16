package com.elopez.spacexdata

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.elopez.spacexdata.model.LaunchData
import com.elopez.spacexdata.ui.theme.SpaceXDataTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retService = RetrofitInstance.getRetrofitInstance().create(SpaceXService::class.java)

        val responseLiveData:LiveData<Response<LaunchData>> = liveData {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = retService.getAllLaunches()
                emit(response)
            }
        }

        responseLiveData.observe(this, Observer{
            val launchList = it.body()?.listIterator()
            if(launchList != null){
                while(launchList.hasNext()){
                    val launchItem = launchList.next()
                    Log.i("TAG", "onCreate: ${launchItem.details}")
                }
            }
        })

        setContent {
            SpaceXDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceXDataTheme {
        Greeting("Android")
    }
}