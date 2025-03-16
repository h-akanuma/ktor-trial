dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":external"))
    implementation(project(":use-cases"))
    implementation(project(":common-lib"))

    implementation(libs.ktor.server.test.host)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.jackson)

    // koin
    implementation(libs.koin.ktor)

    // Koin for JUnit 5
    implementation(libs.koin.test.junit5)

    // add for database testing
    implementation(libs.dbsetup.kotlin)

    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
}