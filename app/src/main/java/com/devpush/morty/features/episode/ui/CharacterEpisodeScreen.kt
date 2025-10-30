package com.devpush.morty.features.episode.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Episode
import kotlinx.coroutines.launch
import com.devpush.network.models.domain.Character
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpush.morty.R
import com.devpush.morty.core.commonui.CharacterImage
import com.devpush.morty.core.commonui.CharacterNameComponent
import com.devpush.morty.core.commonui.DataPoint
import com.devpush.morty.core.commonui.DataPointComponent
import com.devpush.morty.core.commonui.LoadingState
import com.devpush.morty.core.commonui.EpisodeRowComponent
import com.devpush.morty.core.commonui.M3ExpressiveTopToolbar
import com.devpush.morty.ui.theme.RickPrimary
import com.devpush.morty.ui.theme.RickTextPrimary

sealed interface ScreenState {
    object Loading : ScreenState
    data class Success(val character: Character, val episodes: List<Episode>) : ScreenState
    data class Error(val message: String) : ScreenState
}

@Composable
fun CharacterEpisodeScreen(characterId: Int, ktorClient: KtorClient, onBackClicked: () -> Unit){
    var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Loading) }
    val errorMessage = stringResource(R.string.error_network)

    LaunchedEffect(key1 = Unit, block = {
        ktorClient.getCharacters(characterId).onSuccess { character ->
            launch {
                ktorClient.getEpisodes(character.episodeIds).onSuccess { episodes ->
                    screenState = ScreenState.Success(character, episodes)
                }.onFailure {
                    screenState = ScreenState.Error(message = errorMessage)
                }
            }
        }.onFailure {
            screenState = ScreenState.Error(message = errorMessage)
        }
    })

    when(val state = screenState){
        ScreenState.Loading -> {
            LoadingState()
        }
        is ScreenState.Success -> {
            MainScreen(
                character = state.character,
                episodes = state.episodes,
                onBackClicked = onBackClicked
            )
        }
        is ScreenState.Error -> {
            Text(
                text = state.message,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                textAlign = TextAlign.Center,
                fontSize = 26.sp
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(character: Character, episodes: List<Episode>, onBackClicked: () -> Unit) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }

    Column {
        LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
            item { CharacterNameComponent(name = character.name) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                LazyRow {
                    episodeBySeasonMap.forEach { mapEntry ->
                        item {
                            val title = stringResource(R.string.label_season, mapEntry.key)
                            val description = stringResource(R.string.label_episode_count, mapEntry.value.size)
                            DataPointComponent(dataPoint = DataPoint(title, description))
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CharacterImage(imageUrl = character.imageUrl) }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            episodeBySeasonMap.forEach { mapEntry ->
                stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(mapEntry.value) { episode ->
                    EpisodeRowComponent(episode = episode)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = RickPrimary)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.label_season, seasonNumber),
            color = RickTextPrimary,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = RickTextPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        )
    }
}