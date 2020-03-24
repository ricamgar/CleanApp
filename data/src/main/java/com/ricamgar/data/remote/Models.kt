package com.ricamgar.data.remote

data class PostApi(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

data class UserApi(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressApi,
    val phone: String,
    val website: String,
    val company: CompanyApi
)

data class AddressApi(
    val street: String,
    val suite: String,
    val city: String
)

data class CompanyApi(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

data class CommentApi(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)