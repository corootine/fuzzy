package com.corootine.fuzzy.domain.notificationtoken

data class NotificationTokenRetrievalException(override val cause: Throwable? = null) : Exception(cause)