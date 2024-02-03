package id.ngoding.crypto_backend.model

import kotlinx.serialization.Serializable

@Serializable
data class Keys(
    val firstKey: String,
    val secondKey: String
)
