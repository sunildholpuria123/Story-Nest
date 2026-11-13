package com.sd.storyteller.data.remote.api

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */



import com.sd.storyteller.data.remote.model.AuthRequest
import com.sd.storyteller.data.remote.model.AuthResponse
import com.sd.storyteller.data.remote.model.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseAuthApi {

    @POST("auth/v1/signup")
    suspend fun signUp(
        @Header("apikey") apiKey: String,
        @Body request: AuthRequest
    ): Response<AuthResponse>

    @POST("auth/v1/token")
    suspend fun signIn(
        @Header("apikey") apiKey: String,
        @Query("grant_type") grantType: String = "password",
        @Body request: AuthRequest
    ): Response<AuthResponse>

    @POST("auth/v1/token")
    suspend fun refreshToken(
        @Header("apikey") apiKey: String,
        @Query("grant_type") grantType: String = "refresh_token",
        @Body request: RefreshTokenRequest
    ): Response<AuthResponse>

    @POST("auth/v1/logout")
    suspend fun signOut(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): Response<Unit>
}
