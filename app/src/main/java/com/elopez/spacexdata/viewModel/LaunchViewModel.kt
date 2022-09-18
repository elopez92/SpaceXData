package com.elopez.spacexdata.viewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.elopez.spacexdata.RetrofitInstance
import com.elopez.spacexdata.SpaceXService
import com.elopez.spacexdata.model.LaunchData
import com.elopez.spacexdata.model.LaunchDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LaunchViewModel: ViewModel() {
    var launchListResponse: LaunchData by mutableStateOf(LaunchData())
    var errorMessage: String by mutableStateOf("")

    val loading = mutableStateOf(false)
    val selectedLaunch = mutableStateOf(LaunchDataItem())

    val retService = RetrofitInstance.getRetrofitInstance().create(SpaceXService::class.java)

    fun getLaunchData(){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                loading.value = true
            }
            val response = retService.getAllLaunches()
            //emit(response)
            launchListResponse = response.body()!!
            withContext(Dispatchers.Main){
                loading.value = false
            }
        }
    }
}