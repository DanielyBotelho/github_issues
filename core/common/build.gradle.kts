plugins {
    id("com.android.library")
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.murua.githubissues.core.common"
    compileSdk = 34

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)

    testImplementation(kotlin("test"))
}