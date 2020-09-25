package com.corootine.fuzzy.server.dao.appinstance

import org.jetbrains.exposed.sql.Table

object AppInstanceEntityTable : Table() {
    val appInstanceId = varchar("appInstanceId", 6)
    val notificationToken = varchar("notificationToken", 255)

    override val primaryKey = PrimaryKey(appInstanceId, name = "PK_appInstanceId")

    init {
        index(true, notificationToken)
    }
}

data class AppInstanceEntity(val appInstanceId: String, val notificationToken: String)