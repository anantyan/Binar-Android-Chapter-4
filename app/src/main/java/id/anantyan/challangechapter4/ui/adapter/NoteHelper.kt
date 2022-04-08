package id.anantyan.challangechapter4.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challangechapter4.model.Notes

interface NoteHelper {
    /**
     * inisialisasi adapter
     * */
    fun init(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    /**
     * add list to adapter
     * */
    fun differ(listItem: List<Notes>)

    /**
     * state onclick
     * */
    fun onClick(listener: (Int, Notes) -> Unit)
    fun onEditClick(listener: (Int, Notes) -> Unit)
    fun onDeleteClick(listener: (Int, Notes) -> Unit)
}