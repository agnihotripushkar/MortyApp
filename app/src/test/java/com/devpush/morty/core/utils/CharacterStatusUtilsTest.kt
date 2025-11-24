package com.devpush.morty.core.utils

import androidx.compose.ui.graphics.Color
import com.devpush.network.models.domain.CharacterStatus
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for CharacterStatusUtils
 */
class CharacterStatusUtilsTest {

    @Test
    fun `CharacterStatus Alive should return Green color`() {
        val status = CharacterStatus.Alive
        val color = status.asColor()
        
        assertEquals(Color.Green, color)
    }

    @Test
    fun `CharacterStatus Dead should return Red color`() {
        val status = CharacterStatus.Dead
        val color = status.asColor()
        
        assertEquals(Color.Red, color)
    }

    @Test
    fun `CharacterStatus Unknown should return Yellow color`() {
        val status = CharacterStatus.Unknown
        val color = status.asColor()
        
        assertEquals(Color.Yellow, color)
    }

    @Test
    fun `all CharacterStatus values should have color mappings`() {
        val statuses = listOf(
            CharacterStatus.Alive,
            CharacterStatus.Dead,
            CharacterStatus.Unknown
        )
        
        statuses.forEach { status ->
            val color = status.asColor()
            assertNotNull(color)
        }
    }
}
