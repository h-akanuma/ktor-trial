buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:11.3.3")
    }
}

plugins {
    alias(libs.plugins.flyway)
}

dependencies {
    implementation(libs.mysql.connector.j)
    implementation(libs.mysql.socket.factory.connector.j8)
}

val trialDB =
    System.getenv("JDBC_URL") ?: "jdbc:mysql://localhost:3316/ktor_trial?allowPublicKeyRetrieval=true&useSSL=false"
val testDB =
    System.getenv("TEST_JDBC_URL") ?: "jdbc:mysql://localhost:3317/test_db?allowPublicKeyRetrieval=true&useSSL=false"

val dbUserName = "test_user"
val dbPassword = "password"

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
