package id.anantyan.exerciseproject.repository

import id.anantyan.exerciseproject.data.UsersDao
import id.anantyan.exerciseproject.model.Users
import id.anantyan.utils.LiveEvent

class UsersRepository(private val usersDao: UsersDao) {

    val _selectByUsers: LiveEvent<Users> = LiveEvent()
    val _insert: LiveEvent<Users> = LiveEvent()

    suspend fun selectByUsers(item: Users) {
        _selectByUsers.postValue(usersDao.selectByUsers(item.email, item.password))
    }

    suspend fun insert(item: Users) {
        usersDao.insert(item)
        _insert.postValue(item)
    }
}