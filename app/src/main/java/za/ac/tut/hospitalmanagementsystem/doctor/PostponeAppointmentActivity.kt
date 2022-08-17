package za.ac.tut.hospitalmanagementsystem.doctor

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PostponeAppointmentActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var pickedTime: String
    private var min = 0
    private var hour = 0
    private var amOrPm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postpone_appointment)

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

        var date = findViewById<TextInputEditText>(R.id.textInputEditTextDate)
        val pickDate  = findViewById<Button>(R.id.buttonPickDate)
        val buttonSubmit  = findViewById<Button>(R.id.buttonSubmit)

        val appointmentId = intent.getStringExtra("appointmentId")
        //val specializationApp = intent.getStringExtra("specialization")
        val description = intent.getStringExtra("description")
        val appDate = intent.getStringExtra("date")
        val doctorId = intent.getStringExtra("doctorId")

        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewAppointmentId = findViewById<TextView>(R.id.textViewAppointmentId)

        textViewDescription.text = description
        textViewDate.text = appDate
        textViewAppointmentId.text = appointmentId

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateMyCalender(myCalender,date)
        }

        pickDate.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),myCalender.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        buttonSubmit.setOnClickListener {
            updateAppointment(appointmentId,date,doctorId)
        }
    }

    private fun updateMyCalender(myCalender: Calendar, date: TextInputEditText) {
        //date =
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        date.setText(dFormat.format(myCalender.time))
        //pickedDate = dFormat.format(myCalender.time).toString()
    }

    private fun updateAppointment(
        appointmentId: String?,
        date: TextInputEditText,
        doctorId: String?
    ) {
        var record = true
        val pickedDate = date.text.toString()

        pickedTime = "$hour : $min : $amOrPm"

        val nowDate = Date()
        val tomorrow = LocalDate.now().plusDays(30)
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(nowDate).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val tomorrowDate = tomorrow.format(formatter)


        if(!pickedDate.isNullOrEmpty()) {

            val current: LocalDate = LocalDate.parse(currentDate, formatter)
            val maxRange: LocalDate = LocalDate.parse(tomorrowDate, formatter)
            val chosenDate: LocalDate = LocalDate.parse(pickedDate, formatter)


            if (date.text.toString().isEmpty()) {
                record = false
                val dateContainer = findViewById<TextInputLayout>(R.id.dateContainer)
                dateContainer.helperText = "Pick date"
            } else {

                if (current.isAfter(chosenDate)) {
                    //record = false
                    val dateContainer = findViewById<TextInputLayout>(R.id.dateContainer)
                    dateContainer.helperText = "Invalid date"
                }

                if (current.isEqual(chosenDate)) {
                    record = false
                    val dateContainer = findViewById<TextInputLayout>(R.id.dateContainer)
                    dateContainer.helperText = "Invalid date"
                }

                if (maxRange.isBefore(chosenDate)) {
                    record = false
                    val dateContainer = findViewById<TextInputLayout>(R.id.dateContainer)
                    dateContainer.helperText = "Date is out of range(30 days +)"
                }
            }
        }else{
            record = false
            Toast.makeText(this,"Please pick a date", Toast.LENGTH_LONG).show()
        }

        if(record){

            database = FirebaseDatabase.getInstance().getReference("Appointment")
            val updateData = mapOf(
                "time" to pickedTime,
                "date" to pickedDate,
            )
            database.child(appointmentId!!).updateChildren(updateData)
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DoctorActivity::class.java)
            intent.putExtra("doctorId", doctorId)
            startActivity(intent)
        }
    }
}