package cd.projetthealthcare.com.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.projetthealthcare.com.Adapter.Rendev_ad
import cd.projetthealthcare.com.Model.mdl_rendev
import cd.projetthealthcare.com.Utils.RENDEVOUS
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.View.DoctoListActivity
import cd.projetthealthcare.com.databinding.FragmentRendeVBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class RendeVFragment : Fragment() {
    lateinit var binding: FragmentRendeVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRendeVBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        if (Utils.IsDoctor(requireActivity())){
            binding.fab.visibility = View.GONE
            inirendevDoctor()
        }else{
            inirendevPatient()
        }
        binding.fab.setOnClickListener {
            Utils.newIntent(requireActivity(),DoctoListActivity::class.java)
        }
        return binding.root

    }

    fun inirendevPatient(){
     //get data from firestore firebase
        binding.progress.visibility = View.VISIBLE
        val liste_rendevous = ArrayList<mdl_rendev>()
        val db = FirebaseDatabase.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser!!.email
        val myiuid= Utils.getUID(mail!!)
        db.getReference(RENDEVOUS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    liste_rendevous.clear()
                   // Log.d("TAG", "onDataChange: Snapshot children count = ${snapshot.childrenCount}")
                    for (doc in snapshot.children) {
                        val item = doc.getValue(mdl_rendev::class.java)
                        if (item != null) {
                        //    Log.d("TAG", "onDataChange: ItemChat - isDoctor = ${item.estDocteur}, genre = ${item.genre}")
                            if (item.id_patient == myiuid){
                                liste_rendevous.add(item)
                            }
                        } else {
                            Log.e("TAG", "onDataChange: ItemChat object is null")
                        }
                    }
                    val adapter = Rendev_ad(liste_rendevous)
                    if (liste_rendevous.size > 0) {
                        binding.progress.visibility = View.GONE
                        binding.emptyChat.visibility = View.GONE
                        binding.rendevRecycler.adapter = adapter
                    } else {
                        binding.progress.visibility = View.GONE
                        binding.emptyChat.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.progress.visibility = View.GONE
                    Log.d("TAG", "Error getting documents: ", error.toException())
                    binding.emptyChat.visibility = View.VISIBLE
                    binding.emptyText.text = "Erreur de chargement des données"
                }
            }
        )
    }
    fun inirendevDoctor(){
        //get data from firestore firebase
        binding.progress.visibility = View.VISIBLE
        val liste_rendevous = ArrayList<mdl_rendev>()
        val db = FirebaseDatabase.getInstance()
        val mail = FirebaseAuth.getInstance().currentUser!!.email
        val myiuid= Utils.getUID(mail!!)
        db.getReference(RENDEVOUS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    liste_rendevous.clear()
                    // Log.d("TAG", "onDataChange: Snapshot children count = ${snapshot.childrenCount}")
                    for (doc in snapshot.children) {
                        val item = doc.getValue(mdl_rendev::class.java)
                        if (item != null) {
                            //    Log.d("TAG", "onDataChange: ItemChat - isDoctor = ${item.estDocteur}, genre = ${item.genre}")
                            if (item.id_docteur == myiuid){
                                liste_rendevous.add(item)
                            }
                        } else {
                            Log.e("TAG", "onDataChange: ItemChat object is null")
                        }
                    }
                    val adapter = Rendev_ad(liste_rendevous)
                    if (liste_rendevous.size > 0) {
                        binding.progress.visibility = View.GONE
                        binding.emptyChat.visibility = View.GONE
                        binding.rendevRecycler.adapter = adapter
                    } else {
                        binding.progress.visibility = View.GONE
                        binding.emptyChat.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.progress.visibility = View.GONE
                    Log.d("TAG", "Error getting documents: ", error.toException())
                    binding.emptyChat.visibility = View.VISIBLE
                    binding.emptyText.text = "Erreur de chargement des données"
                }
            }
        )
    }

}