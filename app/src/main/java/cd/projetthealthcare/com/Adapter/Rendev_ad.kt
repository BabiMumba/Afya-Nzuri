package cd.projetthealthcare.com.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.mdl_rendev
import cd.projetthealthcare.com.R

class Rendev_ad(val liste: ArrayList<mdl_rendev>): RecyclerView.Adapter<Rendev_ad.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.dct_image)
        val date = itemView.findViewById<TextView>(R.id.dct_date)
        val heure = itemView.findViewById<TextView>(R.id.dct_time)
        val docteur = itemView.findViewById<TextView>(R.id.dct_name)
        val specialite = itemView.findViewById<TextView>(R.id.dct_domaine)
        val status = itemView.findViewById<TextView>(R.id.dct_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rendev_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = liste[position]
        holder.date.text = item.date
        holder.heure.text = item.heure
        holder.docteur.text = item.docteur
        holder.specialite.text = item.specialite
        holder.status.text = item.status
       // Glide.with(holder.itemView.context).load(item.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}