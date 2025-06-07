package com.devpush.morty.features.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.devpush.morty.R
import com.devpush.morty.core.navigation.AppNavigation
import com.devpush.morty.ui.theme.MortyTheme
import com.devpush.network.KtorClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Morty)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()
            .apply {
                setKeepOnScreenCondition {
                    viewModel.isLoading.value
                }
            }
        setContent {
            MortyTheme {
                AppNavigation(ktorClient = ktorClient)
            }
        }
    }
}