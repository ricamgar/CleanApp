package com.ricamgar.cleanapp.posts.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ricamgar.cleanapp.util.Event
import com.ricamgar.data.remote.ImageLoader
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsListViewModel @ViewModelInject constructor(
    private val postsRepository: PostsRepository,
    val imageLoader: ImageLoader
) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>().apply { value = emptyList() }
    val posts: LiveData<List<Post>> = _posts

    val empty: LiveData<Boolean> = Transformations.map(_posts) { it.isEmpty() }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _openPostEvent = MutableLiveData<Event<Int>>()
    val openPostEvent: LiveData<Event<Int>> = _openPostEvent

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    init {
        loadPosts()
    }

    fun loadPosts() {
        _error.value = false
        _loading.value = true
        viewModelScope.launch {
            val postsResponse = postsRepository.getAll()
            if (postsResponse is Success) {
                _posts.value = postsResponse.data
            } else {
                _error.value = true
            }
            _loading.value = false
        }
    }

    fun openPost(id: Int) {
        _openPostEvent.value = Event(id)
    }
}