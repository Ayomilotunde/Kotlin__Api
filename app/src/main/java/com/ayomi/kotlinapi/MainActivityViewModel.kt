package com.ayomi.kotlinapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    lateinit var recyclerListData: MutableLiveData<UserList>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getUserListObservable(): MutableLiveData<UserList> {
        return recyclerListData
    }

    fun getUsersList() {
        val retrofitInstance = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        val call = retrofitInstance.getUsersList()
        call.enqueue(object: Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {

                }
            }
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
    }

    fun searchUser(searchText: String) {
        val retrofitInstance = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        val call = retrofitInstance.searchUsers(searchText)
        call.enqueue(object: Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {

                }
            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

        })
    }
}