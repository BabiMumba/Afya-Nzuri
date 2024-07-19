package cd.projetthealthcare.com.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Adapter.PrescriptionAda
import cd.projetthealthcare.com.Model.MedicalPrescription
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.PRESCRIPTION
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityPrescriptionBinding
import com.google.firebase.firestore.FirebaseFirestore

class PrescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        visiblefab()

        binding.fab.setOnClickListener {
            Utils.newIntent(this, AddPrescripActivity::class.java)
        }
        binding.toolbar.toolbarTitle.text = "Prescription"
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
        getPrescription()

    }

    fun visiblefab(){
        if (Utils.IsDoctor(this)){
            binding.fab.visibility = View.VISIBLE
        }else{
            binding.fab.visibility = View.GONE
        }
    }

    override fun onResume() {
        visiblefab()
        super.onResume()
    }

    fun getPrescription(){
        //get prescription from firebase
        val liste_prescrip = ArrayList<MedicalPrescription>()
        val db = FirebaseFirestore.getInstance()
        db.collection(PRESCRIPTION).get().addOnSuccessListener {
            for (doc in it){
                val prescription = doc.toObject(MedicalPrescription::class.java)
                liste_prescrip.add(prescription)
            }
            binding.recyclerView.adapter = PrescriptionAda(liste_prescrip)

        }


    }
}