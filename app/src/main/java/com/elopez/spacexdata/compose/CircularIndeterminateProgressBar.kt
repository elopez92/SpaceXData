package com.elopez.spacexdata.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularIndeterminateProgressBar(isDisplayed: State<Boolean>){
    if(isDisplayed.value){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}