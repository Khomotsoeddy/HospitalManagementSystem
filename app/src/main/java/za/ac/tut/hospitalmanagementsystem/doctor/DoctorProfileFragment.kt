package za.ac.tut.hospitalmanagementsystem.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import za.ac.tut.hospitalmanagementsystem.R

class DoctorProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = return inflater.inflate(R.layout.fragment_doctor_profile, container, false)

        val data = arguments
        val doctorId = data?.get("doctorId").toString()

        println(doctorId)


        return view
    }
}