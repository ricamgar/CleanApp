package com.ricamgar.citibox.posts.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ricamgar.citibox.util.MainCoroutineRule
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsListViewModelTest {

    private val postsRepository: PostsRepository = mock()
    private lateinit var postsListViewModel: PostsListViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runBlockingTest {
        whenever(postsRepository.getAll()).thenReturn(Response(emptyList(), true))
        postsListViewModel = PostsListViewModel(postsRepository)
    }

    @Test
    fun shouldLoadPosts() = runBlockingTest {
        val response = Response(createListOfPosts(), true)
        whenever(postsRepository.getAll()).thenReturn(response)

        postsListViewModel.loadPosts()

        assertEquals(postsListViewModel.posts.value, response.data)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            Post(it, it, "title$it", "body$it")
        }
    }
}