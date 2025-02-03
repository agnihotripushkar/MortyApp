package com.devpush.morty.repositories

import com.devpush.network.KtorClient
import com.devpush.network.KtorClient.ApiOperation
import javax.inject.Inject
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterPage

class CharacterRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacterPage(
        page: Int,
        params: Map<String, String> = emptyMap()
    ): ApiOperation<CharacterPage> {
        return ktorClient.getCharacterByPage(pageNumber = page, queryParams = params)
    }

    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacters(characterId)
    }

    suspend fun fetchAllCharactersByName(searchQuery: String): ApiOperation<List<Character>> {
        return ktorClient.searchAllCharactersByName(searchQuery)
    }



}