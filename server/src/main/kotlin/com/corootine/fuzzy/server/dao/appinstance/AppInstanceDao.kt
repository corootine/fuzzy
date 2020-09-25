package com.corootine.fuzzy.server.dao.appinstance

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AppInstanceDao {

    fun exists(appInstanceId: String): Boolean {
        return AppInstanceEntityTable.select {
            AppInstanceEntityTable.appInstanceId.eq(appInstanceId)
        }.distinct
    }

    fun insert(appInstanceEntity: AppInstanceEntity) = transaction {
        AppInstanceEntityTable.insert {
            it[appInstanceId] = appInstanceEntity.appInstanceId
            it[notificationToken] = appInstanceEntity.notificationToken
        }
    }
}