plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.ktor.serialization.kotlinx.json)
}