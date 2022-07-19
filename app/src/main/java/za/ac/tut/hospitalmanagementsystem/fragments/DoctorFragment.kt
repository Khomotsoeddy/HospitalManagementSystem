package za.ac.tut.hospitalmanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.ac.tut.hospitalmanagementsystem.R
import za.ac.tut.hospitalmanagementsystem.RecycleAdapter

class DoctorFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var arrayList = ArrayList<String>()

    //about

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_doctor, container, false)

        arrayList.clear()
        arrayList.add("Ajay")//Adding object in arraylist
        arrayList.add("Vijay")
        arrayList.add("Prakash")
        arrayList.add("Rohan")
        arrayList.add("Vijay")

        val buttonAll = view.findViewById<Button>(R.id.buttonAll)
        val buttonDentists = view.findViewById<Button>(R.id.buttonDentists)
        val buttonDermatologist = view.findViewById<Button>(R.id.buttonDermatologist)
        val buttonGynaecologist = view.findViewById<Button>(R.id.buttonGynaecologist)
        val buttonOptometrist = view.findViewById<Button>(R.id.buttonOptometrist)

        buttonAll.setOnClickListener {
            recyclerView = view.findViewById(R.id.recyclerView)

            recyclerView.layoutManager  = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = RecycleAdapter(arrayList)
        }

        return view
    }
}