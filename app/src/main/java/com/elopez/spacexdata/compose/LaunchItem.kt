package com.elopez.spacexdata.compose

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
/*import coil.compose.AsyncImage
import coil.request.ImageRequest*/
import com.elopez.spacexdata.R
import com.elopez.spacexdata.model.LaunchDataItem
import com.elopez.spacexdata.utils.loadPicture
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LaunchItem(
    modifier: Modifier = Modifier,
    missionName: String,
    rocketName: String,
    siteName: String,
    launchDate: String,
    imageString: String?,
    selected: Boolean,
    onLaunchSelected: (missionName: String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(border = BorderStroke(0.5.dp, Color.Gray))
            .clickable { onLaunchSelected(missionName) }
            .background(if(selected) Color.Yellow else Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //Log.i("Patch url", "LaunchItem: ${launchDataItem.links.mission_patch_small ?: ""}")
        imageString.let {
            val image = loadPicture(url = it!!, defaultImage = R.drawable.ic_rocket_black).value
            image?.let{ img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = missionName,
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                contentScale = ContentScale.Fit)
            }
        }

        Column(
            modifier = modifier.padding(8.dp, 0.dp, 0.dp, 0.dp).
                    fillMaxWidth()
        ) {
            Text(missionName,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp)
            Text(rocketName)
            Text(siteName)
            val date = Date(launchDate.toLong()?.times(1000L) ?: 0)
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.ROOT)
            sdf.timeZone = TimeZone.getDefault()
            val formattedDate = sdf.format(date)
            Text(formattedDate)
        }
    }

}