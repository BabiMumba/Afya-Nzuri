package cd.projetthealthcare.com.Utils

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cd.bmduka.com.Utils.DATA
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.Model.Patient
import cd.projetthealthcare.com.R
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.security.Timestamp


object Utils {
    //show toast
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    //new intent
    fun newIntent(context: Context, activity: Class<*>) {
        context.startActivity(Intent(context, activity))
    }
    //new intent with extra
    fun newIntentWithExtra(context: Context, activity: Class<*>, key: String, value: String,id_boutique:String,value_boutique:String) {
        val intent = Intent(context, activity)
        intent.putExtra(key, value)
        intent.putExtra(id_boutique,value_boutique)
        context.startActivity(intent)
    }
    //new intent finish
    fun newIntentFinish(context: Context, activity: Class<*>) {
        context.startActivity(Intent(context, activity))
        (context as android.app.Activity).finish()
    }
    fun isloading(button:View,progressBar: CircularProgressIndicator, isLoading: Boolean) {
        if (isLoading) {
            button.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            button.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

    }
    fun username(context: Context):String{
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val prenom = sharedPreferences.getString("prenom","").toString()
        val nom = sharedPreferences.getString("nom","").toString()
        return "$prenom $nom"
    }
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun getUID(mail:String):String{
        var id_user = mail.substringBefore("@").toString()
        //retirer les points et les caracteres speciaux
        for (i in id_user.indices){
            if (id_user[i] == '.' || id_user[i] == '#' || id_user[i] == '$' || id_user[i] == '[' || id_user[i] == ']'){
                id_user = id_user.replace(id_user[i].toString(),"")
            }
        }

        return id_user
    }
    fun savename(context: Context,name_user:Patient){
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nom",name_user.nom)
        editor.putString("prenom",name_user.prenom)
        editor.apply()
    }
    fun savenameDoctore(context: Context,name_user:Medecin){
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nom",name_user.nom)
        editor.putString("prenom",name_user.prenom)
        editor.apply()
    }

    fun IsDoctor(context: Context):Boolean{
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isVendeur",false)
    }

    fun saveDoctor(context: Context,isVendeur:Boolean){
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isVendeur",isVendeur)
        editor.apply()
    }

    fun loadfragemnt(context: Context,fragment: Fragment){
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun getAge(toString: String): Int {
        //donc la date doit etre en format jj/mm/yyyy
        val date = toString.split("/")
        val year = date[2].toInt()
        val month = date[1].toInt()
        val day = date[0].toInt()
        val currentYear = java.sql.Timestamp(System.currentTimeMillis()).toString().split(" ")[0].split("-")[0].toInt()
        val currentMonth = java.sql.Timestamp(System.currentTimeMillis()).toString().split(" ")[0].split("-")[1].toInt()
        val currentDay = java.sql.Timestamp(System.currentTimeMillis()).toString().split(" ")[0].split("-")[2].toInt()
        var age = currentYear - year
        if (currentMonth < month || (currentMonth == month && currentDay < day)) {
            age--
        }
        return age

    }



    fun liste_hop():ArrayList<Hopital>{
        val liste_hop = ArrayList<Hopital>()
        liste_hop.add(Hopital(1,"Hopital General de Kinshasa", telephone = "+243 975937553"))
        liste_hop.add(Hopital(2,"Hopital du Cinquantenaire", telephone = "+243 996745324"))
        liste_hop.add(Hopital(3,"Hopital General de Reference de Lubumbashi", telephone = "+243 813456780"))
        liste_hop.add(Hopital(4,"Hopital General de Reference de Kisangani", telephone = "+243 895643231"))
        liste_hop.add(Hopital(5,"Hôpital Provincial de Matadi", telephone = "+243 853256322"))
        liste_hop.add(Hopital(6,"Sendwe hopital", telephone = "+243 975937553"))
        liste_hop.add(Hopital(7,"Clinic Universitaire", telephone = "+243 975388833"))
        return liste_hop
    }



}