package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R

class AdminDoctorEditActivity : AppCompatActivity() {
    private lateinit var doctorId:String
    private lateinit var firstName:String
    private lateinit var lastName:String
    private lateinit var idNo:String
    private lateinit var phone:String
    private lateinit var email:String
    private lateinit var office:String
    private lateinit var address:String
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_doctor_edit)

        doctorId = intent.getStringExtra("doctorId").toString()
        firstName = intent.getStringExtra("firstName").toString()
        lastName = intent.getStringExtra("lastName").toString()
        idNo = intent.getStringExtra("idNo").toString()
        phone = intent.getStringExtra("phone").toString()
        email = intent.getStringExtra("email").toString()
        office = intent.getStringExtra("office").toString()
        address = intent.getStringExtra("address").toString()

        val textViewEmployeeId = findViewById<TextView>(R.id.textViewEmployeeId)
        val textViewIDNo = findViewById<TextView>(R.id.textViewIDNo)
        val textInputEditTextFirstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val textInputEditTextLastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName)
        val textInputEditTextPhone = findViewById<TextInputEditText>(R.id.textInputEditTextPhone)
        val textInputEditTextOffice = findViewById<TextInputEditText>(R.id.textInputEditTextOffice)
        val textInputEditTextAddress = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)
        val textInputEditTextEmail = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        textViewEmployeeId.text = doctorId
        textViewIDNo.text = idNo
        textInputEditTextFirstName.setText(firstName)
        textInputEditTextLastName.setText(lastName)
        textInputEditTextPhone.setText(phone)
        textInputEditTextOffice.setText(office)
        textInputEditTextAddress.setText(address)
        textInputEditTextEmail.setText(email)


        buttonSubmit.setOnClickListener {
            updateDoctorDetails(
                doctorId,
                textInputEditTextFirstName,
                textInputEditTextLastName,
                textInputEditTextPhone,
                textInputEditTextOffice,
                textInputEditTextAddress,
                textInputEditTextEmail)
        }

    }

    private fun updateDoctorDetails(
        doctorId: String,
        textInputEditTextFirstName: TextInputEditText?,
        textInputEditTextLastName: TextInputEditText?,
        textInputEditTextPhone: TextInputEditText?,
        textInputEditTextOffice: TextInputEditText?,
        textInputEditTextAddress: TextInputEditText?,
        textInputEditTextEmail: TextInputEditText?
    ) {
        var record = true

        if(textInputEditTextFirstName?.text.toString().isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.nameContainer)
            nameContainer.helperText = "enter the first name"
        }else{
            var counter = 0
            val firstName = textInputEditTextFirstName?.text.toString()
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

        if(textInputEditTextLastName?.text.toString().isEmpty()){
            record = false
            val lastnameContainer = findViewById<TextInputLayout>(R.id.lastnameContainer)
            lastnameContainer.helperText = "enter the last name"
        }else{
            var counter = 0
            val lastName = textInputEditTextLastName?.text.toString()
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

        if(textInputEditTextPhone?.text.toString().length != 10){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }

        if(textInputEditTextEmail?.text.toString().isEmpty()){
            record = false
            val usernameContainer = findViewById<TextInputLayout>(R.id.emailContainer)
            usernameContainer.helperText = "enter the username"
        }

        if(textInputEditTextOffice?.text.toString().isEmpty()){
            record = false
            val usernameContainer = findViewById<TextInputLayout>(R.id.officeContainer)
            usernameContainer.helperText = "enter the office number"
        }

        if(textInputEditTextAddress?.text.toString().isEmpty()){
            record = false
            val addressContainer = findViewById<TextInputLayout>(R.id.addressContainer)
            addressContainer.helperText = "enter the address"
        }

        if(record){
            database = FirebaseDatabase.getInstance().getReference("Doctors")
            val updateData = mapOf(
                "firstName" to textInputEditTextFirstName?.text.toString(),
                "lastName" to textInputEditTextLastName?.text.toString(),
                "phone" to textInputEditTextPhone?.text.toString(),
                "email" to textInputEditTextEmail?.text.toString(),
                "office" to textInputEditTextOffice?.text.toString(),
                "address" to textInputEditTextAddress?.text.toString()
            )
            database.child(doctorId!!).updateChildren(updateData)
            Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }
}