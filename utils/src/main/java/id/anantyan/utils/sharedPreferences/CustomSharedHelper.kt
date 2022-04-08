package id.anantyan.utils.sharedPreferences

interface PreferenceHelper {
    fun setId(value: Long)
    fun getId(): Long
    fun setUsername(value: String)
    fun getUsername(): String
}