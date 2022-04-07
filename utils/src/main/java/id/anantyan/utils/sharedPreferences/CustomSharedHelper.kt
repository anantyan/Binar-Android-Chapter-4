package id.anantyan.utils.sharedPreferences

interface PreferenceHelper {
    fun setLogIn(value: Boolean)
    fun getLogIn(): Boolean?
}