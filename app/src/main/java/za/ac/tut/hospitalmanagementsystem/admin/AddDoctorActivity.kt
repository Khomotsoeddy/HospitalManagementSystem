package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.doctor.Doctors
import kotlin.random.Random

class AddDoctorActivity : AppCompatActivity() {

    private val specialization = arrayOf("Dentist","Dermatologist","Gynaecologist","Optometrist")
    private val gender = arrayOf("Male","Female","Other")
    private lateinit var gen : String
    private lateinit var spec: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_doctor)

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            submitDoctorDetails()
        }

        val actvSpec = findViewById<AutoCompleteTextView>(R.id.auto_complete_specification)
        val arrayAdapterSpec = ArrayAdapter(this, R.layout.drop_down, specialization)

        actvSpec.setAdapter(arrayAdapterSpec)
        actvSpec.setOnItemClickListener { adapterView, _, i, _ ->
            spec = adapterView.getItemAtPosition(i).toString()
        }

        val actv = findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down,gender)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            gen = adapterView.getItemAtPosition(i).toString()
            //Toast.makeText(this, "You Appointed $gen", Toast.LENGTH_SHORT).show()
        }


    }

    private fun submitDoctorDetails(){
        val randomValues = Random.nextInt(100000000)

        val firstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName).text.toString()
        val lastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName).text.toString()
        val idNo = findViewById<TextInputEditText>(R.id.textInputEditTextId).text.toString()
        val office = findViewById<TextInputEditText>(R.id.textInputEditTextOffice).text.toString()
        val phone = findViewById<TextInputEditText>(R.id.textInputEditTextPhone).text.toString()
        val address = findViewById<TextInputEditText>(R.id.textInputEditTextAddress).text.toString()
        val email = findViewById<TextInputEditText>(R.id.textInputEditTextEmail).text.toString()
        val password = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()
        val age = findViewById<TextInputEditText>(R.id.textInputEditTextAge).text.toString()
        val role = "doctor"

        val database  = Firebase.database
        val myref = database.getReference("Doctors").child(randomValues.toString())

        myref.setValue(Doctors(
            firstName,
            lastName,
            idNo,
            age,
            gen,
            phone,
            email,
            office,
            address,
            spec,
            password,
            role))

        Toast.makeText(this,"Details save", Toast.LENGTH_LONG).show()

        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }
}