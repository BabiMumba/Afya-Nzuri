package cd.projetthealthcare.com.Model

data class mdl_rendev(
    val id:String,
    val date: String,
    val heure: String,
    val status: String,
    val hopital: String,
    val id_patient: String,
    val id_docteur: String,
    val date_rdv: String,
    val description : String,
    val patient: patientInfo,
    val medecin: docteurInfo
)
{
    constructor() : this("","","","","","", "", "", "",patientInfo("",""),docteurInfo("","",""))

}

data class patientInfo(
    val patientNom: String = "",
    val patientGenre: String = "",
) {
    // No-argument constructor for Firebase
    constructor() : this("", "")
}
data class docteurInfo(
    val mednom: String = "",
    val medspecialite: String = "",
    val medgenre: String = ""
) {
    // No-argument constructor for Firebase
    constructor() : this("", "", "")
}
