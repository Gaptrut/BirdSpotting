plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false

}
buildscript {
    repositories {

    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.1")
        classpath("com.google.gms:google-services:4.4.0")
    }
}

allprojects {
    repositories {

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
