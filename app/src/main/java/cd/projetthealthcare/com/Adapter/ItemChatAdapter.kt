package cd.projetthealthcare.com.Adapter

import android.util.Log
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

            holder.binding.userName.text = item.name


        Log.d("TAG", "onBindViewHolder: ${item.isDoctor}")
           if (item.isDoctor) {
               if (item.genre == "Femme") {
                   holder.binding.imageProfile.setImageResource(R.drawable.docteur)
               } else {
                   holder.binding.imageProfile.setImageResource(R.drawable.ava_doctore)
               }
           }else{
                if (item.genre == "Femme"){
                     holder.binding.imageProfile.setImageResource(R.drawable.femme)
                }else{
                     holder.binding.imageProfile.setImageResource(R.drawable.avatar_user)
                }
           }

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
