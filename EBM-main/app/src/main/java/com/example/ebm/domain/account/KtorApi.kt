package com.example.ebm.domain.account

import com.example.ebm.data.registration.AccountResponse
import com.example.ebm.data.registration.LoginRecieveRemote
import com.example.ebm.data.registration.LoginResponseRemote
import com.example.ebm.data.registration.RegisterRecieveRemote
import com.example.ebm.data.registration.RegisterResponseRemote
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KtorApi {
    @POST("/register")
    fun register(@Body registerData: RegisterRecieveRemote): Call<RegisterResponseRemote>
    @POST("/login")
    fun login(@Body loginData: LoginRecieveRemote): Call<LoginResponseRemote>
}