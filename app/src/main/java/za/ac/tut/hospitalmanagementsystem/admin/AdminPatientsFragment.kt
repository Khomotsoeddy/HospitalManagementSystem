package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.AppointmentRecycleAdapter
import za.ac.tut.hospitalmanagementsystem.PatientAdapter
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.appointment.Appointment
import za.ac.tut.hospitalmanagementsystem.patient.Patient

class AdminPatientsFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var patData :ArrayList<Patient> = ArrayList()
    private var images :ArrayList<Int> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_patients_admin, container, false)

        getPatientsList(view)

        return view
    }

    private fun getPatientsList(view: View) {

        patData.clear()
        database = FirebaseDatabase.getInstance().getReference("Patients")
        database.get().addOnSuccessListener {
            for(i in it.children){

                    val idNo = i.key.toString()
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val role = i.child("role").value.toString()

                    val patient = Patient(firstName,lastName,idNo,age.toInt(),gender,phone,email,address)

                    patData.add(patient)

            }

            if(patData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.ic_patient_appointment)

            val myAdapter = PatientAdapter(patData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : PatientAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AdminPatientsFragment.requireContext(), AdminPatientProfileActivity::class.java)
                    intent.putExtra("firstName",patData.get(position).firstName)
                    intent.putExtra("lastName",patData.get(position).lastName)
                    intent.putExtra("idNo",patData.get(position).idNo)
                    intent.putExtra("age",patData.get(position).age)
                    intent.putExtra("gender",patData.get(position).gender)
                    intent.putExtra("phone",patData.get(position).phone)
                    intent.putExtra("email",patData.get(position).email)
                    intent.putExtra("address",patData.get(position).address)
                    startActivity(intent)
                }

            })

        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }
}