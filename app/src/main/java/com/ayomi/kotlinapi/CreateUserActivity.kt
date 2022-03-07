package com.ayomi.kotlinapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    lateinit var viewModel: CreateNewUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        val user_id = intent.getStringExtra("user_id")

        initViewModel()
        createUserObservable()

        if (user_id != null) {
            loadUserData(user_id)
        } else {
            btnCreateUser.setOnClickListener {
                createUser(user_id)
            }
        }

        btnDeleteUser.setOnClickListener {
            deleteUser(user_id)
        }
    }

    private fun deleteUser(user_id: String?) {
        viewModel.getDeleteUserObservable().observe(this, Observer<UserResponse?> {

                if (it == null) {
                    Toast.makeText(this, "Failed to user", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Successfully deleted user", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

        })
        viewModel.deleteUser(user_id)
    }

    private fun loadUserData(user_id: String?) {
        viewModel.getLoadUserObservable().observe(this, Observer<UserResponse?> {
            if (it != null) {
                edtName.setText(it.data?.name)
                edtEmail.setText(it.data?.email)
                btnCreateUser.setText("Update")
                btnDeleteUser.visibility = View.VISIBLE
            }
        })
        viewModel.getUserData(user_id)
    }

    private fun createUser(user_id: String?) {
        val user = User("", edtName.text.toString(), edtEmail.text.toString(), "Male", "Active")

        if (user_id == null) {
            viewModel.createUser(user)
        } else {
            viewModel.updateUser(user_id, user)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateNewUserViewModel::class.java)

    }

    private fun createUserObservable() {
        viewModel.getCreateNewUserObservable().observe(this, Observer<UserResponse?> {
            if (it == null) {
                Toast.makeText(this, "Failed to create/update new user", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Successfully created/update new user", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        })
    }
}