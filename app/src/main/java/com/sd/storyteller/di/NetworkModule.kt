package com.sd.storyteller.di

import com.sd.storyteller.BuildConfig
import com.sd.storyteller.data.remote.api.GeminiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sd.storyteller.data.remote.api.SupabaseAuthApi
import com.sd.storyteller.data.remote.api.SupabaseStoryApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            // Never log generated stories or request bodies in release builds.
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {
        val baseUrl = BuildConfig.STORYNEST_API_BASE_URL
        require(baseUrl.startsWith("https://") && baseUrl.endsWith("/")) {
            "STORYNEST_API_BASE_URL must be an HTTPS URL ending with /"
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideGeminiApi(retrofit: Retrofit): GeminiApi =
        retrofit.create(GeminiApi::class.java)



    @Provides
    @Singleton
    @Named("supabase")
    fun provideSupabaseRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {

        val url = BuildConfig.SUPABASE_URL

        require(url.startsWith("https://") && url.endsWith("/")) {
            "Invalid Supabase URL"
        }

        require(BuildConfig.SUPABASE_PUBLISHABLE_KEY.isNotBlank()) {
            "Supabase publishable key is missing"
        }

        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideSupabaseAuthApi(
        @Named("supabase") retrofit: Retrofit
    ): SupabaseAuthApi {
        return retrofit.create(SupabaseAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSupabaseStoryApi(
        @Named("supabase") retrofit: Retrofit
    ): SupabaseStoryApi {
        return retrofit.create(SupabaseStoryApi::class.java)
    }

}
