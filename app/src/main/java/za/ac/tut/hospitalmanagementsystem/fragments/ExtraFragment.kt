package za.ac.tut.hospitalmanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.patient.EditPatientProfileActivity
import za.ac.tut.hospitalmanagementsystem.patient.Patient
import za.ac.tut.hospitalmanagementsystem.patient.PatientRecordActivity

class ExtraFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var patient : Patient
    private lateinit var textViewFirstName : TextView
    private lateinit var textViewLastName : TextView
    private lateinit var textViewIDNo : TextView
    private lateinit var textViewAge : TextView
    private lateinit var textViewGender : TextView
    private lateinit var textViewPhone : TextView
    private lateinit var textViewEmail : TextView
    private lateinit var textViewAddress : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_extra_admin, container, false)

        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val buttonMedical = view.findViewById<Button>(R.id.buttonMedical)

        loadPatient(view)

        buttonMedical.setOnClickListener {
            goToRecords(textViewIDNo)
        }

        buttonSubmit.setOnClickListener {
            goToEditPage(textViewFirstName,textViewLastName,textViewIDNo,textViewPhone,textViewEmail,textViewAddress)
        }
        return view
    }

    private fun goToRecords(textViewIDNo: TextView) {
        val intent = Intent(this.requireContext(), PatientRecordActivity::class.java)
        intent.putExtra("idNo",textViewIDNo.text.toString())
        startActivity(intent)
    }

    private fun loadPatient(view: View) {
        database = FirebaseDatabase.getInstance().getReference("Patients")
        database.get().addOnSuccessListener {
            for(i in it.children){

                val idNo = i.key.toString()

                if(idNo.contentEquals("1234567654321")){
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

            }
            println(patient)
            textViewFirstName = view.findViewById<TextView>(R.id.textViewFirstName)
            textViewLastName = view.findViewById<TextView>(R.id.textViewLastName)
            textViewIDNo = view.findViewById<TextView>(R.id.textViewIDNo)
            textViewAge = view.findViewById<TextView>(R.id.textViewAge)
            textViewGender = view.findViewById<TextView>(R.id.textViewGender)
            textViewPhone = view.findViewById<TextView>(R.id.textViewPhone)
            textViewEmail = view.findViewById<TextView>(R.id.textViewEmail)
            textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)


            textViewFirstName.text = patient.firstName
            textViewLastName.text = patient.lastName
            textViewIDNo.text = patient.idNo
            textViewAge.text = patient.age.toString()
            textViewGender.text = patient.gender
            textViewPhone.text = patient.phone
            textViewEmail.text = patient.email
            textViewAddress.text = patient.address

        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToEditPage(
        textViewFirstName: TextView,
        textViewLastName: TextView,
        textViewIDNo: TextView,
        textViewPhone: TextView,
        textViewEmail: TextView,
        textViewAddress: TextView
    ) {
        val intent = Intent(this.requireContext(), EditPatientProfileActivity::class.java)
        intent.putExtra("idNo",textViewIDNo.text.toString())
        intent.putExtra("firstName",textViewFirstName.text.toString())
        intent.putExtra("lastName",textViewLastName.text.toString())
        intent.putExtra("address",textViewAddress.text.toString())
        intent.putExtra("phone",textViewPhone.text.toString())
        intent.putExtra("email",textViewEmail.text.toString())
        startActivity(intent)
    }
}