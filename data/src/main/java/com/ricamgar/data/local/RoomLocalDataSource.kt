package com.ricamgar.data.local

import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.datasource.LocalDataSource

class RoomLocalDataSource(appDatabase: AppDatabase) : LocalDataSource {

    private val postsDao = appDatabase.postsDao()

    override suspend fun getAllPosts(): List<Post> {
        return postsDao.getPosts()
            .map {
//                val userEntity = postsDao.getUserById(it.userId)
//                val user = with(userEntity) {
//                    val address = Address(address.street, address.suite, address.city)
//                    User(id, name, username, email, address, phone, website)
//                }
                Post(it.id, it.userId, it.title, it.body)
            }
    }

    override suspend fun savePosts(posts: List<Post>) {
        val postEntities = posts.map {
//            val userEntity = with(it.userId) {
//                val addressEntity = AddressEntity(address.street, address.suite, address.city)
//                UserEntity(id, name, username, email, addressEntity, phone, website)
//            }
//            postsDao.insertUser(userEntity)
            PostEntity(it.id, it.userId, it.title, it.body)
        }
        return postsDao.insertPosts(postEntities)
    }
}