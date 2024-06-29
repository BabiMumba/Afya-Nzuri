package cd.projetthealthcare.com.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.ItemChat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.View.ChatActivity
import cd.projetthealthcare.com.databinding.ItemChatBinding

class ItemChatAdapter(val liste_message:ArrayList<ItemChat>):RecyclerView.Adapter<ItemChatAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:ItemChatBinding):RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = liste_message[position]
       /* if (position%2==0){
            holder.itemView.animation = android.view.animation.AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fisrt_item)
        }else{
            holder.itemView.animation = android.view.animation.AnimationUtils.loadAnimation(holder.itemView.context, R.anim.second_iten)
        }*/
        holder.binding.userName.text = item.name
        holder.binding.lastMessage.text = item.lastMessage
        holder.itemView.setOnClickListener {
            Utils.newIntent(holder.itemView.context,ChatActivity::class.java)
        }
    }

    override fun getItemCount(): Int {
        return liste_message.size
    }


}
