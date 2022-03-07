package com.ayomi.kotlinapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateNewUserViewModel : ViewModel() {

    lateinit var createNewUserLiveData: MutableLiveData<UserResponse?>
    lateinit var loadUserdata: MutableLiveData<UserResponse?>
    lateinit var deleteUserLiveData: MutableLiveData<UserResponse?>

    init {
        createNewUserLiveData = MutableLiveData()
        loadUserdata = MutableLiveData()
        deleteUserLiveData = MutableLiveData()
    }

    fun getLoadUserObservable(): MutableLiveData<UserResponse?> {
        return loadUserdata
    }

    fun getCreateNewUserObservable(): MutableLiveData<UserResponse?> {
        return createNewUserLiveData
    }

    fun getDeleteUserObservable(): MutableLiveData<UserResponse?> {
        return deleteUserLiveData
    }

    fun createUser(user: User) {
        val retrofitInstance = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        val call = retrofitInstance.createUser(user)
        call.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }
        })
    }

    fun updateUser(user_id: String, user: User) {
        val retrofitInstance = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        val call = retrofitInstance.updateUser(user_id, user)
        call.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }
        })
    }

    fun deleteUser(user_id: String?) {
        val retrofitInstance = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        val call = retrofitInstance.deleteUser(user_id!!)
        call.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    deleteUserLiveData.postValue(response.body())
                } else {
                    deleteUserLiveData.postValue(null)
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                deleteUserLiveData.postValue(null)
            }
        })
    }


    fun getUserData(user_id: String?) {
        val retrofitInstance = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        val call = retrofitInstance.getUser(user_id!!)
        call.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    loadUserdata.postValue(response.body())
                } else {
                    loadUserdata.postValue(null)
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                loadUserdata.postValue(null)
            }
        })
    }
}