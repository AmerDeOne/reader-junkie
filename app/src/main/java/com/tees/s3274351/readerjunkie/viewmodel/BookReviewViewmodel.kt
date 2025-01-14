package com.tees.s3274351.readerjunkie.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookReviewViewmodel: ViewModel(){

    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val loadingState = mutableStateOf(false)
    val errorMessage = MutableStateFlow<String?>(null)
    val successFullSubmitState = MutableStateFlow(false)

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    init {
        viewModelScope.launch {
            _currentUser.value = firebaseAuth.currentUser
        }
    }


    fun submitReview(formInputs: Map<String, String>){
        loadingState.value = true
        for ((key, value) in formInputs){
            var validationResult = validateInput(key, value, 5)

            if(validationResult != null ){
                errorMessage.value = validationResult
                loadingState.value = false
                return
            }
        }

        // Submit for to firestore to persist
        val collectionRef = firestore.collection("bookReviews")
        // Save the data
        collectionRef.add(formInputs)
            .addOnSuccessListener { documentReference ->
                successFullSubmitState.value = true
                loadingState.value = false
            }
            .addOnFailureListener { e ->
                errorMessage.value = "Error adding document: ${e.message}"
                loadingState.value = false
            }

    }


    fun validateInput(fieldName: String, input: String, compareLength: Int): String? {
        if (input.length >= compareLength){
            return null
        }

        return "$fieldName must be at least $compareLength characters long"
    }


    fun refreshErrorMessage(){
        errorMessage.value = null
    }

}