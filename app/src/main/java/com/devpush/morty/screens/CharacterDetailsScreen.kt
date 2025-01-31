package com.devpush.morty.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devpush.morty.components.character.CharacterDetailsNamePlateComponent
import com.devpush.morty.components.common.CharacterImage
import com.devpush.morty.components.common.DataPointComponent
import com.devpush.morty.components.common.SimpleToolbar
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Character

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    onBackClicked: () -> Unit,
    ktorClient: KtorClient
){
    var character: Character? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(Unit) {
        ktorClient.getCharacters(characterId)
            .onSuccess {
                character = it
            }.onFailure { exception ->
                // TODO: Handle failure
            }
    }

    Column {
        SimpleToolbar(title = "Character Details",
            onBackAction = onBackClicked)
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            when(character){
                null -> {
                    item {
                        Text(text = "Loading...")
                    }
                }
                else -> {
                    item{
                        CharacterDetailsNamePlateComponent(
                            name = character?.name.toString(),
                            status = character!!.status
                        )
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    // Image
                    item {
                        CharacterImage(imageUrl = character!!.imageUrl)
                    }

                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }

            }



        }
    }

}