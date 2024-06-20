package cd.projetthealthcare.com.Auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.View.ChoixHopActivity
import cd.projetthealthcare.com.databinding.MedecinRegisterBinding

class MedecinRegesterActivity : AppCompatActivity() {
    lateinit var binding: MedecinRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= MedecinRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this,ChoixHopActivity::class.java))
        }


    }
}