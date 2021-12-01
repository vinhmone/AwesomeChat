package com.rikkei.awesomechat.data.source.remote.util

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.awesomechat.data.model.User

object FirebaseAPI {

    fun validateUser() = FirebaseAuth.getInstance().currentUser != null

    fun getCurrentUser() = FirebaseAuth.getInstance().currentUser

    fun signInWithEmailAndPassword(user: MutableLiveData<User>) {
        val newUser = user.value?.copy()
        user.value?.let { it ->
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(it.email, it.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        newUser?.uid = getCurrentUser()?.uid ?: ""
                    } else {
                        newUser?.uid = ""
                    }
                    user.value = newUser
                }
                .addOnFailureListener {
                    user.value = newUser
                }
        }
    }
}
