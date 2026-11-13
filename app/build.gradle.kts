import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val localPropertiesFile =
    rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}
val storyNestApiUrl = providers.gradleProperty(
    "STORYNEST_API_BASE_URL"
).orElse(
    localProperties.getProperty("STORYNEST_API_BASE_URL")
        ?: "https://storynest-backend.storynest-sunil.workers.dev/"
).get()
android {
    namespace = "com.sd.storyteller"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sd.storyteller"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // This is your Cloudflare Worker URL, not a Gemini API key.
        // Add STORYNEST_API_BASE_URL to local.properties for your deployment.
        defaultConfig {
            buildConfigField(
                "String",
                "STORYNEST_API_BASE_URL",
                "\"$storyNestApiUrl\""
            )
        }

        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"${localProperties.getProperty(
                "SUPABASE_URL",
                "https://example.supabase.co/"
            )}\""
        )

        buildConfigField(
            "String",
            "SUPABASE_PUBLISHABLE_KEY",
            "\"${localProperties.getProperty(
                "SUPABASE_PUBLISHABLE_KEY",
                ""
            )}\""
        )

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
        }
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.material3)

    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.dagger.hilt)

    implementation(libs.androidx.hilt.navigation)
    implementation(libs.androidx.ui.graphics)

    ksp(libs.dagger.hilt.compiler)

    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.room.ktx)

    ksp(libs.androidx.room.compiler)

    implementation(libs.coil.compose)

    implementation(libs.coil.network.okhttp)

    implementation(libs.datastore)

    implementation(libs.timber)

    implementation(libs.lottie.compose)

    implementation(libs.androidx.camera.core)

    implementation(libs.androidx.camera.camera2)

    implementation(libs.androidx.camera.lifecycle)

    implementation(libs.androidx.camera.view)

    debugImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    implementation(libs.kotlinx.serialization.json)

}

configurations.all {
    resolutionStrategy {
        force("androidx.concurrent:concurrent-futures:1.2.0")
        force("androidx.concurrent:concurrent-futures-ktx:1.2.0")
    }
}