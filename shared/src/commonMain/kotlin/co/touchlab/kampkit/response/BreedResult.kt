package co.touchlab.kampkit.response

import kotlinx.serialization.Serializable

@Serializable
data class BreedResult(
    val message: HashMap<String, List<String>>,
    var status: String
)
