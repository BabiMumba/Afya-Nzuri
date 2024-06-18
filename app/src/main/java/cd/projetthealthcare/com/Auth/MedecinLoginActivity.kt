package cd.projetthealthcare.com.Auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.ActivityMedecinLoginBinding
import cd.projetthealthcare.com.databinding.ActivityPatientLoginBinding

class MedecinLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityMedecinLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedecinLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.createCount.setOnClickListener {

            startActivity(Intent(this, MedecinRegesterActivity::class.java))
        }
    }
}