package com.sd.storyteller.data.repository

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */



import com.sd.storyteller.BuildConfig
import com.sd.storyteller.data.local.secure.SecureTokenStorage
import com.sd.storyteller.data.remote.api.SupabaseAuthApi
import com.sd.storyteller.data.remote.model.AuthRequest
import com.sd.storyteller.data.remote.model.AuthResponse
import com.sd.storyteller.data.remote.model.RefreshTokenRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class SupabaseAuthRepository @Inject constructor(
    private val api: SupabaseAuthApi,
    private val tokenStorage: SecureTokenStorage
) {
    private val apiKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY
    private val refreshMutex = Mutex()

    suspend fun signUp(
        email: String,
        password: String
    ): Result<AuthResponse> = safeRequest {
        api.signUp(apiKey, AuthRequest(email, password))
    }.onSuccess { response ->
        // Persist the session when Supabase returns one (email confirmation
        // settings may cause signup to return a user without a session).
        saveSession(response)
    }

    suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthResponse> {
        return safeRequest {
            api.signIn(
                apiKey = apiKey,
                request = AuthRequest(email, password)
            )
        }.onSuccess { response ->
            saveSession(response)
        }
    }

    suspend fun restoreSession(): Result<AuthResponse> {
        return runCatching {
            val refreshToken = tokenStorage.getRefreshToken()
                ?: error("No saved session")

            val response = api.refreshToken(
                apiKey = apiKey,
                request = RefreshTokenRequest(refreshToken)
            )

            check(response.isSuccessful) {
                "Session refresh failed: HTTP ${response.code()}"
            }

            val authResponse = response.body()
                ?: error("Empty session response")

            saveSession(authResponse)
            authResponse
        }
    }

    /**
     * Refreshes the current Supabase session.
     *
     * The mutex prevents concurrent requests from trying to rotate the same
     * refresh token at the same time.
     */
    suspend fun refreshAccessToken(): Result<String> = refreshMutex.withLock {
        runCatching {
            val refreshToken = tokenStorage.getRefreshToken()
                ?: error("Session expired. Please sign in again.")

            val response = api.refreshToken(
                apiKey = apiKey,
                request = RefreshTokenRequest(refreshToken)
            )

            if (!response.isSuccessful) {
                // A rejected refresh token means the saved session is no longer valid.
                if (response.code() == 400 || response.code() == 401) {
                    tokenStorage.clearTokens()
                }
                error("Session refresh failed: HTTP ${response.code()}")
            }

            val authResponse = response.body()
                ?: error("Empty session refresh response")

            val newAccessToken = authResponse.accessToken
                ?.takeIf { it.isNotBlank() }
                ?: error("Session refresh did not return an access token")

            // Supabase may rotate the refresh token. Save both returned tokens.
            saveSession(authResponse)
            newAccessToken
        }
    }

    suspend fun getAccessToken(): String? =
        tokenStorage.getAccessToken()

    suspend fun signOut(): Result<Unit> {
        return runCatching {
            val accessToken = tokenStorage.getAccessToken()

            if (accessToken != null) {
                val response = api.signOut(
                    apiKey = apiKey,
                    authorization = "Bearer $accessToken"
                )

                check(response.isSuccessful) {
                    "Sign out failed: HTTP ${response.code()}"
                }
            }
        }.also {
            tokenStorage.clearTokens()
        }
    }

    private suspend fun saveSession(response: AuthResponse) {
        val accessToken = response.accessToken
        val refreshToken = response.refreshToken

        if (!accessToken.isNullOrBlank() &&
            !refreshToken.isNullOrBlank()
        ) {
            tokenStorage.saveTokens(accessToken, refreshToken)
        }

        response.user?.id
            ?.takeIf { it.isNotBlank() }
            ?.let { tokenStorage.saveUserId(it) }
    }

    private suspend fun safeRequest(
        request: suspend () -> retrofit2.Response<AuthResponse>
    ): Result<AuthResponse> = runCatching {
        val response = request()

        check(response.isSuccessful) {
            "Authentication failed: HTTP ${response.code()}"
        }

        response.body() ?: error("Empty authentication response")
    }

    suspend fun getUserId(): String? {
        return tokenStorage.getUserId()
    }
}
