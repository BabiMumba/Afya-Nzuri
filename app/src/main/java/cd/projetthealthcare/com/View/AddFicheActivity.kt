package cd.projetthealthcare.com.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.ActivityFicheBinding

class AddFicheActivity : AppCompatActivity() {
    lateinit var binding: ActivityFicheBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFicheBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}