package com.ricamgar.domain.repository

import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.datasource.LocalDataSource
import com.ricamgar.domain.repository.datasource.RemoteDataSource

class DefaultPostsRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : PostsRepository {

    override suspend fun getAll(): Response<List<Post>> {
        return try {
            val posts = remoteDataSource.fetchAllPosts()
            localDataSource.savePosts(*posts.toTypedArray())
            Response.Success(posts)
        } catch (e: Throwable) {
            val posts = localDataSource.getAllPosts()
            if (posts.isNotEmpty()) {
                Response.Success(posts)
            } else {
                Response.Error(Throwable("Error getting posts"))
            }
        }
    }

    override suspend fun getUser(userId: Int): Response<User> {
        return try {
            val user = remoteDataSource.fetchUserById(userId)
            localDataSource.saveUser(user)
            Response.Success(user)
        } catch (e: Throwable) {
            val user = localDataSource.getUser(userId)
            if (user != null) {
                Response.Success(user)
            } else {
                Response.Error(Throwable("Error getting user"))
            }
        }
    }

    override suspend fun getComments(postId: Int): Response<List<Comment>> {
        return try {
            val comments = remoteDataSource.fetchCommentsByPost(postId)
            Response.Success(comments)
        } catch (e: Throwable) {
            Response.Error(Throwable("Error fetching comments"))
        }
    }

    override suspend fun getPost(postId: Int): Response<Post> {
        val postEntity = localDataSource.getPost(postId)
        return postEntity?.run {
            Response.Success(Post(id, userId, title, body))
        } ?: Response.Error(Throwable("Error getting post"))
    }
}

