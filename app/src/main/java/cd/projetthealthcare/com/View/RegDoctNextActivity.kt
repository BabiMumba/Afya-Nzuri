package cd.projetthealthcare.com.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.MainActivity
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityRegDoctNextBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class RegDoctNextActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegDoctNextBinding
     var langues:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegDoctNextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.langueChoise.setOnClickListener {
            chooseLangue()
        }
        binding.continueBtn.setOnClickListener {
            edithopital()
        }



    }
    fun chooseLangue():String{
        val listItems = arrayOf("Francais", "Lingala", "Swahili", "Englais")
        val mBuilder = android.app.AlertDialog.Builder(this)
        mBuilder.setTitle("Choisissez votre langue")
        mBuilder.setMultiChoiceItems(listItems, null) { dialogInterface, i, b ->
            if (b) {
                langues += listItems[i] + ","
            } else {
                langues = langues.replace(listItems[i] + ",", "")
            }
        }
        mBuilder.setCancelable(false)
        mBuilder.setPositiveButton("Ok") { dialogInterface, i ->
            binding.langueChoise.text = langues
        }
        mBuilder.setNegativeButton("Annuler") { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        mBuilder.show()
        return langues
    }
    fun edithopital(){
        Utils.isloading(binding.continueBtn,binding.loader,true)
        //editer l'hopital
        val db = FirebaseFirestore.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser?.email
        val uid = Utils.getUID(mail.toString())
        val hopital = hashMapOf(
            "telephone" to binding.edtPhone.text.toString(),
            "experiance" to binding.edtExperiance.text.toString(),
            "specialite" to binding.specialiterChoise.selectedItem.toString(),
            "langue" to langues,
            "description" to binding.description.text.toString()
        )
        db.collection(MEDECIN).document(uid).set(hopital, SetOptions.merge())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    //on a bien enregistrer l'hopital
                    Utils.showToast(this,"Hopital enregistrer")
                    Utils.newIntent(this, ChoixHopActivity::class.java)
                    finish()
                    Utils.isloading(binding.continueBtn,binding.loader,false)

                }else{
                    Utils.showToast(this,"Erreur lors de l'enregistrement de l'hopital")
                    Utils.isloading(binding.continueBtn,binding.loader,false)
                }
            }

    }
}