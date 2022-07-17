package za.ac.tut.hospitalmanagementsystem.patient

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
import za.ac.tut.hospitalmanagementsystem.MainActivity
import za.ac.tut.hospitalmanagementsystem.R

class PatientRegisterActivity : AppCompatActivity() {

    private val gender = arrayOf("Male","Female","Other")
    private lateinit var gen : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        val actv = findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down,gender)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            gen = adapterView.getItemAtPosition(i).toString()
            //Toast.makeText(this, "You Appointed $gen", Toast.LENGTH_SHORT).show()
        }
        buttonSubmit.setOnClickListener {
            addToDatabase()
        }
    }

    private fun addToDatabase() {

        val firstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val lastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName)
        val idNo = findViewById<TextInputEditText>(R.id.textInputEditTextId)
        val phone = findViewById<TextInputEditText>(R.id.textInputEditTextPhone)
        val address = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)
        val email = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        val password = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)
        val age = findViewById<TextInputEditText>(R.id.textInputEditTextAge)
        val role = "patient"

        val database  = Firebase.database
        val myref = database.getReference("Patients").child(idNo.text.toString())

        myref.setValue(
            Patients(
            firstName.text.toString(),
            lastName.text.toString(),
            age.text.toString(),
            gen ,
            email.text.toString(),
            phone.text.toString() ,
            address.text.toString(),
            password.text.toString(),
            role)
        )
        Toast.makeText(this,"Successfully registered", Toast.LENGTH_LONG).show()
        goToLoginPage()
    }

    private fun goToLoginPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
