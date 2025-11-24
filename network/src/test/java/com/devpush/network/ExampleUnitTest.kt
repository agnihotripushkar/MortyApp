package com.devpush.network

import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterGender
import com.devpush.network.models.domain.CharacterStatus
import com.devpush.network.models.remote.RemoteCharacter
import com.devpush.network.models.remote.toDomainCharacter
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for ApiOperation sealed interface and domain model mappers
 */
class ApiOperationAndMapperTest {

    @Test
    fun `ApiOperation Success should hold data`() {
        val data = "test data"
        val operation = KtorClient.ApiOperation.Success(data)
        
        assertEquals(data, operation.data)
    }

    @Test
    fun `ApiOperation Failure should hold exception`() {
        val exception = Exception("test error")
        val operation = KtorClient.ApiOperation.Failure<String>(exception)
        
        assertEquals(exception, operation.exception)
    }

    @Test
    fun `ApiOperation mapSuccess should transform data on Success`() {
        val operation = KtorClient.ApiOperation.Success(5)
        val mapped = operation.mapSuccess { it * 2 }
        
        assertTrue(mapped is KtorClient.ApiOperation.Success)
        assertEquals(10, (mapped as KtorClient.ApiOperation.Success).data)
    }

    @Test
    fun `ApiOperation mapSuccess should preserve Failure`() {
        val exception = Exception("test error")
        val operation = KtorClient.ApiOperation.Failure<Int>(exception)
        val mapped = operation.mapSuccess { it * 2 }
        
        assertTrue(mapped is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (mapped as KtorClient.ApiOperation.Failure).exception)
    }

    @Test
    fun `ApiOperation onSuccess should execute block on Success`() {
        var executed = false
        val operation = KtorClient.ApiOperation.Success("test")
        
        kotlinx.coroutines.runBlocking {
            operation.onSuccess { executed = true }
        }
        
        assertTrue(executed)
    }

    @Test
    fun `ApiOperation onSuccess should not execute block on Failure`() {
        var executed = false
        val operation = KtorClient.ApiOperation.Failure<String>(Exception("error"))
        
        kotlinx.coroutines.runBlocking {
            operation.onSuccess { executed = true }
        }
        
        assertFalse(executed)
    }

    @Test
    fun `ApiOperation onFailure should execute block on Failure`() {
        var executed = false
        val exception = Exception("test error")
        val operation = KtorClient.ApiOperation.Failure<String>(exception)
        
        operation.onFailure { executed = true }
        
        assertTrue(executed)
    }

    @Test
    fun `ApiOperation onFailure should not execute block on Success`() {
        var executed = false
        val operation = KtorClient.ApiOperation.Success("test")
        
        operation.onFailure { executed = true }
        
        assertFalse(executed)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map female gender correctly`() {
        val remote = createRemoteCharacter(gender = "Female")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterGender.Female, domain.gender)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map male gender correctly`() {
        val remote = createRemoteCharacter(gender = "Male")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterGender.Male, domain.gender)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map genderless correctly`() {
        val remote = createRemoteCharacter(gender = "Genderless")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterGender.Genderless, domain.gender)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map unknown gender correctly`() {
        val remote = createRemoteCharacter(gender = "unknown")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterGender.Unknown, domain.gender)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map alive status correctly`() {
        val remote = createRemoteCharacter(status = "Alive")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterStatus.Alive, domain.status)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map dead status correctly`() {
        val remote = createRemoteCharacter(status = "Dead")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterStatus.Dead, domain.status)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map unknown status correctly`() {
        val remote = createRemoteCharacter(status = "unknown")
        val domain = remote.toDomainCharacter()
        
        assertEquals(CharacterStatus.Unknown, domain.status)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should extract episode IDs from URLs`() {
        val remote = createRemoteCharacter(
            episodes = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/10"
            )
        )
        val domain = remote.toDomainCharacter()
        
        assertEquals(listOf(1, 2, 10), domain.episodeIds)
    }

    @Test
    fun `RemoteCharacter toDomainCharacter should map all fields correctly`() {
        val remote = RemoteCharacter(
            created = "2017-11-04T18:48:46.250Z",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            gender = "Male",
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = RemoteCharacter.Location(name = "Earth", url = "https://rickandmortyapi.com/api/location/20"),
            name = "Rick Sanchez",
            origin = RemoteCharacter.Origin(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1"),
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1"
        )
        
        val domain = remote.toDomainCharacter()
        
        assertEquals("2017-11-04T18:48:46.250Z", domain.created)
        assertEquals(listOf(1), domain.episodeIds)
        assertEquals(CharacterGender.Male, domain.gender)
        assertEquals(1, domain.id)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", domain.imageUrl)
        assertEquals("Earth", domain.location.name)
        assertEquals("https://rickandmortyapi.com/api/location/20", domain.location.url)
        assertEquals("Rick Sanchez", domain.name)
        assertEquals("Earth (C-137)", domain.origin.name)
        assertEquals("https://rickandmortyapi.com/api/location/1", domain.origin.url)
        assertEquals("Human", domain.species)
        assertEquals(CharacterStatus.Alive, domain.status)
        assertEquals("", domain.type)
    }

    private fun createRemoteCharacter(
        gender: String = "Male",
        status: String = "Alive",
        episodes: List<String> = listOf("https://rickandmortyapi.com/api/episode/1")
    ): RemoteCharacter {
        return RemoteCharacter(
            created = "2017-11-04T18:48:46.250Z",
            episode = episodes,
            gender = gender,
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = RemoteCharacter.Location(name = "Earth", url = "https://rickandmortyapi.com/api/location/20"),
            name = "Rick Sanchez",
            origin = RemoteCharacter.Origin(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1"),
            species = "Human",
            status = status,
            type = "",
            url = "https://rickandmortyapi.com/api/character/1"
        )
    }
}