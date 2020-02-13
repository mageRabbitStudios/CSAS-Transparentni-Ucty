package com.kinzlstanislav.csasucty.architecture.repository.model

data class Account(
    val accountNumber: String,
    val bankCode: String,
    val transparencyFrom: String,
    val transparencyTo: String,
    val publicationTo: String,
    val actualizationDate: String,
    val balance: Float?,
    val currency: String,
    val name: String,
    val iban: String
)