package com.timizatechnologies.firebasesample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timizatechnologies.firebasesample.DAO.DAOEmployee
import com.timizatechnologies.firebasesample.models.Employee

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edit_name = findViewById<EditText>(R.id.edit_name)
        val edit_position = findViewById<EditText>(R.id.edit_position)
        val btn_submit = findViewById<Button>(R.id.btn_submit)
        val btn_open = findViewById<Button>(R.id.btn_open)
        btn_open.setOnClickListener { click: View? ->
            val intent = Intent(this@MainActivity, RVActivity::class.java)
            startActivity(intent)
        }
        ///We pass data from other activity to edit
        ///We pass data from other activity to edit
        val emp_edit = intent.getSerializableExtra("EDIT") as Employee?
        if (emp_edit != null) {
            btn_submit.setText("UPDATE")
            edit_name.setText(emp_edit.name)
            edit_position.setText(emp_edit.position)
            btn_open.visibility = View.GONE
        } else {
            btn_submit.setText("SUBMIT")
            btn_open.visibility = View.VISIBLE
        }
        val dao = DAOEmployee()
        btn_submit.setOnClickListener { v: View? ->
            if (emp_edit == null) {
                val emp =
                    Employee(edit_name.text.toString(), edit_position.text.toString())
                //because add() method is return as Task so we can implement like if success we can perform other action and so on
                dao.add(emp)
                    .addOnSuccessListener { suc: Void? ->
                        Toast.makeText(
                            this,
                            "Record is inserted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener { er: Exception ->
                        Toast.makeText(
                            this,
                            "" + er.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                val hashMap =
                    HashMap<String, Any>()
                hashMap["name"] = edit_name.text.toString()
                hashMap["position"] = edit_position.text.toString()
                //update is required key, this is manually input to update
                dao.update("-MXgqaKEPSYq9q8SQaVW", hashMap)
                    .addOnSuccessListener { suc: Void? ->
                        Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener { er: Exception ->
                        Toast.makeText(
                            this,
                            "" + er.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }
}
