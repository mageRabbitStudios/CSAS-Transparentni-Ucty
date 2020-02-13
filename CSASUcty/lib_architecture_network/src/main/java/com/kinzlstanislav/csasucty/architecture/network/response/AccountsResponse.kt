package com.kinzlstanislav.csasucty.architecture.network.response

import com.squareup.moshi.Json

data class AccountsResponse(
    @field:Json(name = "accounts") val accounts: List<AccountResponse>? = null
)

data class AccountResponse(
    @field:Json(name = "accountNumber") val accountNumber: String? = null,
    @field:Json(name = "bankCode") val bankCode: String? = null,
    @field:Json(name = "transparencyFrom") val transparencyFrom: String? = null,
    @field:Json(name = "transparencyTo") val transparencyTo: String? = null,
    @field:Json(name = "publicationTo") val publicationTo: String? = null,
    @field:Json(name = "actualizationDate") val actualizationDate: String? = null,
    @field:Json(name = "balance") val balance: Float? = null,
    @field:Json(name = "currency") val currency: String? = null,
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "iban") val iban: String? = null
)