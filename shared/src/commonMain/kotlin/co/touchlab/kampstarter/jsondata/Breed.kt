package co.touchlab.kampstarter.jsondata

@Serializable
data class Breed(
    val id: String,
    val breedName: String,
    var favorite: Boolean
)