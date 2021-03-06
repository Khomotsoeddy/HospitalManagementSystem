package za.ac.tut.hospitalmanagementsystem.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.fragments.AppointmentsFragment
import za.ac.tut.hospitalmanagementsystem.fragments.DoctorFragment
import za.ac.tut.hospitalmanagementsystem.fragments.ExtraFragment
import za.ac.tut.hospitalmanagementsystem.fragments.SetAppointmentFragment

class PatientActivity : AppCompatActivity() {
    private val appointmentsFragment = AppointmentsFragment()
    private val doctorFragment = DoctorFragment()
    private val setAppointmentFragment = SetAppointmentFragment()
    private val extraFragment = ExtraFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        val patientId = intent.getStringExtra("patientId")
        MobileAds.initialize(this.applicationContext) {}
        replaceFragment(doctorFragment,patientId)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_doctor -> replaceFragment(doctorFragment, patientId)
                R.id.ic_appointments -> replaceFragment(appointmentsFragment, patientId)
                R.id.ic_setAppointment -> replaceFragment(setAppointmentFragment, patientId)
                R.id.ic_extra -> replaceFragment(extraFragment, patientId)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment, patientId: String?){
        val bundle = Bundle()
        bundle.putString("patientId",patientId)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}