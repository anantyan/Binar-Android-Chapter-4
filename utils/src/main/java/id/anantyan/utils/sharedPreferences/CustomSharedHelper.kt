package id.anantyan.utils.sharedPreferences

interface PreferenceHelper {
    fun setLogIn(value: Boolean)
    fun setFullname(value: String)
    fun setProfession(value: String)
    fun setEmail(value: String)
    fun setPassword(value: String)
    fun getLogIn(): Boolean?
    fun getFullname(): String?
    fun getProfession(): String?
    fun getEmail(): String?
    fun getPassword(): String?
}