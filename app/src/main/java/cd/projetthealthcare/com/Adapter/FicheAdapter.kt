package cd.projetthealthcare.com.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.FicheModel
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.View.DetailFicheActivity
import cd.projetthealthcare.com.databinding.ItemFicheBinding

class FicheAdapter(val liste_fiche: ArrayList<FicheModel>): RecyclerView.Adapter<FicheAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFicheBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFicheBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fiche = liste_fiche[position]
        holder.binding.tvFicheTitle.text = "Fiche n° ${position + 1}"
        holder.binding.tvFicheDescription.text = fiche.id_fiche

        holder.itemView.setOnClickListener {
            val id_fiche = fiche.id_fiche
            val intent = Intent(holder.itemView.context, DetailFicheActivity::class.java)
            intent.putExtra("id_fiche", id_fiche)
            holder.itemView.context.startActivity(intent)
        }
        val context = holder.itemView.context
        if (Utils.IsDoctor(context)){
            holder.binding.tvFicheTitle.text = fiche.userinfo.nomcomplete
        }
    }

    override fun getItemCount(): Int {
        return liste_fiche.size
    }
}