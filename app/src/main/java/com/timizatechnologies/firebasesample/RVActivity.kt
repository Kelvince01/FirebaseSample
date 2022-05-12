package com.timizatechnologies.firebasesample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.timizatechnologies.firebasesample.DAO.DAOEmployee
import com.timizatechnologies.firebasesample.adapters.RVAdapter
import com.timizatechnologies.firebasesample.models.Employee

class RVActivity : AppCompatActivity() {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RVAdapter
    lateinit var dao: DAOEmployee
    var isLoading = false
    lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)

        swipeRefreshLayout = findViewById(R.id.swip)
        recyclerView = findViewById(R.id.rv)
        recyclerView.setHasFixedSize(true)
        val manager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(manager)
        adapter = RVAdapter(this)
        recyclerView.setAdapter(adapter)
        dao = DAOEmployee()
        loadData()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItem = linearLayoutManager!!.itemCount
                val lastVisible = linearLayoutManager!!.findLastCompletelyVisibleItemPosition()
                if (totalItem < lastVisible + 3) {
                    if (!isLoading) {
                        isLoading = true
                        loadData()
                    }
                }
            }
        })
    }

    private fun loadData() {
        swipeRefreshLayout.isRefreshing = true
        dao.get(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val emps: ArrayList<Employee> = ArrayList()
                for (data in snapshot.children) {
                    val emp = data.getValue(Employee::class.java)
                    emp!!.key = data.key
                    emps.add(emp)
                    key = data.key!!
                }
                adapter.setItems(emps)
                adapter.notifyDataSetChanged()
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onCancelled(error: DatabaseError) {
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}
