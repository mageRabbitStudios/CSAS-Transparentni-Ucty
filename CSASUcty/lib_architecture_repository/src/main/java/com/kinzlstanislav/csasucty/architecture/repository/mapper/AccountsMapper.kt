package com.kinzlstanislav.csasucty.architecture.repository.mapper

import com.kinzlstanislav.csasucty.architecture.network.response.AccountsResponse
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AccountsMapper {

    private val transparencyDateSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun mapAccounts(response: AccountsResponse): List<Account> = mutableListOf<Account>().apply {
        response.accounts?.forEach {
            with(it) {
                this@apply.add(
                    Account(
                        accountNumber = accountNumber.orEmpty(),
                        bankCode = bankCode.orEmpty(),
                        transparencyFrom = transparencyFrom?.let { tf -> mapTransparencyDate(tf) }.orEmpty(),
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

    private fun mapTransparencyDate(unformattedTimeApiResponse: String) = try {
        transparencyDateSDF.format(transparencyDateSDF.parse(unformattedTimeApiResponse)).replace("-", " ")
    } catch (e: ParseException) {
        ""
    }
}