package id.anantyan.exerciseproject.ui.fragment.messages

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.databinding.FragmentMessagesBinding
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.exerciseproject.ui.activity.BaseFragmentActivity
import id.anantyan.exerciseproject.ui.adapter.messages.MessagesAdapter
import id.anantyan.exerciseproject.ui.adapter.messages.MessagesHelper
import id.anantyan.exerciseproject.ui.SharedViewModel
import id.anantyan.exerciseproject.ui.activity.MessagesDetailActivity
import id.anantyan.utils.Constant.PASSING_TO_MESSAGES_ACTIVITY
import id.anantyan.utils.dividerVertical

class MessagesFragment : Fragment() {

    private var position: Int = -1
    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!
    private val list: MutableList<Messages> = mutableListOf()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: MessagesViewModel by viewModels()
    private val adapter: MessagesHelper by lazy { MessagesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBinding()
        onAdapter()
        onObserver()
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseFragmentActivity).supportActionBar?.title = "Messages"
    }

    private fun onAdapter() {
        adapter.onClick { i, messages ->
            position = i
            val intent = Intent((activity as BaseFragmentActivity), MessagesDetailActivity::class.java)
            intent.putExtra(PASSING_TO_MESSAGES_ACTIVITY, messages)
            (activity as BaseFragmentActivity).onResultActivity.launch(intent)
        }
        adapter.onLongClick { _, messages ->
            onDeleteData(messages)
            Toast.makeText(
                (activity as BaseFragmentActivity),
                messages.senderName,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onBinding() {
        binding.rvItems.adapter = adapter.init()
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = LinearLayoutManager((activity as BaseFragmentActivity))
        binding.rvItems.itemAnimator = DefaultItemAnimator()
        binding.rvItems.addItemDecoration(
            dividerVertical(
                (activity as BaseFragmentActivity),
                32,
                0
            )
        )
    }

    /**
     * Observer LiveData
     * */
    private fun onSharedData() {
        sharedViewModel.messages.observe(viewLifecycleOwner) { item ->
            item?.let {
                if (position == -1) {
                    onInsertData(it)
                } else {
                    onUpdateData(it)
                }
            }
        }
    }

    private fun onSelectData() {
        viewModel.select().observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            adapter.differ(list)
        }
    }

    private fun onInsertData(item: Messages) {
        viewModel.insert(item)
    }

    private fun onUpdateData(item: Messages) {
        viewModel.update(item)
    }

    private fun onDeleteData(item: Messages) {
        viewModel.delete(item)
    }

    private fun onObserver() {
        onSharedData()
        onSelectData()
        viewModel.insert.observe(viewLifecycleOwner) {
            Toast.makeText((activity as BaseFragmentActivity), "Data tersimpan!", Toast.LENGTH_SHORT).show()
        }
        viewModel.update.observe(viewLifecycleOwner) {
            Toast.makeText((activity as BaseFragmentActivity), "Data terupdate!", Toast.LENGTH_SHORT).show()
        }
        viewModel.delete.observe(viewLifecycleOwner) {
            Toast.makeText((activity as BaseFragmentActivity), "Data terhapus!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                position = -1
                val intent = Intent((activity as BaseFragmentActivity), MessagesDetailActivity::class.java)
                (activity as BaseFragmentActivity).onResultActivity.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}