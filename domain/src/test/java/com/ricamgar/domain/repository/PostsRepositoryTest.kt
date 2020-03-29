package com.ricamgar.domain.repository

import com.nhaarman.mockitokotlin2.*
import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.Response.Error
import com.ricamgar.domain.repository.Response.Success
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
    fun `should return posts when fetching succeeds`() = runBlockingTest {
        val postsList = createListOfPosts()
        whenever(remoteDataSourceMock.fetchAllPosts()).thenReturn(postsList)

        val response = postsRepository.getAll() as Success

        assertEquals(response.data, postsList)
    }

    @Test
    fun `should return local posts when fetching fails`() = runBlockingTest {
        val postsList = createListOfPosts()
        whenever(remoteDataSourceMock.fetchAllPosts()).doAnswer { throw IOException() }
        whenever(localDataSourceMock.getAllPosts()).thenReturn(postsList)

        val response = postsRepository.getAll() as Success

        assertEquals(response.data, postsList)
    }

    @Test
    fun `should return error when local and remote fails`() = runBlockingTest {
        whenever(remoteDataSourceMock.fetchAllPosts()).doAnswer { throw IOException() }
        whenever(localDataSourceMock.getAllPosts()).thenReturn(emptyList())

        val response = postsRepository.getAll() as Error

        assertEquals(response.exception::class, Throwable::class)
    }

    @Test
    fun `should return post by id when exists`() = runBlockingTest {
        val post = createListOfPosts(1).first()
        whenever(localDataSourceMock.getPost(post.id)).thenReturn(post)

        val response = postsRepository.getPost(post.id) as Success

        assertEquals(response.data, post)
    }

    @Test
    fun `should throw when post by id does not exist`() = runBlockingTest {
        whenever(localDataSourceMock.getPost(0)).thenReturn(null)
        val response = postsRepository.getPost(0) as Error

        assertEquals(response.exception::class, Throwable::class)
    }

    @Test
    fun `should return user and save to local when fetching succeeds`() = runBlockingTest {
        val userId = 0
        val user = createUser()
        whenever(remoteDataSourceMock.fetchUserById(userId)).thenReturn(user)

        val response = postsRepository.getUser(userId) as Success

        verify(localDataSourceMock, only()).saveUser(user)
        assertEquals(response.data, user)
    }

    @Test
    fun `should return user from local if exists`() = runBlockingTest {
        val userId = 0
        val user = createUser()
        whenever(remoteDataSourceMock.fetchUserById(userId)).doAnswer { throw Throwable() }
        whenever(localDataSourceMock.getUser(userId)).thenReturn(user)

        val response = postsRepository.getUser(userId) as Success

        assertEquals(response.data, user)
    }

    @Test
    fun `should return error when fetching user fails and local not found`() = runBlockingTest {
        val userId = 0
        whenever(remoteDataSourceMock.fetchUserById(userId)).doAnswer { throw Throwable() }
        whenever(localDataSourceMock.getUser(userId)).thenReturn(null)

        val response = postsRepository.getUser(userId) as Error

        assertEquals(response.exception::class, Throwable::class)
    }

    @Test
    fun `should return comments when fetching succeeds`() = runBlockingTest {
        val postId = 0
        val comments = createListOfComments()
        whenever(remoteDataSourceMock.fetchCommentsByPost(postId)).thenReturn(comments)

        val response = postsRepository.getComments(postId) as Success

        assertEquals(response.data, comments)
    }

    @Test
    fun `should return exception when fetching comments fails`() = runBlockingTest {
        val postId = 0
        whenever(remoteDataSourceMock.fetchCommentsByPost(postId)).doAnswer { throw Throwable() }

        val response = postsRepository.getComments(postId) as Error

        assertEquals(response.exception::class, Throwable::class)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            Post(it, it, "title$it", "body$it")
        }
    }

    private fun createUser(): User {
        return User(1, "User", "username", "mail@domain.com")
    }

    private fun createListOfComments(number: Int = 5): List<Comment> {
        return (1..number).map {
            Comment(it, "name$it", "email$it@mail.com", "bpdy$it")
        }
    }
}
