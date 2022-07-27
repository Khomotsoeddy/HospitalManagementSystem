package za.ac.tut.hospitalmanagementsystem.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.RecycleAdapter
import za.ac.tut.hospitalmanagementsystem.doctor.Doctor


class AdminDoctorsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var docData = ArrayList<Doctor>()
    private var images :ArrayList<Int> = ArrayList()
    private lateinit var database : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_doctors_admin, container, false)


        val addDoctor = view.findViewById<TextView>(R.id.textViewAddDoctor)

        displayAllDoctors(view)

        addDoctor.setOnClickListener {
            val intent = Intent(this.requireContext(), AddDoctorActivity::class.java)
            startActivity(intent)
        }

        val buttonAll = view.findViewById<Button>(R.id.buttonAll)
        val buttonDentists = view.findViewById<Button>(R.id.buttonDentists)
        val buttonDermatologist = view.findViewById<Button>(R.id.buttonDermatologist)
        val buttonGynaecologist = view.findViewById<Button>(R.id.buttonGynaecologist)
        val buttonOptometrist = view.findViewById<Button>(R.id.buttonOptometrist)

        buttonAll.setOnClickListener {
            displayAllDoctors(view)
        }

        buttonOptometrist.setOnClickListener {
            displayOptometrist(view)
        }

        buttonDentists.setOnClickListener {
            displayDentists(view)
        }

        buttonDermatologist.setOnClickListener {
            displayDermatologists(view)
        }

        buttonGynaecologist.setOnClickListener {
            displayGynaecologists(view)
        }
        return view
    }

    private fun displayOptometrist(view: View) {

        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for(i in it.children) {
                val specialization = i.child("specialization").value.toString()

                if (specialization.contentEquals("Optometrist")) {

                    val doctorId = i.key.toString()
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val idNo = i.child("idNo").value.toString()
                    val role = i.child("role").value.toString()
                    val office = i.child("office").value.toString()
                    val specialization = i.child("specialization").value.toString()

                    val doc = Doctor(
                        doctorId,
                        firstName,
                        lastName,
                        idNo,
                        age,
                        gender,
                        phone,
                        email,
                        office,
                        address,
                        specialization,
                        role
                    )

                    docData.add(doc)
                }
            }

            if(docData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }


            recyclerView = view.findViewById(R.id.recyclerView)

            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.doctor)
            val myAdapter = RecycleAdapter(docData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : RecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AdminDoctorsFragment.requireContext(), AdminDoctorDetailsActivity::class.java)
                    intent.putExtra("doctorId",docData.get(position).doctorId)
                    intent.putExtra("firstName",docData.get(position).firstName)
                    intent.putExtra("lastName",docData.get(position).lastName)
                    intent.putExtra("idNo",docData.get(position).idNo)
                    intent.putExtra("age",docData.get(position).age)
                    intent.putExtra("gender",docData.get(position).gender)
                    intent.putExtra("phone",docData.get(position).phone)
                    intent.putExtra("email",docData.get(position).email)
                    intent.putExtra("office",docData.get(position).office)
                    intent.putExtra("address",docData.get(position).address)
                    intent.putExtra("specialization",docData.get(position).specialization)
                    startActivity(intent)
                }
            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayGynaecologists(view: View) {

        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for(i in it.children) {
                val specialization = i.child("specialization").value.toString()

                if (specialization.contentEquals("Gynaecologist")) {

                    val doctorId = i.key.toString()
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val idNo = i.child("idNo").value.toString()
                    val role = i.child("role").value.toString()
                    val office = i.child("office").value.toString()
                    val specialization = i.child("specialization").value.toString()

                    val doc = Doctor(
                        doctorId,
                        firstName,
                        lastName,
                        idNo,
                        age,
                        gender,
                        phone,
                        email,
                        office,
                        address,
                        specialization,
                        role
                    )

                    docData.add(doc)
                }
            }

            if(docData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }


            recyclerView = view.findViewById(R.id.recyclerView)

            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.doctor)
            val myAdapter = RecycleAdapter(docData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : RecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AdminDoctorsFragment.requireContext(), AdminDoctorDetailsActivity::class.java)
                    intent.putExtra("doctorId",docData.get(position).doctorId)
                    intent.putExtra("firstName",docData.get(position).firstName)
                    intent.putExtra("lastName",docData.get(position).lastName)
                    intent.putExtra("idNo",docData.get(position).idNo)
                    intent.putExtra("age",docData.get(position).age)
                    intent.putExtra("gender",docData.get(position).gender)
                    intent.putExtra("phone",docData.get(position).phone)
                    intent.putExtra("email",docData.get(position).email)
                    intent.putExtra("office",docData.get(position).office)
                    intent.putExtra("address",docData.get(position).address)
                    intent.putExtra("specialization",docData.get(position).specialization)
                    startActivity(intent)
                }
            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayDermatologists(view: View) {

        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for(i in it.children) {
                val specialization = i.child("specialization").value.toString()

                if (specialization.contentEquals("Dermatologist")) {

                    val doctorId = i.key.toString()
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val idNo = i.child("idNo").value.toString()
                    val role = i.child("role").value.toString()
                    val office = i.child("office").value.toString()
                    val specialization = i.child("specialization").value.toString()

                    val doc = Doctor(
                        doctorId,
                        firstName,
                        lastName,
                        idNo,
                        age,
                        gender,
                        phone,
                        email,
                        office,
                        address,
                        specialization,
                        role
                    )

                    docData.add(doc)
                }
            }

            if(docData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }


            recyclerView = view.findViewById(R.id.recyclerView)

            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.doctor)
            val myAdapter = RecycleAdapter(docData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : RecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AdminDoctorsFragment.requireContext(), AdminDoctorDetailsActivity::class.java)
                    intent.putExtra("doctorId",docData.get(position).doctorId)
                    intent.putExtra("firstName",docData.get(position).firstName)
                    intent.putExtra("lastName",docData.get(position).lastName)
                    intent.putExtra("idNo",docData.get(position).idNo)
                    intent.putExtra("age",docData.get(position).age)
                    intent.putExtra("gender",docData.get(position).gender)
                    intent.putExtra("phone",docData.get(position).phone)
                    intent.putExtra("email",docData.get(position).email)
                    intent.putExtra("office",docData.get(position).office)
                    intent.putExtra("address",docData.get(position).address)
                    intent.putExtra("specialization",docData.get(position).specialization)
                    startActivity(intent)
                }
            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayDentists(view: View) {

        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for(i in it.children) {
                val specialization = i.child("specialization").value.toString()

                if (specialization.contentEquals("Dentist")) {

                    val doctorId = i.key.toString()
                    val firstName = i.child("firstName").value.toString()
                    val lastName = i.child("lastName").value.toString()
                    val age = i.child("age").value.toString()
                    val gender = i.child("gender").value.toString()
                    val email = i.child("email").value.toString()
                    val phone = i.child("phone").value.toString()
                    val address = i.child("address").value.toString()
                    val idNo = i.child("idNo").value.toString()
                    val role = i.child("role").value.toString()
                    val office = i.child("office").value.toString()
                    val specialization = i.child("specialization").value.toString()

                    val doc = Doctor(
                        doctorId,
                        firstName,
                        lastName,
                        idNo,
                        age,
                        gender,
                        phone,
                        email,
                        office,
                        address,
                        specialization,
                        role
                    )

                    docData.add(doc)
                }
            }

            if(docData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }


            recyclerView = view.findViewById(R.id.recyclerView)

            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.doctor)
            val myAdapter = RecycleAdapter(docData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : RecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AdminDoctorsFragment.requireContext(), AdminDoctorDetailsActivity::class.java)
                    intent.putExtra("doctorId",docData.get(position).doctorId)
                    intent.putExtra("firstName",docData.get(position).firstName)
                    intent.putExtra("lastName",docData.get(position).lastName)
                    intent.putExtra("idNo",docData.get(position).idNo)
                    intent.putExtra("age",docData.get(position).age)
                    intent.putExtra("gender",docData.get(position).gender)
                    intent.putExtra("phone",docData.get(position).phone)
                    intent.putExtra("email",docData.get(position).email)
                    intent.putExtra("office",docData.get(position).office)
                    intent.putExtra("address",docData.get(position).address)
                    intent.putExtra("specialization",docData.get(position).specialization)
                    startActivity(intent)
                }
            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayAllDoctors(view: View){

        docData.clear()
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.get().addOnSuccessListener {
            for(i in it.children){
                val doctorId = i.key.toString()
                val firstName = i.child("firstName").value.toString()
                val lastName = i.child("lastName").value.toString()
                val age = i.child("age").value.toString()
                val gender = i.child("gender").value.toString()
                val email = i.child("email").value.toString()
                val phone = i.child("phone").value.toString()
                val address = i.child("address").value.toString()
                val idNo = i.child("idNo").value.toString()
                val role = i.child("role").value.toString()
                val office = i.child("office").value.toString()
                val specialization = i.child("specialization").value.toString()

                val doc = Doctor(doctorId,firstName,lastName,idNo,age,gender,phone,email, office,address,specialization,role)

                docData.add(doc)
            }

            if(docData.size == 0){
                Toast.makeText(this.requireContext(),"No available appointments", Toast.LENGTH_LONG).show()
            }


            recyclerView = view.findViewById(R.id.recyclerView)

            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)

            images.add(R.drawable.doctor)
            val myAdapter = RecycleAdapter(docData,images)
            recyclerView.adapter = myAdapter

            myAdapter.setOnItemClickListener(object : RecycleAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@AdminDoctorsFragment.requireContext(), AdminDoctorDetailsActivity::class.java)
                    intent.putExtra("doctorId",docData.get(position).doctorId)
                    intent.putExtra("firstName",docData.get(position).firstName)
                    intent.putExtra("lastName",docData.get(position).lastName)
                    intent.putExtra("idNo",docData.get(position).idNo)
                    intent.putExtra("age",docData.get(position).age)
                    intent.putExtra("gender",docData.get(position).gender)
                    intent.putExtra("phone",docData.get(position).phone)
                    intent.putExtra("email",docData.get(position).email)
                    intent.putExtra("office",docData.get(position).office)
                    intent.putExtra("address",docData.get(position).address)
                    intent.putExtra("specialization",docData.get(position).specialization)
                    startActivity(intent)
                }
            })
        }.addOnFailureListener {
            Toast.makeText(this.context,"failed", Toast.LENGTH_LONG).show()
        }
    }
}