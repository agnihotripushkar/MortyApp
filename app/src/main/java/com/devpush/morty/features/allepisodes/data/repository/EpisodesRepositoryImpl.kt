package com.devpush.morty.features.allepisodes.data.repository

import com.devpush.morty.features.allepisodes.domain.repository.EpisodesRepository
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Episode
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor
    (private val ktorClient: KtorClient) : EpisodesRepository {

    override suspend fun fetchAllEpisodes(): KtorClient.ApiOperation<List<Episode>> =
        ktorClient.getAllEpisodes()

    override suspend fun getEpisode(episodeId: Int) = ktorClient.getEpisode(episodeId)


}