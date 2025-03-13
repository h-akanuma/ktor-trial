package com.example

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder

fun DbSetupBuilder.deleteAllTables() {
    deleteAllFrom(
        listOf(
            "users"
        )
    )
}

fun DbSetupBuilder.resetAutoIncrement(tableName: String) {
    sql("ALTER TABLE `$tableName` auto_increment = 1;")
}
