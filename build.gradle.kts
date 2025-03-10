plugins {
    alias(libs.plugins.kotlin.jvm) // For refer kotlin plugin via `libs.plugin.kotlin`
    alias(libs.plugins.ktor) // For refer `run` task via `task.named("run")`
}

group = "com.example"
version = "0.0.1"

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
    }
}

// Run `run` task in infrastructure subproject when `run` task in root project is executed.
tasks.named("run") {
    dependsOn(":infrastructure:run")
}
