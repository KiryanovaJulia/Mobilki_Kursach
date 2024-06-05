package com.example.ebm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ebm.data.registration.LoginRecieveRemote
import com.example.ebm.data.registration.LoginResponseRemote
import com.example.ebm.data.registration.RegisterRecieveRemote
import com.example.ebm.data.registration.RegisterResponseRemote
import com.example.ebm.data.registration.UserRepository
import com.example.ebm.domain.account.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val userRepository: UserRepository): ViewModel()  {
    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> get() = _registerResult

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    fun register(username: String, password: String) {
        val registerData = RegisterRecieveRemote(username, username, password)
        userRepository.register(registerData).enqueue(object : Callback<RegisterResponseRemote> {
            override fun onResponse(call: Call<RegisterResponseRemote>, response: Response<RegisterResponseRemote>) {
                if (response.isSuccessful) {
                    _registerResult.postValue("Registration Successful: Token ${response.body()?.token}")
                } else {
                    _registerResult.postValue("Registration Failed")
                }
            }

            override fun onFailure(call: Call<RegisterResponseRemote>, t: Throwable) {
                _registerResult.postValue("Error: ${t.message}")
            }
        })
    }

    fun login(username: String, password: String) {
        val loginData = LoginRecieveRemote(username, password)
        userRepository.login(loginData).enqueue(object : Callback<LoginResponseRemote> {
            override fun onResponse(call: Call<LoginResponseRemote>, response: Response<LoginResponseRemote>) {
                if (response.isSuccessful) {
                    _loginResult.postValue("Login Successful: Token ${response.body()?.token}")
                } else {
                    _loginResult.postValue("Login Failed")
                }
            }

            override fun onFailure(call: Call<LoginResponseRemote>, t: Throwable) {
                _loginResult.postValue("Error: ${t.message}")
            }
        })
    }
}