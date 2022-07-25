package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.medicalreport.MedicalReport

class MedicalReportAdapter(private val reports : ArrayList<MedicalReport>): RecyclerView.Adapter<MedicalReportAdapter.MyViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.record_viewer,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewId.text = reports.get(position).recordNo
        holder.textViewMedication.text = reports[position].medication
        holder.textViewWeight.text = reports[position].weight + " Kg"
        holder.textViewTemperature.text = reports[position].temperature +" Degrees Celsius"
        holder.textViewDate.text =reports[position].date
        holder.textViewPatient.text = reports[position].patientId
        holder.textViewAllergy.text = reports[position].allergy
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    class MyViewHolder(itemView: View ):RecyclerView.ViewHolder(itemView){
        val textViewId = itemView.findViewById<TextView>(R.id.textViewId)!!
        val textViewPatient = itemView.findViewById<TextView>(R.id.textViewPatient)!!
        val textViewMedication = itemView.findViewById<TextView>(R.id.textViewMedication)!!
        val textViewTemperature = itemView.findViewById<TextView>(R.id.textViewTemperature)!!
        val textViewWeight = itemView.findViewById<TextView>(R.id.textViewWeight)!!
        val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)!!
        val textViewAllergy = itemView.findViewById<TextView>(R.id.textViewAllergy)!!
    }
}