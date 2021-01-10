package com.corootine.fuzzy.domain.appinstanceid

import android.content.SharedPreferences
import androidx.core.content.edit
import com.corootine.fuzzy.domain.di.DomainModule.AppInstanceSharedPreferences
import com.corootine.fuzzy.domain.notificationtoken.NotificationTokenProvider
import com.corootine.fuzzy.network.registration.RegistrationController
import javax.inject.Inject

const val KEY_APP_INSTANCE_ID = "appInstanceId"

/**
 * Provides the unique app instance id.
 */
class AppInstanceIdProvider @Inject constructor(
    @AppInstanceSharedPreferences
    private val sharedPreferences: SharedPreferences,
    private val registrationController: RegistrationController,
    private val notificationTokenProvider: NotificationTokenProvider,
) {

    /**
     * Returns the stored unique app instance id.
     * If there is no id stored, it will be fetched from the backend and stored.
     *
     * @return unique app instance id
     */
    suspend fun get(): AppInstanceId {
        return if (sharedPreferences.contains(KEY_APP_INSTANCE_ID)) {
            AppInstanceId(sharedPreferences.getString(KEY_APP_INSTANCE_ID, "")!!)
        } else {
            val token = notificationTokenProvider.get()
            val request = RegistrationController.Request(token)
            val response = registrationController.register(request)
            sharedPreferences.edit(true) {
                putString(KEY_APP_INSTANCE_ID, response.appInstanceId)
            }
            AppInstanceId(response.appInstanceId)
        }
    }
}