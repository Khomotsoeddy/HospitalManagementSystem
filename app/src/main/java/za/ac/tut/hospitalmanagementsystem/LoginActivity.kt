package za.ac.tut.hospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import za.ac.tut.hospitalmanagementsystem.admin.AdminActivity
import za.ac.tut.hospitalmanagementsystem.doctor.DoctorActivity
import za.ac.tut.hospitalmanagementsystem.patient.PatientActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        val radioPatient = findViewById<RadioButton>(R.id.radioPatient)
        val radioAdmin = findViewById<RadioButton>(R.id.radioAdmin)
        val radioDoctor = findViewById<RadioButton>(R.id.radioDoctor)

        buttonLogin.setOnClickListener {

            if(radioAdmin.isChecked){
                goToAdminPage()
            }else if(radioPatient.isChecked){
                goToPatientPage()
            }else if(radioDoctor.isChecked){
                goToDoctorPage()
            }

        }
    }

    private fun goToDoctorPage() {
        val intent = Intent(this, DoctorActivity::class.java)
        startActivity(intent)
    }

    private fun goToPatientPage() {
        val intent = Intent(this, PatientActivity::class.java)
        startActivity(intent)
    }

    private fun goToAdminPage() {
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

}