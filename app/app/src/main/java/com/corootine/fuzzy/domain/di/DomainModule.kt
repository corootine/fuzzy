package com.corootine.fuzzy.domain.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.corootine.fuzzy.domain.userId.provide.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindUserIdProvider(userIdProviderLogic: UserIdProviderLogic): UserIdProvider

    companion object {

        @Singleton
        @Provides
        fun provideUserIdRepository(
            @ApplicationContext context: Context,
            @IODispatcher ioDispatcher: CoroutineDispatcher
        ): UserIdRepository {
            val preferences = context.getSharedPreferences("userIdPreferences", MODE_PRIVATE)
            return UserIdRepositoryLogic(preferences, ioDispatcher)
        }

        @Singleton
        @Provides
        fun provideRefreshUserIdController(retrofit: Retrofit): RefreshUserIdController {
            return retrofit.create(RefreshUserIdController::class.java)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {

    @CPUDispatcher
    @Provides
    fun provideCpuDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IODispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @UIDispatcher
    @Provides
    fun provideUiDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @ApplicationScope
    @Provides
    fun provideApplicationScope(): CoroutineScope = ProcessLifecycleOwner.get().lifecycleScope
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CPUDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UIDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope