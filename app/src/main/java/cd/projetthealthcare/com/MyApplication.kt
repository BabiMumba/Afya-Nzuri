package cd.projetthealthcare.com

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class MyApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
       //

    }

    override fun onCreate() {
        super.onCreate()
        Locale.setDefault(Locale("fr"))//pour changer la langue de l'application en français
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)//pour désactiver le mode nuit
        //pour changer le time en +2
        val timeZone = TimeZone.getTimeZone("GMT+2")
        TimeZone.setDefault(timeZone)
    }
}