package com.ricamgar.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ricamgar.domain.model.Address
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
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
        appDatabase = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java, "app_database"
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
        roomLocalDataSource.savePosts(posts)

        val savedPosts = roomLocalDataSource.getAllPosts()

        assertEquals(posts, savedPosts)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            val address = Address("street$it", "suite$it", "city$it")
            val user = User(
                it, "User$it", "username$it", "mail$it@domain.com", address,
                "123456", "website$it.com"
            )
            Post(it + 10, user, "title$it", "body$it")
        }
    }
}