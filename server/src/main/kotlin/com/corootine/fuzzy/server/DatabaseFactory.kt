package com.corootine.fuzzy.server

import com.corootine.fuzzy.server.dao.appinstance.AppInstanceEntityTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:h2:file:/home/hoost/dev/fuzzy/server/db/db",
            user = "admin",
            password = "password",
            driver = "org.h2.Driver",
        )

        transaction {
            addLogger(StdOutSqlLogger)
            create(AppInstanceEntityTable)
        }
    }
}