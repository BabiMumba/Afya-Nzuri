package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.Adapter.ItemChatAdapter
import cd.projetthealthcare.com.Model.ItemChat
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.Utils.chatListPath
import cd.projetthealthcare.com.View.DoctoListActivity
import cd.projetthealthcare.com.ViewModel.MainViewModel
import cd.projetthealthcare.com.databinding.FragmentMessageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageFragment : Fragment() {
    lateinit var binding: FragmentMessageBinding

    private val viewModel = MainViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        iniTMessage()
        if (Utils.IsDoctor(requireActivity())){
            binding.fab.visibility = View.GONE
        }
        binding.fab.setOnClickListener {
            Utils.newIntent(requireActivity(), DoctoListActivity::class.java)
        }
        return binding.root
    }
    fun iniTMessage() {
        binding.progress.visibility = View.VISIBLE
        val liste_message = ArrayList<ItemChat>()
        FirebaseDatabase.getInstance().getReference(chatListPath).child(viewModel.myUid()).orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    liste_message.clear()
                    Log.d("TAG", "onDataChange: Snapshot children count = ${snapshot.childrenCount}")
                    for (doc in snapshot.children) {
                        val item = doc.getValue(ItemChat::class.java)
                        if (item != null) {
                            Log.d("TAG", "onDataChange: ItemChat - isDoctor = ${item.estDocteur}, genre = ${item.genre}")
                            liste_message.add(item)
                        } else {
                            Log.e("TAG", "onDataChange: ItemChat object is null")
                        }
                    }
                    val adapter = ItemChatAdapter(liste_message)
                    if (liste_message.size > 0) {
                        binding.progress.visibility = View.GONE
                        binding.emptyChat.visibility = View.GONE
                        binding.recyclerChat.adapter = adapter
                    } else {
                        binding.progress.visibility = View.GONE
                        binding.emptyChat.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: ${error.message}")
                }
            })
    }

}