package id.anantyan.challangechapter4.repository

import android.content.Context
import androidx.lifecycle.LiveData
import id.anantyan.challangechapter4.R
import id.anantyan.challangechapter4.data.NotesDao
import id.anantyan.challangechapter4.model.Notes
import id.anantyan.utils.LiveEvent

class NotesRepository(
    private val context: Context,
    private val notesDao: NotesDao
) {
    val _insert: LiveEvent<String?> = LiveEvent()
    val _update: LiveEvent<String?> = LiveEvent()
    val _delete: LiveEvent<String?> = LiveEvent()

    fun select() = notesDao.select()

    suspend fun insert(item: Notes) {
        if (notesDao.insert(item) != 0L) {
            _insert.postValue(context.getString(R.string.txt_field_success_inserted))
        } else {
            _insert.postValue(context.getString(R.string.txt_field_failed_inserted))
        }
    }

    suspend fun update(item: Notes) {
        if (notesDao.update(item) != 0) {
            _update.postValue(context.getString(R.string.txt_field_success_updated))
        } else {
            _update.postValue(context.getString(R.string.txt_field_failed_updated))
        }
    }

    suspend fun delete(item: Notes) {
        if (notesDao.delete(item) != 0) {
            _delete.postValue(context.getString(R.string.txt_field_success_deleted))
        } else {
            _delete.postValue(context.getString(R.string.txt_field_failed_deleted))
        }
    }
}