package za.ac.tut.hospitalmanagementsystem.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.medicalreport.MedicalReports
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class DoctorAddRecordFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_doctor_add_record, container, false)

        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            saveReport(view)
        }

        return view
    }

    private fun saveReport(view: View) {
        var record = true
        val date = Date()
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = dFormat.format(date).toString()

        val textInputEditTextId = view.findViewById<TextInputEditText>(R.id.textInputEditTextId)
        val textInputEditTextAllergy = view.findViewById<TextInputEditText>(R.id.textInputEditTextAllergy)
        val textInputEditWeight = view.findViewById<TextInputEditText>(R.id.textInputEditWeight)
        val textInputEditTextTemperature = view.findViewById<TextInputEditText>(R.id.textInputEditTextTemperature)
        val textInputEditTextMedication = view.findViewById<TextInputEditText>(R.id.textInputEditTextMedication)

        val randomValues = Random.nextInt(10000000)

        if(textInputEditTextId.text.toString().isEmpty() || textInputEditTextId.text.toString().length!=13){
            record = false
            val dateContainer = view.findViewById<TextInputLayout>(R.id.idContainer)
            dateContainer.helperText = "Invalid ID"
        }

        if(textInputEditTextAllergy.text.toString().isEmpty()){
            record = false
            val dateContainer = view.findViewById<TextInputLayout>(R.id.allergyContainer)
            dateContainer.helperText = "This field is needed"
        }

        if(textInputEditWeight.text.toString().isEmpty()){
            record = false
            val dateContainer = view.findViewById<TextInputLayout>(R.id.weightContainer)
            dateContainer.helperText = "This field is needed"
        }
        if(textInputEditTextTemperature.text.toString().isEmpty()){
            record = false
            val dateContainer = view.findViewById<TextInputLayout>(R.id.temperatureContainer)
            dateContainer.helperText = "This field is needed"
        }

        if(textInputEditTextMedication.text.toString().isEmpty()){
            record = false
            val dateContainer = view.findViewById<TextInputLayout>(R.id.medicationContainer)
            dateContainer.helperText = "This field is needed"
        }


        if (record){
            val database  = Firebase.database
            val myref = database.getReference("MedicalReports").child(randomValues.toString())

            myref.setValue(MedicalReports(
                textInputEditTextId.text.toString(),
                textInputEditTextMedication.text.toString(),
                textInputEditTextAllergy.text.toString(), textInputEditWeight.text.toString(),
                textInputEditTextTemperature.text.toString(), currentDate))

            Toast.makeText(this.context,"Details saved", Toast.LENGTH_LONG).show()

            textInputEditTextMedication.text?.clear()
            textInputEditTextTemperature.text?.clear()
            textInputEditWeight.text?.clear()
            textInputEditTextAllergy.text?.clear()
            textInputEditTextId.text?.clear()
        }
    }
}