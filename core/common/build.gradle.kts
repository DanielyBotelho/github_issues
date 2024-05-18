plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.murua.githubissues.core.common"
    compileSdk = 34

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)

    testImplementation(kotlin("test"))
}