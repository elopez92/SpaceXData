package com.elopez.spacexdata.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elopez.spacexdata.R
import com.elopez.spacexdata.model.*
import com.elopez.spacexdata.utils.loadPicture
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LaunchDetails(
    modifier: Modifier = Modifier,
    launchData: LaunchDataItem?
) {
    if (launchData?.mission_name == null) {
        Text(
            text = "Select a launch for more details",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize(),
            fontSize = 20.sp
        )
    } else {

        val date = Date(launchData.launch_date_unix?.toLong()?.times(1000L) ?: 0)
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.ROOT)
        sdf.timeZone = TimeZone.getDefault()
        val formattedDate = sdf.format(date)

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            item {
                val image =
                    launchData.links?.mission_patch?.let {
                        loadPicture(
                            url = it,
                            defaultImage = R.drawable.ic_rocket_black
                        ).value
                    }
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = launchData.details,
                        modifier = Modifier
                            .height(90.dp)
                            .width(90.dp)
                            .padding(16.dp, 0.dp, 0.dp, 0.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Flight number: ${launchData.flight_number.toString()}"
                    )
                    Text(
                        text = launchData.mission_name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    if(launchData.mission_id?.isNotEmpty() == true) {
                        Text(
                            text = "Mission id: ${launchData.mission_id.joinToString()}"
                        )
                    }
                }
                fullWidthTextComposable(text1 = "Launch Date:", text2= formattedDate)
                Divider(color = Color.Black, thickness = 2.dp)
                Spacer(modifier = modifier.padding(4.dp))
            }
                launchData.rocket?.let {
                    item {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Rocket",
                            fontSize = 18.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Rocket id: ${launchData.rocket.rocket_id}"
                            )
                            Text(
                                text = launchData.rocket.rocket_name,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Type ${launchData.rocket.rocket_type}"
                            )
                        }
                        Divider(color = Color.Black, thickness = 1.dp)
                    }

                    launchData.rocket.first_stage.cores.let { cores ->
                        item {
                            Text(
                                text = "First Stage",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Text(
                                text = "Cores",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        itemsIndexed(items = cores) { _, core ->
                            CoreInfo(core = core)
                        }
                    }
                    launchData.rocket.second_stage.let {
                        item {
                            secondStage(secondStage = it)
                        }
                        launchData.rocket.second_stage.payloads?.let { payloadList ->
                            item {
                                Text(
                                    text = "Payloads",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            itemsIndexed(items = payloadList) { _, payload ->
                                fullWidthTextComposable(
                                    text1 = "Payload Id:",
                                    text2 = payload.payload_id
                                )
                                if (payload.norad_id.isNotEmpty())
                                    fullWidthTextComposable(
                                        text1 = "Norad Id:",
                                        text2 = payload.norad_id.joinToString()
                                    )
                                fullWidthTextComposable(text1 = "Reused:", text2 = payload.reused)
                                if (payload.customers.isNotEmpty())
                                    fullWidthTextComposable(
                                        text1 = "Customers:",
                                        text2 = payload.customers.joinToString()
                                    )
                                payload.nationality?.let { it1 ->
                                    fullWidthTextComposable(
                                        "Nationality:",
                                        text2 = it1
                                    )
                                }
                                payload.manufacturer?.let { it1 ->
                                    fullWidthTextComposable(
                                        "Manufacturer:",
                                        text2 = it1
                                    )
                                }
                                if (payload.mass_returned_kg != null)
                                    fullWidthTextComposable(
                                        text1 = "Payload Kg:",
                                        text2 = payload.mass_returned_kg
                                    )
                                if (payload.mass_returned_lbs != null)
                                    fullWidthTextComposable(
                                        text1 = "Payload Lbs:",
                                        text2 = payload.mass_returned_lbs
                                    )
                                fullWidthTextComposable(text1 = "Orbit:", text2 = payload.orbit)
                                orbitParamsInfo(orbitParams = payload.orbit_params)
                                Divider(color = Color.LightGray, thickness = 1.dp)
                            }
                        }
                    }
                    item {
                        if (launchData.rocket.fairings != null) {
                            fairing(fairings = launchData.rocket.fairings)
                        }
                        Divider(color = Color.Black, thickness = 2.dp )
                    }

                }
                item {
                    if (launchData.ships?.isNotEmpty() == true) {
                        fullWidthTextComposable(
                            text1 = "Ships:", text2= launchData.ships.joinToString()
                        )
                    }
                    if (launchData.telemetry?.flight_club != null) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Telemetry",
                            fontSize = 17.sp
                        )
                        fullWidthTextComposable(
                            text1 = "Flight Club:", text2= launchData.telemetry.flight_club
                        )
                    }

                    if (launchData.launch_site != null) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Launch Site",
                            fontSize = 17.sp
                        )
                        fullWidthTextComposable(
                            text1 = "Site id:", text2= launchData.launch_site.site_id
                        )
                        fullWidthTextComposable(
                            text1 = "Site name:", text2= launchData.launch_site.site_name
                        )
                        fullWidthTextComposable(
                            text1 = "Site name long:", text2= launchData.launch_site.site_name_long
                        )
                        launchData.launch_success?.let { it1 -> fullWidthTextComposable(text1 = "Launch success:", text2= it1) }
                    }


                    if (launchData.launch_failure_details != null) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Launch failure details",
                            fontSize = 17.sp
                        )
                        fullWidthTextComposable(text1 = "Time:", text2= launchData.launch_failure_details.time)
                        if (launchData.launch_failure_details.altitude != null)
                            fullWidthTextComposable(text1 = "Altitude:", text2= launchData.launch_failure_details.altitude)
                        fullWidthTextComposable(text1 = "Reason:", text2= launchData.launch_failure_details.reason)
                    }
                    launchData.links?.let { linksDetails(links = it) }
                    launchData.details?.let { fullWidthTextComposable(text1 = "Details:", text2= it) }
                    launchData.static_fire_date_utc?.let { fullWidthTextComposable(text1 = "Static fire data utc:", text2= it) }
                    launchData.static_fire_date_unix?.let { fullWidthTextComposable(text1 = "Static fire unix:", text2= it) }
                    launchData.timeline?.let {
                        timelineDetails(timeline = launchData.timeline)
                    }
                    launchData.crew?.let { fullWidthTextComposable(text1 = "Crew:", text2= it.joinToString()) }
                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }


@Composable
fun CoreInfo(core: Core){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Core serial: ")
                Text(text = "${core.core_serial}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Flight: ")
                Text(text = "${core.flight}")
            }
            if (core.block != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Block: ")
                    Text(text = "${core.block}")
                }
            if (core.gridfins != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Gridfins: ")
                    Text(text = "${core.gridfins}")
                }
            if (core.legs != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Legs: ")
                    Text(text = "${core.legs}")
                }
            if (core.reused != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Reused: ")
                    Text(text = "${core.reused}")
                }
            if (core.land_success != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Land success: ")
                    Text(text = "${core.land_success}")
                }
            if (core.landing_intent != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Landing intent: ")
                    Text(text = "${core.landing_intent}")
                }
            if (core.landing_type != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Landing type: ")
                    Text(text = "${core.landing_type}")
                }
            if (core.landing_vehicle != null)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Landing vehicle: ")
                    Text(text = "${core.landing_vehicle}")
                }
        }



@Composable
fun secondStage(secondStage: SecondStage){
        Text(text="Second Stage",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp)
    secondStage.block?.let { fullWidthTextComposable(text1="Block:", text2= it) }
        //payloadInfo(secondStage.payloads)

}

@Composable
fun payloadInfo(payload: List<Payload>) {
        Text(
            text = "Payloads",
            fontStyle = FontStyle.Italic
        )
        LazyColumn {
            itemsIndexed(items = payload) { _, payload ->
                Text(text = "Payload Id: ${payload.payload_id}")
                if (payload.norad_id.isNotEmpty())
                    Text(text = "Norad Id: ${payload.norad_id.joinToString()}")
                Text(text = "Reused: ${payload.reused}")
                if (payload.customers.isNotEmpty())
                    Text(text = "Customers: ${payload.customers.joinToString()}}")
                Text("Nationality: ${payload.nationality}")
                Text("Manufacturer: ${payload.manufacturer}")
                Text(text = "Payload Kg: ${payload.mass_returned_kg}")
                Text(text = "Payload Lbs: ${payload.mass_returned_lbs}")
                Text(text = "Orbit: ${payload.orbit}")
                orbitParamsInfo(orbitParams = payload.orbit_params)
            }
        }

}

@Composable
fun orbitParamsInfo(orbitParams: OrbitParams){
    Text(text = "Orbit Params",
    modifier = Modifier.fillMaxWidth(),
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp)
    if(orbitParams.reference_system != "null"){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Reference System: ")
            Text(text = "${orbitParams.reference_system}")
        }
    }
    if(orbitParams.regime != "null") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Regime:")
        Text(text=" ${orbitParams.regime}")
        }
    }
    if(orbitParams.longitude != null) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Longitude: ")
        Text(text="${orbitParams.longitude}")
        }
    }
    if(orbitParams.semi_major_axis_km != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Semi major axis Km: ")
            Text(text = "${orbitParams.semi_major_axis_km}")
        }
    }
    if(orbitParams.eccentricity != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Eccentricity: ")
        Text(text="${orbitParams.eccentricity}")
        }
    }
    if(orbitParams.periapsis_km != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Periapsis Km: ")
        Text(text="${orbitParams.periapsis_km}")
        }
    }
    if(orbitParams.apoapsis_km != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Apoapsis: ")
        Text(text="${orbitParams.apoapsis_km}")
        }
    }
    if(orbitParams.inclination_deg != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Inclination Deg: ")
        Text(text="${orbitParams.inclination_deg}")
        }
    }
    if(orbitParams.period_min != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Period min: ")
        Text(text="${orbitParams.period_min}")
        }
    }
    if(orbitParams.lifespan_years != null) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="LifeSpan years: ")
        Text(text="${orbitParams.lifespan_years}")
        }
    }
    if(orbitParams.epoch != "null") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            orbitParams.epoch?.let { fullWidthTextComposable(text1="Epoch:", text2 = it) }
        }
    }
    if(orbitParams.mean_motion != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Mean motion: ")
        Text(text="${orbitParams.mean_motion}")
        }
    }
    if(orbitParams.raan != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Raan: ")
        Text(text="${orbitParams.raan}")
        }
    }
    if(orbitParams.arg_of_pericenter != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Arg of pericenter: ")
        Text(text="${orbitParams.arg_of_pericenter}")
        }
    }
    if(orbitParams.mean_anomaly != null){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
        Text(text="Mean anomaly:")
        Text(text=" ${orbitParams.mean_anomaly}")
        }
    }
}

@Composable
fun fairing(fairings: Fairings){
        Text(text = "Fairings",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp)
    fairings.reused?.let { fullWidthTextComposable(text1="Reused:", text2= it) }
    fairings.recovery_attempt?.let { fullWidthTextComposable(text1="Recovery Attempt:", text2= it) }
    fairings.recovered?.let { fullWidthTextComposable(text1="Recovered:", text2= it) }
        if(fairings.ship != null)
            fullWidthTextComposable(text1="Ship:", text2=fairings.ship)
}

@Composable
fun linksDetails(links: Links){
        Text(text = "Links",
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp)
        if(links.mission_patch != null)
            fullWidthTextComposable(text1 = "Mission patch:", text2 = links.mission_patch)
        if(links.mission_patch_small != null)
            fullWidthTextComposable(text1 = "Mission patch small:", text2 = links.mission_patch_small)
        if(links.reddit_campaign != null)
            fullWidthTextComposable(text1 = "Reddit campaign:", text2= links.reddit_campaign)
        if(links.reddit_launch != null)
            fullWidthTextComposable(text1 = "Reddit launch:", text2=links.reddit_launch)
        if(links.reddit_recovery != null)
            fullWidthTextComposable(text1 = "Reddit recovery:", text2=links.reddit_recovery)
        if(links.reddit_media != null)
            fullWidthTextComposable(text1 = "Reddit media:", text2=links.reddit_media)
        if(links.presskit != null)
            fullWidthTextComposable(text1 = "Presskit:", text2=links.presskit)
        if(links.article_link != null)
            fullWidthTextComposable(text1 = "Article link:", text2=links.article_link)
        if(links.wikipedia != null)
            fullWidthTextComposable(text1 = "Wikipedia:", text2=links.wikipedia)
        if(links.video_link != null)
            fullWidthTextComposable(text1 = "Video link:", text2=links.video_link)
        if(links.youtube_id != null)
            fullWidthTextComposable(text1 = "YouTube id:", text2=links.youtube_id)
        if(links.flickr_images.isNotEmpty()){
            fullWidthTextComposable(text1 = "Flickr Images:", text2= links.flickr_images.joinToString())
        }
}

@Composable
fun timelineDetails(timeline: Timeline){
        Text(text = "Timeline",
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp)
        timeline.webcast_liftoff?.let { fullWidthTextComposable(text1 = "Webcast liftoff:", text2= it) }
}

@Composable
fun fullWidthTextComposable(text1: String, text2: Any){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = text1)
        Text(text = text2.toString(), textAlign = TextAlign.End, modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp))
    }
}
