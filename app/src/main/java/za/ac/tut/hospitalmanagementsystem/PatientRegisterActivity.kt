package za.ac.tut.hospitalmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class PatientRegisterActivity : AppCompatActivity() {

    private val gender = arrayOf("Male","Female","Other")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)

        val firstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val lastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName)
        val idNo = findViewById<TextInputEditText>(R.id.textInputEditTextId)
        val phone = findViewById<TextInputEditText>(R.id.textInputEditTextPhone)
        val address = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)
        val email = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        val password = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)

        val actv = findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this,R.layout.drop_down,gender)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            val gen = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, "You Appointed $gen", Toast.LENGTH_SHORT).show()
        }
    }
}
