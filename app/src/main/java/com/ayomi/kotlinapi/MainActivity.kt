package com.ayomi.kotlinapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: Button
    lateinit var recyclerAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton = findViewById(R.id.btnSearch)
        recyclerView = findViewById(R.id.recyclerView)

        initRecyclerView()
        initViewModel()
        searchUser()

        btnCreateActivity.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
        }
    }

    private fun searchUser() {
        btnSearch.setOnClickListener {
            if (!TextUtils.isEmpty(edtSearch.text.toString())) {
                viewModel.searchUser(edtSearch.text.toString())
            } else {
                viewModel.getUsersList()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            recyclerAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerAdapter

        }
    }
    fun initViewModel()
    {
        viewModel =  ViewModelProvider(this).get(MainActivityViewModel::class.java )
        viewModel.getUserListObservable().observe(this, Observer<UserList> {
            if (it == null) {
                Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show()
            } else {

                recyclerAdapter.userList= it.data.toMutableList()
                recyclerAdapter.notifyDataSetChanged()
            }
        })

        viewModel.getUsersList()
    }

    override fun onItemEditClick(user: User) {
        val intent = Intent(this@MainActivity, CreateUserActivity::class.java)
        intent.putExtra("user_id",user.id )
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000) {
            viewModel.getUsersList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}