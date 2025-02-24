import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.mysql.connector.j)
    implementation(libs.mysql.socket.factory.connector.j8)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks.named<JavaExec>("run") {
    environment("JDBC_URL", "jdbc:mysql://localhost:3316/ktor_trial?allowPublicKeyRetrieval=true&useSSL=false")
}
tasks.withType<Test> {
    environment("JDBC_URL", "jdbc:mysql://localhost:3317/ktor_trial?allowPublicKeyRetrieval=true&useSSL=false")
}
