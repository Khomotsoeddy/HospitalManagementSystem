package za.ac.tut.hospitalmanagementsystem.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R

class DoctorProfileFragment : Fragment() {

    private var docData = ArrayList<Doctor>()
    private lateinit var database : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_doctor_profile, container, false)

        val data = arguments
        val doctorId = data?.get("doctorId").toString()

        println(doctorId)

        displayInformation(view,doctorId)

        return view
    }

    private fun displayInformation(view: View, doctorEmpId: String) {
        val textViewEmployeeId = view.findViewById<TextView>(R.id.textViewEmployeeId)
        val textViewFirstName = view.findViewById<TextView>(R.id.textViewFirstName)
        val textViewLastName = view.findViewById<TextView>(R.id.textViewLastName)
        val textViewIDNo = view.findViewById<TextView>(R.id.textViewIDNo)
        val textViewPhone = view.findViewById<TextView>(R.id.textViewPhone)
        val textViewEmail = view.findViewById<TextView>(R.id.textViewEmail)
        val textViewOffice = view.findViewById<TextView>(R.id.textViewOffice)
        val textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)

        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for (i in it.children) {
                val doctorId = i.key.toString()

                if(doctorId.contentEquals(doctorEmpId)){
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val idNo = i.child("idNo").value.toString()
                    val role = i.child("role").value.toString()
                    val office = i.child("office").value.toString()
                    val specialization = i.child("specialization").value.toString()

                    val doc = Doctor(
                        doctorId,
                        firstName,
                        lastName,
                        idNo,
                        age,
                        gender,
                        phone,
                        email,
                        office,
                        address,
                        specialization,
                        role
                    )

                    docData.add(doc)
                }
            }

            if (docData.size == 0) {
                Toast.makeText(
                    this.requireContext(),
                    "No available appointments",
                    Toast.LENGTH_LONG
                ).show()
            }

            for(i in docData) {
                textViewEmployeeId.text = i.doctorId
                textViewFirstName.text = i.firstName
                textViewLastName.text = i.lastName
                textViewIDNo.text = i.idNo
                textViewPhone.text = i.phone
                textViewEmail.text = i.email
                textViewOffice.text = i.office
                textViewAddress.text = i.address
            }
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }
}