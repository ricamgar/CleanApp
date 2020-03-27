package com.ricamgar.domain.repository

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ricamgar.domain.model.Address
import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.datasource.LocalDataSource
import com.ricamgar.domain.repository.datasource.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class PostsRepositoryTest {

    private val localDataSourceMock: LocalDataSource = mock()
    private val remoteDataSourceMock: RemoteDataSource = mock()

    private val postsRepository = PostsRepository(localDataSourceMock, remoteDataSourceMock)

    @Test
    fun `should return online data when fetching succeeds`() = runBlockingTest {
        val postsList = createListOfPosts()
        whenever(remoteDataSourceMock.fetchAllPosts()).thenReturn(postsList)

        val response = postsRepository.getAll()

        assertEquals(response.online, true)
        assertEquals(response.data, postsList)
    }

    @Test
    fun `should return offline data when fetching fails`() = runBlockingTest {
        val postsList = createListOfPosts()
        whenever(remoteDataSourceMock.fetchAllPosts()).doAnswer { throw IOException() }
        whenever(localDataSourceMock.getAllPosts()).thenReturn(postsList)

        val response = postsRepository.getAll()

        assertEquals(response.online, false)
        assertEquals(response.data, postsList)
    }

    @Test
    fun `should return user when fetching succeeds`() = runBlockingTest {
        val userId = 0
        val user = createUser()
        whenever(remoteDataSourceMock.fetchUserById(userId)).thenReturn(user)

        val response = postsRepository.getUser(userId)

        assertEquals(response.online, true)
        assertEquals(response.data, user)
    }

    @Test(expected = Throwable::class)
    fun `should return exception when fetching user fails`() = runBlockingTest {
        val userId = 0
        whenever(remoteDataSourceMock.fetchUserById(userId)).doAnswer { throw Throwable() }

        postsRepository.getUser(userId)
    }

    @Test
    fun `should return comments when fetching succeeds`() = runBlockingTest {
        val postId = 0
        val comments = createListOfComments()
        whenever(remoteDataSourceMock.fetchCommentsByPost(postId)).thenReturn(comments)

        val response = postsRepository.getComments(postId)

        assertEquals(response.online, true)
        assertEquals(response.data, comments)
    }

    @Test(expected = Throwable::class)
    fun `should return exception when fetching comments fails`() = runBlockingTest {
        val postId = 0
        whenever(remoteDataSourceMock.fetchCommentsByPost(postId)).doAnswer { throw Throwable() }

        postsRepository.getComments(postId)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            Post(it, it, "title$it", "body$it")
        }
    }

    private fun createUser(): User {
        val address = Address("street", "suite", "city")
        return User(
            1, "User", "username", "mail@domain.com", address,
            "123456", "website.com"
        )
    }

    private fun createListOfComments(number: Int = 5): List<Comment> {
        return (1..number).map {
            Comment(it, "name$it", "email$it@mail.com", "bpdy$it")
        }
    }
}
