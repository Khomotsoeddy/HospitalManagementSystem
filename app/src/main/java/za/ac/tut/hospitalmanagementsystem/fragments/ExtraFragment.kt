package za.ac.tut.hospitalmanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.admin.AdminActivity
import za.ac.tut.hospitalmanagementsystem.patient.EditPatientProfileActivity
import za.ac.tut.hospitalmanagementsystem.patient.Patient

class ExtraFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var patient : Patient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_extra_admin, container, false)

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

                patient = Patient(firstName,lastName,idNo,age.toInt(),gender,phone,email,address)

            }
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }

        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            goToEditPage()
        }
        return view
    }

    private fun goToEditPage() {
        val intent = Intent(this.requireContext(), EditPatientProfileActivity::class.java)
        startActivity(intent)
    }


}