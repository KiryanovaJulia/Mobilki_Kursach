package com.example.ebm.data.registration

import com.example.ebm.domain.account.KtorApi
import com.example.ebm.domain.account.UserData
import retrofit2.Call

class UserRepository(private val apiService: KtorApi) {
    fun register(registerData: RegisterRecieveRemote): Call<RegisterResponseRemote> = apiService.register(registerData)
    fun login(loginData: LoginRecieveRemote): Call<LoginResponseRemote> = apiService.login(loginData)
}