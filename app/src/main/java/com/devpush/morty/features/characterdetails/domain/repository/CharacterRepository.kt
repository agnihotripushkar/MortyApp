package com.devpush.morty.features.characterdetails.domain.repository

import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterPage

interface CharacterRepository {
    suspend fun fetchCharacterPage(
        page: Int,
        params: Map<String, String> = emptyMap()
    ): KtorClient.ApiOperation<CharacterPage>

    suspend fun fetchCharacter(characterId: Int): KtorClient.ApiOperation<Character>

    suspend fun fetchAllCharactersByName(searchQuery: String): KtorClient.ApiOperation<List<Character>>
}