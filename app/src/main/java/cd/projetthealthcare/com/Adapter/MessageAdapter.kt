package cd.projetthealthcare.com.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cd.babi.chatal.models.Message
import cd.projetthealthcare.com.ViewModel.MainViewModel
import cd.projetthealthcare.com.databinding.ReceivedMessageItemBinding
import cd.projetthealthcare.com.databinding.SentMessageItemBinding
import com.bumptech.glide.Glide

class MessageAdapter(private var liste_message:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val viewModel = MainViewModel()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType){
            0->{
                ViewHolderReceived(
                    ReceivedMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else->{
                ViewHolderSent(
                    SentMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return liste_message.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = liste_message[position]
        when (holder) {
            is ViewHolderReceived -> {
                holder.bind(currentItem)
            }
            is ViewHolderSent -> {
                holder.bind(currentItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (liste_message[position].senderId==viewModel.myUid()){
            1
        }else 0
    }
    inner class ViewHolderReceived(private val binding: ReceivedMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            //si le texte est vide
            if (item.message.isNotEmpty()){
                binding.tvMessage.text = item.message
            }else{
                binding.tvMessage.visibility = ViewGroup.GONE
            }

        }
    }
    inner class ViewHolderSent(private val binding: SentMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.tvMessage.text = item.message
        }
    }


}