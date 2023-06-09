package co.touchlab.kampkit.data.dog

import kotlinx.serialization.Serializable

@Serializable
data class DogResult(
    val message: Map<String, List<String>>,
    var status: String
)
