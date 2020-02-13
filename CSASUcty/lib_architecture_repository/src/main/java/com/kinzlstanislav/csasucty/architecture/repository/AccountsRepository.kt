package com.kinzlstanislav.csasucty.architecture.repository

import com.kinzlstanislav.csasucty.architecture.network.api.CSASApiService
import com.kinzlstanislav.csasucty.architecture.repository.mapper.AccountsMapper

class AccountsRepository(
    private val mapper: AccountsMapper,
    private val api: CSASApiService
) {

    suspend fun fetchAccounts() = mapper.mapAccounts(api.getAccountsAsync().await())

}