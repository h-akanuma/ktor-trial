buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:11.3.3")
    }
}

plugins {
    alias(libs.plugins.flyway)
}

dependencies {
    implementation(project(":use-cases"))
    implementation(project(":common-lib"))

    implementation(libs.mysql.connector.j)
    implementation(libs.mysql.socket.factory.connector.j8)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.java.time)
    implementation(libs.kotlinx.coroutines.core)
}

val trialDB =
    System.getenv("JDBC_URL") ?: "jdbc:mysql://localhost:3316/ktor_trial?allowPublicKeyRetrieval=true&useSSL=false"
val testDB =
    System.getenv("TEST_JDBC_URL") ?: "jdbc:mysql://localhost:3317/test_db?allowPublicKeyRetrieval=true&useSSL=false"

val dbUserName = System.getenv("DB_USER") ?: "test_user"
val dbPassword = System.getenv("DB_PASSWORD") ?: "password"

task(name = "flywayMigrateTestDB", type = org.flywaydb.gradle.task.FlywayMigrateTask::class) {
    url = testDB
    user = dbUserName
    password = dbPassword
}

task("flywayMigrateAllDB", type = org.flywaydb.gradle.task.FlywayMigrateTask::class) {
    url = trialDB
    user = dbUserName
    password = dbPassword
    dependsOn("flywayMigrateTestDB")
}

task(name = "flywayCleanTestDB", type = org.flywaydb.gradle.task.FlywayCleanTask::class) {
    url = testDB
    user = dbUserName
    password = dbPassword
    cleanDisabled = false
}

task(name = "flywayCleanAllDB", type = org.flywaydb.gradle.task.FlywayCleanTask::class) {
    url = trialDB
    user = dbUserName
    password = dbPassword
    cleanDisabled = false
    dependsOn("flywayCleanTestDB")
}

// For using flywayMigrate task in staging and production env
flyway {
    url = trialDB
    user = dbUserName
    password = dbPassword
}
