package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.View.EditProfileActivity
import cd.projetthealthcare.com.databinding.FragmentSettingBinding
import com.bumptech.glide.util.Util

class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.editCount.setOnClickListener {
            Utils.newIntent(requireActivity(), EditProfileActivity::class.java)
        }
        return binding.root
    }
}