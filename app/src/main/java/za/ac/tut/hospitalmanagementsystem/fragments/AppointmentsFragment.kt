package za.ac.tut.hospitalmanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.RecycleAdapter

class AppointmentsFragment : Fragment() {

    private val order = arrayOf("All","Past","Upcoming","Today")
    private lateinit var recyclerView: RecyclerView
    private var arrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_appointments, container, false)

        val actv = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_text)
        val arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.drop_down, order)

        actv.setAdapter(arrayAdapter)
        actv.setOnItemClickListener { adapterView, _, i, _ ->
            val gen = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this.context, "You Appointed $gen", Toast.LENGTH_SHORT).show()
        }

        arrayList.add("Ajay")//Adding object in arraylist
        arrayList.add("Vijay")
        arrayList.add("Prakash")
        arrayList.add("Rohan")
        arrayList.add("Vijay")

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager  = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RecycleAdapter(arrayList)
        return view
    }
}