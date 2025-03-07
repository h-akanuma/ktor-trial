package com.example.shared.database

import kotlin.properties.Delegates

object DatabaseConfig {
    var driverClassName: String by Delegates.notNull()
    var jdbcUrl: String by Delegates.notNull()
    var userName: String by Delegates.notNull()
    var password: String by Delegates.notNull()
    var cloudSqlInstanceName: String by Delegates.notNull()
    var maximumPoolSize: Int by Delegates.notNull()
    var isAutoCommit: Boolean by Delegates.notNull()
    var transactionIsolation: String by Delegates.notNull()
    var maxLifetime: Long by Delegates.notNull()
    var idleTimeOut: Long by Delegates.notNull()
    var minimumIdle: Int by Delegates.notNull()
}
