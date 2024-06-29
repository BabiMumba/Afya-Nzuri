package cd.projetthealthcare.com.Model

data class mdl_rendev(
    val id: Int=0,
    val date: String,
    val heure: String,
    val docteur: String,
    val specialite: String,
    val image: String="",
    val status: String
)
