package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.Adapter.Speciality
import cd.projetthealthcare.com.R
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


        return binding.root
    }

    fun inispecialiste(){
        val liste_specialiste = ArrayList<specialite>()
        liste_specialiste.add(specialite("Cardiologue", "https://www.google.com"))
        liste_specialiste.add(specialite("Dentiste", "https://www.google.com"))
        liste_specialiste.add(specialite("Gynecologue", "https://www.google.com"))
        liste_specialiste.add(specialite("Pediatre", "https://www.google.com"))
        liste_specialiste.add(specialite("Generaliste", "https://www.google.com"))
        liste_specialiste.add(specialite("Ophtalmologue", "https://www.google.com"))
        liste_specialiste.add(specialite("Dermatologue", "https://www.google.com"))
        liste_specialiste.add(specialite("Neurologue", "https://www.google.com"))
        liste_specialiste.add(specialite("Urologue", "https://www.google.com"))
        liste_specialiste.add(specialite("ORL", "https://www.google.com"))
        liste_specialiste.add(specialite("Chirurgien", "https://www.google.com"))
        liste_specialiste.add(specialite("Radiologue", "https://www.google.com"))

        val adapter = Speciality(liste_specialiste)
        binding.specialRecy.adapter = adapter
        binding.specialRecy.setHasFixedSize(true)


    }
}