package com.kinzlstanislav.csasucty.architecture.repository.mapper

import com.kinzlstanislav.csasucty.architecture.network.response.AccountResponse
import com.kinzlstanislav.csasucty.architecture.network.response.AccountsResponse
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AccountsMapperTest {

    private companion object {
        val INPUT = AccountsResponse(
            listOf(
                AccountResponse(
                    accountNumber = "123",
                    bankCode = "321",
                    transparencyFrom = "2015-01-02T00:00:00",
                    transparencyTo = "?",
                    publicationTo = "?",
                    actualizationDate = "?",
                    balance = 1.0f,
                    currency = "CZK",
                    name = "Spolecenstvi Praha",
                    iban = "12345"
                ),
                AccountResponse()
            )
        )
        val EXPECTED_OUTPUT = listOf(
            Account(
                accountNumber = "123",
                bankCode = "321",
                transparencyFrom = "2015 01 02",
                transparencyTo = "?",
                publicationTo = "?",
                actualizationDate = "?",
                balance = 1.0f,
                currency = "CZK",
                name = "Spolecenstvi Praha",
                iban = "12345"
            ),
            Account(
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                "",
                "",
                ""
            )
        )
    }

    private val subject = AccountsMapper()

    @Test
    fun `mapAccounts()`() {
        assertThat(subject.mapAccounts(INPUT))
            .isEqualTo(EXPECTED_OUTPUT)
    }

}