package co.com.ceiba.mobile.pruebadeingreso.ui.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.ui.UiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(private val repo: UserRepository) : ViewModel() {

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>>
        get() = _listUsers

    private val _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    fun getUsers() {
        emitUiState(showProgress = true)
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUsers().also { result ->
                validateGetUsers(result)
            }
        }
    }

    private fun validateGetUsers(result: Result<List<User>>) {
        when (result) {
            is Result.Success -> {
                val users = result.data
                viewModelScope.launch(Dispatchers.Main) {
                    result.data.let {
                        _listUsers.value = users
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