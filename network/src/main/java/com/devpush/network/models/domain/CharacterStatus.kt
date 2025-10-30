package com.devpush.network.models.domain

import com.devpush.network.constants.NetworkConstants

sealed class CharacterStatus(val displayName: String) {
    object Alive: CharacterStatus(NetworkConstants.CharacterStatus.ALIVE)
    object Dead: CharacterStatus(NetworkConstants.CharacterStatus.DEAD)
    object Unknown: CharacterStatus(NetworkConstants.CharacterStatus.UNKNOWN)
}