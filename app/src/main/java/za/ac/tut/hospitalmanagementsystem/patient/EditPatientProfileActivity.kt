package za.ac.tut.hospitalmanagementsystem.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.admin.AdminActivity

class EditPatientProfileActivity : AppCompatActivity() {

    private lateinit var firstName:String
    private lateinit var lastName:String
    private lateinit var idNo:String
    private lateinit var phone:String
    private lateinit var email:String
    private lateinit var address:String
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_patient_profile)

        firstName = intent.getStringExtra("firstName").toString()
        lastName = intent.getStringExtra("lastName").toString()
        idNo = intent.getStringExtra("idNo").toString()
        phone = intent.getStringExtra("phone").toString()
        email = intent.getStringExtra("email").toString()
        address = intent.getStringExtra("address").toString()

        val textViewIDNo = findViewById<TextView>(R.id.textViewIDNo)
        val textInputEditTextFirstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val textInputEditTextLastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName)
        val textInputEditTextPhone = findViewById<TextInputEditText>(R.id.textInputEditTextPhone)
        val textInputEditTextAddress = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)
        val textInputEditTextEmail = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        textViewIDNo.text = idNo
        textInputEditTextFirstName.setText(firstName)
        textInputEditTextLastName.setText(lastName)
        textInputEditTextPhone.setText(phone)
        textInputEditTextAddress.setText(address)
        textInputEditTextEmail.setText(email)

        buttonSubmit.setOnClickListener {
            updatePatientDetails(
                textViewIDNo,
                textInputEditTextFirstName,
                textInputEditTextLastName,
                textInputEditTextPhone,
                textInputEditTextAddress,
                textInputEditTextEmail)
        }
    }

    private fun updatePatientDetails(textViewIDNo: TextView?, textInputEditTextFirstName: TextInputEditText?, textInputEditTextLastName: TextInputEditText?, textInputEditTextPhone: TextInputEditText?, textInputEditTextAddress: TextInputEditText?, textInputEditTextEmail: TextInputEditText?) {

        database = FirebaseDatabase.getInstance().getReference("Patients")
        val updateData = mapOf(
            "firstName" to textInputEditTextFirstName?.text.toString(),
            "lastName" to textInputEditTextLastName?.text.toString(),
            "phone" to textInputEditTextPhone?.text.toString(),
            "email" to textInputEditTextEmail?.text.toString(),
            "address" to textInputEditTextAddress?.text.toString()
        )
        database.child(textViewIDNo?.text.toString()!!).updateChildren(updateData)
        Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, PatientActivity::class.java)
        startActivity(intent)
    }
}