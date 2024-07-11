package cd.projetthealthcare.com.Adapter

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.mdl_rendev
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import com.google.android.material.button.MaterialButton

class Rendev_ad(val liste: ArrayList<mdl_rendev>): RecyclerView.Adapter<Rendev_ad.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.dct_image)
        val date = itemView.findViewById<TextView>(R.id.dct_date)
        val heure = itemView.findViewById<TextView>(R.id.dct_time)
        val docteur = itemView.findViewById<TextView>(R.id.dct_name)
        val specialite = itemView.findViewById<TextView>(R.id.dct_domaine)
        val status = itemView.findViewById<TextView>(R.id.dct_status)
        val btn_annuler = itemView.findViewById<MaterialButton>(R.id.dct_cancel)
        val modifier = itemView.findViewById<MaterialButton>(R.id.dct_edit)
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
        val status = when(item.status){
            "0" -> "En attente"
            "1" -> "Accepté"
            "2" -> "Refusé"
            else -> "Annulé"
        }
        holder.status.text = status
        val couleurstatus = when(item.status){
            "0" -> R.color.orange
            "1" -> R.color.vert
            "2" -> R.color.red
            else -> R.color.red
        }
        holder.status.setTextColor(holder.itemView.context.resources.getColor(couleurstatus))
       val id_rendev = item.id
        val context = holder.itemView.context
        holder.btn_annuler.setOnClickListener {
            //annuler le rendez-vous
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Annuler le rendez-vous")
            dialog.setMessage("Voulez-vous vraiment annuler le rendez-vous?")
            dialog.setPositiveButton("Oui"){_,_ ->
                //supprimer le rendez-vous
                Utils.AnnulerRendev(id_rendev,context)
            }
            dialog.setNegativeButton("Non"){_,_ ->}
            dialog.create().show()
        }
        holder.modifier.setOnClickListener {
            //modifier le rendez-vous
        }
        //rafraichir la liste
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}