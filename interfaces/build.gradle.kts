plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":use-cases"))

    implementation(libs.ktor.serialization.kotlinx.json)
}