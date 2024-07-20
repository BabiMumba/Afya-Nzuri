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
        GetFiche()
    }

    fun visiblefab(){
        if (!Utils.IsDoctor(this)){
            binding.fab.visibility = View.VISIBLE
        }else{
            binding.fab.visibility = View.GONE
        }
    }

    fun GetFiche(){
        val db = FirebaseFirestore.getInstance()
        db.collection(FICHEMEDICAL)
            .get()
            .addOnSuccessListener {
                result ->
            for (document in result) {
                val fiche = document.toObject(FicheModel::class.java)
                liste_fiche.add(fiche)
            }
            if (liste_fiche.size > 0){
                binding.rvFiche.adapter = FicheAdapter(liste_fiche)
            }else{
                binding.emptyLayout.visibility = View.VISIBLE
            }
        }
    }
}