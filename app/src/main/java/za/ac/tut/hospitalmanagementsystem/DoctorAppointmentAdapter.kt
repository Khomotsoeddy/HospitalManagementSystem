package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment

class DoctorAppointmentAdapter(private val appointments : ArrayList<Appointment>): RecyclerView.Adapter<DoctorAppointmentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointment_view,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewDate.text = appointments[position].date
        holder.textViewDescription.text = appointments[position].description
        holder.textViewTime.text = appointments[position].time
        holder.textViewPatient.text = appointments[position].patientId
        holder.textViewAppointmentId.text = appointments[position].appointmentId
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewPatient = itemView.findViewById<TextView>(R.id.textViewPatient)!!
        val textViewDescription = itemView.findViewById<TextView>(R.id.textViewDescription)!!
        val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)!!
        val textViewTime = itemView.findViewById<TextView>(R.id.textViewTime)!!
        val textViewAppointmentId = itemView.findViewById<TextView>(R.id.textViewAppointmentId)!!
    }
}