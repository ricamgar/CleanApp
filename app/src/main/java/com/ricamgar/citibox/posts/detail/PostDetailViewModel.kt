package com.ricamgar.citibox.posts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricamgar.data.remote.ImageLoader
import com.ricamgar.domain.model.Comment
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.model.User
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    val imageLoader: ImageLoader
) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _comments = MutableLiveData<List<Comment>>().apply { value = emptyList() }
    val comments: LiveData<List<Comment>> = _comments

    private val _commentsNum = MutableLiveData<Int>()
    val commentsNum: LiveData<Int> = _commentsNum

    private val _loading = MutableLiveData<LoadingType>()
    val loading: LiveData<LoadingType> = _loading

    private val _postError = MutableLiveData<Boolean>()
    val postError: LiveData<Boolean> = _postError

    private val _userError = MutableLiveData(false)
    val userError: LiveData<Boolean> = _userError

    private val _commentsError = MutableLiveData(false)
    val commentsError: LiveData<Boolean> = _commentsError

    fun init(postId: Int) {
        viewModelScope.launch {
            val postResponse = postsRepository.getPost(postId)
            if (postResponse is Success) {
                _post.value = postResponse.data
            } else {
                _postError.value = true
                return@launch
            }

            _loading.value = LoadingType.USER
            val userResponse = postsRepository.getUser(postResponse.data.userId)
            if (userResponse is Success) {
                _user.value = userResponse.data
            } else {
                _userError.value = true
            }

            _loading.value = LoadingType.COMMENTS
            val commentsResponse = postsRepository.getComments(postResponse.data.id)
            if (commentsResponse is Success) {
                _comments.value = commentsResponse.data
                _commentsNum.value = commentsResponse.data.size
            } else {
                _commentsError.value = true
            }

            _loading.value = LoadingType.NONE
        }
    }

    enum class LoadingType {
        USER, COMMENTS, NONE
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