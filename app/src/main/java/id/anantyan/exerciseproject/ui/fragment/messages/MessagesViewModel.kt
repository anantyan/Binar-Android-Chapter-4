package id.anantyan.exerciseproject.ui.fragment.messages

import android.app.Application
import androidx.lifecycle.*
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.exerciseproject.repository.MessagesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MessagesRepository

    val insert: LiveData<Messages>
    val update: LiveData<Messages>
    val delete: LiveData<Messages>

    init {
        val messagesDao = RoomDB.database(application).messagesDao()
        repository = MessagesRepository(messagesDao)
        insert = repository._insert
        update = repository._update
        delete = repository._delete
    }

    fun select() = repository.select()

    fun selectById(id: Int) = repository.selectById(id)

    fun insert(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(item)
    }

    fun update(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(item)
    }

    fun delete(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.delete(item)
    }

    fun deleteAll() = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteAll()
    }
}