package com.expeditee.plantdiagnosis.model

enum class Language(
    val displayName: String,
    val isoCountry: String,
    val nativeName: String
) {
    ENGLISH("English", "en", "English"),
    VIETNAMESE("Tiếng Việt", "vi", "Vietnamese"),
    SPANISH("Español", "es", "Spanish"),
    FRENCH("Français", "fr", "French"),
    GERMAN("Deutsch", "de", "German");
    
    companion object {
        fun fromIsoCode(isoCode: String?): Language? {
            return entries.firstOrNull { it.isoCountry == isoCode }
        }
        
        fun getDefault(): Language = ENGLISH
    }
}
