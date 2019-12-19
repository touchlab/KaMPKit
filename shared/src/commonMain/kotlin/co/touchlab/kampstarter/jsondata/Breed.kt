package co.touchlab.kampstarter.jsondata

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val breedName: String,
    var favorite: Boolean
)