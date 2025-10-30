package com.devpush.morty.features.allepisodes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpush.morty.core.constants.UiConstants
import com.devpush.morty.features.allepisodes.domain.repository.EpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllEpisodesViewModel @Inject constructor
    (private val repository: EpisodesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<AllEpisodesUiState>(AllEpisodesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun refreshAllEpisodes(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (forceRefresh) _uiState.update { AllEpisodesUiState.Loading }
        repository.fetchAllEpisodes()
            .onSuccess { episodeList ->
                _uiState.update {
                    AllEpisodesUiState.Success(
                        data = episodeList.groupBy {
                            it.seasonNumber.toString()
                        }.mapKeys {
                            UiConstants.SEASON_FORMAT.format(it.key)
                        }
                    )
                }
            }.onFailure {
                _uiState.update { AllEpisodesUiState.Error }
            }
    }

}