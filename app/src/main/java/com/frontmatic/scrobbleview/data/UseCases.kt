package com.frontmatic.scrobbleview.data

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
    operator fun invoke(username: String) = repository.getUserInfo(username)
}


class GetAllFriendsUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getAllFriends()
}

data class UseCases(
    val saveUsername: SaveUsernameUseCase,
    val getUsername: GetUsernameUseCase,
    val getAllFriendsUseCase: GetAllFriendsUseCase
)