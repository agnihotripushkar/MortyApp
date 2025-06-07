package com.devpush.morty.features.allepisodes.domain.repository

import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Episode

interface EpisodesRepository {

    suspend fun fetchAllEpisodes(): KtorClient.ApiOperation<List<Episode>>

    suspend fun getEpisode(episodeId: Int): KtorClient.ApiOperation<Episode>
}