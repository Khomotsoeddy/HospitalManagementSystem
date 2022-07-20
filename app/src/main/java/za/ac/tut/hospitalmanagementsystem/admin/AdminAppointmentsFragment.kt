package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.AppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment

class AdminAppointmentsFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var appData :ArrayList<Appointment> = ArrayList()
    private var images :ArrayList<Int> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_appointment_admin, container, false)

        displayAllAppointments(view)
        val buttonAll = view.findViewById<Button>(R.id.buttonAll)
        val buttonNew = view.findViewById<Button>(R.id.buttonNew)
        buttonAll.setOnClickListener {
            getAppointmentsList(view)
        }

        buttonNew.setOnClickListener {
            getNewAppointments(view)
        }
        return view
    }

    private fun displayAllAppointments(view: View){
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val appointmentId = i.key.toString()
                val doctor = i.child("doctor").value.toString()
                val date = i.child("date").value.toString()
                val patient = i.child("patient").value.toString()
                val specialization = i.child("specialization").value.toString()
                val status = i.child("status").value.toString()
                val submitDate = i.child("submitDate").value.toString()
                val time = i.child("time").value.toString()
                val description = i.child("description").value.toString()

                val appoint = Appointment(appointmentId,patient,doctor,specialization,description,time,submitDate,date,status)

                appData.add(appoint)
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }
    private fun getNewAppointments(view: View) {
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val doctor = i.child("doctor").value.toString()
                if(doctor.contentEquals("N/A")){
                    val appointmentId = i.key.toString()
                    val doctor = i.child("doctor").value.toString()
                    val date = i.child("date").value.toString()
                    val patient = i.child("patient").value.toString()
                    val specialization = i.child("specialization").value.toString()
                    val status = i.child("status").value.toString()
                    val submitDate = i.child("submitDate").value.toString()
                    val time = i.child("time").value.toString()
                    val description = i.child("description").value.toString()

                    val appoint = Appointment(appointmentId,patient,doctor,specialization,description,time,submitDate,date,status)

                    appData.add(appoint)
                }
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {

                    val intent = Intent(this@AdminAppointmentsFragment.requireContext(), AdminAppointmentEditActivity::class.java)
                    startActivity(intent)
                }

            })

        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getAppointmentsList(view: View) {
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val appointmentId = i.key.toString()
                val doctor = i.child("doctor").value.toString()
                val date = i.child("date").value.toString()
                val patient = i.child("patient").value.toString()
                val specialization = i.child("specialization").value.toString()
                val status = i.child("status").value.toString()
                val submitDate = i.child("submitDate").value.toString()
                val time = i.child("time").value.toString()
                val description = i.child("description").value.toString()

                val appoint = Appointment(appointmentId,patient,doctor,specialization,description,time,submitDate,date,status)

                appData.add(appoint)
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }
}