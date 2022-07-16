package za.ac.tut.hospitalmanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.RecycleAdapter

class DoctorFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var arrayList = ArrayList<String>()

    //about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        arrayList.add("Ajay")//Adding object in arraylist
        arrayList.add("Vijay")
        arrayList.add("Prakash")
        arrayList.add("Rohan")
        arrayList.add("Vijay")


        val view : View =  inflater.inflate(R.layout.fragment_doctor, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager  = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RecycleAdapter(arrayList)
        return view
    }
}