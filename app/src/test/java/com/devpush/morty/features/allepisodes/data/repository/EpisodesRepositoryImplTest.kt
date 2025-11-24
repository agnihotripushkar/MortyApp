package com.devpush.morty.features.allepisodes.data.repository

import com.devpush.morty.features.allepisodes.domain.repository.EpisodesRepository
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Episode
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for EpisodesRepositoryImpl
 */
class EpisodesRepositoryImplTest {

    private lateinit var ktorClient: KtorClient
    private lateinit var repository: EpisodesRepository

    @Before
    fun setup() {
        ktorClient = mockk()
        repository = EpisodesRepositoryImpl(ktorClient)
    }

    @Test
    fun `fetchAllEpisodes should delegate to ktorClient getAllEpisodes`() = runTest {
        val expectedEpisodes = listOf(
            createMockEpisode(id = 1, name = "Pilot"),
            createMockEpisode(id = 2, name = "Lawnmower Dog")
        )
        
        coEvery { 
            ktorClient.getAllEpisodes() 
        } returns KtorClient.ApiOperation.Success(expectedEpisodes)
        
        val result = repository.fetchAllEpisodes()
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertEquals(expectedEpisodes, (result as KtorClient.ApiOperation.Success).data)
        coVerify { ktorClient.getAllEpisodes() }
    }

    @Test
    fun `fetchAllEpisodes should return failure when ktorClient fails`() = runTest {
        val exception = Exception("Network error")
        
        coEvery { 
            ktorClient.getAllEpisodes() 
        } returns KtorClient.ApiOperation.Failure(exception)
        
        val result = repository.fetchAllEpisodes()
        
        assertTrue(result is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (result as KtorClient.ApiOperation.Failure).exception)
    }

    @Test
    fun `getEpisode should delegate to ktorClient getEpisode`() = runTest {
        val episodeId = 1
        val expectedEpisode = createMockEpisode(id = episodeId, name = "Pilot")
        
        coEvery { 
            ktorClient.getEpisode(episodeId) 
        } returns KtorClient.ApiOperation.Success(expectedEpisode)
        
        val result = repository.getEpisode(episodeId)
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertEquals(expectedEpisode, (result as KtorClient.ApiOperation.Success).data)
        coVerify { ktorClient.getEpisode(episodeId) }
    }

    @Test
    fun `getEpisode should return failure when episode not found`() = runTest {
        val episodeId = 999
        val exception = Exception("Episode not found")
        
        coEvery { 
            ktorClient.getEpisode(episodeId) 
        } returns KtorClient.ApiOperation.Failure(exception)
        
        val result = repository.getEpisode(episodeId)
        
        assertTrue(result is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (result as KtorClient.ApiOperation.Failure).exception)
    }

    @Test
    fun `fetchAllEpisodes should return empty list when no episodes exist`() = runTest {
        val emptyList = emptyList<Episode>()
        
        coEvery { 
            ktorClient.getAllEpisodes() 
        } returns KtorClient.ApiOperation.Success(emptyList)
        
        val result = repository.fetchAllEpisodes()
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertTrue((result as KtorClient.ApiOperation.Success).data.isEmpty())
    }

    private fun createMockEpisode(
        id: Int,
        name: String,
        seasonNumber: Int = 1,
        episodeNumber: Int = 1
    ): Episode {
        return Episode(
            id = id,
            name = name,
            seasonNumber = seasonNumber,
            episodeNumber = episodeNumber,
            airDate = "December 2, 2013",
            characterIdsInEpisode = listOf(1, 2, 3)
        )
    }
}
