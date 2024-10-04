// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // save args for navigation
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.2")

    }
}

plugins {
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

