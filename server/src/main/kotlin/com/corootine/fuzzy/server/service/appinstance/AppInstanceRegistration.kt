package com.corootine.fuzzy.server.service.appinstance

import com.corootine.fuzzy.server.dao.appinstance.AppInstanceDao
import com.corootine.fuzzy.server.dao.appinstance.AppInstanceEntity
import kotlin.random.Random

class AppInstanceRegistration(private val appInstanceDao: AppInstanceDao) {

    private val random = Random.Default

    fun register(notificationToken: String): String {
        val entity = AppInstanceEntity(random6DigitNumber(), notificationToken)

        return if (appInstanceDao.exists(entity.appInstanceId)) {
            register(notificationToken)
        } else {
            appInstanceDao.insert(entity)
            entity.appInstanceId
        }
    }

    private fun random6DigitNumber() = random.nextInt(100000, 999999).toString()
}