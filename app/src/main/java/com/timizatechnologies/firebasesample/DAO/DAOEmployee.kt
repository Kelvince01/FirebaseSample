package com.timizatechnologies.firebasesample.DAO

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.timizatechnologies.firebasesample.models.Employee
import kotlin.collections.HashMap

class DAOEmployee {
    private val databaseReference: DatabaseReference
    fun add(emp: Employee?): Task<Void> {
        return databaseReference.push().setValue(emp)
    }

    fun update(key: String?, hashMap: HashMap<String, Any>): Task<Void> {
        return databaseReference.child(key!!).updateChildren(hashMap)
    }

    fun remove(key: String?): Task<Void> {
        return databaseReference.child(key!!).removeValue()
    }

    fun get(key: String): Query {
        return databaseReference
    }

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(Employee::class.java.getSimpleName())
    }
}
