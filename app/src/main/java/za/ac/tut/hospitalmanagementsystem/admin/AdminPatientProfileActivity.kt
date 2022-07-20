package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R

class AdminPatientProfileActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private val adminPatientsFragment =AdminPatientsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_patient_profile)

        val firstName = intent.getStringExtra("firstName").toString()
        val lastName = intent.getStringExtra("lastName").toString()
        val idNo = intent.getStringExtra("idNo").toString()
        val age = intent.getIntExtra("age",0)
        val gender = intent.getStringExtra("gender").toString()
        val phone = intent.getStringExtra("phone").toString()
        val email = intent.getStringExtra("email").toString()
        val address = intent.getStringExtra("address").toString()

        val textViewFirstName = findViewById<TextView>(R.id.textViewFirstName)
        val textViewLastName = findViewById<TextView>(R.id.textViewLastName)
        val textViewIDNo = findViewById<TextView>(R.id.textViewIDNo)
        val textViewAge = findViewById<TextView>(R.id.textViewAge)
        val textViewGender = findViewById<TextView>(R.id.textViewGender)
        val textViewPhone = findViewById<TextView>(R.id.textViewPhone)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        textViewFirstName.text = firstName
        textViewLastName.text = lastName
        textViewIDNo.text = idNo
        textViewAge.text = age.toString()
        textViewGender.text = gender
        textViewPhone.text = phone
        textViewEmail.text = email
        textViewAddress.text = address

        buttonDelete.setOnClickListener {
            deleteDoctor(idNo)
        }
    }

    private fun deleteDoctor(idNo: String) {

        database = FirebaseDatabase.getInstance().getReference("Patients").child(idNo)
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Patient removed", Toast.LENGTH_LONG).show()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,adminPatientsFragment)
        transaction.commit()
    }
}