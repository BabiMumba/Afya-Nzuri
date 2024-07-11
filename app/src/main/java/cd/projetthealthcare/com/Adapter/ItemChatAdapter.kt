package cd.projetthealthcare.com.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.ItemChat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.View.ChatActivity
import cd.projetthealthcare.com.ViewModel.MainViewModel
import cd.projetthealthcare.com.databinding.ItemChatBinding

class ItemChatAdapter(val liste_message:ArrayList<ItemChat>):RecyclerView.Adapter<ItemChatAdapter.ViewHolder>() {

    val viewModel = MainViewModel()
    inner class ViewHolder(val binding:ItemChatBinding):RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemChatAdapter.ViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemChatAdapter.ViewHolder, position: Int) {
        val item = liste_message[position]

        val contexte = holder.itemView.context
      /*  if (item.senderId == viewModel.myUid()) {
            viewModel.GetDataUser(item.receiverId) { user->
                if (user != null){
                    holder.binding.userName.text = user.name
                }else{
                    holder.binding.userName.text = "Inconnu"

                }
            }
        }else{
            viewModel.GetDataUser(item.senderId) { user->
                if (user != null){
                    holder.binding.userName.text = user.name
                }else{
                    holder.binding.userName.text = "Inconnu"

                }
            }
        }*/
        holder.binding.lastMessage.text = item.lastMessage
        holder.itemView.setOnClickListener {
            val receiver = if (item.senderId == viewModel.myUid()){
                item.receiverId
            }else{
                item.senderId
            }
            Utils.newIntentWithExtra(contexte,ChatActivity::class.java,"id_sender",receiver)
        }
    }

    override fun getItemCount(): Int {
        return liste_message.size
    }


}
