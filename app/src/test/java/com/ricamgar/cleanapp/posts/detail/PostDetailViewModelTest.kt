package com.ricamgar.cleanapp.posts.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ricamgar.cleanapp.util.MainCoroutineRule
import com.ricamgar.data.remote.ImageLoader
import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostDetailViewModelTest {
    private val postsRepository: PostsRepository = mock()
    private val imageLoader: ImageLoader = mock()
    private lateinit var postDetailViewModel: PostDetailViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runBlockingTest {
        postDetailViewModel = PostDetailViewModel(postsRepository, imageLoader)
    }

    @Test
    fun shouldLoadPostDetails() = runBlockingTest {
        val post = createPost()
        val user = createUser()
        val comments = createListOfComments()
        whenever(postsRepository.getPost(post.id)).thenReturn(Response.Success(post))
        whenever(postsRepository.getUser(post.userId)).thenReturn(Response.Success(user))
        whenever(postsRepository.getComments(post.id)).thenReturn(Response.Success(comments))

        postDetailViewModel.init(post.id)

        assertEquals(postDetailViewModel.post.value, post)
        assertEquals(postDetailViewModel.user.value, user)
        assertEquals(postDetailViewModel.comments.value, comments)
    }

    private fun createPost(): Post {
        return Post(0, 1, "Post title", "Post body")
    }

    private fun createUser(): User {
        return User(1, "User", "username", "mail@domain.com")
    }

    private fun createListOfComments(number: Int = 5): List<Comment> {
        return (1..number).map {
            Comment(it, "name$it", "email$it@mail.com", "body$it")
        }
    }

}