package cd.projetthealthcare.com.Adapter

import android.app.AlertDialog
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.mdl_rendev
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class Rendev_ad(val liste: ArrayList<mdl_rendev>): RecyclerView.Adapter<Rendev_ad.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.dct_image)
        val date = itemView.findViewById<TextView>(R.id.dct_date)
        val heure = itemView.findViewById<TextView>(R.id.dct_time)
        val docteur = itemView.findViewById<TextView>(R.id.dct_name)
        val specialite = itemView.findViewById<TextView>(R.id.dct_domaine)
        val status = itemView.findViewById<TextView>(R.id.dct_status)
        val hopital = itemView.findViewById<TextView>(R.id.txtv_hopital)
        val btn_annuler = itemView.findViewById<MaterialButton>(R.id.dct_cancel)
        val btn_refuser = itemView.findViewById<MaterialButton>(R.id.dct_refuse)
        val btn_accepter = itemView.findViewById<MaterialButton>(R.id.dct_accept)
        val modifier = itemView.findViewById<MaterialButton>(R.id.dct_edit)
        val lyt_specialite = itemView.findViewById<LinearLayout>(R.id.dct_specialite)
        val lyt_action = itemView.findViewById<LinearLayout>(R.id.dct_action)
        val lyt_patient= itemView.findViewById<LinearLayout>(R.id.dct_action_patient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rendev_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = liste[position]
        holder.date.text = item.date
        holder.heure.text = item.heure
        holder.hopital.text = item.hopital
        val mail = FirebaseAuth.getInstance().currentUser!!.email
        val myiuid= Utils.getUID(mail!!)

        Log.d("namedoct",item.medecin.mednom)
        if (item.id_docteur==myiuid){
            holder.docteur.text = item.patient.patientNom
            if (item.patient.patientGenre=="Femme") {
                holder.image.setImageResource(R.drawable.femme)
            }else{
                holder.image.setImageResource(R.drawable.avatar_user)
            }
            holder.lyt_patient.visibility = View.GONE
            holder.lyt_action.visibility = View.VISIBLE
            holder.btn_refuser.setOnClickListener {
                //refuser le rendez-vous
                val dialog = AlertDialog.Builder(holder.itemView.context)
                dialog.setTitle("Refuser le rendez-vous")
                dialog.setMessage("Voulez-vous vraiment refuser le rendez-vous?")
                dialog.setPositiveButton("Oui"){_,_ ->
                    //refuser le rendez-vous
                    Utils.showToast(holder.itemView.context,"Rendez-vous refusé")
                }
                dialog.setNegativeButton("Non"){_,_ ->}
                dialog.create().show()
            }
            holder.btn_accepter.setOnClickListener {
                //accepter le rendez-vous
                val dialog = AlertDialog.Builder(holder.itemView.context)
                dialog.setTitle("Accepter le rendez-vous")
                dialog.setMessage("Voulez-vous vraiment accepter le rendez-vous?")
                dialog.setPositiveButton("Oui"){_,_ ->
                    //accepter le rendez-vous
                    Utils.showToast(holder.itemView.context,"Rendez-vous accepté")
                }
                dialog.setNegativeButton("Non"){_,_ ->}
                dialog.create().show()
            }

        }else{
            holder.docteur.text = item.medecin.mednom
            holder.lyt_specialite.visibility = View.VISIBLE
            holder.specialite.text = item.medecin.medspecialite
            if (item.medecin.medgenre=="Femme") {
                holder.image.setImageResource(R.drawable.docteur)
            }else{
                holder.image.setImageResource(R.drawable.ava_doctore)
            }
        }


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

    }

    override fun getItemCount(): Int {
        return liste.size
    }
}