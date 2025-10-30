package com.devpush.morty.features.characterdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpush.morty.core.commonui.DataPoint
import com.devpush.morty.core.constants.ErrorConstants
import com.devpush.morty.core.constants.UiConstants
import com.devpush.morty.features.characterdetails.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel(){

    private val _internalStorageFlow = MutableStateFlow<CharacterDetailsViewState>(
        value = CharacterDetailsViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _internalStorageFlow.update { return@update CharacterDetailsViewState.Loading }
        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            val dataPoints = buildList {
                add(DataPoint(UiConstants.DATA_POINT_LAST_KNOWN_LOCATION, character.location.name))
                add(DataPoint(UiConstants.DATA_POINT_SPECIES, character.species))
                add(DataPoint(UiConstants.DATA_POINT_GENDER, character.gender.displayName))
                character.type.takeIf { it.isNotEmpty() }?.let { type ->
                    add(DataPoint(UiConstants.DATA_POINT_TYPE, type))
                }
                add(DataPoint(UiConstants.DATA_POINT_ORIGIN, character.origin.name))
                add(DataPoint(UiConstants.DATA_POINT_EPISODE_COUNT, character.episodeIds.size.toString()))
            }
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Success(
                    character = character,
                    characterDataPoints = dataPoints
                )
            }
        }.onFailure { exception ->
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Error(
                    message = exception.message ?: ErrorConstants.UNKNOWN_ERROR
                )
            }
        }
    }

}