package com.elopez.spacexdata.compose

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elopez.spacexdata.model.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LaunchDetails(
    modifier: Modifier = Modifier,
    launchData: LaunchDataItem?
){
    if(launchData?.mission_name == null){
        Text(text = "Select a launch for more details",
        textAlign = TextAlign.Center)
    }else {

        val date = Date(launchData.launch_date_unix?.toLong()?.times(1000L) ?: 0)
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.ROOT)
        sdf.timeZone = TimeZone.getDefault()
        val formattedDate = sdf.format(date)

        val scrollState = rememberScrollState()
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(this@BoxWithConstraints.maxHeight)
                ) {
                    Column {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = launchData.flight_number.toString()
                            )
                            Text(
                                text = launchData.mission_name,
                                fontWeight = FontWeight.Bold
                            )
                            launchData.mission_id?.let {
                                Text(
                                    text = it.joinToString()
                                )
                            }
                        }
                        Text(text = "Launch Date: $formattedDate")

                        launchData.rocket?.let {
                            Text(
                                fontWeight = FontWeight.Bold,
                                text = "Rocket"
                            )
                            rocketInfo(rocket = it) }

                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Ships: ${launchData.ships?.joinToString()}"
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun rocketInfo(rocket: Rocket, modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Rocket id: ${rocket.rocket_id}"
            )
            Text(
                text = rocket.rocket_name,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Type ${rocket.rocket_type}"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(text="First Stage",
                    fontWeight = FontWeight.Bold)
                Text(
                    text = "Cores",
                    fontStyle = FontStyle.Italic
                )
                CoresInfo(firstStage = rocket.first_stage)
            }
            Column(Modifier.weight(1f)) {
                secondStage(secondStage = rocket.second_stage)
            }
        }
        if(rocket.fairings != null)
            fairing(fairings = rocket.fairings)

    }
}

@Composable
fun CoresInfo(firstStage: FirstStage){
    LazyColumn{
        itemsIndexed(items = firstStage.cores){_, core ->
            Text(text="Core serial: ${core.core_serial}")
            Text(text = "Flight: ${core.flight}")
            if(core.block != null)
                Text(text = "Block: ${core.block}")
            if(core.gridfins != null)
                Text(text = "Gridfins: ${core.gridfins}")
            if(core.legs != null)
                Text(text = "Legs: ${core.legs}")
            if(core.reused != null)
                Text(text = "Reused: ${core.reused}")
            if(core.land_success != null)
                Text(text = "Land Success: ${core.land_success}")
            if(core.landing_intent != null)
                Text(text = "Landing intent: ${core.landing_intent}")
            if(core.landing_type != null)
                Text(text = "Landing Type: ${core.landing_type}")
            if(core.landing_vehicle != null)
                Text(text = "Landing Vehicle: ${core.landing_vehicle}")
        }
    }

}

@Composable
fun secondStage(secondStage: SecondStage){
    Column(
        modifier = Modifier.fillMaxWidth(),) {
        Text(text="Second Stage",
        fontWeight = FontWeight.Bold)
        Text(text="Block: ${secondStage.block}")
        payloadInfo(secondStage.payloads)
    }
}

@Composable
fun payloadInfo(payload: List<Payload>) {
    Column {
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
}

@Composable
fun orbitParamsInfo(orbitParams: OrbitParams){
    Column {
        Text(text="Reference System: ${orbitParams.reference_system}")
        Text(text="Regime: ${orbitParams.regime}")
        Text(text="Longitude: ${orbitParams.longitude}")
        Text(text="Semi major axis Km: ${orbitParams.semi_major_axis_km}")
        Text(text="Eccentricity: ${orbitParams.eccentricity}")
        Text(text="Periapsis Km: ${orbitParams.periapsis_km}")
        Text(text="Apoapsis: ${orbitParams.apoapsis_km}")
        Text(text="Inclination Deg: ${orbitParams.inclination_deg}")
        Text(text="Period Min: ${orbitParams.period_min}")
        Text(text="LifeSpan years: ${orbitParams.lifespan_years}")
        Text(text="Epoch: ${orbitParams.epoch}")
        Text(text="Mean motion: ${orbitParams.mean_motion}")
        Text(text="Raan: ${orbitParams.raan}")
        Text(text="Arg of pericenter: ${orbitParams.arg_of_pericenter}")
        Text(text="Mean anomaly: ${orbitParams.mean_anomaly}")
    }
}

@Composable
fun fairing(fairings: Fairings){
    Column {
        Text(text = "Fairings",
        fontStyle = FontStyle.Italic)
        Text(text="Reused: ${fairings.reused}")
        Text(text="Recovery Attempt: ${fairings.recovery_attempt}")
        Text(text="Recovered: ${fairings.recovered}")
        if(fairings.ship != null)
            Text(text="Ship: ${fairings.ship}")
    }
}

@Composable
fun telemetryDetails(telemetry: Telemetry){
    Column {


    }
}