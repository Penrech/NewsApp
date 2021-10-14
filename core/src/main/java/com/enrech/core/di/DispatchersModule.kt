package com.enrech.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object DispatchersModule {

    const val MAIN = "main"
    const val IO = "io"
    const val DEFAULT = "default"
    const val UNCONFINED = "unconfined"

    @ViewModelScoped
    @Provides
    @Named(MAIN)
    fun provideMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @ViewModelScoped
    @Provides
    @Named(IO)
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @ViewModelScoped
    @Provides
    @Named(DEFAULT)
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @ViewModelScoped
    @Provides
    @Named(UNCONFINED)
    fun provideUnconfinedCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}