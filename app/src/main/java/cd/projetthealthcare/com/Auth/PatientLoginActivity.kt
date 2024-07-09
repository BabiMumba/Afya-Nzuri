package cd.projetthealthcare.com.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.MainActivity
import cd.projetthealthcare.com.Model.Patient
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.PATIANT
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityPatientLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityPatientLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPatientLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginBtn.setOnClickListener {
            LoginWithMail()
        }
        binding.createCount.setOnClickListener {
            startActivity(Intent(this, PatienRegisterActivity::class.java))
        }
    }

    fun LoginWithMail(){
        val email = binding.edtEmail.text.toString()
        val password = binding.password.text.toString()
        if (Utils.isValidEmail(email) && Utils.isValidPassword(password)) {
            Utils.isloading(binding.loginBtn,binding.loader,true)
            val uid = Utils.getUID(email)
            val db = FirebaseFirestore.getInstance()
            db.collection(PATIANT).document(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    val patient = it.toObject(Patient::class.java)
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Utils.savename(this, patient!!)
                            Utils.newIntentFinish(this, MainActivity::class.java)
                            Utils.isloading(binding.loginBtn,binding.loader,false)
                        } else {
                            Utils.showToast(this, "Erreur de connexion")
                            Utils.isloading(binding.loginBtn,binding.loader,false)
                        }
                    }
                } else {
                    Utils.isloading(binding.loginBtn,binding.loader,false)
                    Utils.showToast(this, "Aucun compte patient trouvé")

                }
            }
        }else{
            Utils.showToast(this, "Veuillez remplir tous les champs")
        }

    }
}