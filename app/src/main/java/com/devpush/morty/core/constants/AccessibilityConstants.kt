package com.devpush.morty.core.constants

/**
 * Constants for accessibility-related strings and descriptions
 * Note: These constants are for internal use and consistency.
 * UI components should use string resources for localization support.
 */
object AccessibilityConstants {
    
    // Navigation accessibility patterns
    const val NAVIGATE_BACK = "Navigate back to previous screen"
    const val BOTTOM_NAV_BAR = "Bottom navigation bar"
    const val TOOLBAR_NAVIGATION = "Toolbar navigation"
    const val TAB_NAVIGATION = "Tab navigation"
    
    // State descriptions
    const val STATE_SELECTED = "Currently selected"
    const val STATE_NOT_SELECTED = "Not selected"
    const val STATE_LOADING = "Loading content"
    const val STATE_ERROR = "Error occurred"
    const val STATE_EXPANDED = "Expanded"
    const val STATE_COLLAPSED = "Collapsed"
    const val STATE_ENABLED = "Enabled"
    const val STATE_DISABLED = "Disabled"
    
    // Content descriptions
    const val CHARACTER_IMAGE = "Character profile image"
    const val CHARACTER_STATUS_INDICATOR = "Character status indicator"
    const val EPISODE_ITEM = "Episode item"
    const val CLICKABLE_ITEM = "Clickable item"
    const val LIST_ITEM = "List item"
    const val GRID_ITEM = "Grid item"
    const val CARD_ITEM = "Card item"
    
    // Action descriptions
    const val ACTION_TAP_TO_VIEW = "Tap to view details"
    const val ACTION_TAP_TO_SELECT = "Tap to select"
    const val ACTION_SWIPE_TO_REFRESH = "Swipe to refresh"
    const val ACTION_DOUBLE_TAP = "Double tap to activate"
    const val ACTION_LONG_PRESS = "Long press for options"
    
    // Screen reader announcements
    const val ANNOUNCE_PAGE_LOADED = "Page loaded successfully"
    const val ANNOUNCE_ERROR_OCCURRED = "An error occurred while loading"
    const val ANNOUNCE_REFRESHING = "Refreshing content"
    const val ANNOUNCE_LOADING = "Loading new content"
    const val ANNOUNCE_SEARCH_RESULTS = "Search results updated"
    
    // Common accessibility roles
    const val ROLE_BUTTON = "Button"
    const val ROLE_LINK = "Link"
    const val ROLE_HEADING = "Heading"
    const val ROLE_LIST = "List"
    const val ROLE_LIST_ITEM = "List item"
    const val ROLE_IMAGE = "Image"
    const val ROLE_TAB = "Tab"
    
    // Minimum touch target size (in dp)
    const val MIN_TOUCH_TARGET_SIZE = 48
    
}