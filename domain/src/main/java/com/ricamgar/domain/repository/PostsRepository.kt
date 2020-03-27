package com.ricamgar.domain.repository

import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.datasource.LocalDataSource
import com.ricamgar.domain.repository.datasource.RemoteDataSource
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getAll(): Response<List<Post>> {
        return try {
            val posts = remoteDataSource.fetchAllPosts()
            localDataSource.savePosts(posts)
            Response(posts, true)
        } catch (e: Throwable) {
            val posts = localDataSource.getAllPosts()
            Response(posts, false)
        }
    }

    suspend fun getUser(userId: Int): Response<User> {
        val user = remoteDataSource.fetchUserById(userId)
        return Response(user, true)
    }

    suspend fun getComments(postId: Int): Response<List<Comment>> {
        val comments = remoteDataSource.fetchCommentsByPost(postId)
        return Response(comments, true)
    }
}

data class Response<Data>(
    val data: Data,
    val online: Boolean
)

