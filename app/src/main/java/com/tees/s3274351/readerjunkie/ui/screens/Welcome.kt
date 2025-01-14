package com.tees.s3274351.readerjunkie.ui.screens

import com.tees.s3274351.readerjunkie.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tees.s3274351.readerjunkie.ui.CustomButton
import com.tees.s3274351.readerjunkie.ui.theme.ReaderjunkieTheme


@Composable
fun Welcome(navController: NavController){

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(Modifier.height(200.dp))

            Image(
                painter = painterResource(id = R.drawable.book),
                contentDescription = "My PNG Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Welcome to reader-junkie.\n" +
                        "Let's make your reading and book review experience memorable",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.Black.copy(0.7f)
            )

            Spacer(Modifier.height(40.dp))

            CustomButton(text = "Create Account") {
                navController.navigate("signup"){
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            }

            Spacer(Modifier.height(80.dp))

            Text(
                text = "Already Have an Account?",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Black.copy(0.7f)
            )

            Spacer(Modifier.height(10.dp))

            Text(
                modifier = Modifier.clickable{
                    navController.navigate("login"){
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                text = "Login Here",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.Blue.copy(0.7f)
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun WPreview() {
    ReaderjunkieTheme {
        Welcome(rememberNavController())
    }
}
