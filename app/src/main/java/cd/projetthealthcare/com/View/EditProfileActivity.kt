package cd.projetthealthcare.com.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Model.Patient
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.PATIANT
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityEditProfileBinding
import com.bumptech.glide.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTitle.text = "Modifier le profil"
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
        GetData()
        binding.btnUpdate.setOnClickListener {
            UpdateData()
        }

    }
    fun GetData(){
        val db = FirebaseFirestore.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser?.email.toString()
        val uid = Utils.getUID(mail)
        val user = db.collection(PATIANT).document(uid)
        user.get().addOnSuccessListener {
            if (it.exists()){
                val patient = it.toObject(Patient::class.java)
                binding.edtName.setText(patient?.nom)
                binding.edtPrenom.setText(patient?.prenom)
                binding.edtPhone.setText(patient?.telephone)
                binding.adresse.setText(patient?.adresse)
                binding.edtPostenom.setText(patient?.poste_nom)
            }
        }
    }
    fun UpdateData(){
        Utils.isloading(binding.btnUpdate,binding.progressBar,true)
        val db = FirebaseFirestore.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser?.email.toString()
        val uid = Utils.getUID(mail)
        val user = db.collection(PATIANT).document(uid)
        user.update(
            "nom",binding.edtName.text.toString(),
            "prenom",binding.edtPrenom.text.toString(),
            "telephone",binding.edtPhone.text.toString(),
            "adresse",binding.adresse.text.toString(),
            "poste_nom",binding.edtPostenom.text.toString()
        ).addOnSuccessListener {
            val patient = Patient(
                binding.adresse.text.toString(),
                0,
                mail,
                "",
                uid,
                "",
                binding.edtName.text.toString(),
                "",
                binding.edtPrenom.text.toString(),
                binding.edtPostenom.text.toString(),
                binding.edtPhone.text.toString()
            )
            Utils.savename(this,patient)
            Utils.isloading(binding.btnUpdate,binding.progressBar,false)
            Utils.showToast(this,"Profil mis à jour")
        }
            .addOnFailureListener {
                Utils.isloading(binding.btnUpdate,binding.progressBar,false)
                Utils.showToast(this,it.message.toString())
            }
    }
}