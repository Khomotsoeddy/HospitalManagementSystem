package za.ac.tut.hospitalmanagementsystem.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.doctor.Doctor

class AdminAppointmentEditActivity : AppCompatActivity() {

    private lateinit var employee: String
    private var docData = ArrayList<Doctor>()
    private var doctors :ArrayList<String> = ArrayList()
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_appointment_edit)

        val appointmentId = intent.getStringExtra("appointmentId")
        val specializationApp = intent.getStringExtra("specialization")
        val description = intent.getStringExtra("description")
        val date = intent.getStringExtra("date")

        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewAppointmentId = findViewById<TextView>(R.id.textViewAppointmentId)

        textViewDescription.text = description
        textViewDate.text = date
        textViewAppointmentId.text = appointmentId

        getSpecificDoctors(specializationApp)

        doDropDown()

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            prin()
        }
    }

    private fun doDropDown() {
        val actv = findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down, doctors)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            employee = adapterView.getItemAtPosition(i).toString()
        }
    }

    private fun prin() {
        val splitter = employee.split("-")
        println("splited "+splitter[0].trim())
    }


    private fun getSpecificDoctors(specializationApp: String?) {
        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for (i in it.children) {

                val specialization = i.child("specialization").value.toString()

                if(specialization.contentEquals(specializationApp)){

                    val doctorId = i.key.toString()
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val idNo = i.child("idNo").value.toString()
                    val role = i.child("role").value.toString()
                    val office = i.child("office").value.toString()
                    val specialization = i.child("specialization").value.toString()

                    val doc = Doctor(
                        doctorId,
                        firstName,
                        lastName,
                        idNo,
                        age,
                        gender,
                        phone,
                        email,
                        office,
                        address,
                        specialization,
                        role
                    )

                    docData.add(doc)
                }
            }

            println(docData.size)

            for(i in docData){

                doctors.add(i.doctorId +" - Dr "+i.firstName+" "+i.lastName)
            }

            if (docData.size == 0) {
                Toast.makeText(
                    this,
                    "No available appointments",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}