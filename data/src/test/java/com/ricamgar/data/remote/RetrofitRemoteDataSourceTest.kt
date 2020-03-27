package com.ricamgar.data.remote

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

class RetrofitRemoteDataSourceTest {

    private val mockWebServer = MockWebServer()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url(""))
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(JsonPlaceholderApi::class.java)

    private val dataSource = RetrofitRemoteDataSource(api)

    @Test
    fun `should return posts if fetching posts call succeeds`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(getJson("posts.json")))

        val posts = dataSource.fetchAllPosts()

        assertEquals(posts.size, 4)
        assertEquals(posts[0].id, 1)
        assertEquals(posts[2].body, "body5")
        assertEquals(posts[3].userId, 9)
    }

    @Test(expected = Throwable::class)
    fun `should throw exception if fetching posts call fails`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        dataSource.fetchAllPosts()

        return@runBlocking
    }

    @Test
    fun `should return user if fetching user call succeeds`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(getJson("user.json")))

        val user = dataSource.fetchUserById(0)

        assertEquals(user.id, 1)
        assertEquals(user.name, "Leanne Graham")
        assertEquals(user.username, "Bret")
        assertEquals(user.email, "Sincere@april.biz")
        assertEquals(user.address.street, "Kulas Light")
    }

    @Test(expected = Throwable::class)
    fun `should throw exception if fetching user call fails`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        dataSource.fetchUserById(0)

        return@runBlocking
    }

    @Test
    fun `should return comments if fetching comments call succeeds`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(getJson("comments.json")))

        val comments = dataSource.fetchCommentsByPost(0)

        assertEquals(comments.size, 5)
        assertEquals(comments[0].id, 1)
        assertEquals(comments[2].body, "quia molestiae")
        assertEquals(comments[3].name, "alias odio sit")
        assertEquals(comments[4].email, "Hayden@althea.biz")
    }

    @Test(expected = Throwable::class)
    fun `should throw exception if fetching comments call fails`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        dataSource.fetchCommentsByPost(0)

        return@runBlocking
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}