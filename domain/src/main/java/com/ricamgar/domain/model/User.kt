package com.ricamgar.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String
)

data class Address(
    val street: String,
    val suite: String,
    val city: String
)
