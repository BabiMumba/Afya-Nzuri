package cd.projetthealthcare.com.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.View.ProfileDoctoreActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class FilterDocteur : RecyclerView.Adapter<FilterDocteur.ViewHolder>(),Filterable{
    var items:ArrayList<Medecin> = ArrayList()
        set(value) {
            field = value
            medecinfilter = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.doctore_home_item,parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = medecinfilter[position]
        val context = holder.itemView.context
        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        holder.nameTextView.text = "Dr ${currentUser.nom} ${currentUser.prenom}"
        holder.specialiteTextView.text = currentUser.specialite
        holder.hopitalTextView.text = currentUser.hopital
        holder.experiance.text = "${currentUser.experiance} ans d'expérience"
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProfileDoctoreActivity::class.java)
            intent.putExtra("id", currentUser.id)
            holder.itemView.context.startActivity(intent)
        }
        val genre = currentUser.genre
        if (genre == "Femme") {
            Glide.with(context)
                .load(R.drawable.docteur)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(circularProgressDrawable)
                .into(holder.imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.ava_doctore)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(circularProgressDrawable)
                .into(holder.imageView)
        }

    }

    override fun getItemCount()=medecinfilter.size

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                medecinfilter = if (charSearch.isEmpty()){
                    items
                }else{
                    val resultlist = items.filter {
                        it.nom.lowercase().contains( charSearch.lowercase())
                    }
                    resultlist as ArrayList<Medecin>
                }
                val filterResults = FilterResults()
                filterResults.values = medecinfilter
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                medecinfilter = p1?.values as ArrayList<Medecin>
                notifyDataSetChanged()
            }

        }
    }
    private  var medecinfilter:ArrayList<Medecin> = ArrayList()

    inner  class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val nameTextView: TextView = itemView.findViewById(R.id.dct_name)
        val specialiteTextView: TextView = itemView.findViewById(R.id.dct_domaine)
        val hopitalTextView: TextView = itemView.findViewById(R.id.hopital)
        val experiance: TextView = itemView.findViewById(R.id.dct_experience)
        val imageView: ImageView = itemView.findViewById(R.id.dct_image)
    }

}