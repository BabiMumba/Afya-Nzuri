package cd.projetthealthcare.com.Fragment

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cd.projetthealthcare.com.Adapter.DoctoreAdapter
import cd.projetthealthcare.com.Adapter.Speciality
import cd.projetthealthcare.com.Model.Doctore
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.Utils.Utils.iniprofile
import cd.projetthealthcare.com.View.DoctoListActivity
import cd.projetthealthcare.com.View.FicheActivity
import cd.projetthealthcare.com.View.NotifcationActivity
import cd.projetthealthcare.com.View.PrescriptionActivity
import cd.projetthealthcare.com.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import specialite


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        inispecialiste()
        iniDoctore()
        iniprofile(requireActivity(),binding.profileImv)
        binding.notificationIc.setOnClickListener {
            Utils.newIntent(requireActivity(),NotifcationActivity::class.java)
        }
        binding.showMore.setOnClickListener {
            Utils.newIntent(requireActivity(), DoctoListActivity::class.java)
        }
        binding.childTwo.setOnClickListener {
            Utils.newIntent(requireActivity(),PrescriptionActivity::class.java)
        }
        binding.childOne.setOnClickListener {
            Utils.newIntent(requireActivity(),FicheActivity::class.java)
        }
        binding.tvUserName.text = Utils.username(requireActivity())
        return binding.root
    }

    fun inispecialiste(){
        val liste_specialiste = ArrayList<specialite>()
        liste_specialiste.add(specialite("Cardiologue", "https://cdn-icons-png.flaticon.com/128/4795/4795449.png"))
        liste_specialiste.add(specialite("Dentiste", "https://cdn-icons-png.flaticon.com/128/4635/4635353.png"))
        liste_specialiste.add(specialite("Pediatre", "https://cdn-icons-png.flaticon.com/128/10154/10154448.png"))
        liste_specialiste.add(specialite("Dermatologue", "https://cdn-icons-png.flaticon.com/128/1807/1807373.png"))
        liste_specialiste.add(specialite("Neurologue", "https://cdn-icons-png.flaticon.com/128/13563/13563565.png"))
        liste_specialiste.add(specialite("Chirurgien", "https://cdn-icons-png.flaticon.com/128/1722/1722975.png"))
        liste_specialiste.add(specialite("Radiologue", "https://cdn-icons-png.flaticon.com/128/9098/9098623.png"))
        liste_specialiste.add(specialite("Gynecologue", "https://cdn-icons-png.flaticon.com/128/6401/6401484.png"))
        liste_specialiste.add(specialite("Ophtalmologue", "https://cdn-icons-png.flaticon.com/128/9352/9352217.png"))
        liste_specialiste.add(specialite("Generaliste", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuWsH594F9AqoKK4RQi5u_vZaSspuECWzglw&usqp=CAU"))

        val adapter = Speciality(liste_specialiste)
        binding.specialRecy.adapter = adapter
        binding.specialRecy.setHasFixedSize(true)


    }

    fun iniDoctore() {
        binding.loader.visibility = View.VISIBLE
        val liste_docteur = ArrayList<Medecin>()
        val db = FirebaseFirestore.getInstance()
        val email = FirebaseAuth.getInstance().currentUser!!.email.toString()
        val myid = Utils.getUID(email)
        db.collection(MEDECIN)
            .limit(3)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    liste_docteur.clear()
                    for (document in it.result!!) {
                        val doctore = document.toObject(Medecin::class.java)
                        if (doctore.id != myid) {
                            liste_docteur.add(doctore)
                        }
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    if (liste_docteur.isNotEmpty()) {
                        val adapter = DoctoreAdapter(liste_docteur)
                        binding.doctoreRecy.adapter = adapter
                        binding.doctoreRecy.setHasFixedSize(true)
                    } else {
                        Utils.showToast(requireActivity(), "Aucun docteur disponible")
                    }
                    binding.loader.visibility = View.GONE
                } else {
                    Log.d("TAG", "Error getting documents: ", it.exception)
                    binding.loader.visibility = View.GONE
                }
            }
    }

}