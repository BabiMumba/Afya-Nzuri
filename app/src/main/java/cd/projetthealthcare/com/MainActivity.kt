package cd.projetthealthcare.com

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cd.projetthealthcare.com.Adapter.HopitalAdapter
import cd.projetthealthcare.com.Fragment.HomeFragment
import cd.projetthealthcare.com.Fragment.MessageFragment
import cd.projetthealthcare.com.Fragment.RendeVFragment
import cd.projetthealthcare.com.Fragment.SettingFragment
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityMainBinding
import cd.projetthealthcare.com.databinding.BottomSheetListBinding
import com.bumptech.glide.util.Util
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        inifragment()
        binding.bottomNavigationView.selectedItemId = R.id.homeMenu
        inidoctui()
        binding.floatingButton.setOnClickListener {
            BottomSheetHop()
        }

        
    }
    
    fun loadFragment(fragment: Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.commit()
    }
    fun inifragment(){
        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.homeMenu -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.setting -> {
                    loadFragment(SettingFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.chat_menu -> {
                    loadFragment(MessageFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.rendev -> {
                    loadFragment(RendeVFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStack()
        }else{
            finish()
        }
        super.onBackPressed()
    }

    fun inidoctui(){
        val isdocteur = Utils.IsDoctor(this)
        if (isdocteur){
            binding.bottomNavigationView.menu.findItem(R.id.calle).isVisible = false
            binding.floatingButton.visibility = View.GONE
        }
    }

    fun BottomSheetHop(){
        //fonction qui va afficher le bottomsheet avec la liste des hopitaux
        val bottomSheetFragment = BottomSheetDialog(this)
        val view = BottomSheetListBinding.inflate(layoutInflater)
        bottomSheetFragment.setContentView(view.root)
        val liste_hop = Utils.liste_hop()
        val myadapter = HopitalAdapter(liste_hop)
        view.recyclerView.adapter = myadapter
        myadapter.setOnItemClickListener(object : HopitalAdapter.OnItemClickListener{
            override fun onItemClick(item: Hopital) {
                //on click
                for (i in liste_hop){
                    i.isSelected = false
                }
                item.isSelected = true
                view.callBtn.visibility= if (item.isSelected) View.VISIBLE else View.GONE

                view.callBtn.setOnClickListener {
                    val numero = item.telephone
                    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null))
                    startActivity(intent)

                }
            }
        })
        bottomSheetFragment.show()
    }
}