package za.ac.tut.hospitalmanagementsystem.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import za.ac.tut.hospitalmanagementsystem.R

class AdminActivity : AppCompatActivity() {
    private val AdminAppointmentsFragment = AdminAppointmentsFragment()
    private val AdminDoctorsFragment = AdminDoctorsFragment()
    private val AdminPatientsFragment =AdminPatientsFragment()
    private val AdminExtraFragment = AdminExtraFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        MobileAds.initialize(this.applicationContext) {}
        replaceFragmentAdmin(AdminDoctorsFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationAdmin)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_admin_appointments -> replaceFragmentAdmin(AdminAppointmentsFragment)
                R.id.ic_admin_doctor -> replaceFragmentAdmin(AdminDoctorsFragment)
                R.id.ic_admin_patient -> replaceFragmentAdmin(AdminPatientsFragment)
                //R.id.ic_admin_additional -> replaceFragmentAdmin(AdminExtraFragment)
            }
            true
        }
    }

    private fun replaceFragmentAdmin(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}