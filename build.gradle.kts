plugins {
    alias(libs.plugins.kotlin.jvm) // For refer kotlin plugin via `libs.plugin.kotlin`
    alias(libs.plugins.ktor) // For refer `run` task via `task.named("run")`
}

group = "com.example"
version = "0.0.1"

ext {
    set("trialDB", System.getenv("JDBC_URL") ?: "jdbc:mysql://localhost:3316/ktor_trial?allowPublicKeyRetrieval=true&useSSL=false")
    set("testDB", System.getenv("TEST_JDBC_URL") ?: "jdbc:mysql://localhost:3317/test_db?allowPublicKeyRetrieval=true&useSSL=false")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    // For refer libs.versions.toml in subprojects section.
    val libs = rootProject.libs

    // For using `implementation()` in dependencies section.
    apply(plugin = libs.plugins.kotlin.asProvider().get().pluginId)

    dependencies {
        implementation(libs.hikaricp)
        implementation(libs.kotlin.result)

        testImplementation(libs.junit.jupiter.api)
        testImplementation(libs.junit.jupiter.params)
        testImplementation(libs.junit.jupiter.engine)
        testImplementation(libs.assertj.core)
        testImplementation(libs.koin.test)
    }

    tasks {
        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }

            // 変更がないテストも毎回実行されるようにする
            dependsOn("cleanTest")
        }
    }
}

// Run `run` task in infrastructure subproject when `run` task in root project is executed.
tasks.named("run") {
    dependsOn(":infrastructure:run")
}
