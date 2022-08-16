package za.ac.tut.hospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.admin.AdminActivity
import za.ac.tut.hospitalmanagementsystem.doctor.DoctorActivity
import za.ac.tut.hospitalmanagementsystem.patient.PatientActivity
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    // fingerprint or face
    lateinit var executor: Executor;
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        val radioPatient = findViewById<RadioButton>(R.id.radioPatient)
        val radioAdmin = findViewById<RadioButton>(R.id.radioAdmin)
        val radioDoctor = findViewById<RadioButton>(R.id.radioDoctor)

        val textInputEditTextId = findViewById<TextInputEditText>(R.id.textInputEditTextId)
        val textInputEditTextPassword = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = androidx.biometric.BiometricPrompt(this@LoginActivity,executor,object:androidx.biometric.BiometricPrompt.AuthenticationCallback(){

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@LoginActivity, "Error! $errString",Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                ///Toast.makeText(this@LoginActivity,result.toString(),Toast.LENGTH_SHORT).show()
                if(radioAdmin.isChecked){
                    val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Successful",Toast.LENGTH_SHORT).show()
                }
                if(radioPatient.isChecked){
                    val intent = Intent(this@LoginActivity, PatientActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Successful",Toast.LENGTH_SHORT).show()
                }
                if(radioDoctor.isChecked){
                    val intent = Intent(this@LoginActivity, DoctorActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Successful",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@LoginActivity, "Failed",Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()


        /*if(radioAdmin.isChecked){

            Toast.makeText(this@LoginActivity,promptInfo.negativeButtonText,Toast.LENGTH_SHORT).show()
        }lse if(radioPatient.isChecked){
            biometricPrompt.authenticate(promptInfo)

            Toast.makeText(this@LoginActivity,promptInfo.negativeButtonText,Toast.LENGTH_SHORT).show()
        }else if(radioDoctor.isChecked){
            biometricPrompt.authenticate(promptInfo)

            Toast.makeText(this@LoginActivity,promptInfo.negativeButtonText,Toast.LENGTH_SHORT).show()
        }*/

        buttonLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
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