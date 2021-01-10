package com.corootine.fuzzy.domain.notificationtoken

import android.content.SharedPreferences
import com.corootine.fuzzy.domain.di.DomainModule.NotificationTokenSharedPreferences
import com.google.firebase.iid.FirebaseInstanceId
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val KEY_NOTIFICATION_TOKEN = "notificationToken"

/**
 * Provides the notification token.
 */
class NotificationTokenProvider @Inject constructor(
    @NotificationTokenSharedPreferences
    private val sharedPreferences: SharedPreferences,
    private val firebaseInstanceId: FirebaseInstanceId
) {

    /**
     * Retrieves the notification token either from the repository if available, or directly from firebase.
     * In case the retrieval fails, this method will fail with [NotificationTokenRetrievalException].
     *
     * @return the notification token
     * @throws NotificationTokenRetrievalException in case the retrieval fails
     */
    suspend fun get(): String = suspendCoroutine { continuation ->
        if (sharedPreferences.contains(KEY_NOTIFICATION_TOKEN)) {
            val result = sharedPreferences.getString(KEY_NOTIFICATION_TOKEN, "")!!
            continuation.resume(result)
        } else {
            firebaseInstanceId.instanceId
                .addOnSuccessListener {
                    continuation.resume(it.token)
                }
                .addOnFailureListener { cause ->
                    continuation.resumeWithException(
                        NotificationTokenRetrievalException(cause)
                    )
                }
                .addOnCanceledListener {
                    continuation.resumeWithException(
                        NotificationTokenRetrievalException()
                    )
                }
        }
    }
}