package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import za.ac.tut.hospitalmanagementsystem.doctor.Doctor


class RecycleAdapter(private val docData : ArrayList<Doctor>, private val images : ArrayList<Int>) : RecyclerView.Adapter<RecycleAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)

        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.doctorName.text = "Dr "+docData[position].firstName + " "+docData[position].lastName
        holder.image.setImageResource(images[0])
        holder.specialization.text = docData.get(position).specialization
        holder.doctorEmail.text = docData.get(position).email
        holder.doctorPhone.text = docData.get(position).phone
        holder.office.text = docData.get(position).office
    }

    override fun getItemCount(): Int {
        return docData.size
    }
    class MyViewHolder(itemView: View, listener: onItemClickListener) : ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)!!
        val doctorName = itemView.findViewById<TextView>(R.id.textViewDoctor)!!
        val doctorEmail = itemView.findViewById<TextView>(R.id.textViewEmail)!!
        val doctorPhone = itemView.findViewById<TextView>(R.id.textViewPhone)!!
        val specialization = itemView.findViewById<TextView>(R.id.textViewSpecialization)!!
        val office = itemView.findViewById<TextView>(R.id.textViewOffice)!!

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}