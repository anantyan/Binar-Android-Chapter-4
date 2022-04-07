package id.anantyan.exerciseproject.ui.activity.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.model.Users
import id.anantyan.exerciseproject.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UsersRepository

    val selectByUsers: LiveData<Users>
    val insert: LiveData<Users>

    init {
        val usersDao = RoomDB.database(application).usersDao()
        repository = UsersRepository(usersDao)
        selectByUsers = repository._selectByUsers
        insert = repository._insert
    }

    fun selectByUsers(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.selectByUsers(item)
    }

    fun insert(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(item)
    }
}