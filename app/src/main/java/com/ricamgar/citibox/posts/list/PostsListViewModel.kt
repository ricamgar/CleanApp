package com.ricamgar.citibox.posts.list

import android.util.Log
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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        loadPosts()
    }

    fun loadPosts() {
        _loading.value = true
        viewModelScope.launch {
            val postsResponse = postsRepository.getAll()
            _posts.value = postsResponse.data
            _loading.value = false
            Log.e("LKM", loading.value!!.toString())
        }
    }

    fun openPost(id: Int) {

    }
}