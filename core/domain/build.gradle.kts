plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.murua.githubissues.core.domain"
    compileSdk = 34

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}