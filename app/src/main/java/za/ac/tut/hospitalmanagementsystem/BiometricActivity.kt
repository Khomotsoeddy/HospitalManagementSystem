package za.ac.tut.hospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import za.ac.tut.hospitalmanagementsystem.admin.AdminActivity
import java.util.concurrent.Executor

class BiometricActivity : AppCompatActivity() {

    // fingerprint or face
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)

        val radioPatient = findViewById<RadioButton>(R.id.radioPatient)
        val radioAdmin = findViewById<RadioButton>(R.id.radioAdmin)
        val radioDoctor = findViewById<RadioButton>(R.id.radioDoctor)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = androidx.biometric.BiometricPrompt(this,executor,object:androidx.biometric.BiometricPrompt.AuthenticationCallback(){

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@BiometricActivity, "Error! $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                ///Toast.makeText(this@LoginActivity,result.toString(),Toast.LENGTH_SHORT).show()
                if (radioAdmin.isChecked) {
                    val intent = Intent(this@BiometricActivity, AdminActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@BiometricActivity, "Successful", Toast.LENGTH_SHORT).show()
                } else if (radioPatient.isChecked) {
                    //val intent = Intent(this@BiometricActivity, PatientActivity::class.java)
                    //startActivity(intent)
                    Toast.makeText(this@BiometricActivity, "Not authorized", Toast.LENGTH_SHORT).show()
                } else if (radioDoctor.isChecked) {
                    //val intent = Intent(this@BiometricActivity, DoctorActivity::class.java)
                    //startActivity(intent)
                    Toast.makeText(this@BiometricActivity, "Not authorized", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(
                        this@BiometricActivity,
                        "Please choose your role",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@BiometricActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()

        //if(radioAdmin.isChecked || radioPatient.isChecked || radioDoctor.isChecked) {
        buttonLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)

        }
    }
}