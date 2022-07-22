package za.ac.tut.hospitalmanagementsystem.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.fragments.ExtraFragment

class AdminActivity : AppCompatActivity() {
    private val adminAppointmentsFragment = AdminAppointmentsFragment()
    private val adminDoctorsFragment = AdminDoctorsFragment()
    private val adminPatientsFragment =AdminPatientsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        MobileAds.initialize(this.applicationContext) {}
        replaceFragmentAdmin(adminDoctorsFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationAdmin)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_admin_appointments -> replaceFragmentAdmin(adminAppointmentsFragment)
                R.id.ic_admin_doctor -> replaceFragmentAdmin(adminDoctorsFragment)
                R.id.ic_admin_patient -> replaceFragmentAdmin(adminPatientsFragment)
                //R.id.ic_admin_additional -> replaceFragmentAdmin(adminExtraFragment)
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