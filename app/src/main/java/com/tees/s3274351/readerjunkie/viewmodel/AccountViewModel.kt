package com.tees.s3274351.readerjunkie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AccountViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userState = MutableStateFlow<UserAccount?>(null)
    val userState: StateFlow<UserAccount?> get() = _userState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    val isLoggedOut = MutableStateFlow(false)
    val isLoggingOut = MutableStateFlow(false)

    init {
        fetchUser()
    }

    private fun fetchUser() {
        _loadingState.value = true
        val currentUser = auth.currentUser
        if (currentUser == null) {
            _userState.value = null
            _loadingState.value = false
            return
        }

        viewModelScope.launch {
            try {
                val userId = currentUser.uid
                val userDoc = firestore.collection("users").document(userId).get().await()
                val name = userDoc.getString("name")!!
                val email = userDoc.getString("email")!!

                _userState.value = UserAccount(name = name, email = email)
                _loadingState.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _loadingState.value = false
                _userState.value = null
            }
        }
    }


    fun logout() {
        isLoggingOut.value = true
        auth.signOut()
        _userState.value = null
        isLoggedOut.value = true
        isLoggingOut.value = false
    }

}


data class UserAccount(
    val name: String = "",
    val email: String = "",
)
