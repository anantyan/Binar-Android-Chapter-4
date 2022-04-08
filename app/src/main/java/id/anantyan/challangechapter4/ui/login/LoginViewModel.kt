package id.anantyan.challangechapter4.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.anantyan.challangechapter4.database.RoomDB
import id.anantyan.challangechapter4.model.Users
import id.anantyan.challangechapter4.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsersRepository

    val users: LiveData<Users?>
    val selectByEmailAndPassword: LiveData<String?>

    init {
        val usersDao = RoomDB.database(application).usersDao()
        repository = UsersRepository(application, usersDao)
        users = repository._users
        selectByEmailAndPassword = repository._selectByEmailAndPassword
    }

    fun selectByEmailAndPassword(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.selectByEmailAndPassword(item)
    }
}