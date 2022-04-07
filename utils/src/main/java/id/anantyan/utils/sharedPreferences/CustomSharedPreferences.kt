package id.anantyan.utils.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.lang.UnsupportedOperationException

class PreferenceManager(context: Context) : PreferenceHelper {

    private var prefShared: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val LOG_IN = "LOG_IN"
    }

    override fun setLogIn(value: Boolean) {
        prefShared.set(LOG_IN, value)
    }

    override fun getLogIn(): Boolean? {
        return prefShared.getBoolean(LOG_IN, false)
    }
}

/**
 * setting listener editor & apply sharedPreferences
 * with extenstion function
 * */
private fun SharedPreferences.settings(operation: (SharedPreferences.Editor) -> Unit) {
    this.edit(true) {
        operation(this)
        this.apply()
    }
}

private fun SharedPreferences.set(key: String, value: Any?) {
     when(value) {
         is String -> this.settings { it.putString(key, value) }
         is Boolean -> this.settings { it.putBoolean(key, value) }
         is Int -> this.settings { it.putInt(key, value) }
         is Long -> this.settings { it.putLong(key, value) }
         is Float -> this.settings { it.putFloat(key, value) }
         else -> throw UnsupportedOperationException("Not yet implemented")
     }
}