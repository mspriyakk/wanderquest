package com.example.wanderquest.data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * Created by Gauri Gadkari on 11/6/23.
 */

@Serializable
data class Destination(
    @SerialName("business_status")
    val businessStatus: String,
)

@Serializable
data class Geometry(
    val location: Location,
    val viewport: Viewport,
)

@Serializable
data class Location(
    val lat: Double,
    val lng: Double,
)

@Serializable
data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest,
)

@Serializable
data class Northeast(
    val lat: Double,
    val lng: Double,
)

@Serializable
data class Southwest(
    val lat: Double,
    val lng: Double,
)

@Serializable
data class OpeningHours(
    @SerialName("open_now")
    val openNow: Boolean,
)

@Serializable
data class Photo(
    val height: Long,
    @SerialName("html_attributions")
    val htmlAttributions: List<String>,
    @SerialName("photo_reference")
    val photoReference: String,
    val width: Long,
)

@Serializable
data class PlusCode(
    @SerialName("compound_code")
    val compoundCode: String,
    @SerialName("global_code")
    val globalCode: String,
)

@Serializable
data class Response(val results: List<Destination>)