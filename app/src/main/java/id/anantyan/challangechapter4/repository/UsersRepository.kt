package id.anantyan.challangechapter4.repository

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import id.anantyan.challangechapter4.R
import id.anantyan.challangechapter4.data.UsersDao
import id.anantyan.challangechapter4.model.Notes
import id.anantyan.challangechapter4.model.Users
import id.anantyan.utils.LiveEvent

class UsersRepository(
    private val context: Context,
    private val usersDao: UsersDao
) {
    val _users: LiveEvent<Users?> = LiveEvent()
    val _selectByEmailAndPassword: LiveEvent<String?> = LiveEvent()
    val _insert: LiveEvent<String?> = LiveEvent()

    suspend fun selectByEmailAndPassword(item: Users) {
        val users = usersDao.selectByEmailAndPassword(item.email, item.password)
        if (users == null) {
            _selectByEmailAndPassword.postValue(context.getString(R.string.txt_field_failed_login))
        } else {
            _users.postValue(users)
            _selectByEmailAndPassword.postValue(null)
        }
    }

    suspend fun insert(item: Users) {
        val users = usersDao.selectByUsernameOrEmail(item.username, item.email)
        if (users == null) {
            if (usersDao.insert(item) != 0L) {
                _insert.postValue(context.getString(R.string.txt_field_success_inserted))
            } else {
                _insert.postValue(context.getString(R.string.txt_field_failed_inserted))
            }
        } else {
            _insert.postValue(context.getString(R.string.txt_field_failed_register))
        }
    }
}