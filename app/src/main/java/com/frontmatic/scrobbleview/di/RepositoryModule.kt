package com.frontmatic.scrobbleview.di

import android.content.Context
import com.frontmatic.scrobbleview.data.DeleteAllFriendsUseCase
import com.frontmatic.scrobbleview.data.GetAllFriendsUseCase
import com.frontmatic.scrobbleview.data.GetUserChangedUseCase
import com.frontmatic.scrobbleview.data.GetUserInfoByNameUseCase
import com.frontmatic.scrobbleview.data.GetUsernameUseCase
import com.frontmatic.scrobbleview.data.SaveUserChangedUseCase
import com.frontmatic.scrobbleview.data.SaveUserInfoUseCase
import com.frontmatic.scrobbleview.data.SaveUsernameUseCase
import com.frontmatic.scrobbleview.data.UseCases
import com.frontmatic.scrobbleview.data.repository.DataStoreOperations
import com.frontmatic.scrobbleview.data.repository.Repository
import com.frontmatic.scrobbleview.data.repository.impl.DataStoreOperationsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(
            context = context
        )
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveUsername = SaveUsernameUseCase(repository),
            getUsername = GetUsernameUseCase(repository),
            getAllFriends = GetAllFriendsUseCase(repository),
            getUserInfoByName = GetUserInfoByNameUseCase(repository),
            saveUserInfo = SaveUserInfoUseCase(repository),
            deleteAllFriends = DeleteAllFriendsUseCase(repository),
            getUserChanged = GetUserChangedUseCase(repository),
            saveUserChanged = SaveUserChangedUseCase(repository),
        )
    }
}