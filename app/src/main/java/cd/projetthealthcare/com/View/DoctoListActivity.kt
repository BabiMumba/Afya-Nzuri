package cd.projetthealthcare.com.View

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import cd.projetthealthcare.com.Adapter.FilterDocteur
import cd.projetthealthcare.com.Model.MedecinMdl
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivitySiteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DoctoListActivity : AppCompatActivity() {
    lateinit var binding: ActivitySiteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniDoctore()

    }
    fun iniDoctore() {
        binding.loader.visibility = View.VISIBLE
        val filterDocteur = FilterDocteur()
        val liste_docteur = ArrayList<MedecinMdl>()
        val db = FirebaseFirestore.getInstance()
        val email = FirebaseAuth.getInstance().currentUser!!.email.toString()
        val myid = Utils.getUID(email)
        db.collection(MEDECIN)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    liste_docteur.clear()
                    for (document in it.result!!) {
                        val doctore = document.toObject(MedecinMdl::class.java)
                        if (doctore.id != myid) {
                            liste_docteur.add(doctore)
                        }
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    if (liste_docteur.isNotEmpty()) {
                        filterDocteur.items = liste_docteur
                        binding.doctoreRecy.adapter = filterDocteur
                        binding.doctoreRecy.setHasFixedSize(true)
                    } else {
                        Utils.showToast(this, "Aucun docteur disponible")
                    }
                    binding.loader.visibility = View.GONE
                } else {
                    Log.d("TAG", "Error getting documents: ", it.exception)
                    binding.loader.visibility = View.GONE
                }
            }

        binding.searchEdt.addTextChangedListener {
            filterDocteur.filter.filter(it.toString())
        }
    }
}