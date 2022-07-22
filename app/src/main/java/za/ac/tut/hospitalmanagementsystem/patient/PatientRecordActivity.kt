package za.ac.tut.hospitalmanagementsystem.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import za.ac.tut.hospitalmanagementsystem.R

class PatientRecordActivity : AppCompatActivity() {

    private lateinit var idNo:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_record)

        idNo = intent.getStringExtra("idNo").toString()
    }
}