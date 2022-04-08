package id.anantyan.challangechapter4.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.anantyan.challangechapter4.databinding.DialogSetNoteBinding
import id.anantyan.challangechapter4.model.Notes
import id.anantyan.utils.generalValid
import id.anantyan.utils.passwordValid
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class NoteDialog(private val context: Context) : NoteDialogHelper {
    override fun dialogInsertNote(listener: (notes: Notes, dialog: AlertDialog) -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        val binding = DialogSetNoteBinding.inflate(LayoutInflater.from(context))
        builder.setCancelable(false)
        builder.setTitle("Tambah Data")
        builder.setView(binding.root)
        builder.setPositiveButton("Iya", null)
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            val item = Notes(
                title = binding.txtInputTitle.text.toString(),
                note = binding.txtInputNote.text.toString()
            )
            validator(context) {
                this.mode = Mode.CONTINUOUS
                this.listener = onSetDialog(item, dialog, listener)
                this.validate(
                    generalValid(binding.txtLayoutTitle),
                    generalValid(binding.txtLayoutNote)
                )
            }
        }
    }

    override fun dialogUpdateNote(
        notes: Notes,
        listener: (notes: Notes, dialog: AlertDialog) -> Unit
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        val binding = DialogSetNoteBinding.inflate(LayoutInflater.from(context))
        binding.txtInputTitle.setText(notes.title)
        binding.txtInputNote.setText(notes.note)
        builder.setCancelable(false)
        builder.setTitle("Update ${notes.title}")
        builder.setView(binding.root)
        builder.setPositiveButton("Iya", null)
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            val item = Notes(
                id = notes.id,
                title = binding.txtInputTitle.text.toString(),
                note = binding.txtInputNote.text.toString()
            )
            validator(context) {
                this.mode = Mode.CONTINUOUS
                this.listener = onSetDialog(item, dialog, listener)
                this.validate(
                    generalValid(binding.txtLayoutTitle),
                    generalValid(binding.txtLayoutNote)
                )
            }
        }
    }

    private fun onSetDialog(
        notes: Notes,
        dialog: AlertDialog,
        listener: (notes: Notes, dialog: AlertDialog) -> Unit
    ) = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            listener.invoke(notes, dialog)
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    override fun dialogDetailNote(notes: Notes) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setCancelable(false)
        builder.setTitle("${notes.title}")
        builder.setMessage("${notes.note}")
        builder.setPositiveButton("Oke") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun dialogDeleteNote(
        notes: Notes,
        listener: (notes: Notes, dialog: AlertDialog) -> Unit
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setCancelable(false)
        builder.setTitle("Attention!")
        builder.setMessage("Apakah anda ingin menhapus ${notes.note}?")
        builder.setPositiveButton("Iya", null)
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            listener.invoke(notes, dialog)
        }
    }

    override fun dialogLogout(listener: (dialog: AlertDialog) -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setCancelable(false)
        builder.setTitle("Attention!")
        builder.setMessage("Apakah anda ingin keluar dari akun ini?")
        builder.setPositiveButton("Iya", null)
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            listener.invoke(dialog)
        }
    }
}