package za.ac.tut.hospitalmanagementsystem.doctor

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
import za.ac.tut.hospitalmanagementsystem.DoctorAppointmentAdapter
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DoctorAppointmentFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var appData :ArrayList<Appointment> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_doctor_appointment, container, false)

        val data = arguments
        val doctorId = data?.get("doctorId").toString()

        println(doctorId)
        displayAllAppointments(view,doctorId)

        val buttonAll = view.findViewById<Button>(R.id.buttonAll)
        val buttonComing = view.findViewById<Button>(R.id.buttonComing)
        val buttonToday = view.findViewById<Button>(R.id.buttonToday)

        buttonAll.setOnClickListener {
            displayAllAppointments(view,doctorId)
        }

        buttonComing.setOnClickListener {
            comingAppointment(view,doctorId)
        }

        buttonToday.setOnClickListener {
            todayAppointment(view,doctorId)
        }
        return view
    }

    private fun todayAppointment(view: View, doctorId: String) {

        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)

        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children) {

                val doctor = i.child("doctor").value.toString()
                if(doctorId.contentEquals(doctor)){
                    val date = i.child("date").value.toString()
                    val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                    if(current.isEqual(chosenDate)) {
                        val appointmentId = i.key.toString()
                        val doctor = i.child("doctor").value.toString()
                        val date = i.child("date").value.toString()
                        val patient = i.child("patient").value.toString()
                        val specialization = i.child("specialization").value.toString()
                        val status = i.child("status").value.toString()
                        val submitDate = i.child("submitDate").value.toString()
                        val time = i.child("time").value.toString()
                        val description = i.child("description").value.toString()

                        val appoint = Appointment(
                            appointmentId,
                            patient,
                            doctor,
                            specialization,
                            description,
                            time,
                            submitDate,
                            date,
                            status
                        )

                        appData.add(appoint)
                    }
                }
            }
            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            recyclerView.adapter = DoctorAppointmentAdapter(appData)

        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun comingAppointment(view: View, doctorId: String) {
        appData.clear()
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children) {

                val doctor = i.child("doctor").value.toString()
                if(doctorId.contentEquals(doctor)){
                    val date = i.child("date").value.toString()
                    val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                    if(current.isBefore(chosenDate)) {
                        val appointmentId = i.key.toString()
                        val doctor = i.child("doctor").value.toString()
                        val date = i.child("date").value.toString()
                        val patient = i.child("patient").value.toString()
                        val specialization = i.child("specialization").value.toString()
                        val status = i.child("status").value.toString()
                        val submitDate = i.child("submitDate").value.toString()
                        val time = i.child("time").value.toString()
                        val description = i.child("description").value.toString()

                        val appoint = Appointment(
                            appointmentId,
                            patient,
                            doctor,
                            specialization,
                            description,
                            time,
                            submitDate,
                            date,
                            status
                        )

                        appData.add(appoint)
                    }
                }
            }
            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            recyclerView.adapter = DoctorAppointmentAdapter(appData)

        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayAllAppointments(view: View, doctorId: String){
        appData.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children) {
                val doctor = i.child("doctor").value.toString()
                if(doctorId.contentEquals(doctor)){
                    val appointmentId = i.key.toString()
                    val doctor = i.child("doctor").value.toString()
                    val date = i.child("date").value.toString()
                    val patient = i.child("patient").value.toString()
                    val specialization = i.child("specialization").value.toString()
                    val status = i.child("status").value.toString()
                    val submitDate = i.child("submitDate").value.toString()
                    val time = i.child("time").value.toString()
                    val description = i.child("description").value.toString()

                    val appoint = Appointment(
                        appointmentId,
                        patient,
                        doctor,
                        specialization,
                        description,
                        time,
                        submitDate,
                        date,
                        status
                    )

                    appData.add(appoint)
                }
            }
            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            recyclerView.adapter = DoctorAppointmentAdapter(appData)

        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }
}