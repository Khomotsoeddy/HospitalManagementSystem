package za.ac.tut.hospitalmanagementsystem.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import za.ac.tut.hospitalmanagementsystem.R

class DoctorActivity : AppCompatActivity() {

    private val doctorAppointmentFragment = DoctorAppointmentFragment()
    private val doctorAddRecordFragment = DoctorAddRecordFragment()
    private val doctorProfileFragment = DoctorProfileFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        MobileAds.initialize(this.applicationContext) {}
        replaceFragmentAdmin(doctorAppointmentFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationAdmin)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_admin_appointments -> replaceFragmentAdmin(doctorAppointmentFragment)
                R.id.ic_admin_doctor -> replaceFragmentAdmin(doctorAddRecordFragment)
                R.id.ic_admin_patient -> replaceFragmentAdmin(doctorProfileFragment)
                //R.id.ic_admin_additional -> replaceFragmentAdmin(adminExtraFragment)
            }
            true
        }
    }

    private fun replaceFragmentAdmin(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}