package za.ac.tut.hospitalmanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.AppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.R
import android.widget.Toast
import com.google.firebase.database.*
import za.ac.tut.hospitalmanagementsystem.admin.AdminAppointmentEditActivity
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

        displayAllAppointments(view)

        val buttonAll = view.findViewById<Button>(R.id.buttonAll)
        val buttonToday = view.findViewById<Button>(R.id.buttonToday)
        val buttonComing = view.findViewById<Button>(R.id.buttonComing)
        val buttonPast = view.findViewById<Button>(R.id.buttonPast)

        buttonAll.setOnClickListener {
            getAppointmentsList(view)
        }

        buttonToday.setOnClickListener {
            getTodayAppointment(view)
        }

        buttonComing.setOnClickListener {
            getUpcomingAppointments(view)
        }

        buttonPast.setOnClickListener {
            getPastAppointments(view)
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
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            var myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPastAppointments(view: View) {
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val date = i.child("date").value.toString()
                val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                if(current.isAfter(chosenDate)){
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
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            var myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getUpcomingAppointments(view: View) {
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val date = i.child("date").value.toString()
                val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                if(current.isBefore(chosenDate)){
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
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }
            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            var myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTodayAppointment(view: View) {
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val date = i.child("date").value.toString()
                val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                if(current.isEqual(chosenDate)){
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
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            var myAdapter = AppointmentRecycleAdapter(appData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
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
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            var myAdapter = AppointmentRecycleAdapter(appData,images)
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