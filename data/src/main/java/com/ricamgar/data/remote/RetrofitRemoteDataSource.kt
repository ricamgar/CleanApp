package com.ricamgar.data.remote

import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.datasource.RemoteDataSource

class RetrofitRemoteDataSource(
    private val api: JsonPlaceholderApi
) : RemoteDataSource {

    override suspend fun fetchAllPosts(): List<Post> {
        val posts = api.getPosts()
        return posts.map {
            Post(it.id, it.userId, it.title, it.body)
        }
    }

    override suspend fun fetchUserById(userId: Int): User {
        return api.getUserById(userId).run {
            User(id, name, username, email)
        }
    }

    override suspend fun fetchCommentsByPost(postId: Int): List<Comment> {
        return api.getCommentsByPostId(postId)
            .map {
                Comment(it.id, it.name, it.email, it.body)
            }
    }

}