package cd.projetthealthcare.com.View

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Model.FicheModel
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.Model.MedecinMdl
import cd.projetthealthcare.com.Model.PatientMdl
import cd.projetthealthcare.com.Model.admininfo
import cd.projetthealthcare.com.Model.dataSource
import cd.projetthealthcare.com.Model.userinfo_fiche
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.FICHEMEDICAL
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityAddFicheBinding
import cd.projetthealthcare.com.databinding.ActivityFicheBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddFicheActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    var IdMedecin= ""
    //liste des patients
    val liste_medecin= ArrayList<MedecinMdl>()
    lateinit var binding: ActivityAddFicheBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFicheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initlistehop()
        iniMedecin()
        Handler().postDelayed({
            binding.docteurChoice.visibility = View.VISIBLE
            setupMedecinSpinner()
        }, 1000)


        binding.btnSave.setOnClickListener {
            saveFiche()
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }


    }

    fun initlistehop(){
        val liste_hopital = Utils.liste_hop().map { hopital -> hopital.nom }

        val adapter_hopital = ArrayAdapter(this, android.R.layout.simple_spinner_item, liste_hopital)
        adapter_hopital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.hopitalChoice.adapter = adapter_hopital
        binding.hopitalChoice.setSelection(0)

    }

    fun setupMedecinSpinner(){
        val patientNames = liste_medecin.map { medecin -> "${medecin.prenom} ${medecin.nom}" }

        // Création d'un ArrayAdapter avec la liste des noms
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, patientNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.docteurChoice.adapter = adapter

        // Définir un écouteur pour récupérer l'ID du patient sélectionné
        binding.docteurChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPatient = liste_medecin[position]
                IdMedecin = selectedPatient.id // Récupérer l'ID du patient sélectionné
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire
            }
        }
    }

    fun iniMedecin(){
        val db = FirebaseFirestore.getInstance()
        db.collection(MEDECIN).get().addOnSuccessListener { result ->
            for (document in result) {
                val medecin = document.toObject(MedecinMdl::class.java)
                liste_medecin.add(medecin)
            }
            val adapter_medecin = ArrayAdapter(this, android.R.layout.simple_spinner_item, liste_medecin)
            adapter_medecin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.docteurChoice.adapter = adapter_medecin
            binding.docteurChoice.setSelection(0)
        }
    }

    fun saveFiche() {
        binding.btnSave.isEnabled = false
        val id_fiche = db.collection(FICHEMEDICAL).document().id

        val admininfo = admininfo(
            binding.docteurChoice.selectedItem.toString(),
            binding.hopitalChoice.selectedItem.toString()
        )
        val email = FirebaseAuth.getInstance().currentUser?.email
        val uid = Utils.getUID(email!!)
        val dataSource = dataSource(
            uid,
            IdMedecin,
            binding.etVisitDate.text.toString(),
            binding.etAllergies.text.toString(),
            binding.etTraitement.text.toString(),
            binding.motifVisite.text.toString()
        )
        val nomcomple = Utils.username(this)
        val userinfo = userinfo_fiche(
            nomcomple,
            binding.etAge.text.toString(),
            binding.genreChoice.selectedItem.toString(),
            binding.etAddress.text.toString(),
            binding.etPhoneNumber.text.toString()
        )
        val fiche = FicheModel(
            id_fiche,
            admininfo,
            dataSource,
            userinfo
        )
        db.collection(FICHEMEDICAL).document(id_fiche).set(fiche).addOnSuccessListener {
            Utils.showsnakbar(binding.root, "Fiche enregistrée avec succès")
            onBackPressed()
            binding.btnSave.isEnabled = true
        }.addOnFailureListener {
            Utils.showToast(this, "Erreur lors de l'enregistrement de la fiche")
            binding.btnSave.isEnabled = true
        }
    }
}