package com.timizatechnologies.firebasesample.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.timizatechnologies.firebasesample.DAO.DAOEmployee
import com.timizatechnologies.firebasesample.EmployeeVH
import com.timizatechnologies.firebasesample.MainActivity
import com.timizatechnologies.firebasesample.R
import com.timizatechnologies.firebasesample.models.Employee
import kotlin.collections.ArrayList

class RVAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /*private lateinit var context: Context
    constructor(ctx : Context) {
        this.context = ctx
    }*/

    var list: ArrayList<Employee> = ArrayList<Employee>()
    fun setItems(emp: ArrayList<Employee>) {
        list.addAll(emp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false)
        return EmployeeVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val e: Employee? = null
        this.onBindViewHolder(holder, position, e!!)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        e: Employee
    ) {
        val vh: EmployeeVH = holder as EmployeeVH
        val emp: Employee = e
        vh.txt_name.setText(emp.name)
        vh.txt_position.setText(emp.position)
        vh.txt_option.setOnClickListener { v ->
            val popupMenu = PopupMenu(context, vh.txt_option)
            popupMenu.inflate(R.menu.option_menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_edit -> {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("EDIT", emp)
                        context.startActivity(intent)
                    }
                    R.id.menu_remove -> {
                        val dao = DAOEmployee()
                        dao.remove(emp.key).addOnSuccessListener { suc ->
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show()
                            notifyItemRemoved(position)
                            list.remove(emp)
                        }.addOnFailureListener { er ->
                            Toast.makeText(
                                context,
                                "" + er.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                false
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
