package za.ac.tut.hospitalmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*


class RecycleAdapter(private val about : ArrayList<String>) : RecyclerView.Adapter<RecycleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = about[position]
    }

    override fun getItemCount(): Int {
        return about.size
    }
    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.textView)
        val name1 = itemView.findViewById<TextView>(R.id.textView2)
        val name2 = itemView.findViewById<TextView>(R.id.textView3)
        val name3 = itemView.findViewById<TextView>(R.id.textView4)
        val name4 = itemView.findViewById<TextView>(R.id.textView5)
    }
}