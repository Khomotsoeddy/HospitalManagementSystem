package za.ac.tut.hospitalmanagementsystem

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import za.ac.tut.hospitalmanagementsystem.patient.PatientRegisterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_twitter ->
                    goToTwitterPage()
                R.id.ic_facebook ->
                    goToFacebookPage()

            }
            true
        }
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        val textViewWebsite = findViewById<TextView>(R.id.textViewWebsite)
        val textViewLocation = findViewById<TextView>(R.id.textViewLocation)

        textViewWebsite.setOnClickListener {
            goToWebsite()
        }

        textViewLocation.setOnClickListener {
            goToMap()
        }
        buttonLogin.setOnClickListener {
            goToLoginPage()
        }
        buttonRegister.setOnClickListener {
            goToRegisterPage()
        }

        textViewRegister.setOnClickListener {
            goToRegisterPage()
        }
    }

    private fun goToWebsite() {
        val url = "https://www.sbah.org.za"
        val openURL = Intent(Intent.ACTION_VIEW)

        openURL.data = Uri.parse(url)

        startActivity(openURL)
    }



    private fun goToMap() {
        //val url = "https://www.google.com/maps/dir/-26.0039276,28.2397105/steve+biko+hospital/@-26.0039276,28.2397105,14z/data=!4m9!4m8!1m1!4e1!1m5!1m1!1s0x1e95621d2d47a46f:0x5a8428c06fb5d761!2m2!1d28.2024381!2d-25.7294765"

        val url = "https://www.google.com/maps/dir/-26.0039276,28.2397105/steve+biko+hospital"
        val openURL = Intent(Intent.ACTION_VIEW)

        openURL.data = Uri.parse(url)

        startActivity(openURL)
    }

    private fun goToRegisterPage() {
        val intent = Intent(this, PatientRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToFacebookPage() {
        val url = "https://www.facebook.com/HealthZA"

        val openURL = Intent(Intent.ACTION_VIEW)

        openURL.data = Uri.parse(url)

        startActivity(openURL)
    }

    private fun goToTwitterPage() {
        val url = "https://twitter.com/HealthZA?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"

        val openURL = Intent(Intent.ACTION_VIEW)

        openURL.data = Uri.parse(url)

        startActivity(openURL)
    }

    private fun goToLoginPage() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}