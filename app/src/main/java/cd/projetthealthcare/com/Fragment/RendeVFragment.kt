package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.Adapter.Rendev_ad
import cd.projetthealthcare.com.Model.mdl_rendev
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.FragmentRendeVBinding

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
        val liste_rend = ArrayList<mdl_rendev>()
        liste_rend.add(mdl_rendev(1,"12/12/2021","12:00","Dr. MUKENDI","Cardiologue","image","En attente"))
        liste_rend.add(mdl_rendev(2,"12/10/2021","13:00","Dr. Pierre","Dentiste","image","En attente"))
        //docta alber
        liste_rend.add(mdl_rendev(3,"12/10/2021","13:00","Dr. alber","Dentiste","image","En attente"))
        //dct sarah
        liste_rend.add(mdl_rendev(4,"12/10/2021","13:00","Dr. sarah","Dentiste","image","En attente"))
        val adapter = Rendev_ad(liste_rend)
        binding.rendevRecycler.adapter = adapter
    }

}