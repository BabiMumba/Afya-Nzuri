package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }

}