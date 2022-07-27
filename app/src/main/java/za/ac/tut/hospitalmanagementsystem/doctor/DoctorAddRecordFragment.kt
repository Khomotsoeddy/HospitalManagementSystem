package za.ac.tut.hospitalmanagementsystem.doctor

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.medicalreport.MedicalReports
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class DoctorAddRecordFragment : Fragment() {

    private var STORAGE_CODE = 1001
    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_doctor_add_record, container, false)

        val data = arguments
        val doctorId = data?.get("doctorId").toString()

        println(doctorId)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val buttonMedical = view.findViewById<Button>(R.id.buttonMedical)

        buttonMedical.setOnClickListener {
            saveMedicalReports()
            //saveReportToFiles()
        }

        buttonSubmit.setOnClickListener {
            saveReport(view)
        }


        return view
    }
    private fun saveReportToFiles() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(checkSelfPermission(this.requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,STORAGE_CODE)
            }else{
                saveMedicalReports()
            }
        }else{
            saveMedicalReports()
        }
    }

    private fun saveMedicalReports() {
        val mDoc  = Document()
        val wordDoc = XWPFDocument()
        val excelDoc = HSSFWorkbook()
        val mFilename = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val mFilePath = "Record-$mFilename.pdf"
        val wordFilePath = "Record-$mFilename.docx"
        val excelFilePath = "Record-$mFilename.xls"
        val txtFilePath = "Record-$mFilename.txt"
        var data: String
        try{
            //word
            val para = wordDoc.createParagraph()
            val paraRun = para.createRun()
            var wordCount = 1
            //pdf
            PdfWriter.getInstance(mDoc, FileOutputStream(File(this.requireContext().applicationContext.getExternalFilesDir("data"),mFilePath)))
            mDoc.open()
            //excel
            var excelNumber = 1
            val excelSheet = excelDoc.createSheet("Doctor Report")
            val excelRow = excelSheet.createRow(0)
            val excelCell0 = excelRow.createCell(0)
            excelCell0.setCellValue("Record Number")

            val excelCell1 = excelRow.createCell(1)
            excelCell1.setCellValue("Patient ID")

            val excelCell2 = excelRow.createCell(2)
            excelCell2.setCellValue("Medication")

            val excelCell3 = excelRow.createCell(3)
            excelCell3.setCellValue("allergy")

            val excelCell4 = excelRow.createCell(4)
            excelCell4.setCellValue("date")

            val excelCell5 = excelRow.createCell(5)
            excelCell5.setCellValue("temperature")

            val excelCell6 = excelRow.createCell(6)
            excelCell6.setCellValue("weight")

            paraRun.setText("Patients' Records ",0)
            val sb = StringBuilder()


            database = FirebaseDatabase.getInstance().getReference("MedicalReports")
            database.get().addOnSuccessListener {
                val sb = StringBuilder()
                for(i in it.children){

                    val id = i.key.toString()
                    val patientId = i.child("patientId").value.toString()
                    val medication = i.child("medication").value.toString()
                    val allergy = i.child("allergy").value.toString()
                    val weight = i.child("weight").value.toString()
                    val temperature = i.child("temperature").value.toString()
                    val date = i.child("date").value.toString()

                    sb.append("Record No: $id\nPatient ID: $patientId\nmedication: $medication\nAllergy: $allergy\nTemperature: $temperature\nWeight: $weight\nDate: $date\n_____________________________\n")

                    paraRun.fontSize = 10
                    paraRun.setText("Record No: $id",wordCount++)
                    paraRun.setText("Patient ID: $patientId",wordCount++)
                    paraRun.setText("Temperature: $temperature",wordCount++)
                    paraRun.setText("Allergy: $allergy",wordCount++)
                    paraRun.setText("medication: $medication",wordCount++)
                    paraRun.setText("Weight: $weight",wordCount++)
                    paraRun.setText("Date: $date",wordCount++)

                    paraRun.setText("\n_____________________________",wordCount++)

                    val excelRow = excelSheet.createRow(excelNumber++)
                    val excelCell0 = excelRow.createCell(0)
                    excelCell0.setCellValue(id)

                    val excelCell1 = excelRow.createCell(1)
                    excelCell1.setCellValue(patientId)

                    val excelCell2 = excelRow.createCell(2)
                    excelCell2.setCellValue(medication)

                    val excelCell3 = excelRow.createCell(3)
                    excelCell3.setCellValue(allergy)

                    val excelCell4 = excelRow.createCell(4)
                    excelCell4.setCellValue(date)

                    val excelCell5 = excelRow.createCell(5)
                    excelCell5.setCellValue(temperature)

                    val excelCell6 = excelRow.createCell(6)
                    excelCell6.setCellValue(weight)
                }
                data = sb.toString()

                //create pdf
                mDoc.add(Paragraph("Patients' Records \n\n$data"))
                mDoc.close()

                //create word


                wordDoc.write(FileOutputStream(File(this.requireContext().applicationContext.getExternalFilesDir("data"),wordFilePath)))
                wordDoc.close()

                //create txt file
                val writeIntoFile = FileOutputStream(File(this.requireContext().applicationContext.getExternalFilesDir("data"),txtFilePath))
                writeIntoFile.write("Patients' Records \n\n$data".toByteArray())

                //create excel
                excelDoc.write(FileOutputStream(File(this.requireContext().applicationContext.getExternalFilesDir("data"),excelFilePath)))
                excelDoc.close()
                Toast.makeText(this.requireContext(),"${this.requireContext().applicationContext.getExternalFilesDir("data")} + $mFilename.pdf is successfully save",
                    Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this.requireContext(),"failed", Toast.LENGTH_LONG).show()
            }
        }catch (ex : Exception){
            Toast.makeText(this.requireContext(),ex.toString(), Toast.LENGTH_SHORT).show()
        }
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