package com.kinzlstanislav.csasucty.architecture.repository.mapper

import com.kinzlstanislav.csasucty.architecture.network.response.AccountsResponse
import com.kinzlstanislav.csasucty.architecture.repository.model.Account

class AccountsMapper {

    fun mapAccounts(response: AccountsResponse): List<Account> = mutableListOf<Account>().apply {
        response.accounts?.forEach {
            with(it) {
                this@apply.add(
                    Account(
                        accountNumber = accountNumber.orEmpty(),
                        bankCode = bankCode.orEmpty(),
                        transparencyFrom = transparencyFrom.orEmpty(),
                        transparencyTo = transparencyTo.orEmpty(),
                        publicationTo = publicationTo.orEmpty(),
                        actualizationDate = actualizationDate.orEmpty(),
                        balance = balance,
                        currency = currency.orEmpty(),
                        name = name.orEmpty(),
                        iban = iban.orEmpty()
                    )
                )
            }
        }
    }
}