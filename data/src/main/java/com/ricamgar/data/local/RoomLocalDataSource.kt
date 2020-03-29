package com.ricamgar.data.local

import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.datasource.LocalDataSource

class RoomLocalDataSource(appDatabase: AppDatabase) : LocalDataSource {

    private val postsDao = appDatabase.postsDao()

    override suspend fun getAllPosts(): List<Post> {
        return postsDao.getPosts()
            .map {
                Post(it.id, it.userId, it.title, it.body)
            }
    }

    override suspend fun getPost(postId: Int): Post? {
        return postsDao.getPost(postId)?.run {
            Post(id, userId, title, body)
        }
    }

    override suspend fun savePosts(vararg posts: Post) {
        val postEntities = posts.map {
            PostEntity(it.id, it.userId, it.title, it.body)
        }
        return postsDao.insertPosts(postEntities)
    }

    override suspend fun getUser(userId: Int): User? {
        return postsDao.getUser(userId)?.run {
            User(id, name, username, email)
        }
    }

    override suspend fun saveUser(user: User) {
        val userEntity = user.run { UserEntity(id, name, username, email) }
        return postsDao.insertUser(userEntity)
    }
}