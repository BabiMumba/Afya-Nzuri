package cd.projetthealthcare.com.Model

data class MedicalPrescription(
    val patientId: String = "",
    val doctorId: String = "",
    val prescriptionDate: String = "",
    val medications:Medicament = Medicament(),
    val userdata: userdata = userdata(),
)
data class Medicament(
    val name: String = "",
    val dosage: String = "",
    val frequency: String = "",
    val duration: String = "",
    val duree: String = "",
    val unity : String = "",
    val heure : String = "",
)

data class userdata(
    val patientName: String = "",
    val doctorIdName: String = "",

    )
