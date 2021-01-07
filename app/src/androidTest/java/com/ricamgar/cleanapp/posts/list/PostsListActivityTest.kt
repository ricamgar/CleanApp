package com.ricamgar.cleanapp.posts.list

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ricamgar.cleanapp.R
import com.ricamgar.data.DataModule
import com.ricamgar.data.remote.ImageLoader
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@UninstallModules(DataModule::class)
@HiltAndroidTest
class PostsListActivityTest {

    @BindValue
    @JvmField
    val postsRepository: PostsRepository = mock()

    @BindValue
    @JvmField
    val imageLoader: ImageLoader = mock()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun shouldDisplayPostsWhenNotEmpty() = runBlockingTest {
        val response = Response.Success(createListOfPosts())
        whenever(postsRepository.getAll()).thenReturn(response)

        launchActivity()

        onView(withText("title1")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayErrorWhenGetPostsFails() = runBlockingTest {
        val response = Response.Error(Throwable())
        whenever(postsRepository.getAll()).thenReturn(response)

        launchActivity()

        onView(withText(R.string.error_loading_posts)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayEmptyMessageWhenGetPostsIsEmpty() = runBlockingTest {
        val response = Response.Success(emptyList<Post>())
        whenever(postsRepository.getAll()).thenReturn(response)

        launchActivity()

        onView(withText(R.string.no_posts)).check(matches(isDisplayed()))
    }

    private fun launchActivity(): ActivityScenario<PostsListActivity>? {
        return launch(PostsListActivity::class.java)
    }

    private fun createListOfPosts(number: Int = 10): List<Post> {
        return (1..number).map {
            Post(it, it, "title$it", "body$it")
        }
    }
}