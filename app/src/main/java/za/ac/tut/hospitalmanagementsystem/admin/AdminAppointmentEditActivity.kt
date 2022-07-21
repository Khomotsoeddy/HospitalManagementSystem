package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.doctor.Doctor

class AdminAppointmentEditActivity : AppCompatActivity() {

    private var employee = ""
    private var docData = ArrayList<Doctor>()
    private var doctors :ArrayList<String> = ArrayList()
    private lateinit var database : DatabaseReference
    private lateinit var pickedTime: String
    private var min = 0
    private var hour = 0
    private var amOrPm = ""
    private val adminAppointmentsFragment = AdminAppointmentsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_appointment_edit)

        val numberPickerHour = findViewById<NumberPicker>(R.id.numberPickerHour)
        val numberPickerMinutes = findViewById<NumberPicker>(R.id.numberPickerMinutes)
        val numberPickerAmPm = findViewById<NumberPicker>(R.id.numberPickerAmPm)

        numberPickerHour.maxValue = 12
        numberPickerHour.minValue = 1

        numberPickerMinutes.minValue = 0
        numberPickerMinutes.maxValue = 59

        val str = arrayOf("Am","Pm")
        numberPickerAmPm.minValue = 0
        numberPickerAmPm.maxValue = (str.size -1)
        numberPickerAmPm.displayedValues = str

        println("${str.size} + ${str[0]} + ${str[1]}")
        numberPickerHour.setOnValueChangedListener { numberPicker, _, _ ->
            hour = numberPicker.value
        }
        numberPickerMinutes.setOnValueChangedListener { numberPicker, _, _ ->
            min = numberPicker.value
        }
        numberPickerAmPm.setOnValueChangedListener { numberPicker, _, _ ->
            val i = numberPicker.value
            amOrPm = str[i]
        }

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
            updateAppointment(appointmentId)
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
        if(employee == null){
            Toast.makeText(
                this,
                "Please a doctor",
                Toast.LENGTH_LONG
            ).show()
        }else {
            val splitter = employee.split("-")
            println("splited " + splitter[0].trim())
            pickedTime = "$hour : $min : $amOrPm"
            println(pickedTime)
        }
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
                    "No available $specializationApp",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateAppointment(appointmentId: String?) {
        pickedTime = "$hour : $min : $amOrPm"
        val splitter = employee.split("-")

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        val updateData = mapOf(
            "time" to pickedTime,
            "doctor" to splitter[0].trim()
        )
        database.child(appointmentId!!).updateChildren(updateData)
        Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()

        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

}