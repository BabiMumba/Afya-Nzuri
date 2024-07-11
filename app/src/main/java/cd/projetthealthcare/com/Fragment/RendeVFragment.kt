package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.Adapter.Rendev_ad
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.Model.mdl_rendev
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.RENDEVOUS
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.FragmentRendeVBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RendeVFragment : Fragment() {
    lateinit var binding: FragmentRendeVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRendeVBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        inirendev()
        return binding.root

    }

    fun inirendev(){
     //get data from firestore firebase
        val liste_docteur = ArrayList<mdl_rendev>()
        val db = FirebaseFirestore.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser!!.email
        val myiuid= Utils.getUID(mail!!)
        db.collection(RENDEVOUS)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    for (doc in it.result!!){
                        val rendev = doc.toObject(mdl_rendev::class.java)
                        if (rendev.id_patient == myiuid){
                            liste_docteur.add(rendev)
                        }
                    }
                    val adapter = Rendev_ad(liste_docteur)
                    binding.rendevRecycler.adapter = adapter
                }else{
                   // binding.loader.visibility = View.GONE
                    Log.d("TAG", "Error getting documents: ", it.exception)
                }
            }
    }

}