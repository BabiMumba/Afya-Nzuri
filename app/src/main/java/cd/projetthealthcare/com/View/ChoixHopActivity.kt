package cd.projetthealthcare.com.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Adapter.HopitalAdapter
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.ActivityChoixHopBinding

class ChoixHopActivity : AppCompatActivity() {
    lateinit var binding: ActivityChoixHopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoixHopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ini_hop()

        //changer la couleur du statusbar
        window.statusBarColor = resources.getColor(R.color.primary)

    }

    fun ini_hop(){
        binding.recyclerView.apply {
            val liste_hop = ArrayList<Hopital>()
            liste_hop.add(Hopital("Hopital General de Kinshasa", "https://www.google.com"))
            liste_hop.add(Hopital("Hopital du Cinquantenaire", "https://www.google.com"))
            liste_hop.add(Hopital("Hopital General de Reference de Lubumbashi", "https://www.google.com"))
            liste_hop.add(Hopital("Hopital General de Reference de Kisangani", "https://www.google.com"))
            liste_hop.add(Hopital("Hôpital Provincial de Matadi", "https://www.google.com"))
            liste_hop.add(Hopital("Sendwe hopital", "https://www.google.com"))
            liste_hop.add(Hopital("Clinic Universitaire", "https://www.google.com"))

            binding.recyclerView.adapter = HopitalAdapter(liste_hop)


        }
    }
}