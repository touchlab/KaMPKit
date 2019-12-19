package co.touchlab.kampstarter.jsondata

import kotlinx.serialization.Serializable

@Serializable
data class BreedResult(
    val message: HashMap<String,List<String>>,
    var status: String
)

@Serializable
data class Breed(
    val id: String,
    val breedName: String,
    var favorite: Boolean
)