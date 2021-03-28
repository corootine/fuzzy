package com.corootine.fuzzy.domain.userId.di

import androidx.datastore.preferences.preferencesDataStore
import com.corootine.fuzzy.domain.userId.api.UserIdProvider
import com.corootine.fuzzy.domain.userId.implementation.RefreshUserIdController
import com.corootine.fuzzy.domain.userId.implementation.UserIdProviderLogic
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Singleton
    @Binds
    abstract fun bindUserIdProvider(userIdProviderLogic: UserIdProviderLogic): UserIdProvider

    @Singleton
    @Provides
    fun provideRefreshUserIdController(retrofit: Retrofit): RefreshUserIdController {
        return retrofit.create(RefreshUserIdController::class.java)
    }

    @Singleton
    @Provides
    fun provideUserIdDataStore() = preferencesDataStore("userIdPreferences")
}