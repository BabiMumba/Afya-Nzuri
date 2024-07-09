package cd.projetthealthcare.com.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.ActivityFilterPageBinding

class FilterPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFilterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val domaine = intent.getStringExtra("domaine")

        binding.filter.text = domaine

    }
}