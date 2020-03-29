package com.ricamgar.citibox.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricamgar.citibox.util.Event
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>().apply { value = emptyList() }
    val posts: LiveData<List<Post>> = _posts

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _openPostEvent = MutableLiveData<Event<Int>>()
    val openPostEvent: LiveData<Event<Int>> = _openPostEvent

    init {
        loadPosts()
    }

    fun loadPosts() {
        _loading.value = true
        viewModelScope.launch {
            val postsResponse = postsRepository.getAll()
            if (postsResponse is Success) {
                _posts.value = postsResponse.data
                _loading.value = false
            }
        }
    }

    fun openPost(id: Int) {
        _openPostEvent.value = Event(id)
    }
}