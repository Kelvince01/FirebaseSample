package com.timizatechnologies.firebasesample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.timizatechnologies.firebasesample.R

class EmployeeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txt_name: TextView
    var txt_position: TextView
    var txt_option: TextView

    init {
        txt_name = itemView.findViewById(R.id.txt_name)
        txt_position = itemView.findViewById(R.id.txt_position)
        txt_option = itemView.findViewById(R.id.txt_option)
    }
}
