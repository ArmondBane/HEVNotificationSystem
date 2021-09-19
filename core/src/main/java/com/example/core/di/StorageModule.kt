package com.example.core.di

import com.example.core.data.local.SharedPreferencesStorage
import com.example.core.data.local.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class StorageModule {

    @Binds
    abstract fun bindSharedPreferencesStorage(storage: SharedPreferencesStorage): Storage
}