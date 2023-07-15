package com.frontmatic.scrobbleview.data

import com.frontmatic.scrobbleview.data.model.User
import com.frontmatic.scrobbleview.data.repository.Repository


class SaveUsernameUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(username: String) {
        repository.saveUsername(username)
    }
}

class GetUsernameUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getUsername()
}

class GetUserInfoUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getUserInfo()
}

class SaveUserInfoUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(user: User) = repository.saveUserInfo(user)
}


class GetAllFriendsUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getAllFriends()
}

class DeleteAllFriendsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.deleteAllFriends()
}

class GetUserChangedUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getUserChanged()
}

class SaveUserChangedUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(userChanged: Boolean) = repository.saveUserChanged(userChanged)
}

data class UseCases(
    val saveUsername: SaveUsernameUseCase,
    val getUsername: GetUsernameUseCase,
    val getAllFriends: GetAllFriendsUseCase,
    val getUserInfo: GetUserInfoUseCase,
    val saveUserInfo: SaveUserInfoUseCase,
    val deleteAllFriends: DeleteAllFriendsUseCase,
    val getUserChanged: GetUserChangedUseCase,
    val saveUserChanged: SaveUserChangedUseCase
)