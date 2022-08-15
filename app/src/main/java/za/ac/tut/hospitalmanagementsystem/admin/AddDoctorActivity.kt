package za.ac.tut.hospitalmanagementsystem.admin

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
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.doctor.Doctors
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.random.Random

class AddDoctorActivity : AppCompatActivity() {

    private val specialization = arrayOf("Dentist","Dermatologist","Gynaecologist","Optometrist")
    private val gender = arrayOf("Male","Female","Other")
    //private lateinit var gen : String
    //private lateinit var spec: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_doctor)
        var gen : String = ""
        var spec: String = ""

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
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            submitDoctorDetails(gen,spec)
        }

    }

    private fun submitDoctorDetails(gen: String, spec: String) {
        var record = true
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

        if(gen.isNullOrEmpty()){
            record = false
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
        }

        if(spec.isNullOrEmpty()){
            record = false
            Toast.makeText(this, "Please select a specialization", Toast.LENGTH_SHORT).show()
        }
        if(age.isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.AgeContainer)
            nameContainer.helperText = "enter age"
        }

        if(age.isNotEmpty()){
            if(age.toInt()<0 && age.toInt()>170){
                record = false
                val nameContainer = findViewById<TextInputLayout>(R.id.AgeContainer)
                nameContainer.helperText = "Invalid age"
            }
        }

        if(firstName.isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.nameContainer)
            nameContainer.helperText = "enter the first name"
        }else{
            var counter = 0
            val firstName = firstName
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
        if(lastName.isEmpty()){
            record = false
            val lastnameContainer = findViewById<TextInputLayout>(R.id.lastnameContainer)
            lastnameContainer.helperText = "enter the last name"
        }else{
            var counter = 0
            val lastName = lastName
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


        if(idNo.isEmpty() ||idNo.length != 13){
            record = false
            val idContainer = findViewById<TextInputLayout>(R.id.idContainer)
            idContainer.helperText = "Invalid ID Number"
        }

        if(phone.length != 10){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }

        if(!phoneNotValid(phone)){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }


        if(email.isEmpty()){
            record = false
            val usernameContainer = findViewById<TextInputLayout>(R.id.emailContainer)
            usernameContainer.helperText = "enter the username"
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            record = false
            val usernameContainer = findViewById<TextInputLayout>(R.id.emailContainer)
            usernameContainer.helperText = "Invalid email"
        }

        if(address.isEmpty()){
            record = false
            val addressContainer = findViewById<TextInputLayout>(R.id.addressContainer)
            addressContainer.helperText = "enter the address"
        }

        if(office.isEmpty()){
            record = false
            val addressContainer = findViewById<TextInputLayout>(R.id.officeContainer)
            addressContainer.helperText = "enter the office number"
        }
        if(password.isEmpty()){
            record = false
            val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
            passwordContainer.helperText = "Invalid password"
        }else if (password.length <6 || password.length >16){
            record = false
            val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
            passwordContainer.helperText = "Invalid password length"
        }

        if(record){
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

    private fun phoneNotValid(toString: String): Boolean {
        var p: Pattern = Pattern.compile("[0][6-8][0-9]{8}")
        var m: Matcher = p.matcher(toString)

        return m.matches()
    }
}