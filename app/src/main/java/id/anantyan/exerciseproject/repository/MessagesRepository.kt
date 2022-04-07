package id.anantyan.exerciseproject.repository

import id.anantyan.exerciseproject.data.MessagesDao
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.utils.LiveEvent

class MessagesRepository(private val usersDao: MessagesDao) {

    val _seectById: LiveEvent<Messages> = LiveEvent()
    val _insert: LiveEvent<Messages> = LiveEvent()
    val _update: LiveEvent<Messages> = LiveEvent()
    val _delete: LiveEvent<Messages> = LiveEvent()
    var _deleteAll: LiveEvent<Unit> = LiveEvent()

    fun select() = usersDao.select()

    suspend fun selectById(id: Int) {
        _seectById.postValue(usersDao.selectById(id))
    }

    suspend fun insert(item: Messages) {
        usersDao.insert(item)
        _insert.postValue(item)
    }

    suspend fun update(item: Messages) {
        usersDao.update(item)
        _update.postValue(item)
    }

    suspend fun delete(item: Messages) {
        usersDao.delete(item)
        _delete.postValue(item)
    }

    suspend fun deleteAll() {
        _deleteAll.postValue(usersDao.deleteAll())
    }
}