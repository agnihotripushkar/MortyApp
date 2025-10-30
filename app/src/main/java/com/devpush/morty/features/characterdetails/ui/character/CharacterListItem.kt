package com.devpush.morty.features.characterdetails.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpush.morty.R
import com.devpush.network.models.domain.Character
import com.devpush.morty.core.commonui.CharacterImage
import com.devpush.morty.core.commonui.DataPointComponent
import com.devpush.morty.ui.theme.RickAction
import com.devpush.morty.core.commonui.DataPoint
import com.devpush.network.models.domain.CharacterGender
import com.devpush.network.models.domain.CharacterStatus

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: Character,
    characterDataPoints: List<DataPoint>,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(140.dp)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.Transparent, RickAction)),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box {
            CharacterImage(
                imageUrl = character.imageUrl,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            CharacterStatusCircle(
                status = character.status,
                modifier = Modifier.padding(start = 6.dp, top = 6.dp)
            )
        }
        val nameTitle = stringResource(R.string.preview_data_point_name_title)
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = {
                items(
                    items = listOf(
                        DataPoint(
                            title = nameTitle,
                            description = character.name
                        )
                    ) + characterDataPoints,
                    key = { it.hashCode() }
                ) { dataPoint ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        DataPointComponent(dataPoint = dataPoint)
                    }
                }
            })
    }
}

private fun sanitizeDataPoint(dataPoint: DataPoint): DataPoint {
    val newDescription = if (dataPoint.description.length > 14) {
        dataPoint.description.take(12) + ".."
    } else {
        dataPoint.description
    }
    return dataPoint.copy(description = newDescription)
}


@Preview
@Composable
private fun CharacterListItemPreview() {
    CharacterListItem(
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
        characterDataPoints = listOf(
            DataPoint(title = stringResource(R.string.preview_data_point_title_1), description = stringResource(R.string.preview_data_point_description_1)),
            DataPoint(title = stringResource(R.string.preview_data_point_title_2), description = stringResource(R.string.preview_data_point_description_2)),
            DataPoint(title = stringResource(R.string.preview_data_point_title_3), description = stringResource(R.string.preview_data_point_description_3)),
            DataPoint(title = stringResource(R.string.preview_data_point_title_4), description = stringResource(R.string.preview_data_point_description_4)),
            DataPoint(title = stringResource(R.string.preview_data_point_title_5), description = stringResource(R.string.preview_data_point_description_5)),
        ),
        onClick = {}
    )
}