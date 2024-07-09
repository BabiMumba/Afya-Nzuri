package cd.projetthealthcare.com.Model

data class Hopital(
    val id: Int,
    val nom: String,
    //val adresse: String,
    val image: String="",
    var isSelected: Boolean = false
)