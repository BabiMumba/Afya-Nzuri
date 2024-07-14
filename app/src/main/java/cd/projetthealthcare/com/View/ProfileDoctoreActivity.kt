package cd.projetthealthcare.com.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.RENDEVOUS
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityProfileBinding
import cd.projetthealthcare.com.databinding.RendevousComponeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class ProfileDoctoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    var gr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTitle.text = "Profile du docteur"
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
        val id = intent.getStringExtra("id")
        Log.d("TAG", "onCreate: $id")
        getDataDocto()
        binding.contactBtn.setOnClickListener {
           // Utils.newIntentWithExtra(this,ChatActivity::class.java,"id_sender",intent.getStringExtra("id")!!)
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("id_sender", id)
            intent.putExtra("isdoct", true)
            intent.putExtra("name", binding.nameDoctor.text.toString())
            intent.putExtra("imadoctore",Utils.IsDoctor(this))
            intent.putExtra("genre",gr)
            startActivity(intent)
        }
        binding.rendezBtn.setOnClickListener {
            appointement()
        }
    }

    fun getDataDocto(){
        inivisi(true)
        val id = intent.getStringExtra("id")
        val db = FirebaseFirestore.getInstance()
        db.collection(MEDECIN).document(id!!).get().addOnSuccessListener {
            if (it.exists()){
                inivisi(false)
                val medecin = it.toObject(Medecin::class.java)
                binding.nameDoctor.text = "${medecin!!.nom} ${medecin.prenom}"
                binding.specialiteDoc.text = medecin.specialite
                binding.hoptitalDoc.text = medecin.hopital
                binding.langueDoc.text = "Langue: "+medecin.langue
                binding.experianceDoc.text = "Experiance: "+medecin.experiance+" ans"
                binding.phoneDoc.text = "+243"+medecin.telephone
                binding.descriptionDoctor.text = medecin.description
                val genre = medecin.genre
                gr = genre
                if (genre == "Femme") {
                    Glide.with(this)
                        .load(R.drawable.docteur)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.profilDoctor)
                } else {
                    Glide.with(this)
                        .load(R.drawable.ava_doctore)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.profilDoctor)
                }
            }else{
                inivisi(false)
                Utils.showToast(this,"Docteur non trouvé")
            }
        }

    }
    fun inivisi(status:Boolean){
        if (status){
            binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
            binding.nestedScrollView.visibility = View.GONE
            binding.rendezBtn.visibility = View.GONE
        }else{
            binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
            binding.nestedScrollView.visibility = View.VISIBLE
            binding.rendezBtn.visibility = View.VISIBLE
        }
    }

    fun appointement(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val viewbinding = RendevousComponeBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(viewbinding.root)
        val dates= viewbinding.rendezvousDate
        val time = viewbinding.rendezvousTime
        val btn = viewbinding.rendezvousSend
        val description = viewbinding.rendezvousDescription
        dates.setOnClickListener {
            Utils.datePicker(this,dates)
        }
        time.setOnClickListener {
            Utils.timePicker(this,time)
        }
        btn.setOnClickListener {
            sendData(time.text.toString(),dates.text.toString(),description.text.toString())
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
    fun sendData(heure:String,date:String,description:String){
        val sdf = SimpleDateFormat("dd/M/yyyy HH:M")
        val date_id = SimpleDateFormat("dd-M-yyyy-HH-M")
        val date_dins = sdf.format(Date()).toString()
        val firestore = FirebaseFirestore.getInstance()
        val id_docteur = intent.getStringExtra("id")
        val mail = FirebaseAuth.getInstance().currentUser!!.email.toString()
        val id_patient = Utils.getUID(mail)
        val id = FirebaseFirestore.getInstance().collection(RENDEVOUS).document().id

        val rendevous = hashMapOf(
            "id" to id,
            "date" to date,
            "heure" to heure,
            "docteur" to binding.nameDoctor.text.toString(),
            "specialite" to binding.specialiteDoc.text.toString(),
            "image" to "",
            "status" to "0",
            "hopital" to binding.hoptitalDoc.text.toString(),
            "id_patient" to id_patient,
            "id_docteur" to id_docteur,
            "date_rdv" to date_dins,
            "description" to description
        )
        firestore
            .collection(RENDEVOUS)
            .document(id)
            .set(rendevous)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Utils.showToast(this,"Rendez-vous envoyé")
                }else{
                    Utils.showToast(this,"Erreur d'envoie")
                }
            }
    }

}