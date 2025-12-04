import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.hiltApplication)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.safe.args.navigation)
    // Google services / Crashlytics plugins temporarily disabled for local build to avoid task cycles
}

android {
    namespace = "com.hellotractor.android.notes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hellotractor.android.notes"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":theme"))
    implementation(project(":presentation"))

    implementation(libs.expandable.fab)
    implementation(libs.facebook.shimmer.effect)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.android)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.utilities)
    implementation(libs.bundles.google)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.viewModels)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.image.tools)
    implementation(libs.kotlin.extension)
    implementation(libs.androidx.browser.tab)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.google.firebase.messaging)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    ksp(libs.hilt.compiler)

    implementation(libs.bundles.review)
    implementation(libs.bundles.otp)
    implementation(libs.firebase.remote.config)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
    testImplementation(libs.bundles.unit.test.dependinces)
    testImplementation(libs.hilt.android.unit)
    androidTestImplementation(libs.hilt.android.unit)
    kspAndroidTest(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestUtil(libs.androidx.test.orchestrator)
    debugImplementation(libs.androidx.fragment.testing.manifest)
    androidTestImplementation(libs.bundles.android.test.dependencies)
    androidTestImplementation(libs.bundles.android.ui.test.dependencies)
}