package com.ricamgar.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): List<PostApi>

    @GET("/posts/{id}/comments")
    suspend fun getCommentsByPostId(@Path("id") postId: Int): List<CommentApi>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): UserApi
}