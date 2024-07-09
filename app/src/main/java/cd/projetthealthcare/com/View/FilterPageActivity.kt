package cd.projetthealthcare.com.View

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Adapter.DoctoreAdapter
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityFilterPageBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class FilterPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val domaine = intent.getStringExtra("domaine").toString()

        binding.filter.text = domaine
        binding.back.setOnClickListener {
            finish()
        }
        GetData(domaine)

    }

    fun GetData(domaine: String){
        // Get data from the database
        binding.loader.visibility = View.VISIBLE
        val liste_docteur = ArrayList<Medecin>()
        val db = FirebaseFirestore.getInstance()
        db.collection(MEDECIN)
            .limit(3)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    liste_docteur.clear()
                    for (document in it.result!!) {
                        val doctore = document.toObject(Medecin::class.java)
                        if (doctore.specialite == domaine) {
                            liste_docteur.add(doctore)
                        }
                    }
                    if (liste_docteur.isNotEmpty()) {
                        val adapter = DoctoreAdapter(liste_docteur)
                        binding.recyclerView.adapter = adapter
                        binding.recyclerView.setHasFixedSize(true)
                    } else {
                        binding.emptyListeLayout.visibility = View.VISIBLE
                        Glide.with(this).asGif().load(R.raw.empty_list).into(binding.emptyListe)

                    }
                    binding.loader.visibility = View.GONE
                } else {
                    Log.d("TAG", "Error getting documents: ", it.exception)
                    binding.loader.visibility = View.GONE
                }
            }
    }
}