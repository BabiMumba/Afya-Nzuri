package cd.projetthealthcare.com.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import cd.bmduka.com.Utils.DATA
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.Utils.Utils.iniprofile
import cd.projetthealthcare.com.View.EditProfileActivity
import cd.projetthealthcare.com.View.OnboardActivity
import cd.projetthealthcare.com.databinding.FragmentSettingBinding
import com.bumptech.glide.util.Util
import com.google.firebase.auth.FirebaseAuth

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
        GetName()

        binding.logout.setOnClickListener {
            logout()
        }
        iniprofile(requireActivity(),binding.profileImv)
        binding.condition.setOnClickListener {
            val lien = "https://sites.google.com/view/afyanzuri-conditions/accueil"
            OpenSite(lien)
        }
        binding.politique.setOnClickListener {
            val lien = "https://sites.google.com/view/afyanzuri-politic/accueil"
            OpenSite(lien)
        }
        binding.abouteUs.setOnClickListener {
            val lien = "https://sites.google.com/view/afyanzuri-about/accueil"
            OpenSite(lien)
        }
        return binding.root
    }
    fun logout(){
        val FirebaseAuth = FirebaseAuth.getInstance()
       val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Déconnexion")
        dialog.setMessage("Voulez-vous vraiment vous déconnecter?")
        dialog.setPositiveButton("Oui"){_,_ ->
            FirebaseAuth.signOut()
            val sharedPreferences = requireActivity().getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            Utils.newIntentFinish(requireActivity(), OnboardActivity::class.java)
        }
        dialog.setNegativeButton("Non"){_,_ ->}
        dialog.create().show()
    }
    fun GetName(){
        binding.profileName.text = Utils.username(requireActivity())
        if (Utils.IsDoctor(requireActivity())){
            binding.editCount.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        GetName()
    }
    fun OpenSite(lien_site:String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = android.net.Uri.parse(lien_site)
        startActivity(intent)

    }
}