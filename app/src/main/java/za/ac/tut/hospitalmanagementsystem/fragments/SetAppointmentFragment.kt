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
import android.widget.Toast.makeText

class SetAppointmentFragment : Fragment() {

    private val specialization = arrayOf("Dentist","Dermatologist","Gynaecologist","Optometrist")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setappointment, container, false)

        val pickDate  = view.findViewById<Button>(R.id.buttonPickDate)

        val actv = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.drop_down, specialization)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            val gen = adapterView.getItemAtPosition(i).toString()
            makeText(this.context, "You Appointed $gen", Toast.LENGTH_SHORT).show()
        }

        //button
        pickDate.setOnClickListener {
            dialog(view)
        }
        return view
    }

    private fun dialog(view: View) {
        val date = view.findViewById<TextView>(R.id.textViewDate)

        //calendar
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this.requireContext(),DatePickerDialog.OnDateSetListener { view , mYear, mMonth, mDay ->
            date.text = "$mDay-$mMonth-$mYear"
        },year,month,day)
        dpd.datePicker.minDate = System.currentTimeMillis() + 1000
        //dpd.datePicker.maxDate = System.currentTimeMillis() - 1000//minDate = System.currentTimeMillis() - 100
        dpd.show()
    }

}