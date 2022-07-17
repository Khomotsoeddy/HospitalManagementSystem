package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment
import za.ac.tut.hospitalmanagementsystem.appointment.Appointments

class AppointmentRecycleAdapter(private val appointments : ArrayList<Appointment>) : RecyclerView.Adapter<AppointmentRecycleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewer,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = appointments.get(position).appointmentId
        holder.description.text = appointments.get(position).description
        holder.submittedDate.text = appointments.get(position).submitDate
        holder.date.text = appointments.get(position).date
        holder.time.text = appointments.get(position).time
        holder.doctorName.text = appointments.get(position).doctorId
        holder.specialization.text = appointments.get(position).specialization
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.textViewAppointmentId)!!
        val description = itemView.findViewById<TextView>(R.id.textViewDescription)!!
        val submittedDate = itemView.findViewById<TextView>(R.id.textViewSubmitDate)!!
        val date = itemView.findViewById<TextView>(R.id.textViewDate)!!
        val time = itemView.findViewById<TextView>(R.id.textViewTime)!!
        val doctorName = itemView.findViewById<TextView>(R.id.textViewDoctor)!!
        val specialization = itemView.findViewById<TextView>(R.id.textViewSpecialization)!!
    }
}