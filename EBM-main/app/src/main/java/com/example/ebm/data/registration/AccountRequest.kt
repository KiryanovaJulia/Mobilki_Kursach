package com.example.ebm.data.registration

import com.example.ebm.domain.account.UserData

data class AccountRequest(val userData: UserData, val isRegister: Boolean)