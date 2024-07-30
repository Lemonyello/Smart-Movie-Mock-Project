plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hangpp.smartmovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hangpp.smartmovie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    viewBinding {
        enable = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            versionNameSuffix = "release"
            signingConfig = signingConfigs.getByName("debug")
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = "debug"
        }
    }

    flavorDimensions += "versions"

    productFlavors {
        create("free") {
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
//            resValue("string", "app_name", "Smart Movie Free")
            dimension = "versions"
        }

        create("premium") {
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"
//            resValue("string", "app_name", "Smart Movie Premium")
            dimension = "versions"
        }
    }

//    sourceSets {
//        getByName("free") {
//            res.srcDirs("src/main/res")
//            java.srcDirs("src/main/java")
//            manifest.srcFile("src/main/AndroidManifest.xml")
//        }
//
//        getByName("pro") {
//            res.srcDirs("src/main/res")
//            java.srcDirs("src/main/java")
//            manifest.srcFile("src/pro/AndroidManifest.xml")
//        }
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation( project(":data"))
    implementation( project(":domain"))

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.squareup.picasso:picasso:2.4.0")
    implementation ("androidx.work:work-runtime-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.1")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Mockito
//    testImplementation("org.mockito:mockito-core:3.11.2")
//    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
//    testImplementation("org.mockito:mockito-inline:3.2.0")
//    androidTestImplementation ("org.mockito:mockito-android:2.12.0")

    // AndroidX Testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.0")

    // navigation
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // pull to refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // MockK
    testImplementation("io.mockk:mockk:1.13.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
}