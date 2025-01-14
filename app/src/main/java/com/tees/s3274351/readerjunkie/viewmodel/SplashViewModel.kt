package com.tees.s3274351.readerjunkie.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(context: Context): ViewModel(){

    val firebase = FirebaseAuth.getInstance()

    val nextRoute = MutableStateFlow("")


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("reader-junkie", Context.MODE_PRIVATE)

    var canNavigate = MutableStateFlow(false)


    init {
        startTimer()
    }


    fun startTimer() {
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                viewModelScope.launch {

                  // Check if it is users first time
                    val isFirstTime = sharedPreferences.getString("is-first-time", "") ?: ""
                    if (isFirstTime.isEmpty()){
                        nextRoute.value = "welcome"
                        canNavigate.value = true

                        // Now, save something to show that it is not not first time
                        sharedPreferences.edit().putString("is-first-time", "Not anymore").apply()
                    }else {

                        // Check if the user has logged In
                        if (firebase.currentUser == null){
                            nextRoute.value = "login"
                            canNavigate.value = true
                        }else {
                            nextRoute.value = "createReview"
                            canNavigate.value = true
                        }

                    }



                }
            }
        }
        timer.start()
    }


}