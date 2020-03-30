package com.ricamgar.domain.repository

import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User

interface PostsRepository {

    suspend fun getAll(): Response<List<Post>>

    suspend fun getUser(userId: Int): Response<User>

    suspend fun getComments(postId: Int): Response<List<Comment>>

    suspend fun getPost(postId: Int): Response<Post>
}

sealed class Response<out R> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: Throwable) : Response<Nothing>()
}

