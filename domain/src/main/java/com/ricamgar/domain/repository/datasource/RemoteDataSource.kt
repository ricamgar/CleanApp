package com.ricamgar.domain.repository.datasource

import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post

interface RemoteDataSource {
    suspend fun fetchAllPosts(): List<Post>

    suspend fun fetchCommentsByPost(postId: Int): List<Comment>
}