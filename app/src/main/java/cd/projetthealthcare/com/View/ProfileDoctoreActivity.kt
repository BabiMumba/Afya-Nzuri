package cd.projetthealthcare.com.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileDoctoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTitle.text = "Profile du docteur"
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.contactBtn.setOnClickListener {
            Utils.newIntent(this,ChatActivity::class.java)
        }
        getDataDocto()
    }

    fun getDataDocto(){
        binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
        val id = intent.getStringExtra("id")
        val db = FirebaseFirestore.getInstance()
        db.collection(MEDECIN).document(id!!).get().addOnSuccessListener {
            if (it.exists()){
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                val medecin = it.toObject(Medecin::class.java)
                binding.nameDoctor.text = medecin!!.nom
                binding.categorie.text = "Specialite: "+medecin.specialite
                binding.hoptitalDoc.text = "Hopital: "+medecin.hopital
                binding.langueDoc.text = "Langue: "+medecin.langue
                binding.experianceDoc.text = "Experiance: "+medecin.experiance
                binding.phoneDoc.text = "+243"+medecin.telephone
                binding.descriptionDoctor.text = medecin.description
            }else{
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                Utils.showToast(this,"Docteur non trouvé")
            }
        }

    }
}