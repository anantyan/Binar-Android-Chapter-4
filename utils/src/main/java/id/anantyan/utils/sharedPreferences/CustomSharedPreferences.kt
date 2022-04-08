package id.anantyan.utils.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.lang.UnsupportedOperationException

class PreferenceManager(context: Context) : PreferenceHelper {

    private var prefShared: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val ID = "ID"
        private const val USERNAME = "USERNAME"
    }

    override fun setId(value: Long) {
        prefShared.set(ID, value)
    }

    override fun getId(): Long {
        return prefShared.getLong(ID, -1)
    }

    override fun setUsername(value: String) {
        prefShared.set(USERNAME, value)
    }

    override fun getUsername(): String {
        return prefShared.getString(USERNAME, "")!!
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