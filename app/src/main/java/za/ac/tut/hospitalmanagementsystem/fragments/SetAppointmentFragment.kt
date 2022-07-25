package za.ac.tut.hospitalmanagementsystem.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import za.ac.tut.hospitalmanagementsystem.R
import java.util.*
import android.widget.ArrayAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import za.ac.tut.hospitalmanagementsystem.appointment.Appointments
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class SetAppointmentFragment : Fragment() {
    private lateinit var database : DatabaseReference
    private val specialization = arrayOf("Dentist","Dermatologist","Gynaecologist","Optometrist")
    private lateinit var spec: String
    private lateinit var pickedDate : String
    private lateinit var textViewEmail : TextView
    private lateinit var textViewPhone : TextView
    private lateinit var date: TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setappointment, container, false)

        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewPhone = view.findViewById(R.id.textViewPhone)
        val pickDate  = view.findViewById<Button>(R.id.buttonPickDate)

        val actv = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.drop_down, specialization)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            spec = adapterView.getItemAtPosition(i).toString()
        }

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateMyCalender(myCalender,view)

        }

        //button
        pickDate.setOnClickListener {
            DatePickerDialog(this.requireContext(),datePicker,myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),myCalender.get(
                Calendar.DAY_OF_MONTH)).show()

        }
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            submitAppointment(view)
        }

        adminDetails()
        return view
    }

    private fun adminDetails() {
        var email = ""
        var phone = ""
        database = FirebaseDatabase.getInstance().getReference("Admins")
        database.get().addOnSuccessListener {
            for (i in it.children) {
                
                email = i.child("email").value.toString()
                phone = i.child("phone").value.toString()
            }
            textViewPhone.text = phone
            textViewEmail.text = email
        }
    }

    private fun updateMyCalender(myCalender: Calendar,view: View) {
        date = view.findViewById(R.id.textInputEditTextDate)
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        date.setText(dFormat.format(myCalender.time))
        pickedDate = dFormat.format(myCalender.time).toString()
    }

    private fun submitAppointment(view: View) {
        var record = true

        val descriptionRecord: String
        val nowDate = Date()
        val tomorrow = LocalDate.now().plusDays(30)
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(nowDate).toString()

        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val tomorrowDate = tomorrow.format(formatter)


        val current: LocalDate = LocalDate.parse(currentDate, formatter)
        val maxRange: LocalDate = LocalDate.parse(tomorrowDate, formatter)
        val chosenDate: LocalDate = LocalDate.parse(pickedDate, formatter)

        if(date.text.toString().isEmpty()){
            record = false
            val dateContainer = view.findViewById<TextInputLayout>(R.id.dateContainer)
            dateContainer.helperText = "Pick date"
        }else{

            if(current.isAfter(chosenDate)){
                record = false
                val dateContainer = view.findViewById<TextInputLayout>(R.id.dateContainer)
                dateContainer.helperText = "Invalid date"
            }

            if(current.isEqual(chosenDate)){
                record = false
                val dateContainer = view.findViewById<TextInputLayout>(R.id.dateContainer)
                dateContainer.helperText = "Invalid date"
            }

            if(maxRange.isBefore(chosenDate)){
                record = false
                val dateContainer = view.findViewById<TextInputLayout>(R.id.dateContainer)
                dateContainer.helperText = "Date is out of range(30 days +)"
            }
        }

        val description = requireView().findViewById<TextInputEditText>(R.id.textInputEditTextDescription)

        if(description.text.toString().isEmpty()){
            record = false
            descriptionRecord = "No description"
        }else{
            descriptionRecord = description.text.toString()
        }
        val id =  "1234567654321"
        val randomValues = Random.nextInt(100000)

        if (record){
            val database  = Firebase.database
            val myref = database.getReference("Appointment").child(randomValues.toString())

            myref.setValue(Appointments(
                id,
                "N/A",
                spec,
                descriptionRecord,
                "N/A",
                currentDate,
                pickedDate,
                "Pending"
            ))

            Toast.makeText(this.context,"Details sent", Toast.LENGTH_LONG).show()
        }
    }
}
