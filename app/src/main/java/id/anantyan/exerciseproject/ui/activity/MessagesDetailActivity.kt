package id.anantyan.exerciseproject.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.anantyan.exerciseproject.databinding.ActivityMessagesDetailBinding
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.utils.Constant.PASSING_TO_MESSAGES_ACTIVITY

class MessagesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Set Data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        onBinding()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun onBinding() {
        var messages = intent.extras?.getParcelable<Messages>(PASSING_TO_MESSAGES_ACTIVITY)
        if (intent.hasExtra(PASSING_TO_MESSAGES_ACTIVITY)) {
            binding.txtInputSendTo.setText(messages?.senderName)
            binding.txtInputMessages.setText(messages?.message)
            binding.btnSubmit.setOnClickListener {
                messages = Messages(
                    messages?.id,
                    messages?.fromName,
                    binding.txtInputSendTo.text.toString(),
                    binding.txtInputMessages.text.toString()
                )
                val intent = Intent()
                intent.putExtra(PASSING_TO_MESSAGES_ACTIVITY, messages)
                setResult(RESULT_OK, intent)
                finish()
            }
        } else {
            binding.btnSubmit.setOnClickListener {
                messages = Messages(
                    null,
                    "Arya Rezza",
                    binding.txtInputSendTo.text.toString(),
                    binding.txtInputMessages.text.toString()
                )
                val intent = Intent()
                intent.putExtra(PASSING_TO_MESSAGES_ACTIVITY, messages)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}