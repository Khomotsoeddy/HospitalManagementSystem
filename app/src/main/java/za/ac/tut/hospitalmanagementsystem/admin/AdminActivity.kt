package za.ac.tut.hospitalmanagementsystem.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import za.ac.tut.hospitalmanagementsystem.R

class AdminActivity : AppCompatActivity() {
    private val AppointmentFragment = AppointmentFragment()
    private val DoctorsFragment = DoctorsFragment()
    private val PatientsFragment =PatientsFragment()
    private val ExtraFragment = ExtraFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        MobileAds.initialize(this) {}
        replaceFragment(AppointmentFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_doctor -> replaceFragment(AppointmentFragment)
                R.id.ic_appointments -> replaceFragment(DoctorsFragment)
                R.id.ic_patient -> replaceFragment(PatientsFragment)
                R.id.ic_additional -> replaceFragment(ExtraFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}