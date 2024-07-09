package cd.projetthealthcare.com.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Adapter.HopitalAdapter
import cd.projetthealthcare.com.MainActivity
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityChoixHopBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ChoixHopActivity : AppCompatActivity() {
    lateinit var binding: ActivityChoixHopBinding
    var hopitalselecionner:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoixHopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ini_hop()
        binding.continueBtn.setOnClickListener {
            edithopital()
        }

        //changer la couleur du statusbar
        window.statusBarColor = resources.getColor(R.color.primary)

    }

    fun ini_hop(){
        binding.recyclerView.apply {

            val liste_hop = Utils.liste_hop()
            val myadapter = HopitalAdapter(liste_hop)
            binding.recyclerView.adapter = myadapter
            myadapter.setOnItemClickListener(object : HopitalAdapter.OnItemClickListener{
                override fun onItemClick(item: Hopital) {
                    //on click
                    for (i in liste_hop){
                        i.isSelected = false
                    }
                    item.isSelected = true
                    hopitalselecionner = item.nom
                    binding.continueBtn.visibility= if (item.isSelected) View.VISIBLE else View.GONE
                }
            })




        }
    }
    fun edithopital(){
        Utils.isloading(binding.continueBtn,binding.loader,true)
        //editer l'hopital
        val db = FirebaseFirestore.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser?.email
        val uid = Utils.getUID(mail.toString())
        val hopital = hashMapOf(
            "hopital" to hopitalselecionner
        )
        db.collection(MEDECIN).document(uid).set(hopital, SetOptions.merge())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    //on a bien enregistrer l'hopital
                    Utils.saveDoctor(this, true)
                    Utils.showToast(this,"Hopital enregistrer")
                    Utils.newIntent(this, MainActivity::class.java)
                    finish()
                    Utils.isloading(binding.continueBtn,binding.loader,false)

                }else{
                    Utils.showToast(this,"Erreur lors de l'enregistrement de l'hopital")
                    Utils.isloading(binding.continueBtn,binding.loader,false)
                }
            }

    }
}