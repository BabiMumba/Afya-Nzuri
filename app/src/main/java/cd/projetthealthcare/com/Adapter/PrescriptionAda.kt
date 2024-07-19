package cd.projetthealthcare.com.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.MedicalPrescription
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ItemPrescriptionBinding


class PrescriptionAda(val liste_prescription: ArrayList<MedicalPrescription>): RecyclerView.Adapter<PrescriptionAda.ViewHolder>(){

    //utiliser le viewholder pour afficher les données avec binding
    inner class ViewHolder(val binding: ItemPrescriptionBinding):RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPrescriptionBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return liste_prescription.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = liste_prescription[position]
        holder.binding.etMedicationName.text = item.medications.name
        holder.binding.etMedicationHours.text = item.medications.heure
        holder.binding.etMedicationDosage.text = item.medications.dosage
        holder.binding.etMedicationDuration.text = item.medications.duration
        holder.binding.etMedicationFrequency.text = item.medications.frequency
        holder.binding.etMedicationUnity.text = item.medications.unity
        val contexte = holder.itemView.context
        if (Utils.IsDoctor(contexte)){
            holder.binding.nameUse.text = item.userdata.patientName
        }else{
            holder.binding.txvUser.text = "MEDECIN: "
            holder.binding.nameUse.text = "Dr. ${item.userdata.doctorIdName}"
        }


    }
}