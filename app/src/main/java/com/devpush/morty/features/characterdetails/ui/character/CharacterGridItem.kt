package com.devpush.morty.features.characterdetails.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpush.morty.R
import com.devpush.morty.core.commonui.CharacterImage
import com.devpush.morty.ui.theme.RickAction
import com.devpush.morty.ui.theme.RickGradientStart
import com.devpush.morty.ui.theme.RickTextPrimary
import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterGender
import com.devpush.network.models.domain.CharacterStatus

@Composable
fun CharacterGridItem(
    modifier: Modifier,
    character: Character,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(listOf(RickGradientStart, RickAction)),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ){
        Box {
            CharacterImage(imageUrl = character.imageUrl)
            CharacterStatusCircle(
                status = character.status,
                modifier = Modifier.padding(start = 6.dp, top = 6.dp)
            )
        }
        Text(
            text = character.name,
            color = RickTextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Preview
@Composable
private fun CharacterGridItemPreview() {
    CharacterGridItem(
        modifier = Modifier.fillMaxWidth(),
        character = Character(
            created = stringResource(R.string.preview_timestamp),
            episodeIds = listOf(1, 2, 3, 4, 5),
            gender = CharacterGender.Male,
            id = 123,
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            location = Character.Location(
                name = stringResource(R.string.preview_location_earth),
                url = ""
            ),
            name = stringResource(R.string.preview_character_name_morty),
            origin = Character.Origin(
                name = stringResource(R.string.preview_location_earth),
                url = ""
            ),
            species = stringResource(R.string.preview_species_human),
            status = CharacterStatus.Alive,
            type = ""
        ),
        onClick = {}
    )
}