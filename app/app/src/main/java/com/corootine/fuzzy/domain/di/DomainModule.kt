package com.corootine.fuzzy.domain.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.corootine.fuzzy.domain.userId.UserIdProvider
import com.corootine.fuzzy.domain.userId.implementation.RefreshUserIdController
import com.corootine.fuzzy.domain.userId.implementation.UserIdProviderLogic
import com.corootine.fuzzy.domain.userId.implementation.UserIdRepository
import com.corootine.fuzzy.domain.userId.implementation.UserIdRepositoryLogic
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindUserIdProvider(userIdProviderLogic: UserIdProviderLogic): UserIdProvider

    companion object {

        @Singleton
        @Provides
        fun provideUserIdRepository(@ApplicationContext context: Context): UserIdRepository {
            val preferences = context.getSharedPreferences("userIdPreferences", MODE_PRIVATE)
            return UserIdRepositoryLogic(preferences)
        }

        @Singleton
        @Provides
        fun provideRefreshUserIdController(retrofit: Retrofit): RefreshUserIdController {
            return retrofit.create(RefreshUserIdController::class.java)
        }
    }
}