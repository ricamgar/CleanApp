package com.ricamgar.domain.repository.datasource

import com.ricamgar.domain.model.Post

interface LocalDataSource {
    suspend fun getAllPosts(): List<Post>
    suspend fun savePosts(vararg posts: Post)
    suspend fun getPost(postId: Int): Post?
}