package com.devpush.network.models.domain

import com.devpush.network.constants.NetworkConstants

sealed class CharacterGender(val displayName: String) {
    object Male: CharacterGender(NetworkConstants.CharacterGender.MALE)
    object Female: CharacterGender(NetworkConstants.CharacterGender.FEMALE)
    object Genderless: CharacterGender(NetworkConstants.CharacterGender.GENDERLESS)
    object Unknown: CharacterGender(NetworkConstants.CharacterGender.UNKNOWN)
}