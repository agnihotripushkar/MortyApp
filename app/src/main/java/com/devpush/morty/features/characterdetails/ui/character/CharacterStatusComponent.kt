package com.devpush.morty.features.characterdetails.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpush.morty.R
import com.devpush.morty.ui.theme.MortyTheme
import com.devpush.morty.ui.theme.RickTextPrimary
import com.devpush.morty.core.utils.asColor
import com.devpush.network.models.domain.CharacterStatus

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.border(
            width = 1.dp,
            color = characterStatus.asColor(),
            shape = RoundedCornerShape(12.dp)
        )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ){
        Text(
            text = stringResource(R.string.label_status, characterStatus.displayName),
            fontSize = 20.sp,
            color = RickTextPrimary
        )

    }

}

@Preview
@Composable
fun CharacterStatusComponentPreviewAlive() {
    MortyTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Alive)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewDead() {
    MortyTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Dead)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewUnknown() {
    MortyTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Unknown)
    }
}