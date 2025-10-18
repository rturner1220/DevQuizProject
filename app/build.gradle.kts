plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.devquiz"

    // Use what your template created; 34 or 35 also work if your AGP is older
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.devquiz"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Java/Kotlin toolchains
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Enable Jetpack Compose
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Kotlin compiler extension for Compose (match your Android Studio suggestion if different)
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // Version catalog deps you already had
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // --- Compose BOM keeps all Compose libs on a consistent version ---
    implementation(platform(libs.compose.bom))

    // Core Compose
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    // Material 3 composable (for your Compose UI)
    implementation(libs.material3)

    // Android Material Components (for XML theme)
    implementation(libs.material)

    // Optional helpers
    implementation(libs.compose.foundation)
    implementation(libs.lifecycle.runtime.ktx)
}