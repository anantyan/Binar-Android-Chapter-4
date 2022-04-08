package id.anantyan.challangechapter4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challangechapter4.databinding.ListItemNoteBinding
import id.anantyan.challangechapter4.model.Notes

class NoteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), NoteHelper {

    private var oldNotes: MutableList<Notes> = mutableListOf()
    private var _onClick: ((Int, Notes) -> Unit)? = null
    private var _onEditClick : ((Int, Notes) -> Unit)? = null
    private var _onDeleteClick : ((Int, Notes) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                itemView.setOnClickListener {
                    _onClick?.let {
                        it(adapterPosition, oldNotes[adapterPosition])
                    }
                }

                binding.btnEdit.setOnClickListener {
                    _onEditClick?.let {
                        it(adapterPosition, oldNotes[adapterPosition])
                    }
                }

                binding.btnDelete.setOnClickListener {
                    _onDeleteClick?.let {
                        it(adapterPosition, oldNotes[adapterPosition])
                    }
                }
            }

            fun bind(item: Notes) {
                binding.txtTitle.text = item.title
                binding.txtNote.text = item.note
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val item = oldNotes[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return oldNotes.size
    }

    override fun init(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(listItem: List<Notes>) {
        val diffUtil = DiffUtilNotes(oldNotes, listItem)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldNotes.clear()
        oldNotes.addAll(listItem)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClick(listener: (Int, Notes) -> Unit) {
        _onClick = listener
    }

    override fun onEditClick(listener: (Int, Notes) -> Unit) {
        _onEditClick = listener
    }

    override fun onDeleteClick(listener: (Int, Notes) -> Unit) {
        _onDeleteClick = listener
    }
}

class DiffUtilNotes(
    private val oldNotes: List<Notes>,
    private val newNotes: List<Notes>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldNotes.size

    override fun getNewListSize() = newNotes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotes[oldItemPosition].id == newNotes[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldNotes[oldItemPosition].id != newNotes[newItemPosition].id -> {
                false
            }
            oldNotes[oldItemPosition].title != newNotes[newItemPosition].title -> {
                false
            }
            oldNotes[oldItemPosition].note != newNotes[newItemPosition].note -> {
                false
            }
            else -> true
        }
    }
}