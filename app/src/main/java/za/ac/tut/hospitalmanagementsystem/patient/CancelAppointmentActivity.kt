package za.ac.tut.hospitalmanagementsystem.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R

class CancelAppointmentActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_appointment)

        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        val appointmentId = intent.getStringExtra("appointmentId").toString()
        val specialization = intent.getStringExtra("specialization").toString()
        val description = intent.getStringExtra("description").toString()
        val date = intent.getStringExtra("date").toString()

        val textViewSpecialization = findViewById<TextView>(R.id.textViewSpecialization)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewAppointmentId = findViewById<TextView>(R.id.textViewAppointmentId)

        textViewSpecialization.text = specialization
        textViewDate.text  =date
        textViewDescription.text = description
        textViewAppointmentId.text = appointmentId
        buttonCancel.setOnClickListener {
            cancelAppointment(appointmentId)
        }
    }

    private fun cancelAppointment(appointmentId: String?) {
        database = FirebaseDatabase.getInstance().getReference("Appointment").child(appointmentId.toString())
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Appointment cancelled", Toast.LENGTH_LONG).show()
        }
        val intent = Intent(this, PatientActivity::class.java)
        startActivity(intent)
    }
}