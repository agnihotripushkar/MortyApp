package com.devpush.network.constants

/**
 * Constants for network module internal use
 */
object NetworkConstants {
    
    // API Configuration
    const val BASE_URL = "https://rickandmortyapi.com/api/"
    const val CHARACTER_ENDPOINT = "character"
    const val EPISODE_ENDPOINT = "episode"
    const val PARAM_PAGE = "page"
    const val PARAM_NAME = "name"
    const val PATH_SEPARATOR = "/"
    const val QUERY_SEPARATOR = ","
    
    // Domain Display Strings
    object CharacterStatus {
        const val ALIVE = "Alive"
        const val DEAD = "Dead"
        const val UNKNOWN = "Unknown"
    }
    
    object CharacterGender {
        const val MALE = "Male"
        const val FEMALE = "Female"
        const val GENDERLESS = "No gender"
        const val UNKNOWN = "Not specified"
    }
    
}