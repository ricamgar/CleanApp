package com.ricamgar.cleanapp.posts.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ricamgar.cleanapp.util.MainCoroutineRule
import com.ricamgar.data.remote.ImageLoader
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response
import com.ricamgar.domain.repository.Response.Success
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsListViewModelTest {

    private val postsRepository: PostsRepository = mock()
    private val imageLoader: ImageLoader = mock()
    private lateinit var postsListViewModel: PostsListViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runBlockingTest {
        whenever(postsRepository.getAll()).thenReturn(Success(emptyList()))
        postsListViewModel = PostsListViewModel(postsRepository, imageLoader)
    }

    @Test
    fun shouldLoadPosts() = runBlockingTest {
        val response = Success(createListOfPosts())
        whenever(postsRepository.getAll()).thenReturn(response)

        mainCoroutineRule.pauseDispatcher()

        postsListViewModel.loadPosts()

        assertEquals(postsListViewModel.loading.value, true)

        mainCoroutineRule.resumeDispatcher()

        assertEquals(postsListViewModel.posts.value, response.data)
        assertEquals(postsListViewModel.loading.value, false)
    }

    @Test
    fun shouldSendErrorEventWhenGettingPostsFails() = runBlockingTest {
        val response = Response.Error(Throwable())
        whenever(postsRepository.getAll()).thenReturn(response)

        mainCoroutineRule.pauseDispatcher()

        postsListViewModel.loadPosts()

        assertEquals(postsListViewModel.loading.value, true)

        mainCoroutineRule.resumeDispatcher()

        assertEquals(postsListViewModel.posts.value!!.isEmpty(), true)
        assertEquals(postsListViewModel.error.value, true)
        assertEquals(postsListViewModel.loading.value, false)
    }

    @Test
    fun shouldSendOpenDetailEvent() {
        val postId = 0
        postsListViewModel.openPost(0)

        assertEquals(postsListViewModel.openPostEvent.value!!.peekContent(), postId)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            Post(it, it, "title$it", "body$it")
        }
    }
}