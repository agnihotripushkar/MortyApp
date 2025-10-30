package com.devpush.morty.core.constants

/**
 * Constants for error messages and error handling
 */
object ErrorConstants {
    
    // Generic error messages
    const val GENERIC_ERROR = "Something went wrong"
    const val NETWORK_ERROR = "Whoops, something went wrong"
    const val LOADING_ERROR = "Failed to load data"
    const val UNKNOWN_ERROR = "An unknown error occurred"
    
    // Specific error contexts
    const val CHARACTER_LOAD_ERROR = "Failed to load character details"
    const val EPISODES_LOAD_ERROR = "Failed to load episodes"
    const val CHARACTER_LIST_ERROR = "Failed to load character list"
    const val EPISODE_DETAILS_ERROR = "Failed to load episode details"
    
    // Network-specific errors
    const val NO_INTERNET_ERROR = "No internet connection"
    const val TIMEOUT_ERROR = "Request timed out"
    const val SERVER_ERROR = "Server error occurred"
    
    // Data-specific errors
    const val NO_DATA_ERROR = "No data available"
    const val INVALID_DATA_ERROR = "Invalid data received"
    const val PARSING_ERROR = "Failed to parse response"
    
    // User action errors
    const val RETRY_ERROR = "Retry failed"
    const val REFRESH_ERROR = "Refresh failed"
    
}