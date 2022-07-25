package za.ac.tut.hospitalmanagementsystem.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.MedicalReportAdapter
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.medicalreport.MedicalReport

class PatientRecordActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private val reports : ArrayList<MedicalReport> = ArrayList()
    private lateinit var idNo:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_record)

        idNo = intent.getStringExtra("idNo").toString()

        loadReports()
    }

    private fun loadReports() {
        reports.clear()
        database = FirebaseDatabase.getInstance().getReference("MedicalReports")
        database.get().addOnSuccessListener {
            for (i in it.children) {
                val recordNo = i.key.toString()
                val patientId = i.child("patientId").value.toString()
                val medication = i.child("medication").value.toString()
                val allergy = i.child("allergy").value.toString()
                val weight = i.child("weight").value.toString()
                val temperature = i.child("temperature").value.toString()
                val date = i.child("date").value.toString()

                val report = MedicalReport(
                    recordNo,
                    patientId,
                    medication,
                    allergy,
                    weight,
                    temperature,
                    date
                )
                reports.add(report)
            }
            if (reports.size == 0) {
                Toast.makeText(this, "No available appointments", Toast.LENGTH_LONG).show()
            }

            recyclerView = findViewById(R.id.recyclerViewView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)

            recyclerView.adapter = MedicalReportAdapter(reports)
        }.addOnFailureListener {
            Toast.makeText(this,"failed", Toast.LENGTH_LONG).show()
        }
    }
}