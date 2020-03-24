package com.ricamgar.domain.model

data class Post(
    val id: Int,
    val user: User,
    val title: String,
    val body: String
)
