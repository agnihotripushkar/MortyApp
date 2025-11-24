package com.devpush.morty.features.characterdetails.ui

import app.cash.turbine.test
import com.devpush.morty.core.commonui.DataPoint
import com.devpush.morty.features.characterdetails.domain.repository.CharacterRepository
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterGender
import com.devpush.network.models.domain.CharacterStatus
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for CharacterDetailsViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {

    private lateinit var characterRepository: CharacterRepository
    private lateinit var viewModel: CharacterDetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        characterRepository = mockk()
        viewModel = CharacterDetailsViewModel(characterRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        viewModel.stateFlow.test {
            val initialState = awaitItem()
            assertTrue(initialState is CharacterDetailsViewState.Loading)
        }
    }

    @Test
    fun `fetchCharacter should emit Success state with character data`() = runTest(testDispatcher) {
        val characterId = 1
        val character = createMockCharacter(id = characterId)
        
        coEvery { 
            characterRepository.fetchCharacter(characterId) 
        } returns KtorClient.ApiOperation.Success(character)
        
        viewModel.fetchCharacter(characterId)
        
        viewModel.stateFlow.test {
            val state = awaitItem()
            assertTrue(state is CharacterDetailsViewState.Success)
            assertEquals(character, (state as CharacterDetailsViewState.Success).character)
        }
    }

    @Test
    fun `fetchCharacter should emit Error state on failure`() = runTest(testDispatcher) {
        val characterId = 1
        val errorMessage = "Character not found"
        val exception = Exception(errorMessage)
        
        coEvery { 
            characterRepository.fetchCharacter(characterId) 
        } returns KtorClient.ApiOperation.Failure(exception)
        
        viewModel.fetchCharacter(characterId)
        
        viewModel.stateFlow.test {
            val state = awaitItem()
            assertTrue(state is CharacterDetailsViewState.Error)
            assertEquals(errorMessage, (state as CharacterDetailsViewState.Error).message)
        }
    }

    @Test
    fun `fetchCharacter should create correct data points`() = runTest(testDispatcher) {
        val characterId = 1
        val character = createMockCharacter(
            id = characterId,
            location = Character.Location("Citadel of Ricks", ""),
            species = "Human",
            gender = CharacterGender.Male,
            type = "Clone",
            origin = Character.Origin("Earth (C-137)", ""),
            episodeIds = listOf(1, 2, 3, 4, 5)
        )
        
        coEvery { 
            characterRepository.fetchCharacter(characterId) 
        } returns KtorClient.ApiOperation.Success(character)
        
        viewModel.fetchCharacter(characterId)
        
        viewModel.stateFlow.test {
            val successState = awaitItem() as CharacterDetailsViewState.Success
            val dataPoints = successState.characterDataPoints
            
            assertTrue(dataPoints.any { it.title == "Last known location" && it.description == "Citadel of Ricks" })
            assertTrue(dataPoints.any { it.title == "Species" && it.description == "Human" })
            assertTrue(dataPoints.any { it.title == "Gender" && it.description == "Male" })
            assertTrue(dataPoints.any { it.title == "Type" && it.description == "Clone" })
            assertTrue(dataPoints.any { it.title == "Origin" && it.description == "Earth (C-137)" })
            assertTrue(dataPoints.any { it.title == "Episode count" && it.description == "5" })
        }
    }

    @Test
    fun `fetchCharacter should not include type data point when type is empty`() = runTest(testDispatcher) {
        val characterId = 1
        val character = createMockCharacter(id = characterId, type = "")
        
        coEvery { 
            characterRepository.fetchCharacter(characterId) 
        } returns KtorClient.ApiOperation.Success(character)
        
        viewModel.fetchCharacter(characterId)
        
        viewModel.stateFlow.test {
            val successState = awaitItem() as CharacterDetailsViewState.Success
            val dataPoints = successState.characterDataPoints
            
            assertFalse(dataPoints.any { it.title == "Type" })
        }
    }

    @Test
    fun `fetchCharacter should verify repository is called with correct ID`() = runTest(testDispatcher) {
        val characterId = 42
        val character = createMockCharacter(id = characterId)
        
        coEvery { 
            characterRepository.fetchCharacter(characterId) 
        } returns KtorClient.ApiOperation.Success(character)
        
        viewModel.fetchCharacter(characterId)
        
        coVerify { characterRepository.fetchCharacter(characterId) }
    }

    private fun createMockCharacter(
        id: Int = 1,
        name: String = "Rick Sanchez",
        location: Character.Location = Character.Location("Earth", ""),
        species: String = "Human",
        gender: CharacterGender = CharacterGender.Male,
        type: String = "",
        origin: Character.Origin = Character.Origin("Earth (C-137)", ""),
        episodeIds: List<Int> = listOf(1, 2, 3)
    ): Character {
        return Character(
            created = "2017-11-04T18:48:46.250Z",
            episodeIds = episodeIds,
            gender = gender,
            id = id,
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
            location = location,
            name = name,
            origin = origin,
            species = species,
            status = CharacterStatus.Alive,
            type = type
        )
    }
}

