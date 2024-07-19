package cd.projetthealthcare.com.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
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

            Log.d("TAG", "docteur ${item.name}: ${item.estDocteur}")

        Log.d("TAG", "onBindViewHolder: ${item.estDocteur}")
           if (item.estDocteur) {
               holder.binding.userName.text = "Dr ${item.name}"
               if (item.genre == "Femme") {

                   holder.binding.imageProfile.setImageResource(R.drawable.docteur)
               } else {
                   holder.binding.imageProfile.setImageResource(R.drawable.ava_doctore)
               }
           }else{
               holder.binding.userName.text = item.name
                if (item.genre == "Femme"){
                     holder.binding.imageProfile.setImageResource(R.drawable.femme)
                }else{
                     holder.binding.imageProfile.setImageResource(R.drawable.avatar_user)
                }
           }

        val heure = Utils.convertTime(item.timestamp)
        holder.binding.dateMsg.text = heure
        holder.binding.lastMessage.text = item.lastMessage
        holder.itemView.setOnClickListener {
            val receiver = if (item.senderId == viewModel.myUid()){
                item.receiverId
            }else{
                item.senderId
            }
            val intent = Intent(contexte, ChatActivity::class.java)
            intent.putExtra("id_sender", receiver)
            intent.putExtra("isdoct", item.estDocteur)
            intent.putExtra("name", item.name)
            intent.putExtra("imadoctore",Utils.IsDoctor(contexte))
            intent.putExtra("genre",item.genre)
            startActivity(contexte,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return liste_message.size
    }


}
