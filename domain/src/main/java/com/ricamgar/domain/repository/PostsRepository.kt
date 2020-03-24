package com.ricamgar.domain.repository

import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.datasource.LocalDataSource
import com.ricamgar.domain.repository.datasource.RemoteDataSource
import java.io.IOException

class PostsRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getAll(): Response<List<Post>> {
        return try {
            val posts = remoteDataSource.fetchAllPosts()
            localDataSource.savePosts(posts)
            Response(posts, true)
        } catch (e: IOException) {
            val posts = localDataSource.getAllPosts()
            Response(posts, false)
        }
    }
}

data class Response<Data>(
    val data: Data,
    val online: Boolean
)

