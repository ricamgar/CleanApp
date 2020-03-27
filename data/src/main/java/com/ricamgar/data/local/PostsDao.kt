package com.ricamgar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPost(postId: Int): PostEntity?

    @Insert
    suspend fun insertPosts(posts: List<PostEntity>)
}