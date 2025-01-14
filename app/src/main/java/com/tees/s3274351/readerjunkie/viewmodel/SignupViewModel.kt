package com.tees.s3274351.readerjunkie.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow

class SignupViewModel: ViewModel(){

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseFireStore = FirebaseFirestore.getInstance()
    var nameState = mutableStateOf("")
    var emailState = mutableStateOf("")
    var passwordState = mutableStateOf("")
    var confirmPasswordState = mutableStateOf("")

    var signUpError = MutableStateFlow("")
    var signUpSuccess = MutableStateFlow(false)
    var loadingState = mutableStateOf(false)



    fun createAccount() {
        if (nameState.value.length < 4) {
            signUpError.value = "Name must be at least 5-characters long"
            return
        }

        if (emailState.value.length < 5){
            signUpError.value = "Email does not match email format"
            return
        }

        if (passwordState.value.length < 8){
            signUpError.value = "Password must be at least 8 characters long"
            return
        }

        if (confirmPasswordState.value != passwordState.value){
            signUpError.value = "Confirm passwords did not not match password"
            return
        }

        loadingState.value = true

        firebaseAuth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener{ task ->
                if (!task.isSuccessful){
                    signUpError.value = task.exception?.message!!
                    loadingState.value = false
                    return@addOnCompleteListener
                }else{
                    val userId = firebaseAuth.currentUser?.uid

                    val userData = mapOf(
                        "name" to nameState.value,
                        "email" to emailState.value,
                    )

                    val fireStoreBatch = firebaseFireStore.batch()
                    val documentReference = firebaseFireStore.collection("users").document(userId!!)
                    fireStoreBatch.set(documentReference, userData)

                    fireStoreBatch.commit().addOnCompleteListener{ result ->
                        if (!result.isSuccessful){
                            Log.d("CreateAccount", "Error Creating User: ${result.exception!!.message}")
                            loadingState.value = false
                        }
                    }

                    signUpSuccess.value = true
                }


            }





    }


    fun clearErrorMessage() {
        signUpError.value = ""
        signUpSuccess.value = false
    }

}