package com.elopez.spacexdata.compose

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
/*import coil.compose.AsyncImage
import coil.request.ImageRequest*/
import com.elopez.spacexdata.R
import com.elopez.spacexdata.utils.loadPicture

@Composable
fun LaunchItem(
    modifier: Modifier = Modifier,
    missionName: String,
    rocketName: String,
    siteName: String,
    launchDate: String,
    imageString: String?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(border = BorderStroke(0.5.dp, Color.Gray)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_rocket_black),
            contentDescription = missionName,
            contentScale = ContentScale.Crop
        )*/
        Log.i("Patch url", "LaunchItem: ${imageString}")
        imageString.let {
            val image = loadPicture(url = it!!, defaultImage = R.drawable.ic_rocket_black).value
            image?.let{ img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = missionName,
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp),
                contentScale = ContentScale.Fit)
            }
        }

        Column {
            Text(missionName)
            Text(rocketName)
            Text(siteName)
            Text(launchDate)
        }
    }

}