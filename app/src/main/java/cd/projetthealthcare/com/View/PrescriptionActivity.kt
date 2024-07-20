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
import com.bumptech.glide.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PrescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setColorSchemeResources(R.color.primary)
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.setOnRefreshListener {
            visiblefab()
        }

        binding.fab.setOnClickListener {
            Utils.newIntent(this, AddPrescripActivity::class.java)
        }
        binding.toolbar.toolbarTitle.text = "Prescription"
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    fun visiblefab(){
        if (Utils.IsDoctor(this)){
            binding.fab.visibility = View.VISIBLE
            getPrescriptionDoc()

        }else{
            getPrescription()
            binding.fab.visibility = View.GONE
        }
    }


    fun getPrescription(){
        val mail = FirebaseAuth.getInstance().currentUser!!.email.toString()
        val uid = Utils.getUID(mail)
        //get prescription from firebase
        val liste_prescrip = ArrayList<MedicalPrescription>()
        val db = FirebaseFirestore.getInstance()
        db.collection(PRESCRIPTION).get().addOnSuccessListener {
            for (doc in it){
                val prescription = doc.toObject(MedicalPrescription::class.java)
                if(prescription.patientId==uid){
                    liste_prescrip.add(prescription)
                }
            }
            if (liste_prescrip.size==0){
                binding.emptyView.visibility = View.VISIBLE
            }else{
                binding.recyclerView.adapter = PrescriptionAda(liste_prescrip)
                binding.swipeRefresh.isRefreshing = false
            }


        }


    }
    fun getPrescriptionDoc(){
        val mail = FirebaseAuth.getInstance().currentUser!!.email.toString()
        val uid = Utils.getUID(mail)
        //get prescription from firebase
        val liste_prescrip = ArrayList<MedicalPrescription>()
        val db = FirebaseFirestore.getInstance()
        db.collection(PRESCRIPTION).get().addOnSuccessListener {
            for (doc in it){
                val prescription = doc.toObject(MedicalPrescription::class.java)
                if(prescription.doctorId==uid){
                    liste_prescrip.add(prescription)
                }
            }
            if (liste_prescrip.size==0){
                binding.emptyView.visibility = View.VISIBLE
            }else{
                binding.recyclerView.adapter = PrescriptionAda(liste_prescrip)
                binding.swipeRefresh.isRefreshing = false
            }

        }


    }

    override fun onResume() {
        visiblefab()
        super.onResume()
    }



}