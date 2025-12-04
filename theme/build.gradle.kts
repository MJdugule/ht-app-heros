import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.hellotractor.notes.theme"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.google)
    implementation(libs.firebase.remote.config)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.coroutines)
    implementation(libs.kotlin.extension)
    implementation(libs.androidx.coroutines.android)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.google.playstore)
    ksp(libs.hilt.compiler)
}
