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

        //button
        pickDate.setOnClickListener {
            dialog(view)
        }
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            submitAppointment()
        }

        return view
    }

    private fun submitAppointment() {

        val description = requireView().findViewById<TextInputEditText>(R.id.textInputEditTextDescription)
        val id =  "1234567654321"
        val randomValues = Random.nextInt(1000)

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

        Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()

    }

    private fun dialog(view: View) {
        val date = view.findViewById<TextInputEditText>(R.id.textInputEditTextDate)

        //calendar
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this.requireContext(), { _, mYear, mMonth, mDay ->
            date.setText("$mDay-$mMonth-$mYear")
            pickedDate = date.text.toString()
        },year,month,day)

        dpd.show()
    }

}
