package id.anantyan.challangechapter4.ui.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.challangechapter4.database.RoomDB
import id.anantyan.challangechapter4.model.Notes
import id.anantyan.challangechapter4.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository
    val insert: LiveData<String?>
    val update: LiveData<String?>
    val delete: LiveData<String?>

    init {
        val notesDao = RoomDB.database(application).notesDao()
        repository = NotesRepository(application, notesDao)
        insert = repository._insert
        update = repository._update
        delete = repository._delete
    }

    fun select() = repository.select()

    fun insert(item: Notes) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(item)
    }

    fun update(item: Notes) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(item)
    }

    fun delete(item: Notes) = CoroutineScope(Dispatchers.IO).launch {
        repository.delete(item)
    }
}