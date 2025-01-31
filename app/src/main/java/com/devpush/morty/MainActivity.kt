package com.devpush.morty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devpush.morty.ui.theme.MortyTheme
import com.devpush.network.KtorClient
import com.devpush.network.models.domain.Character
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.devpush.morty.screens.CharacterDetailsScreen
import com.devpush.morty.ui.theme.RickPrimary

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color= RickPrimary
                ) {
                    CharacterDetailsScreen(characterId = 1,onback, ktorClient)

                }
            }
        }
    }
}

private val onback = {

}