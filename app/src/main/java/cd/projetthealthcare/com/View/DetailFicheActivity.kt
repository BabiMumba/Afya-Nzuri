package cd.projetthealthcare.com.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Model.FicheModel
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.FICHEMEDICAL
import cd.projetthealthcare.com.databinding.ActivityDetailFicheBinding
import com.google.firebase.firestore.FirebaseFirestore

class DetailFicheActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailFicheBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailFicheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.container.visibility = View.GONE
        GetDataFiche()


    }

    fun GetDataFiche(){
        binding.progrees.visibility = View.VISIBLE
        val id_fiche = intent.getStringExtra("id_fiche").toString()

        val db = FirebaseFirestore.getInstance()
        db.collection(FICHEMEDICAL)
            .document(id_fiche)
            .get()
            .addOnSuccessListener {
                document ->
                if (document.exists()){
                    binding.progrees.visibility = View.GONE
                    binding.container.visibility = View.VISIBLE
                    val fiche = document.toObject(FicheModel::class.java)
                    binding.tvFicheNom.text = fiche?.userinfo!!.nomcomplete
                    binding.tvAge.text = fiche.userinfo.age
                    binding.tvSexe.text = fiche.userinfo.genre
                    binding.tvTelephone.text = fiche.userinfo.phone
                    binding.tvAdresse.text = fiche.userinfo.adresse
                    binding.tvHopitalName.text = fiche.admininfo.hopitalnom
                    binding.tvDateVisite.text = fiche.dataSource.date
                    binding.tvAlergie.text = fiche.dataSource.allergies
                    binding.tvMotif.text = fiche.dataSource.motif_consultation

                }
            }
    }
}