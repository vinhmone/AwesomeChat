package com.rikkei.awesomechat.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rikkei.awesomechat.base.BaseViewModel
import com.rikkei.awesomechat.data.model.User
import com.rikkei.awesomechat.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    fun validateUser(action: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            if (repository.validateUser()) {
                action()
            }
        }
    }

    fun setUser(email: String, pass: String) {
        _user.value = User(uid = null, email = email, password = pass)
        println(email)
    }


    fun login() {
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            repository.signInWithEmailAndPassword(_user)
        }
    }

}
