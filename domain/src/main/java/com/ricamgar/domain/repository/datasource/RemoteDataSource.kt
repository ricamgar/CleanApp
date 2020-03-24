package com.ricamgar.domain.repository.datasource

import com.ricamgar.domain.model.Post

interface RemoteDataSource {
    suspend fun fetchAllPosts(): List<Post>
}