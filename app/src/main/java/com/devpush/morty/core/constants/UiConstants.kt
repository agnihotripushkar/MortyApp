package com.devpush.morty.core.constants

/**
 * Constants for internal UI-related strings and configurations
 */
object UiConstants {
    
    // Default values
    const val DEFAULT_TITLE = "Rick & Morty"
    const val DEFAULT_PLACEHOLDER = "Loading..."
    const val DEFAULT_EMPTY_MESSAGE = "No items found"
    
    // Screen identifiers
    const val SCREEN_HOME = "home"
    const val SCREEN_EPISODES = "episodes"
    const val SCREEN_CHARACTER_DETAILS = "character_details"
    const val SCREEN_EPISODE_DETAILS = "episode_details"
    
    // Component identifiers
    const val COMPONENT_TOOLBAR = "toolbar"
    const val COMPONENT_BOTTOM_NAV = "bottom_navigation"
    const val COMPONENT_CHARACTER_LIST = "character_list"
    const val COMPONENT_EPISODE_LIST = "episode_list"
    
    // Animation durations (in milliseconds)
    const val ANIMATION_DURATION_SHORT = 150L
    const val ANIMATION_DURATION_MEDIUM = 300L
    const val ANIMATION_DURATION_LONG = 500L
    
    // Layout constants
    const val GRID_COLUMNS = 2
    const val LIST_ITEM_HEIGHT = 80
    const val IMAGE_SIZE_SMALL = 48
    const val IMAGE_SIZE_MEDIUM = 96
    const val IMAGE_SIZE_LARGE = 200
    
    // Formatting patterns
    const val SEASON_FORMAT = "Season %s"
    const val EPISODE_FORMAT = "Episode %s"
    const val CHARACTER_COUNT_FORMAT = "%d unique characters"
    
    // Navigation destination titles (internal constants)
    const val NAV_DESTINATION_HOME = "Home"
    const val NAV_DESTINATION_EPISODES = "Episodes"
    
    // Data point labels (internal constants)
    const val DATA_POINT_LAST_KNOWN_LOCATION = "Last known location"
    const val DATA_POINT_SPECIES = "Species"
    const val DATA_POINT_GENDER = "Gender"
    const val DATA_POINT_TYPE = "Type"
    const val DATA_POINT_ORIGIN = "Origin"
    const val DATA_POINT_EPISODE_COUNT = "Episode count"
    
    // Debug and logging tags
    const val LOG_TAG_NETWORK = "NetworkModule"
    const val LOG_TAG_REPOSITORY = "Repository"
    const val LOG_TAG_VIEWMODEL = "ViewModel"
    const val LOG_TAG_UI = "UI"
    
}