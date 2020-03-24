package com.ricamgar.data.remote

import com.ricamgar.domain.model.*
import com.ricamgar.domain.repository.datasource.RemoteDataSource

class RetrofitRemoteDataSource(
    private val api: JsonPlaceholderApi
) : RemoteDataSource {

    override suspend fun fetchAllPosts(): List<Post> {
        val posts = api.getPosts()
        return posts.map {
            val userApi = api.getUserById(it.userId)
            val user = with(userApi) {
                val address = Address(address.street, address.suite, address.city)
                val company = Company(company.name, company.catchPhrase, company.bs)
                User(id, name, username, email, address, phone, website, company)
            }

            Post(it.id, user, it.title, it.body)
        }
    }

    override suspend fun fetchCommentsByPost(postId: Int): List<Comment> {
        return api.getCommentsByPostId(postId)
            .map {
                Comment(it.id, it.name, it.email, it.body)
            }
    }

}