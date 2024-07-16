package cd.projetthealthcare.com.Model

data class ItemChat (
    var id: String="",
    var lastMessage: String="",
    var timestamp: Long=0,
    var senderId: String="",
    var receiverId: String="",
    var estDocteur: Boolean=false,
    var name: String="",
    var genre: String="",
)
