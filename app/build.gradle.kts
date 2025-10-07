plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services") // ✅ add this
}



android {
    namespace = "com.mzansiplatess.app" //  updated from snippet
    compileSdk = 36 //  keeping latest (you had 36)

    defaultConfig {
        applicationId = "com.mzansiplatess.app" //  updated
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Expose BASE_URL to BuildConfig; override per buildType below if needed
        buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:3000/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // You can set a different debug URL here if desired
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:3000/\"")
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true //  enable Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.0" //  fix syntax
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}




dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM (manages versions automatically)
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material")

    //  Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0-alpha01")
    implementation(libs.androidx.ui.text)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase BOM (manages versions for all Firebase libs)
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")
    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Retrofit + Gson converter
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// OkHttp logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Coroutines (for networking on background threads)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

// Lifecycle ViewModel (compose-friendly)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

// Coil for images (optional, used for restaurant images)
    implementation("io.coil-kt:coil-compose:2.2.2")
    // Coroutines for async calls
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // DataStore Preferences for persisting settings
    implementation("androidx.datastore:datastore-preferences:1.1.1")



}
