package cd.projetthealthcare.com.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.babi.chatal.models.Message
import cd.projetthealthcare.com.Adapter.MessageAdapter
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val messages = ArrayList<Message>()
    private val messageAdapter by lazy { MessageAdapter(messages) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recycleMessage.adapter = messageAdapter
        initChat()

        binding.btnSend.setOnClickListener {
            val message = binding.message.text.toString().trim()
            if (message.isNotEmpty()) {
                addMessage(Message(message, false))
                binding.message.text.clear()
            }
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initChat() {
        addMessage(Message("bonjour docteur steve", false))
        addMessage(Message("bonjour \ncomment puis-je vous aider", true))
        addMessage(Message("je suis malade", false))
        addMessage(Message("tu peux me d'ecrire tes symptomes", true))
        addMessage(Message("j'ai mal a la tete", false))
        addMessage(Message("tu as de la fievre ?", true))
        addMessage(Message("oui", false))
        addMessage(Message("tu as pris des medicaments ?", true))
        addMessage(Message("non", false))
        addMessage(Message("tu peux prendre du paracetamol", true))
        addMessage(Message("d'accord", false))
    }

    private fun addMessage(message: Message) {
        messages.add(message)
        messageAdapter.notifyItemInserted(messages.size - 1)
        binding.recycleMessage.scrollToPosition(messages.size - 1)
    }
}