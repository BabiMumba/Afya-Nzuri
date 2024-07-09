package cd.projetthealthcare.com.Model

data class Hopital(
    val id: Int,
    val nom: String,
    val image: String="",
    val telephone: String="",
    var isSelected: Boolean = false
)