import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import de.undercouch.gradle.tasks.download.Download

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.download)
}

val isOpenTelemetryReady = (System.getenv("IS_OPENTELEMETRY_READY") ?: "true") == "true"
val openTelemetryAgentJar = layout.buildDirectory.file("otel/opentelemetry-javaagent.jar").get().asFile

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")

    if (isOpenTelemetryReady) {
        applicationDefaultJvmArgs += "-javaagent:$openTelemetryAgentJar"
    }
}

dependencies {
    implementation(project(":interfaces"))

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
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit5)
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks.named<JavaExec>("run") {
    args = (project.findProperty("appArgs") as? String)?.split(" ") ?: listOf("-config=application.local.conf")
    environment("JDBC_URL", "jdbc:mysql://localhost:3316/ktor_trial?allowPublicKeyRetrieval=true&useSSL=false")
    environment("OTEL_SERVICE_NAME", "ktor-trial")
    environment("OTEL_EXPORTER_OTLP_PROTOCOL", "http/protobuf")
    environment("OTEL_EXPORTER_OTLP_TRACES_ENDPOINT", "http://localhost:4318/v1/traces")
    environment("OTEL_TRACES_EXPORTER", "otlp")
    environment("OTEL_METRICS_EXPORTER", "none")
    environment("OTEL_LOGS_EXPORTER", "none")
}

tasks.withType<Test> {
    environment("JDBC_URL", "jdbc:mysql://localhost:3317/test_db?allowPublicKeyRetrieval=true&useSSL=false")
}

task<Download>("downloadOpenTelemetryJavaAgent") {
    onlyIf { isOpenTelemetryReady }
    src("https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/${libs.versions.opentelemetry.java.instrumentation.get()}/opentelemetry-javaagent.jar")
    dest(file(openTelemetryAgentJar))
}

jib {
    from {
        // Java アプリケーション用の軽量な実行環境イメージ
        image = "gcr.io/distroless/java21"
    }
    container {
        // コンテナがリッスンするポート
        ports = listOf("8080")
        // 必要に応じてメインクラスを明示的に指定（JAR のマニフェストに設定されていれば不要）
        mainClass = "io.ktor.server.netty.EngineMain"
    }
}

tasks.configureEach {
    if (name == "downloadOpenTelemetryJavaAgent") return@configureEach
    dependsOn("downloadOpenTelemetryJavaAgent")
}
