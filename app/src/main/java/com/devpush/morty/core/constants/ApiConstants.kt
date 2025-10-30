package com.devpush.morty.core.constants

/**
 * Constants for API-related strings and configurations
 */
object ApiConstants {
    
    // Base API configuration
    const val BASE_URL = "https://rickandmortyapi.com/api/"
    
    // Endpoint paths
    const val CHARACTER_ENDPOINT = "character"
    const val EPISODE_ENDPOINT = "episode"
    const val LOCATION_ENDPOINT = "location"
    
    // Query parameters
    const val PARAM_PAGE = "page"
    const val PARAM_NAME = "name"
    const val PARAM_STATUS = "status"
    const val PARAM_SPECIES = "species"
    const val PARAM_GENDER = "gender"
    
    // API response fields
    const val FIELD_RESULTS = "results"
    const val FIELD_INFO = "info"
    const val FIELD_COUNT = "count"
    const val FIELD_PAGES = "pages"
    const val FIELD_NEXT = "next"
    const val FIELD_PREV = "prev"
    
    // Content types
    const val CONTENT_TYPE_JSON = "application/json"
    
    // URL path separators and formatting
    const val PATH_SEPARATOR = "/"
    const val QUERY_SEPARATOR = ","
    
}