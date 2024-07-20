package cd.projetthealthcare.com.Model

data class FicheModel(
    val id_fiche: String,
    val admininfo: admininfo,
    val dataSource: dataSource,
    val userinfo: userinfo_fiche,
){
    constructor(): this("",admininfo(),dataSource(),userinfo_fiche())
}

data class dataSource(
    val id_patient: String,
    val id_doctor: String,
    val date: String,
    val allergies: String,
    val antecedents: String,
    val motif_consultation: String,
){
    constructor(): this("","","","","","")
}
data class admininfo(
    val docteurnom : String,
    val hopitalnom: String,
){
    constructor(): this("","")
}

data class userinfo_fiche(
    val nomcomplete: String,
    val age: String,
    val genre: String,
    val adresse: String,
    val phone: String,
){
    constructor(): this("","","","","")
}
