package cd.babi.chatal.models

import java.util.Date

data class Message(
    var senderId: String = "",
    var receiverId: String = "",
    var message: String = "",
    var messageId: String = "",
    var time: Long = Date().time,
    var statedoctoe: statedoctore = statedoctore(),
    var user : user = user(),
)

class statedoctore(
    var sender:Boolean = false,
    var receiver:Boolean = false
)

class user(
    var sendername:String = "",
    var receivername:String = "",
    var genresender:String = "",
    var genrereceiver:String = ""
)
