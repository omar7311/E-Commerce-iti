plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // save args for navigation
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.e_commerce_iti"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.e_commerce_iti"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions { // take care of this part
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    implementation ("androidx.activity:activity-ktx:1.9.2")
    implementation ("com.google.accompanist:accompanist-pager:0.30.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")
    implementation ("androidx.compose.material3:material3:1.3.0")

    implementation("com.github.skydoves:landscapist-glide:1.3.7")
    implementation ("com.google.firebase:firebase-auth:23.0.0")
    implementation ("com.google.android.gms:play-services-auth:21.2.0") // Google Identity Services (One-Tap sign-in)
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.2")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
    implementation("androidx.navigation:navigation-compose:2.8.2")

    implementation("de.hdodenhof:circleimageview:3.1.0") // CircleImageView
    implementation("com.airbnb.android:lottie:6.0.0") // Lottie animations
    implementation("com.airbnb.android:lottie-compose:5.0.3") // Lottie in Compose

    implementation("io.coil-kt:coil-compose:2.0.0") // Coil for image loading

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Google Fonts
    implementation("com.google.android.material:compose-theme-adapter:1.2.1")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Unit Testing Libraries
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // AndroidX Test - JVM Testing
    testImplementation("androidx.test:core-ktx:1.5.0")

    // AndroidX Test - Instrumented Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Timber for Logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Hamcrest Libraries
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    androidTestImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")

    // Compose UI and material
    implementation ("androidx.compose.ui:ui:1.4.3")
    implementation ("androidx.compose.material3:material3:1.1.0")

    // Accompanist FlowLayout
    implementation ("com.google.accompanist:accompanist-flowlayout:0.36.0")
}
