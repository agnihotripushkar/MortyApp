package com.devpush.morty.core.constants

/**
 * Constants for domain model display strings
 * These constants provide consistent display names for domain entities
 * Note: For full localization support, these should eventually be moved to string resources
 */
object DomainConstants {
    
    // Character Status display names
    object CharacterStatus {
        const val ALIVE = "Alive"
        const val DEAD = "Dead"
        const val UNKNOWN = "Unknown"
    }
    
    // Character Gender display names
    object CharacterGender {
        const val MALE = "Male"
        const val FEMALE = "Female"
        const val GENDERLESS = "No gender"
        const val UNKNOWN = "Not specified"
    }
    
    // Character Species common values (for consistency)
    object CharacterSpecies {
        const val HUMAN = "Human"
        const val ALIEN = "Alien"
        const val HUMANOID = "Humanoid"
        const val UNKNOWN = "Unknown"
        const val ANIMAL = "Animal"
        const val ROBOT = "Robot"
        const val CRONENBERG = "Cronenberg"
        const val DISEASE = "Disease"
    }
    
    // Location types (for future use)
    object LocationType {
        const val PLANET = "Planet"
        const val SPACE_STATION = "Space station"
        const val MICROVERSE = "Microverse"
        const val DIMENSION = "Dimension"
        const val UNKNOWN = "Unknown"
    }
    
}