package za.ac.tut.hospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.admin.AdminActivity
import za.ac.tut.hospitalmanagementsystem.doctor.DoctorActivity
import za.ac.tut.hospitalmanagementsystem.patient.PatientActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        val radioPatient = findViewById<RadioButton>(R.id.radioPatient)
        val radioAdmin = findViewById<RadioButton>(R.id.radioAdmin)
        val radioDoctor = findViewById<RadioButton>(R.id.radioDoctor)

        val textInputEditTextId = findViewById<TextInputEditText>(R.id.textInputEditTextId)
        val textInputEditTextPassword = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)

        buttonLogin.setOnClickListener {

            if(radioAdmin.isChecked){
                goToAdminPage(textInputEditTextId,textInputEditTextPassword)
            }else if(radioPatient.isChecked){
                goToPatientPage(textInputEditTextId,textInputEditTextPassword)
            }else if(radioDoctor.isChecked){
                goToDoctorPage(textInputEditTextId,textInputEditTextPassword)
            }

        }
    }

    private fun goToDoctorPage(textInputEditTextId: TextInputEditText, textInputEditTextPassword: TextInputEditText) {
        if (textInputEditTextId.text.toString().isEmpty()){

            Toast.makeText(this,"Please enter your ID",Toast.LENGTH_SHORT).show()
        }else if (textInputEditTextPassword.text.toString().isEmpty()){

            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show()
        }else{
            database = FirebaseDatabase.getInstance().getReference("Doctors").child(textInputEditTextId.text.toString())
            database.get().addOnSuccessListener {
                if(it.exists()) {

                    val passwordData: String = it.child("password").value.toString()

                    if (textInputEditTextPassword.text.toString().contentEquals(passwordData)) {
                        val intent = Intent(this, DoctorActivity::class.java)
                        intent.putExtra("doctorId", it.key.toString())
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Incorrect login details",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"Incorrect login details",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToPatientPage(textInputEditTextId: TextInputEditText, textInputEditTextPassword: TextInputEditText) {

        if (textInputEditTextId.text.toString().isEmpty()){

            Toast.makeText(this,"Please enter your ID",Toast.LENGTH_SHORT).show()
        }else if (textInputEditTextPassword.text.toString().isEmpty()){

            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show()
        }else{

            database = FirebaseDatabase.getInstance().getReference("Patients").child(textInputEditTextId.text.toString())
            database.get().addOnSuccessListener {
                if (it.exists()) {
                    val passwordData: String = it.child("password").value.toString()

                    if (textInputEditTextPassword.text.toString().contentEquals(passwordData)) {
                        val intent = Intent(this, PatientActivity::class.java)
                        intent.putExtra("patientId",it.key.toString())
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Incorrect login details",Toast.LENGTH_SHORT).show()
                    }

                }  else {
                    Toast.makeText(this, "Incorrect login details", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun goToAdminPage(textInputEditTextId: TextInputEditText, textInputEditTextPassword: TextInputEditText) {
        if (textInputEditTextId.text.toString().isEmpty()){

            Toast.makeText(this,"Please enter your ID",Toast.LENGTH_SHORT).show()
        }else if (textInputEditTextPassword.text.toString().isEmpty()){

            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show()
        }else{
            database = FirebaseDatabase.getInstance().getReference("Admins").child(textInputEditTextId.text.toString())
            database.get().addOnSuccessListener {
                if(it.exists()){
                    val passwordData : String = it.child("password").value.toString()

                    if(textInputEditTextPassword.text.toString().contentEquals(passwordData)){
                        val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Incorrect login details",Toast.LENGTH_SHORT).show()
                    }

                }  else {
                    Toast.makeText(this, "Incorrect login details", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
            }
        }
    }

}