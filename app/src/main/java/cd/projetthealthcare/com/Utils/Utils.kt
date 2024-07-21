package cd.projetthealthcare.com.Utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cd.bmduka.com.Utils.DATA
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.Model.MedecinMdl
import cd.projetthealthcare.com.Model.PatientMdl
import cd.projetthealthcare.com.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


object Utils {
    //show toast
    fun showToast(context: Context, message: String) {
        Log.d("message", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun showsnakbar(view: View, message: String) {
        Log.d("message", message)
        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
    }
    //new intent
    fun newIntent(context: Context, activity: Class<*>) {
        context.startActivity(Intent(context, activity))
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
    fun savename(context: Context,name_user:PatientMdl){
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nom",name_user.nom)
        editor.putString("prenom",name_user.prenom)
        editor.apply()
    }
    fun savenameDoctore(context: Context,name_user:MedecinMdl){
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
    fun ishomme(context: Context):Boolean{
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getBoolean("homme",false)
    }
    fun savehomme(context: Context,isHomme:Boolean){
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("homme",isHomme)
        editor.apply()
    }

    fun saveDoctor(context: Context,isVendeur:Boolean){
        val sharedPreferences = context.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isVendeur",isVendeur)
        editor.apply()
    }
    fun iniprofile(context: Context,image: ImageView){
        val genre = ishomme(context)
        val isdocteur = IsDoctor(context)
        if (isdocteur){
            if (genre){
                image.setImageResource(R.drawable.docteur)
            }else{
                image.setImageResource(R.drawable.ava_doctore)
            }
        }else{
            if (genre){
                image.setImageResource(R.drawable.femme)
            }else{
                image.setImageResource(R.drawable.avatar_user)
            }
        }

    }

    fun checkgenre(context: Context,sexe:String){
        //check genre
        if (sexe == "Homme"){
            savehomme(context,false)
        }else{
            savehomme(context,true)
        }
    }
    //fonction pour convertir timestamp en date et heure
    fun convertTime(time: Long): String {
        val date = java.sql.Timestamp(time).toString().split(" ")[0]
        //heure sera ajouter +2h
        val heure = java.sql.Timestamp(time).toString().split(" ")[1].split(":")
        return "${heure[0]}:${heure[1]}"
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
        liste_hop.add(Hopital(1,"Hopital Sendwe", telephone = "+243 975937553"))//babi
        liste_hop.add(Hopital(2,"Hopital du Cinquantenaire", telephone = "+243 979405343"))//pierre
        liste_hop.add(Hopital(3,"Hopital General de Reference de Lubumbashi", telephone = "+243 977000013"))//phina
        liste_hop.add(Hopital(4,"Gecamine sud", telephone = "+243 831501106"))//angel
        liste_hop.add(Hopital(5,"Hôpital La Grace", telephone = "+243 970431122"))//Yves
        liste_hop.add(Hopital(6,"Afya Don Bosco", telephone = "+243 973070991"))//lizzy
        liste_hop.add(Hopital(7,"Clinic Universitaire", telephone = "+243 973038270"))//pascal
        return liste_hop
    }


    fun datePicker(context: Context,dates:MaterialButton){
        val date = java.sql.Timestamp(System.currentTimeMillis()).toString().split(" ")[0].split("-")
        val year = date[0].toInt()
        val month = date[1].toInt()
        val day = date[2].toInt()
        val datePickerDialog = DatePickerDialog(context, { view, year, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1
            val date = "$dayOfMonth/$month/$year"
            dates.text = date
        }, year, month, day)
        datePickerDialog.show()
    }

    fun timePicker(context: Context,timechose:MaterialButton){
        val timePickerDialog = TimePickerDialog(context, { view, hourOfDay, minute ->
            val time = "${hourOfDay}H$minute"
            timechose.text = time
        }, 24, 0, false)//sa veut dire que le temps est en 24h
        timePickerDialog.show()
    }

    fun AnnulerRendev(id:String,context: Context){
        //supprimer le rendez-vous
        val db = FirebaseDatabase.getInstance()
        db.getReference(RENDEVOUS).child(id).removeValue()
            .addOnSuccessListener {
                showToast(context,"Rendez-vous annulé")
            }
            .addOnFailureListener {
                showToast(context,"Erreur d'annulation du rendez-vous")
            }
    }

    fun changerStatus(idRendev: String, status: String, context: Context) {
        val db= FirebaseDatabase.getInstance()
        db.getReference(RENDEVOUS).child(idRendev).child("status").setValue(status)
            .addOnSuccessListener {
                showToast(context,"Status changé")
            }
            .addOnFailureListener {
                showToast(context,"Erreur de changement de status")
            }

    }


}