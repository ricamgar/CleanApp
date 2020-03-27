package com.ricamgar.citibox.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ricamgar.domain.model.Post
import com.ricamgar.domain.repository.PostsRepository
import com.ricamgar.domain.repository.Response
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    fun getPosts(): LiveData<Response<List<Post>>> {
        return liveData(context = viewModelScope.coroutineContext) {
            emit(postsRepository.getAll())
        }
    }
}