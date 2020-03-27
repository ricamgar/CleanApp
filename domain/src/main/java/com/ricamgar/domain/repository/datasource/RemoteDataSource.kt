package com.ricamgar.domain.repository.datasource

import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User

interface RemoteDataSource {
    suspend fun fetchAllPosts(): List<Post>

    suspend fun fetchCommentsByPost(postId: Int): List<Comment>

    suspend fun fetchUserById(userId: Int): User
}