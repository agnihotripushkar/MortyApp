package com.devpush.morty.features.characterdetails.data.repository

import com.devpush.morty.features.characterdetails.domain.repository.CharacterRepository
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterGender
import com.devpush.network.models.domain.CharacterPage
import com.devpush.network.models.domain.CharacterStatus
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for CharacterRepositoryImpl
 */
class CharacterRepositoryImplTest {

    private lateinit var ktorClient: KtorClient
    private lateinit var repository: CharacterRepository

    @Before
    fun setup() {
        ktorClient = mockk()
        repository = CharacterRepositoryImpl(ktorClient)
    }

    @Test
    fun `fetchCharacterPage should delegate to ktorClient getCharacterByPage`() = runTest {
        val page = 1
        val params = mapOf("name" to "Rick")
        val expectedCharacterPage = createMockCharacterPage()
        
        coEvery { 
            ktorClient.getCharacterByPage(page, params) 
        } returns KtorClient.ApiOperation.Success(expectedCharacterPage)
        
        val result = repository.fetchCharacterPage(page, params)
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertEquals(expectedCharacterPage, (result as KtorClient.ApiOperation.Success).data)
        coVerify { ktorClient.getCharacterByPage(page, params) }
    }

    @Test
    fun `fetchCharacterPage should return failure when ktorClient fails`() = runTest {
        val page = 1
        val params = emptyMap<String, String>()
        val exception = Exception("Network error")
        
        coEvery { 
            ktorClient.getCharacterByPage(page, params) 
        } returns KtorClient.ApiOperation.Failure(exception)
        
        val result = repository.fetchCharacterPage(page, params)
        
        assertTrue(result is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (result as KtorClient.ApiOperation.Failure).exception)
    }

    @Test
    fun `fetchCharacter should delegate to ktorClient getCharacters`() = runTest {
        val characterId = 1
        val expectedCharacter = createMockCharacter(id = characterId)
        
        coEvery { 
            ktorClient.getCharacters(characterId) 
        } returns KtorClient.ApiOperation.Success(expectedCharacter)
        
        val result = repository.fetchCharacter(characterId)
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertEquals(expectedCharacter, (result as KtorClient.ApiOperation.Success).data)
        coVerify { ktorClient.getCharacters(characterId) }
    }

    @Test
    fun `fetchCharacter should return failure when ktorClient fails`() = runTest {
        val characterId = 1
        val exception = Exception("Character not found")
        
        coEvery { 
            ktorClient.getCharacters(characterId) 
        } returns KtorClient.ApiOperation.Failure(exception)
        
        val result = repository.fetchCharacter(characterId)
        
        assertTrue(result is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (result as KtorClient.ApiOperation.Failure).exception)
    }

    @Test
    fun `fetchAllCharactersByName should delegate to ktorClient searchAllCharactersByName`() = runTest {
        val searchQuery = "Rick"
        val expectedCharacters = listOf(
            createMockCharacter(id = 1, name = "Rick Sanchez"),
            createMockCharacter(id = 2, name = "Rick Prime")
        )
        
        coEvery { 
            ktorClient.searchAllCharactersByName(searchQuery) 
        } returns KtorClient.ApiOperation.Success(expectedCharacters)
        
        val result = repository.fetchAllCharactersByName(searchQuery)
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertEquals(expectedCharacters, (result as KtorClient.ApiOperation.Success).data)
        coVerify { ktorClient.searchAllCharactersByName(searchQuery) }
    }

    @Test
    fun `fetchAllCharactersByName should return failure when search fails`() = runTest {
        val searchQuery = "NonExistent"
        val exception = Exception("No results found")
        
        coEvery { 
            ktorClient.searchAllCharactersByName(searchQuery) 
        } returns KtorClient.ApiOperation.Failure(exception)
        
        val result = repository.fetchAllCharactersByName(searchQuery)
        
        assertTrue(result is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (result as KtorClient.ApiOperation.Failure).exception)
    }

    private fun createMockCharacter(
        id: Int = 1,
        name: String = "Rick Sanchez"
    ): Character {
        return Character(
            created = "2017-11-04T18:48:46.250Z",
            episodeIds = listOf(1, 2, 3),
            gender = CharacterGender.Male,
            id = id,
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
            location = Character.Location(name = "Earth", url = ""),
            name = name,
            origin = Character.Origin(name = "Earth (C-137)", url = ""),
            species = "Human",
            status = CharacterStatus.Alive,
            type = ""
        )
    }

    private fun createMockCharacterPage(): CharacterPage {
        return CharacterPage(
            info = CharacterPage.Info(count = 1, pages = 1, next = null, prev = null),
            characters = listOf(createMockCharacter())
        )
    }
}
