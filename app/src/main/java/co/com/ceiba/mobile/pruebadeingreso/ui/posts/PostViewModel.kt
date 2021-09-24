package co.com.ceiba.mobile.pruebadeingreso.ui.posts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepository
import co.com.ceiba.mobile.pruebadeingreso.ui.UiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel @ViewModelInject constructor(private val repo: PostRepository) : ViewModel() {

    private val _listPost = MutableLiveData<List<Post>>()
    val listPost: LiveData<List<Post>>
        get() = _listPost

    private val _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    fun getPosts(userId: Int) {
        emitUiState(showProgress = true)
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPosts(userId).also { result ->
                validateGetPost(result)
            }
        }
    }

    private fun validateGetPost(result: Result<List<Post>>) {
        when (result) {
            is Result.Success -> {
                val posts = result.data
                viewModelScope.launch(Dispatchers.Main) {
                    result.data.let {
                        _listPost.value = posts
                    }
                }
                emitUiState(showProgress = false)
            }
            is Result.Failure -> {
                emitUiState(showMessageDialog = result.exception.message.toString())
            }
        }
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        showMessageDialog: String = ""
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val uiModel = UiModel(
                showProgress,
                showMessageDialog
            )
            _uiModel.value = uiModel
        }
    }
}