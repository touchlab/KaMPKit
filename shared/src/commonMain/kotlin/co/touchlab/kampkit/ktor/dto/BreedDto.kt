package co.touchlab.kampkit.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    val message: Map<String, List<String>>,
    var status: String
)
