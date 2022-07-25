package za.ac.tut.hospitalmanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.AppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.R
import android.widget.Toast
import com.google.firebase.database.*
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment
import za.ac.tut.hospitalmanagementsystem.doctor.Doctor
import za.ac.tut.hospitalmanagementsystem.patient.CancelAppointmentActivity
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
    //private var doctorDetails : ArrayList<String> = ArrayList()
    private var doctorDetails: ArrayList<String> = ArrayList()
    private var docData: ArrayList<Doctor> = ArrayList()
    private var doctorNames: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_appointments, container, false)
        getTheDoctorDetail()

        val data = arguments
        val patientId = data?.get("patientId").toString()

        println(patientId)
        displayAllAppointments(view,patientId)

        val buttonAll = view.findViewById<Button>(R.id.buttonAll)
        val buttonToday = view.findViewById<Button>(R.id.buttonToday)
        val buttonComing = view.findViewById<Button>(R.id.buttonComing)
        val buttonPast = view.findViewById<Button>(R.id.buttonPast)

        buttonAll.setOnClickListener {
            getAppointmentsList(view,patientId)
        }

        buttonToday.setOnClickListener {
            getTodayAppointment(view,patientId)
        }

        buttonComing.setOnClickListener {
            getUpcomingAppointments(view,patientId)
        }

        buttonPast.setOnClickListener {
            getPastAppointments(view,patientId)
        }

        return view
    }

    private fun displayAllAppointments(view: View, patientId: String){
        appData.clear()
        doctorDetails.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children) {
                val patient = i.child("patient").value.toString()

                if (patientId.contentEquals(patient)){
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
                    for(j in doctorNames) {

                        val realName = j.split(" ")

                        if (doctor.contentEquals(realName[0])) {
                            doctorDetails.add("Dr "+realName[1] + " " + realName[2])
                        } else if (doctor.contentEquals("N/A")) {
                            doctorDetails.add("N/A")
                        }
                    }
                }
            }
            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images,doctorDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPastAppointments(view: View, patientId: String) {
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)
        appData.clear()
        doctorDetails.clear()

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val patient = i.child("patient").value.toString()

                if (patientId.contentEquals(patient)) {
                    val date = i.child("date").value.toString()
                    val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                    if (current.isAfter(chosenDate)) {
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
                        for (j in doctorNames) {

                            val realName = j.split(" ")

                            if (doctor.contentEquals(realName[0])) {
                                doctorDetails.add("Dr " + realName[1] + " " + realName[2])
                            } else if (doctor.contentEquals("N/A")) {
                                doctorDetails.add("N/A")
                            }
                        }
                    }
                }
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images,doctorDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getUpcomingAppointments(view: View, patientId: String) {
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)

        appData.clear()
        doctorDetails.clear()

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val patient = i.child("patient").value.toString()

                if (patientId.contentEquals(patient)) {
                    val date = i.child("date").value.toString()
                    val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                    if (current.isBefore(chosenDate)) {
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
                        for (j in doctorNames) {

                            val realName = j.split(" ")

                            if (doctor.contentEquals(realName[0])) {
                                doctorDetails.add("Dr " + realName[1] + " " + realName[2])
                            } else if (doctor.contentEquals("N/A")) {
                                doctorDetails.add("N/A")
                            }
                        }
                    }
                }
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }
            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images,doctorDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AppointmentsFragment.requireContext(),CancelAppointmentActivity::class.java)
                    intent.putExtra("appointmentId",appData[position].appointmentId)
                    intent.putExtra("specialization",appData[position].specialization)
                    intent.putExtra("description",appData[position].description)
                    intent.putExtra("date",appData[position].date)
                    startActivity(intent)
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTodayAppointment(view: View, patientId: String) {
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val current: LocalDate = LocalDate.parse(currentDate, formatter)
        appData.clear()
        doctorDetails.clear()

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val patient = i.child("patient").value.toString()

                if (patientId.contentEquals(patient)) {
                    val date = i.child("date").value.toString()
                    val chosenDate: LocalDate = LocalDate.parse(date, formatter)

                    if (current.isEqual(chosenDate)) {
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
                        for (j in doctorNames) {

                            val realName = j.split(" ")

                            if (doctor.contentEquals(realName[0])) {
                                doctorDetails.add("Dr " + realName[1] + " " + realName[2])
                            } else if (doctor.contentEquals("N/A")) {
                                doctorDetails.add("N/A")
                            }
                        }
                    }
                }
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images,doctorDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getAppointmentsList(view: View, patientId: String) {
        appData.clear()
        doctorDetails.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for(i in it.children){

                val patient = i.child("patient").value.toString()

                if (patientId.contentEquals(patient)) {
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
                    for (j in doctorNames) {

                        val realName = j.split(" ")

                        if (doctor.contentEquals(realName[0])) {
                            doctorDetails.add("Dr " + realName[1] + " " + realName[2])
                        } else if (doctor.contentEquals("N/A")) {
                            doctorDetails.add("N/A")
                        }
                    }
                }
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments",Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AppointmentRecycleAdapter(appData,images,doctorDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTheDoctorDetail(){
        doctorNames.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {

            for(k in it.children){
                val doctorId = k.key.toString()
                val firstName = k.child("firstName").value.toString()
                val lastName = k.child("lastName").value.toString()
                val age = k.child("age").value.toString()
                val gender = k.child("gender").value.toString()
                val email = k.child("email").value.toString()
                val phone = k.child("phone").value.toString()
                val address = k.child("address").value.toString()
                val idNo = k.child("idNo").value.toString()
                val role =k.child("role").value.toString()
                val office = k.child("office").value.toString()
                val specialization = k.child("specialization").value.toString()

                val doc = Doctor(doctorId,firstName,lastName,idNo,age,gender,phone,email, office,address,specialization,role)
                //println(doc)
                docData.add(doc)
                doctorNames.add("$doctorId $firstName $lastName")

            }
            println(doctorNames)

        }.addOnFailureListener {
            Toast.makeText(this.requireContext(), "failed", Toast.LENGTH_LONG).show()
        }
    }
}