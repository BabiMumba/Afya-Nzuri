package cd.projetthealthcare.com.Model

import cd.babi.chatal.models.user

data class ItemChat (
    var id: String="",
    var lastMessage: String="",
    var timestamp: Long=0,
    var senderId: String="",
    var receiverId: String="",
    var isDoctor: Boolean=false,
    var name: String="",
    var genre: String="",
)
