package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment

class AdminAppointmentRecycleAdapter(private val appointments : ArrayList<Appointment>, private val images : ArrayList<Int>, private val doctorDetails : ArrayList<String>,private val patientDetails: ArrayList<String>) : RecyclerView.Adapter<AdminAppointmentRecycleAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewer_admin, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = appointments[position].appointmentId
        holder.description.text = appointments[position].description
        holder.submittedDate.text = appointments[position].submitDate
        holder.date.text = appointments[position].date
        holder.time.text = appointments[position].time
        holder.doctorName.text = doctorDetails[position]
        holder.patient.text = patientDetails[position]
        holder.specialization.text = appointments[position].specialization
        holder.image.setImageResource(images[0])
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    inner class MyViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)!!
        val name = itemView.findViewById<TextView>(R.id.textViewAppointmentId)!!
        val description = itemView.findViewById<TextView>(R.id.textViewDescription)!!
        val submittedDate = itemView.findViewById<TextView>(R.id.textViewSubmitDate)!!
        val date = itemView.findViewById<TextView>(R.id.textViewDate)!!
        val time = itemView.findViewById<TextView>(R.id.textViewTime)!!
        val doctorName = itemView.findViewById<TextView>(R.id.textViewDoctor)!!
        val specialization = itemView.findViewById<TextView>(R.id.textViewSpecialization)!!
        val patient = itemView.findViewById<TextView>(R.id.textViewPatient)!!

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}