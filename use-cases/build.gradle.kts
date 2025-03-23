plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    testImplementation(project(":test-lib"))

    implementation(libs.ktor.serialization.kotlinx.json)
}