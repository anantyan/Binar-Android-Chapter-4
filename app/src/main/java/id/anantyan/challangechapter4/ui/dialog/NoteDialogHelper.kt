package id.anantyan.challangechapter4.ui.dialog

import androidx.appcompat.app.AlertDialog
import id.anantyan.challangechapter4.model.Notes

interface NoteDialogHelper {
    fun dialogInsertNote(listener: (notes: Notes, dialog: AlertDialog) -> Unit)
    fun dialogUpdateNote(notes: Notes, listener: (notes: Notes, dialog: AlertDialog) -> Unit)
    fun dialogDetailNote(notes: Notes)
    fun dialogDeleteNote(notes: Notes, listener: (notes: Notes, dialog: AlertDialog) -> Unit)
    fun dialogLogout(listener: (dialog: AlertDialog) -> Unit)
}