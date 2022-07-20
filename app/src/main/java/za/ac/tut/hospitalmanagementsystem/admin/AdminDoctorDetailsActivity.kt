package za.ac.tut.hospitalmanagementsystem.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R

class AdminDoctorDetailsActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private val adminDoctorsFragment = AdminDoctorsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_doctor_details)

        val doctorId = intent.getStringExtra("doctorId").toString()
        val firstName = intent.getStringExtra("firstName").toString()
        val lastName = intent.getStringExtra("lastName").toString()
        val idNo = intent.getStringExtra("idNo").toString()
        val age = intent.getStringExtra("age").toString()
        val gender = intent.getStringExtra("gender").toString()
        val phone = intent.getStringExtra("phone").toString()
        val email = intent.getStringExtra("email").toString()
        val office = intent.getStringExtra("office").toString()
        val address = intent.getStringExtra("address").toString()
        val specialization = intent.getStringExtra("specialization").toString()

        val textViewEmployeeId = findViewById<TextView>(R.id.textViewEmployeeId)
        val textViewFirstName = findViewById<TextView>(R.id.textViewFirstName)
        val textViewLastName = findViewById<TextView>(R.id.textViewLastName)
        val textViewIDNo = findViewById<TextView>(R.id.textViewIDNo)
        val textViewAge = findViewById<TextView>(R.id.textViewAge)
        val textViewGender = findViewById<TextView>(R.id.textViewGender)
        val textViewPhone = findViewById<TextView>(R.id.textViewPhone)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        val textViewOffice = findViewById<TextView>(R.id.textViewOffice)
        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)
        val textViewSpecialization = findViewById<TextView>(R.id.textViewSpecialization)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        textViewEmployeeId.text = doctorId
        textViewFirstName.text = firstName
        textViewLastName.text = lastName
        textViewIDNo.text = idNo
        textViewAge.text = age
        textViewGender.text = gender
        textViewPhone.text = phone
        textViewEmail.text = email
        textViewOffice.text = office
        textViewAddress.text = address
        textViewSpecialization.text = specialization

        buttonDelete.setOnClickListener {
            deleteDoctor(doctorId)
        }
    }

    private fun deleteDoctor(doctorId: String) {

        database = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId)
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Doctor removed", Toast.LENGTH_LONG).show()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,adminDoctorsFragment)
        transaction.commit()
    }
}