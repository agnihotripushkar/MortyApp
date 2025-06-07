package com.devpush.morty.components.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.devpush.morty.ui.theme.RickAction

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingState(modifier: Modifier = defaultModifier) {
    LoadingIndicator(
        modifier = modifier,
        color = RickAction
    )
}