package com.tees.s3274351.readerjunkie.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tees.s3274351.readerjunkie.model.BookReview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllReviewViewModel() : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // State to expose to the UI
    private val _state = MutableStateFlow<AllReviewState>(AllReviewState.Loading)
    val state: StateFlow<AllReviewState> get() = _state

    val selectedReview = MutableStateFlow<BookReview?>(null)
    init {
        fetchCurrentUserReviews()
    }

    private fun fetchCurrentUserReviews() {

        viewModelScope.launch {
            // Ensure user is authenticated
            val userId = auth.currentUser?.uid
            if (userId == null) {
                _state.value = AllReviewState.Error("User not authenticated")
                return@launch
            }

            // Fetch book reviews for the current user
            firestore.collection("bookReviews")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        _state.value = AllReviewState.Empty
                    } else {
                        val reviews = result.documents.mapNotNull { document ->
                            val review = document.toObject(BookReview::class.java)!!
                            review.copy(id = document.id)
                        }
                        _state.value = AllReviewState.Success(reviews)
                    }
                }
                .addOnFailureListener { exception ->
                    _state.value = AllReviewState.Error(exception.message ?: "An error occurred")
                }
        }
    }

}


sealed class AllReviewState {
    object Loading : AllReviewState()
    data class Success(val reviews: List<BookReview>) : AllReviewState()
    data class Error(val message: String) : AllReviewState()
    object Empty : AllReviewState()
}

