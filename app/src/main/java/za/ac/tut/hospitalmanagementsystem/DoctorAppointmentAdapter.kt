package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment

class DoctorAppointmentAdapter(private val appointments : ArrayList<Appointment>): RecyclerView.Adapter<DoctorAppointmentAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointment_view,parent,false)
        return MyViewHolder(itemView,mListener)
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

    class MyViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val textViewPatient = itemView.findViewById<TextView>(R.id.textViewPatient)!!
        val textViewDescription = itemView.findViewById<TextView>(R.id.textViewDescription)!!
        val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)!!
        val textViewTime = itemView.findViewById<TextView>(R.id.textViewTime)!!
        val textViewAppointmentId = itemView.findViewById<TextView>(R.id.textViewAppointmentId)!!

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}