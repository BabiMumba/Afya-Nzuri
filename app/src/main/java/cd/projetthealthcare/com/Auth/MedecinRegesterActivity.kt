package cd.projetthealthcare.com.Auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.Utils.Utils.checkgenre
import cd.projetthealthcare.com.View.RegDoctNextActivity
import cd.projetthealthcare.com.databinding.MedecinRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MedecinRegesterActivity : AppCompatActivity() {
    lateinit var binding: MedecinRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= MedecinRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            register()
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
        }else if (binding.genreChoice.selectedItem.toString() == "Genre"){
            Toast.makeText(this, "Votre Genre", Toast.LENGTH_SHORT).show()
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

    fun saveuser(medecin: Medecin){
        //save user in database
        val db = FirebaseFirestore.getInstance()
        db.collection(MEDECIN)
            .document(medecin.id)
            .set(medecin)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: $documentReference")
                checkgenre(this,binding.genreChoice.selectedItem.toString())
                Utils.savenameDoctore(this,medecin)
                Utils.newIntentFinish(this, RegDoctNextActivity::class.java)
                binding.loader.loaderFrameLayout.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
                binding.loader.loaderFrameLayout.visibility = View.GONE
            }
    }
    fun register(){
        binding.loader.loaderFrameLayout.visibility = View.VISIBLE
        val auth = FirebaseAuth.getInstance()
        if (checkFields()){
            val id = Utils.getUID(binding.edtEmail.text.toString())
            val medecin = Medecin(
                id,
                binding.genreChoice.selectedItem.toString(),
                binding.edtName.text.toString(),
                binding.edtPostenom.text.toString(),
                binding.edtPrenom.text.toString(),
                "",
                "",
                "",
                "",
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString(),
                "",
                "",
                ""
            )
            auth.createUserWithEmailAndPassword(medecin.mail, medecin.password).addOnCompleteListener {
                if (it.isSuccessful){
                    saveuser(medecin)
                }else{
                    binding.loader.loaderFrameLayout.visibility = View.GONE
                    Utils.showToast(this, "Erreur lors de l'inscription")
                    Log.d("TAG", "register: ${it.exception}")
                }
            }

        }
    }
}