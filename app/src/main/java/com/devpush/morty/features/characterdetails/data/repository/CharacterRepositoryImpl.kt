package com.devpush.morty.features.characterdetails.data.repository

import com.devpush.morty.features.characterdetails.domain.repository.CharacterRepository
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterPage
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor
    (private val ktorClient: KtorClient) : CharacterRepository {

    override suspend fun fetchCharacterPage(
        page: Int,
        params: Map<String, String>
    ): KtorClient.ApiOperation<CharacterPage> {
        return ktorClient.getCharacterByPage(pageNumber = page, queryParams = params)
    }

    override suspend fun fetchCharacter(characterId: Int): KtorClient.ApiOperation<Character> {
        return ktorClient.getCharacters(characterId)
    }

    override suspend fun fetchAllCharactersByName(searchQuery: String): KtorClient.ApiOperation<List<Character>> {
        return ktorClient.searchAllCharactersByName(searchQuery)
    }


}