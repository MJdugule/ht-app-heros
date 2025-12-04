import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.hellotractor.notes.data"
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
    implementation(project(":network"))

    implementation(libs.bundles.android)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)
    implementation(libs.bundles.retrofit)
    implementation(libs.firebase.database)
    implementation(libs.bundles.room)
    implementation(libs.bundles.utilities)
    implementation(libs.bundles.datastore)
    implementation(libs.kotlin.extension)
    implementation(libs.com.jakewharton.att)
    coreLibraryDesugaring(libs.core.library.desugaring)
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    testImplementation(libs.bundles.unit.test.dependinces)
    testImplementation(libs.bundles.android.test.dependencies)
}
