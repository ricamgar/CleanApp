package com.ricamgar.citibox.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>().apply { value = emptyList() }
    val posts: LiveData<List<Post>> = _posts

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            val postsResponse = postsRepository.getAll()
            _posts.value = postsResponse.data
        }
    }
}