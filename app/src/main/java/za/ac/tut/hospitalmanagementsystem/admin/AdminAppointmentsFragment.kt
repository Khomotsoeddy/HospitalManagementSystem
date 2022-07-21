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
import za.ac.tut.hospitalmanagementsystem.AdminAppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.AppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment
import za.ac.tut.hospitalmanagementsystem.doctor.Doctor
import za.ac.tut.hospitalmanagementsystem.patient.Patient

class AdminAppointmentsFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var appData: ArrayList<Appointment> = ArrayList()
    private var images: ArrayList<Int> = ArrayList()
    private var doctorDetails: ArrayList<String> = ArrayList()
    private var docData: ArrayList<Doctor> = ArrayList()
    private var doctorNames: ArrayList<String> = ArrayList()
    private var patData :ArrayList<Patient> = ArrayList()
    private var patientNames: ArrayList<String> = ArrayList()
    private var patientDetails: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_appointment_admin, container, false)

        getTheDoctorDetail()
        getPatientDetail()

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

    private fun displayAllAppointments(view: View) {
        appData.clear()
        doctorDetails.clear()
        patientDetails.clear()
        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            for (i in it.children) {

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
                for(j in patientNames){

                    val realName = j.split(" ")

                    if(patient.contentEquals(realName[0])){
                        patientDetails.add( realName[1]+" "+realName[2])
                    }
                }
                for(j in doctorNames){

                    val realName = j.split(" ")

                    if(doctor.contentEquals(realName[0])){
                        doctorDetails.add( realName[1]+" "+realName[2])
                    }else if(doctor.contentEquals("N/A")){
                        doctorDetails.add("N/A")
                    }
                }
            }

            println(doctorDetails)
            if (appData.size == 0) {
                Toast.makeText(
                    this.requireContext(),
                    "No available appointments",
                    Toast.LENGTH_LONG
                ).show()
            }
            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AdminAppointmentRecycleAdapter(appData, images, doctorDetails,patientDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AdminAppointmentRecycleAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener{
            Toast.makeText(this.context, "failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getNewAppointments(view: View) {
        appData.clear()
        doctorDetails.clear()
        patientNames.clear()
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

                    for(j in patientNames){

                        val realName = j.split(" ")

                        if(patient.contentEquals(realName[0])){
                            patientNames.add( realName[1]+" "+realName[2])
                        }
                    }
                    doctorDetails.add("N/A")
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

            val myAdapter = AdminAppointmentRecycleAdapter(appData,images,doctorDetails,patientDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AdminAppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {

                    val intent = Intent(this@AdminAppointmentsFragment.requireContext(), AdminAppointmentEditActivity::class.java)
                    intent.putExtra("appointmentId", appData[position].appointmentId)
                    intent.putExtra("description", appData[position].description)
                    intent.putExtra("date", appData[position].date)
                    intent.putExtra("specialization", appData[position].specialization)
                    startActivity(intent)
                }

            })

        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getAppointmentsList(view: View) {
        appData.clear()
        doctorDetails.clear()
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
                for(j in patientNames){

                    val realName = j.split(" ")

                    if(patient.contentEquals(realName[0])){
                        patientNames.add( realName[1]+" "+realName[2])
                    }
                }
                for(j in doctorNames){

                    val realName = j.split(" ")

                    if(doctor.contentEquals(realName[0])){
                        doctorDetails.add( realName[1]+" "+realName[2])
                    }else if(doctor.contentEquals("N/A")){
                        doctorDetails.add("N/A")
                    }
                }
            }

            if(appData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = AdminAppointmentRecycleAdapter(appData,images,doctorDetails,patientDetails)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : AdminAppointmentRecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTheDoctorDetail(){
        doctorNames.clear()
        docData.clear()
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

    private fun getPatientDetail() {
        patientNames.clear()
        patData.clear()
        database = FirebaseDatabase.getInstance().getReference("Patients")
        database.get().addOnSuccessListener {
            for (i in it.children) {

                val idNo = i.key.toString()
                val firstName = i.child("firstName").value.toString()
                val lastName = i.child("lastName").value.toString()
                val age = i.child("age").value.toString()
                val gender = i.child("gender").value.toString()
                val email = i.child("email").value.toString()
                val phone = i.child("phone").value.toString()
                val address = i.child("address").value.toString()
                val role = i.child("role").value.toString()

                val patient =
                    Patient(firstName, lastName, idNo, age.toInt(), gender, phone, email, address)

                patData.add(patient)
                patientNames.add("$idNo $firstName $lastName")

            }

            println(patientNames)
            if(patData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }
 }