package com.kinzlstanislav.csasucty.architecture.network.api

import com.kinzlstanislav.csasucty.architecture.network.response.AccountsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CSASApiService {

    @GET("transparentAccounts")
    @Throws(Exception::class)
    fun getAccountsAsync(): Deferred<AccountsResponse>

}