package com.ricamgar.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ricamgar.domain.model.Post
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomLocalDataSourceTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var roomLocalDataSource: RoomLocalDataSource

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).build()
        roomLocalDataSource = RoomLocalDataSource(appDatabase)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun shouldInsertAndGetPostsSuccessfully() = runBlocking {
        val posts = createListOfPosts()
        roomLocalDataSource.savePosts(*posts.toTypedArray())

        val savedPosts = roomLocalDataSource.getAllPosts()

        assertEquals(posts, savedPosts)
    }

    @Test
    fun shouldGetPostById() = runBlocking {
        val post = createListOfPosts(1).first()
        roomLocalDataSource.savePosts(post)

        val savedPost = roomLocalDataSource.getPost(post.id)

        assertEquals(post, savedPost)
    }

    @Test
    fun shouldReturnNullIfPostByIdDoesNotExist() = runBlocking {
        val savedPost = roomLocalDataSource.getPost(0)
        assertEquals(savedPost, null)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            Post(it, it, "title$it", "body$it")
        }
    }
}