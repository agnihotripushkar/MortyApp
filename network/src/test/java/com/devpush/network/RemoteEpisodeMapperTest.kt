package com.devpush.network

import com.devpush.network.models.domain.Episode
import com.devpush.network.models.remote.RemoteEpisode
import com.devpush.network.models.remote.toDomainEpisode
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for RemoteEpisode to Episode mapper
 */
class RemoteEpisodeMapperTest {

    @Test
    fun `toDomainEpisode should extract season number correctly from S01E01 format`() {
        val remote = RemoteEpisode(
            id = 1,
            name = "Pilot",
            episode = "S01E01",
            air_date = "December 2, 2013",
            characters = listOf()
        )
        
        val domain = remote.toDomainEpisode()
        
        assertEquals(1, domain.seasonNumber)
    }

    @Test
    fun `toDomainEpisode should extract episode number correctly from S01E01 format`() {
        val remote = RemoteEpisode(
            id = 1,
            name = "Pilot",
            episode = "S01E01",
            air_date = "December 2, 2013",
            characters = listOf()
        )
        
        val domain = remote.toDomainEpisode()
        
        assertEquals(1, domain.episodeNumber)
    }

    @Test
    fun `toDomainEpisode should handle double digit season numbers`() {
        val remote = RemoteEpisode(
            id = 100,
            name = "Test Episode",
            episode = "S10E05",
            air_date = "January 1, 2024",
            characters = listOf()
        )
        
        val domain = remote.toDomainEpisode()
        
        assertEquals(10, domain.seasonNumber)
        assertEquals(5, domain.episodeNumber)
    }

    @Test
    fun `toDomainEpisode should handle double digit episode numbers`() {
        val remote = RemoteEpisode(
            id = 50,
            name = "Test Episode",
            episode = "S05E15",
            air_date = "January 1, 2024",
            characters = listOf()
        )
        
        val domain = remote.toDomainEpisode()
        
        assertEquals(5, domain.seasonNumber)
        assertEquals(15, domain.episodeNumber)
    }

    @Test
    fun `toDomainEpisode should extract character IDs from URLs`() {
        val remote = RemoteEpisode(
            id = 1,
            name = "Pilot",
            episode = "S01E01",
            air_date = "December 2, 2013",
            characters = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
                "https://rickandmortyapi.com/api/character/35"
            )
        )
        
        val domain = remote.toDomainEpisode()
        
        assertEquals(listOf(1, 2, 35), domain.characterIdsInEpisode)
    }

    @Test
    fun `toDomainEpisode should map all fields correctly`() {
        val remote = RemoteEpisode(
            id = 28,
            name = "The Ricklantis Mixup",
            episode = "S03E07",
            air_date = "September 10, 2017",
            characters = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2"
            )
        )
        
        val domain = remote.toDomainEpisode()
        
        assertEquals(28, domain.id)
        assertEquals("The Ricklantis Mixup", domain.name)
        assertEquals(3, domain.seasonNumber)
        assertEquals(7, domain.episodeNumber)
        assertEquals("September 10, 2017", domain.airDate)
        assertEquals(listOf(1, 2), domain.characterIdsInEpisode)
    }

    @Test
    fun `toDomainEpisode should handle empty character list`() {
        val remote = RemoteEpisode(
            id = 1,
            name = "Pilot",
            episode = "S01E01",
            air_date = "December 2, 2013",
            characters = emptyList()
        )
        
        val domain = remote.toDomainEpisode()
        
        assertTrue(domain.characterIdsInEpisode.isEmpty())
    }
}
