package cd.projetthealthcare.com.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Adapter.FicheAdapter
import cd.projetthealthcare.com.Model.FicheModel
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.FICHEMEDICAL
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityFicheBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FicheActivity : AppCompatActivity() {
    lateinit var binding: ActivityFicheBinding
    val liste_fiche = ArrayList<FicheModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFicheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        visiblefab()
        binding.fab.setOnClickListener {
            Utils.newIntent(this, AddFicheActivity::class.java)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
        GetFiche()
    }

    fun visiblefab(){
        if (!Utils.IsDoctor(this)){
            binding.fab.visibility = View.VISIBLE
        }else{
            binding.fab.visibility = View.GONE
        }
    }

    override fun onResume() {
        GetFiche()
        super.onResume()
    }
    fun GetFiche(){
        val db = FirebaseFirestore.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser?.email.toString()
        db.collection(FICHEMEDICAL)
            .get()
            .addOnSuccessListener {
                result ->
            for (document in result) {
                val fiche = document.toObject(FicheModel::class.java)
                if (Utils.IsDoctor(this)){
                    val uid = Utils.getUID(mail)
                    if (fiche.dataSource.id_doctor == uid){
                        liste_fiche.add(fiche)
                    }
                }else{
                    val uid = Utils.getUID(mail)
                    if (fiche.dataSource.id_patient == uid){
                        liste_fiche.add(fiche)
                    }
                }
            }
            if (liste_fiche.size > 0){
                binding.rvFiche.adapter = FicheAdapter(liste_fiche)
            }else{
                binding.emptyLayout.visibility = View.VISIBLE
            }
        }
    }
}