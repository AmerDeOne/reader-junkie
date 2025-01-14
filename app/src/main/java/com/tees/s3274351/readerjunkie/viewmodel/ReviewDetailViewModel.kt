package com.tees.s3274351.readerjunkie.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewDetailViewModel: ViewModel(){

    private val firestore = FirebaseFirestore.getInstance()


    private val _deleteState = MutableStateFlow<ResultState>(ResultState.Idle)
    val deleteState: StateFlow<ResultState> = _deleteState

    fun deleteReview(reviewId: String) {
        _deleteState.value = ResultState.Deleting

        firestore.collection("bookReviews")
            .document(reviewId)
            .delete()
            .addOnSuccessListener {
                _deleteState.value = ResultState.DeleteSuccess
            }
            .addOnFailureListener { exception ->
                _deleteState.value = ResultState.DeleteError(exception.message ?: "Unknown error")
            }
    }

}


// Sealed class for representing different states of the delete operation
sealed class ResultState {
    object Idle : ResultState()
    object Deleting : ResultState()
    object DeleteSuccess : ResultState()
    data class DeleteError(val message: String) : ResultState()
}