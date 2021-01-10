package com.corootine.fuzzy.domain.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.iid.FirebaseInstanceId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideFirebaseInstanceId(): FirebaseInstanceId = FirebaseInstanceId.getInstance()

    @Singleton
    @Provides
    @AppInstanceSharedPreferences
    fun provideAppInstanceSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "e8144c95-db32-4ecd-aa0e-95ba262843fb",
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    @NotificationTokenSharedPreferences
    fun provideNotificationTokenSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "814e4aa0-6801-4e1d-bc3d-e5f6805745ad",
            Context.MODE_PRIVATE
        )
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppInstanceSharedPreferences

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NotificationTokenSharedPreferences
}