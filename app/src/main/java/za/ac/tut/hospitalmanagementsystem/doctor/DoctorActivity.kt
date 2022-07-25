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
        val doctorId = intent.getStringExtra("doctorId")

        replaceFragmentAdmin(doctorAppointmentFragment,doctorId)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationAdmin)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_appointments -> replaceFragmentAdmin(doctorAppointmentFragment, doctorId)
                R.id.ic_records -> replaceFragmentAdmin(doctorAddRecordFragment, doctorId)
                R.id.ic_extra -> replaceFragmentAdmin(doctorProfileFragment, doctorId)
                //R.id.ic_admin_additional -> replaceFragmentAdmin(adminExtraFragment)
            }
            true
        }
    }

    private fun replaceFragmentAdmin(fragment: Fragment, doctorId: String?) {
        val bundle = Bundle()
        bundle.putString("doctorId",doctorId)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}