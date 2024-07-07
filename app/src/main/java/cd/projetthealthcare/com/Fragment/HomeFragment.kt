package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.Adapter.DoctoreAdapter
import cd.projetthealthcare.com.Adapter.Speciality
import cd.projetthealthcare.com.Model.Doctore
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.View.FicheActivity
import cd.projetthealthcare.com.View.NotifcationActivity
import cd.projetthealthcare.com.View.PrescriptionActivity
import cd.projetthealthcare.com.databinding.FragmentHomeBinding
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
        binding.notificationIc.setOnClickListener {
            Utils.newIntent(requireActivity(),NotifcationActivity::class.java)
        }
        binding.childTwo.setOnClickListener {
            Utils.newIntent(requireActivity(),PrescriptionActivity::class.java)
        }
        binding.childOne.setOnClickListener {
            Utils.newIntent(requireActivity(),FicheActivity::class.java)
        }
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

        val adapter = Speciality(liste_specialiste)
        binding.specialRecy.adapter = adapter
        binding.specialRecy.setHasFixedSize(true)


    }

    fun iniDoctore(){
        val liste_docteur = ArrayList<Doctore>()
        liste_docteur.add(Doctore("Dr. MUKENDI", specialite= "Cardiologue"))
        liste_docteur.add(Doctore("Dr. Steve", specialite= "Dentiste"))
        liste_docteur.add(Doctore("Dr. Pierre sam", specialite= "Gynecologue"))
        liste_docteur.add(Doctore("Dr. Babistone", specialite= "Pediatre"))

        val adapter = DoctoreAdapter(liste_docteur)
        binding.doctoreRecy.adapter = adapter
    }
}