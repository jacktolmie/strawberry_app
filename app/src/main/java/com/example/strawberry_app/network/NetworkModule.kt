package com.example.strawberry_app.network

import com.example.strawberry_app.network.protocol.IncomingMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.SharedFlow

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideServerMessages(networkManager: NetworkManager): SharedFlow<@JvmSuppressWildcards IncomingMessage> {
        return networkManager.serverMessages
    }
}