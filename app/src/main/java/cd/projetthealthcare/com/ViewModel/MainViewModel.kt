package cd.projetthealthcare.com.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cd.babi.chatal.models.Message
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.Utils.chatListPath
import cd.projetthealthcare.com.Utils.messagePath
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel():ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val mydb = FirebaseDatabase.getInstance()
    val auth = Firebase.auth
    private val _messages = MutableLiveData<ArrayList<Message>>()
    val messages: LiveData<ArrayList<Message>> = _messages



    fun GetmailUser():String{
        val mail = auth.currentUser!!.email.toString()
        return mail
    }
    fun myUid():String{
        val uid = Utils.getUID(auth.currentUser!!.email.toString())
        return uid
    }
    fun sendMessage(message: Message){
        message.messageId = mydb.getReference(messagePath).push().key!!
        message.time = System.currentTimeMillis()
        mydb.getReference(messagePath).child(message.messageId).setValue(message)
            .addOnSuccessListener {
                mydb.getReference(chatListPath).child(message.senderId)
                    .child(message.receiverId).setValue(mapOf(
                        "id" to message.receiverId,
                        "lastMessage" to message.message,
                        "timestamp" to message.time,
                        "senderId" to message.senderId,
                        "receiverId" to message.receiverId,
                        "estDocteur" to message.statedoctoe.sender,
                        "name" to message.user.receivername,
                        "genre" to message.user.genrereceiver
                    ))
                mydb.getReference(chatListPath).child(message.receiverId)
                    .child(message.senderId).setValue(mapOf(
                        "id" to message.senderId,
                        "lastMessage" to message.message,
                        "timestamp" to message.time,
                        "senderId" to message.senderId,
                        "receiverId" to message.receiverId,
                        "estDocteur" to message.statedoctoe.receiver,
                        "name" to message.user.sendername,
                        "genre" to message.user.genresender
                    ))
            }
    }

    fun fetchMessage(receiverId: String) {
        val messageList = ArrayList<Message>()
        mydb.getReference(messagePath)
            .orderByChild("time")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(Message::class.java)
                    if (message != null) {
                        if(
                            (message.senderId ==myUid() && message.receiverId ==receiverId) || (message.senderId ==receiverId && message.receiverId==myUid())
                        ){
                            messageList.add(message)
                            _messages.value = messageList
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            })
    }





}