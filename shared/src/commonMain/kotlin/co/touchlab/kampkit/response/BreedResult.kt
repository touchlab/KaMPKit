package co.touchlab.kampkit.response

import kotlinx.serialization.Serializable

@Serializable
internal data class BreedResult(
    val message: Map<String, List<String>>,
    var status: String
)
