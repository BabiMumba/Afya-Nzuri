package cd.projetthealthcare.com.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.babi.chatal.models.Message
import cd.babi.chatal.models.statedoctore
import cd.babi.chatal.models.user
import cd.projetthealthcare.com.Adapter.MessageAdapter
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.Utils.Utils.iniprofile
import cd.projetthealthcare.com.Utils.Utils.ishomme
import cd.projetthealthcare.com.ViewModel.MainViewModel
import cd.projetthealthcare.com.databinding.ActivityChatBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var liste_message = ArrayList<Message>()
    private val viewModel = MainViewModel()
    lateinit var myadapter: MessageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myadapter = MessageAdapter(liste_message)
        binding.recycleMessage.adapter = myadapter

        val id_receiver = intent.getStringExtra("id_sender").toString()
        val isdoct = intent.getBooleanExtra("isdoct", false)
        val name = intent.getStringExtra("name").toString()
        val imadoctore = intent.getBooleanExtra("imadoctore", false)
        val genre = intent.getStringExtra("genre").toString()
        val myname = Utils.username(this)
        val genresender = if (ishomme(this)) "Homme" else "Femme"

        val myuser = user(
            myname,
            name,
            genrereceiver = genresender,
            genresender = genre
            )
     //   Log.d("TAG", "onCreate: ${myuser.genre}")
        Log.d("TAG", "Genre2: ${intent.getStringExtra("genre").toString()}")
        binding.userName.text = name
        if (isdoct) {
            if (genre == "Femme") {
                Glide.with(this)
                    .load(R.drawable.docteur)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageProfile)
            } else {
                Glide.with(this)
                    .load(R.drawable.ava_doctore)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageProfile)
            }
        } else {
            if (genre == "Femme"){
                binding.imageProfile.setImageResource(R.drawable.femme)
            }else{
                binding.imageProfile.setImageResource(R.drawable.avatar_user)
            }

        }
        val mystate = statedoctore(isdoct, imadoctore)

        InitMessages(id_receiver)
        Toast.makeText(this, "$id_receiver", Toast.LENGTH_SHORT).show()
        
        binding.btnSend.setOnClickListener {
            val message = binding.message.text.toString()
            if (message.isNotEmpty()) {
                val messages = Message(
                    viewModel.myUid(),
                    id_receiver,
                    message,
                    statedoctoe = mystate,
                    user = myuser
                )
                viewModel.sendMessage(messages)
                binding.message.setText("")
                val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(binding.message.windowToken, 0)
            } else {
                Utils.showToast(this, "Votre message ne peut pas être vide.")
            }

        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun InitMessages(id_receiver: String) {
        viewModel.fetchMessage( id_receiver)
        viewModel.messages.observe(this) { messageList ->
            liste_message.clear()
            liste_message.addAll(messageList)
            myadapter.notifyDataSetChanged()
            binding.recycleMessage.scrollToPosition(liste_message.size - 1)
        }

    }


}