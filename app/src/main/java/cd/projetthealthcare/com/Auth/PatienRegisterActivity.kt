package cd.projetthealthcare.com.Auth

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.MainActivity
import cd.projetthealthcare.com.Model.Patient
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.PATIANT
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityPatienRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatienRegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityPatienRegisterBinding
    lateinit var datenaissance:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPatienRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerBtn.setOnClickListener {
            if (checkFields()){
                val uid = Utils.getUID(binding.edtEmail.text.toString())
                val patient = Patient(
                    binding.adresse.text.toString(),
                    Utils.getAge(datenaissance),
                    binding.edtEmail.text.toString(),
                    binding.genreChoice.selectedItem.toString(),
                    uid,
                    "",
                    binding.edtName.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtPrenom.text.toString(),
                    binding.edtPhone.text.toString()
                )
                registerbymail(patient){
                    if (it){
                        Toast.makeText(this, "Inscription réussie", Toast.LENGTH_SHORT).show()
                        saveuser(patient)
                    }else{
                        Toast.makeText(this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.dateNaissanceBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = android.app.DatePickerDialog(this,
                android.app.DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    datenaissance = "$dayOfMonth/${monthOfYear+1}/$year"
                    binding.dateNaissanceBtn.setText(datenaissance)
                }, year, month, day
            )
            datePickerDialog.show()
        }

    }

    fun registerbymail(patient: Patient,callback: (Boolean) -> Unit){
        //register patient by mail
        val auth = FirebaseAuth.getInstance()
        binding.loader.loaderFrameLayout.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(patient.email, patient.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    callback(true)
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
                    callback(false)
                    binding.loader.loaderFrameLayout.visibility = View.GONE
                }
            }
    }

    fun checkFields():Boolean{
        //check if all fields are filled
        return if (binding.edtName.text.toString().isEmpty()){
            binding.edtName.error = "Veuillez entrer votre nom"
            false
        }else if (binding.edtPrenom.text.toString().isEmpty()) {
            Toast.makeText(this, "Votre prenom", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.edtPhone.text.toString().length!=9) {
            Toast.makeText(this, "Votre numéro doit comporter 9 chiffres", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.edtPhone.text.toString().isEmpty()) {
            Toast.makeText(this, "Votre numéro Téléphone ou texto.", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.adresse.text.toString().isEmpty()) {
            Toast.makeText(this, "Votre Adresse", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.genreChoice.selectedItem.toString() == "Genre"){
            Toast.makeText(this, "Votre Genre", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.dateNaissanceBtn.text.toString()=="Date de naissance"){
            Toast.makeText(this, "Votre date de naissance", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.edtEmail.text.toString().isEmpty()){
            Toast.makeText(this, "Votre mail", Toast.LENGTH_SHORT).show()
            false
        }else if (!Utils.isValidEmail(binding.edtEmail.text.toString())){
            Toast.makeText(this, "Votre mail n'est pas valide", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.edtPassword.text.toString().isEmpty()){
            Toast.makeText(this, "Votre mot de passe", Toast.LENGTH_SHORT).show()
            false
        }else if (binding.edtPassword.text.toString().length<6){
            Toast.makeText(this, "Votre mot de passe doit comporter au moins 6 caractères", Toast.LENGTH_SHORT).show()
            false
        }else
            true

    }
    fun saveuser(patient: Patient){
        //save user in database
        val db = FirebaseFirestore.getInstance()
        db.collection(PATIANT)
            .document(patient.id)
            .set(patient)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.toString()}")
                Utils.savename(this,patient)
                Utils.newIntentFinish(this, MainActivity::class.java)
                binding.loader.loaderFrameLayout.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }
}