package cd.projetthealthcare.com.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.projetthealthcare.com.Model.Doctore
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.View.ProfileDoctoreActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class DoctoreAdapter(val liste:ArrayList<Medecin>): RecyclerView.Adapter<DoctoreAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.dct_name)
        val specialiteTextView: TextView = itemView.findViewById(R.id.dct_domaine)
       // val hopitalTextView: TextView = itemView.findViewById(R.id.hopital_docteur)
       // val imageView: ImageView = itemView.findViewById(R.id.image_docteur)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.doctore_home_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = liste[position]
        val context = holder.itemView.context
        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        holder.nameTextView.text = currentUser.nom
        holder.specialiteTextView.text = currentUser.specialite
      //  holder.hopitalTextView.text = currentUser.hopital
        holder.itemView.setOnClickListener {
            //passer les données à l'activité suivante
            val intent = Intent(holder.itemView.context, ProfileDoctoreActivity::class.java)
            intent.putExtra("id", currentUser.id)
            holder.itemView.context.startActivity(intent)
        }

        /*Glide
            .with(holder.itemView.context)
            .load(currentUser.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.overrideOf(300,600))
            .centerInside()
            .placeholder(circularProgressDrawable)
            .into(holder.imageView)*/
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}