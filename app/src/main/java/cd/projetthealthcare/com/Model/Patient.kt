package cd.projetthealthcare.com.Model

data class Patient(
    val adresse: String,
    val age: Int,
    val email: String,
    val genre: String,
    var id: String,
    val image: String="",
    val nom: String,
    val password: String,
    val prenom: String,
    val telephone: String
)
{
    constructor() : this("", 0, "", "", "", "", "", "", "", "")
}
