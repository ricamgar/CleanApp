package com.ricamgar.domain.repository

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ricamgar.domain.model.*
import com.ricamgar.domain.repository.datasource.LocalDataSource
import com.ricamgar.domain.repository.datasource.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

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
    fun `should return comments when fetching succeeds`() = runBlockingTest {
        val postId = 0
        val comments = createListOfComments()
        whenever(remoteDataSourceMock.fetchCommentsByPost(postId)).thenReturn(comments)

        val response = postsRepository.getComments(postId)

        assertEquals(response.online, true)
        assertEquals(response.data, comments)
    }

    @Test(expected = Throwable::class)
    fun `should return exception when fetching fails`() = runBlockingTest {
        val postId = 0
        whenever(remoteDataSourceMock.fetchCommentsByPost(postId)).doAnswer { throw Throwable() }

        postsRepository.getComments(postId)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            val address = Address("street$it", "suite$it", "city$it")
            val company = Company("company$it", "phrase$it", "bs$it")
            val user = User(
                1, "User$it", "username$it", "mail$it@domain.com", address, "123456",
                "website$it.com", company
            )
            Post(it, user, "title$it", "body$it")
        }
    }

    private fun createListOfComments(number: Int = 5): List<Comment> {
        return (1..number).map {
            Comment(it, "name$it", "email$it@mail.com", "bpdy$it")
        }
    }
}
