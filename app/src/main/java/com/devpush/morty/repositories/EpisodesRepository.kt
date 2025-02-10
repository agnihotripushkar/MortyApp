package com.devpush.morty.repositories

import com.devpush.network.KtorClient
import com.devpush.network.KtorClient.ApiOperation
import com.devpush.network.models.domain.Episode
import javax.inject.Inject

class EpisodesRepository @Inject constructor(private val ktorClient: KtorClient) {

    suspend fun fetchAllEpisodes():ApiOperation<List<Episode>> = ktorClient.getAllEpisodes()

    suspend fun getEpisode(episodeId: Int) = ktorClient.getEpisode(episodeId)


}