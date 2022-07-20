package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.patient.Patient

class PatientAdapter(private val patients: ArrayList<Patient>,private val images : ArrayList<Int>) : RecyclerView.Adapter<PatientAdapter.MyViewHolder>()  {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.patient_row,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(images[0])
        holder.patientId.text = patients.get(position).idNo
        holder.patientName.text = patients.get(position).firstName +" "+patients.get(position).lastName
        holder.patientEmail.text = patients.get(position).email
        holder.patientPhone.text = patients.get(position).phone
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val patientName = itemView.findViewById<TextView>(R.id.textViewPatient)!!
        val patientId = itemView.findViewById<TextView>(R.id.textViewIDNo)!!
         val patientEmail = itemView.findViewById<TextView>(R.id.textViewEmail)!!
        val patientPhone = itemView.findViewById<TextView>(R.id.textViewPhone)!!
        val image = itemView.findViewById<ImageView>(R.id.image)!!

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}