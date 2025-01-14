package com.tees.s3274351.readerjunkie.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.tees.s3274351.readerjunkie.ui.theme.ReaderjunkieTheme
import com.tees.s3274351.readerjunkie.viewmodel.SplashViewModel

@Composable
fun Splash(navController: NavController, viewModel: SplashViewModel){
    val canNavigate = viewModel.canNavigate.collectAsState()
    val nextRoute = viewModel.nextRoute.collectAsState()

    if (canNavigate.value){
        if (canNavigate.value){
            navController.navigate(nextRoute.value)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Blue.copy(0.75f)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "reader-Junkie",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    ReaderjunkieTheme {
       Splash(rememberNavController(), SplashViewModel(LocalContext.current))
    }
}