package com.ricamgar.domain.repository.datasource

import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User

interface LocalDataSource {
    suspend fun getAllPosts(): List<Post>
    suspend fun savePosts(vararg posts: Post)
    suspend fun getPost(postId: Int): Post?
    suspend fun saveUser(user: User)
    suspend fun getUser(userId: Int): User?
}