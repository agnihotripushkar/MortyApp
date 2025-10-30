package com.devpush.network

import com.devpush.network.constants.NetworkConstants
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterPage
import com.devpush.network.models.domain.Episode
import com.devpush.network.models.domain.EpisodePage
import com.devpush.network.models.remote.RemoteCharacter
import com.devpush.network.models.remote.RemoteCharacterPage
import com.devpush.network.models.remote.RemoteEpisode
import com.devpush.network.models.remote.RemoteEpisodePage
import com.devpush.network.models.remote.toDomainCharacter
import com.devpush.network.models.remote.toDomainCharacterPage
import com.devpush.network.models.remote.toDomainEpisode
import com.devpush.network.models.remote.toDomainEpisodePage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url(NetworkConstants.BASE_URL) }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    private var characterCache = mutableMapOf<Int, Character>()

    suspend fun getCharacters(id: Int): ApiOperation<Character> {
        characterCache[id]?.let { return ApiOperation.Success(it) }
        return safeApiCall {
            client.get("${NetworkConstants.CHARACTER_ENDPOINT}${NetworkConstants.PATH_SEPARATOR}$id")
                .body<RemoteCharacter>()
                .toDomainCharacter()
                .also { characterCache[id] = it }
        }
    }

    suspend fun getCharacterByPage(
        pageNumber: Int,
        queryParams: Map<String, String>
    ): ApiOperation<CharacterPage> {
        return safeApiCall {
            client.get(NetworkConstants.CHARACTER_ENDPOINT) {
                url {
                    parameters.append(NetworkConstants.PARAM_PAGE, pageNumber.toString())
                    queryParams.forEach { parameters.append(it.key, it.value) }
                }
            }
                .body<RemoteCharacterPage>()
                .toDomainCharacterPage()
        }
    }

    suspend fun searchAllCharactersByName(searchQuery: String): ApiOperation<List<Character>> {
        val data = mutableListOf<Character>()
        var exception: Exception? = null

        getCharacterByPage(
            pageNumber = 1,
            queryParams = mapOf(NetworkConstants.PARAM_NAME to searchQuery)
        ).onSuccess { firstPage ->
            val totalPageCount = firstPage.info.pages
            data.addAll(firstPage.characters)

            repeat(totalPageCount - 1) { index ->
                getCharacterByPage(
                    pageNumber = index + 2,
                    queryParams = mapOf(NetworkConstants.PARAM_NAME to searchQuery)
                ).onSuccess { nextPage ->
                    data.addAll(nextPage.characters)
                }.onFailure { error ->
                    exception = error
                }

                if (exception != null) {
                    return@onSuccess
                }
            }
        }.onFailure {
            exception = it
        }

        return exception?.let { ApiOperation.Failure(it) } ?: ApiOperation.Success(data)
    }

    suspend fun getEpisode(episodeId: Int): ApiOperation<Episode> {
        return safeApiCall {
            client.get("${NetworkConstants.EPISODE_ENDPOINT}${NetworkConstants.PATH_SEPARATOR}$episodeId")
                .body<RemoteEpisode>()
                .toDomainEpisode()
        }
    }

    suspend fun getEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
        return if (episodeIds.size == 1) {
            getEpisode(episodeIds[0]).mapSuccess {
                listOf(it)
            }
        } else {
            val idsCommaSeparated = episodeIds.joinToString(separator = NetworkConstants.QUERY_SEPARATOR)
            safeApiCall {
                client.get("${NetworkConstants.EPISODE_ENDPOINT}${NetworkConstants.PATH_SEPARATOR}$idsCommaSeparated")
                    .body<List<RemoteEpisode>>()
                    .map { it.toDomainEpisode() }
            }
        }
    }

    suspend fun getEpisodesByPage(pageIndex: Int): ApiOperation<EpisodePage> {
        return safeApiCall {
            client.get(NetworkConstants.EPISODE_ENDPOINT) {
                url {
                    parameters.append(NetworkConstants.PARAM_PAGE, pageIndex.toString())
                }
            }
                .body<RemoteEpisodePage>()
                .toDomainEpisodePage()
        }
    }

    suspend fun getAllEpisodes(): ApiOperation<List<Episode>> {
        val data = mutableListOf<Episode>()
        var exception: Exception? = null

        getEpisodesByPage(pageIndex = 1).onSuccess { firstPage ->
            val totalPageCount = firstPage.info.pages
            data.addAll(firstPage.episodes)

            repeat(totalPageCount - 1) { index ->
                getEpisodesByPage(pageIndex = index + 2).onSuccess { nextPage ->
                    data.addAll(nextPage.episodes)
                }.onFailure { error ->
                    exception = error
                }

                if (exception != null) {
                    return@onSuccess
                }
            }
        }.onFailure {
            exception = it
        }

        return exception?.let { ApiOperation.Failure(it) } ?: ApiOperation.Success(data)
    }


    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }

    sealed interface ApiOperation<T> {
        data class Success<T>(val data: T) : ApiOperation<T>
        data class Failure<T>(val exception: Exception) : ApiOperation<T>

        fun <R> mapSuccess(transform: (T) -> R): ApiOperation<R> {
            return when (this) {
                is Success -> Success(transform(data))
                is Failure -> Failure(exception)
            }
        }

        suspend fun onSuccess(block: suspend (T) -> Unit): ApiOperation<T> {
            if (this is Success) block(data)
            return this
        }

        fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
            if (this is Failure) block(exception)
            return this
        }
    }
}