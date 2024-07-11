package cd.projetthealthcare.com.Model

data class mdl_rendev(
    val id:String,
    val date: String,
    val heure: String,
    val docteur: String,
    val specialite: String,
    val image: String="",
    val status: String,
    val hopital: String,
    val id_patient: String,
    val id_docteur: String,
    val date_rdv: String,
    val description : String
){
    constructor():this("","","","","","","","","","","","")
}
