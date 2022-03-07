package com.ayomi.kotlinapi

import retrofit2.Call
import retrofit2.http.*

interface RetroService  {

    @GET("users")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUsersList(): Call<UserList>

    @GET("users")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun searchUsers(@Query("name") searchText: String): Call<UserList>

    @GET("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUser(@Path("user_id") user_id: String): Call<UserResponse >

    @POST("users")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer 8bd164aafe6551a571ffa66b4889a9b9c9dfc05920767bcf66374a50710e5c9b")
    fun createUser(@Body params: User): Call<UserResponse>

    @PATCH("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer 8bd164aafe6551a571ffa66b4889a9b9c9dfc05920767bcf66374a50710e5c9b")
    fun updateUser(@Path("user_id") user_id: String, @Body params: User): Call<UserResponse>

    @DELETE ("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer 8bd164aafe6551a571ffa66b4889a9b9c9dfc05920767bcf66374a50710e5c9b")
    fun deleteUser(@Path("user_id") user_id: String): Call<UserResponse>
}