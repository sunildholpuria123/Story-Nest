package com.sd.storyteller.data.remote.model

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String,
    val password: String
)

@Serializable
data class RefreshTokenRequest(
    @SerialName("refresh_token")
    val refreshToken: String
)

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String? = null,

    @SerialName("refresh_token")
    val refreshToken: String? = null,

    @SerialName("expires_in")
    val expiresIn: Long? = null,

    @SerialName("token_type")
    val tokenType: String? = null,

    val user: AuthUser? = null
)

@Serializable
data class AuthUser(
    val id: String,
    val email: String? = null
)
