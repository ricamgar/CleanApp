package com.ricamgar.citibox.posts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.PostsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _comments = MutableLiveData<List<Comment>>().apply { value = emptyList() }
    val comment: LiveData<List<Comment>> = _comments

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun init(postId: Int) {
        viewModelScope.launch {
            val postResponse = postsRepository.getPost(postId)
            _post.value = postResponse.data
        }
    }

//    fun loadPosts() {
//        _loading.value = true
//        viewModelScope.launch {
//            val postsResponse = postsRepository.getAll()
//            _user.value = postsResponse.data
//            _loading.value = false
//            Log.e("LKM", loading.value!!.toString())
//        }
//    }
}