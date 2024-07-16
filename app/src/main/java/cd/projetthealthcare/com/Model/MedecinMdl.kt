package cd.projetthealthcare.com.Model

data class MedecinMdl(
    val id: String,
    val genre: String,
    val nom: String,
    val postnom: String,
    val prenom: String="",
    val telephone: String,
    val specialite: String,
    val hopital: String="",
    val image: String="",
    val mail: String,
    val password: String,
    val langue: String,
    val experiance: String,
    val description: String)
{
    constructor() : this("","","","","","", "", "", "", "", "", "", "", "")
}
