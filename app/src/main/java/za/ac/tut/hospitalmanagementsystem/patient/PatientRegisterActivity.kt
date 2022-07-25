package za.ac.tut.hospitalmanagementsystem.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
        var record = true

        val firstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val lastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName)
        val idNo = findViewById<TextInputEditText>(R.id.textInputEditTextId)
        val phone = findViewById<TextInputEditText>(R.id.textInputEditTextPhone)
        val address = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)
        val email = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        val password = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)
        val age = findViewById<TextInputEditText>(R.id.textInputEditTextAge)
        val role = "patient"

        if(age.text.toString().isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.AgeContainer)
            nameContainer.helperText = "enter age"
        }

        if(firstName.text.toString().isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.nameContainer)
            nameContainer.helperText = "enter the first name"
        }else{
            var counter = 0
            val firstName = firstName.text.toString()
            for( i in 0 until firstName.length){

                val c = firstName[i]

                if(c.isDigit()){
                    counter++
                }else if(c.isLetter()){
                }else if(c == ' '){
                }else{
                    counter++
                }
            }
            if(counter>0){
                record = false
                val nameContainer = findViewById<TextInputLayout>(R.id.nameContainer)
                nameContainer.helperText = "Surname can't contain number or special character"
            }
        }

        if(lastName.text.toString().isEmpty()){
            record = false
            val lastnameContainer = findViewById<TextInputLayout>(R.id.lastnameContainer)
            lastnameContainer.helperText = "enter the last name"
        }else{
            var counter = 0
            val lastName = lastName.text.toString()
            for( i in 0 until lastName.length){

                val c = lastName[i]

                if(c.isDigit()){
                    counter++
                }else if(c.isLetter()){
                }else if(c == ' '){
                }else{
                    counter++
                }
            }
            if(counter>0){
                record = false
                val nameContainer = findViewById<TextInputLayout>(R.id.lastnameContainer)
                nameContainer.helperText = "last name can't contain number or special character"
            }
        }

        if(phone.text.toString().length != 10){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }

        if(email.text.toString().isEmpty()){
            record = false
            val usernameContainer = findViewById<TextInputLayout>(R.id.emailContainer)
            usernameContainer.helperText = "enter the username"
        }

        if(address.text.toString().isEmpty()){
            record = false
            val addressContainer = findViewById<TextInputLayout>(R.id.addressContainer)
            addressContainer.helperText = "enter the address"
        }

        if(idNo.text.toString().isEmpty() ||idNo.text.toString().length != 13){
            record = false
            val idContainer = findViewById<TextInputLayout>(R.id.idContainer)
            idContainer.helperText = "Invalid ID Number"
        }

        if(password.text.toString().isEmpty()){
            record = false
            val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
            passwordContainer.helperText = "Invalid password"
        }else if (password.text.toString().length <6 || password.text.toString().length >16){
            record = false
            val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
            passwordContainer.helperText = "Invalid password length"
        }

        if(record){
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
    }

    private fun goToLoginPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
