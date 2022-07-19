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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import za.ac.tut.hospitalmanagementsystem.appointment.Appointments
import java.text.SimpleDateFormat
import kotlin.random.Random

class SetAppointmentFragment : Fragment() {

    private val specialization = arrayOf("Dentist","Dermatologist","Gynaecologist","Optometrist")
    private lateinit var spec: String
    private lateinit var pickedDate : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setappointment, container, false)

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
            submitAppointment()
        }

        return view
    }

    private fun updateMyCalender(myCalender: Calendar,view: View) {
        val date = view.findViewById<TextInputEditText>(R.id.textInputEditTextDate)
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        date.setText(dFormat.format(myCalender.time))
        pickedDate = dFormat.format(myCalender.time).toString()
    }

    private fun submitAppointment() {

        val description = requireView().findViewById<TextInputEditText>(R.id.textInputEditTextDescription)
        val id =  "1234567654321"
        val randomValues = Random.nextInt(100000)

        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()


        val database  = Firebase.database
        val myref = database.getReference("Appointment").child(randomValues.toString())

        myref.setValue(Appointments(
            id,
            "N/A",
            spec,
            description.text.toString(),
            "N/A",
            currentDate,
            pickedDate,
            "Pending"
        ))

        Toast.makeText(this.context,"Details sent", Toast.LENGTH_LONG).show()

    }
}
