package cd.projetthealthcare.com

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cd.projetthealthcare.com.Fragment.HomeFragment
import cd.projetthealthcare.com.Fragment.MessageFragment
import cd.projetthealthcare.com.Fragment.RendeVFragment
import cd.projetthealthcare.com.Fragment.SettingFragment
import cd.projetthealthcare.com.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        inifragment()
        binding.bottomNavigationView.selectedItemId = R.id.homeMenu
        
    }
    
    fun loadFragment(fragment: Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.addToBackStack(null)
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
}