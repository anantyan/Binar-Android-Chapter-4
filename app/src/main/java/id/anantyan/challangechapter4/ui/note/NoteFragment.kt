package id.anantyan.challangechapter4.ui.note

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.anantyan.challangechapter4.MainActivity
import id.anantyan.challangechapter4.databinding.FragmentNoteBinding
import id.anantyan.challangechapter4.model.Notes
import id.anantyan.challangechapter4.ui.adapter.NoteAdapter
import id.anantyan.challangechapter4.ui.adapter.NoteHelper
import id.anantyan.challangechapter4.ui.dialog.NoteDialog
import id.anantyan.challangechapter4.ui.dialog.NoteDialogHelper
import id.anantyan.utils.dividerVertical
import id.anantyan.utils.rvFloatingActionButtonView

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val list: MutableList<Notes> = mutableListOf()
    private val viewModel: NoteViewModel by viewModels()
    private val adapter: NoteHelper by lazy { NoteAdapter() }
    private val dialog: NoteDialogHelper by lazy { NoteDialog((context as MainActivity)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNotes.adapter = adapter.init()
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.layoutManager = LinearLayoutManager((context as MainActivity))
        binding.rvNotes.itemAnimator = DefaultItemAnimator()
        binding.rvNotes.addOnScrollListener(rvFloatingActionButtonView(binding.fabAdd))
        binding.rvNotes.addItemDecoration(
            dividerVertical((context as MainActivity), 32, 0)
        )
        binding.fabAdd.setOnClickListener {
            onInsertData()
        }
        adapter.onClick { _, notes ->
            dialog.dialogDetailNote(notes)
        }
        adapter.onEditClick { _, notes ->
            onUpdateData(notes)
        }
        adapter.onDeleteClick { _, notes ->
            onDeleteData(notes)
        }
        onObserverData()
    }

    private fun onSelectData() {
        viewModel.select().observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            adapter.differ(list)
        }
    }

    private fun onInsertData() {
        dialog.dialogInsertNote { item, dialog ->
            viewModel.insert(item)
            dialog.dismiss()
        }
    }

    private fun onUpdateData(notes: Notes) {
        dialog.dialogUpdateNote(notes) { item, dialog ->
            viewModel.update(item)
            dialog.dismiss()
        }
    }

    private fun onDeleteData(notes: Notes) {
        dialog.dialogDeleteNote(notes) { item, dialog ->
            viewModel.delete(item)
            dialog.dismiss()
        }
    }

    private fun onObserverData() {
        onSelectData()
        viewModel.insert.observe(viewLifecycleOwner) {
            onToast((context as MainActivity), it.toString())
        }
        viewModel.update.observe(viewLifecycleOwner) {
            onToast((context as MainActivity), it.toString())
        }
        viewModel.delete.observe(viewLifecycleOwner) {
            onToast((context as MainActivity), it.toString())
        }
    }

    private fun onToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}