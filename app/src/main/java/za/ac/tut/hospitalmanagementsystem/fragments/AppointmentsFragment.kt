package za.ac.tut.hospitalmanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.AppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.R
import android.widget.Toast
import com.google.firebase.database.*
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class AppointmentsFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var appData :ArrayList<Appointment> = ArrayList()
    private var images :ArrayList<Int> = ArrayList()
    private val order= arrayOf("All","Past","Upcoming","Today")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_appointments, container, false)

        getAppointmentsList(view)

        doDropDown(view)

        return view
    }

    private fun doDropDown(view: View) {

        val actv = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this.requireContext().applicationContext, R.layout.drop_down, order)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            val sort = adapterView.getItemAtPosition(i).toString()
            //Toast.makeText(this.context, "You Appointed $gen", Toast.LENGTH_SHORT).show()
            //Println()
            System.out.println(appData.size)

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            recyclerView.adapter = AppointmentRecycleAdapter(appData,images)
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
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }

    }
}