package com.tees.s3274351.readerjunkie.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tees.s3274351.readerjunkie.viewmodel.AccountViewModel

@Composable
fun UserAccountScreen(navController: NavController, vm: AccountViewModel) {

    val user by vm.userState.collectAsState()
    val isLoading by vm.loadingState.collectAsState()
    val isLoggOut by vm.isLoggedOut.collectAsState()
    val isLoggingOut by vm.isLoggingOut.collectAsState()

    if (isLoggOut){
        navController.navigate("login"){
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            if (isLoading && user == null){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }else if (!isLoading && user != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Spacer(modifier = Modifier.height(80.dp))
                    // Profile Icon
                    Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp)
                            .clip(CircleShape)
                    )

                    // User Name
                    Text(
                        text = user?.name!!,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    // User Email
                    Text(
                        text = user?.email!!,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }

                // Logout Button
                TextButton(
                    onClick = {
                        vm.logout()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {

                    if (isLoggingOut){
                        CircularProgressIndicator(modifier = Modifier.size(30.dp))
                    }else{

                        Text(
                            text = "Logout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }

        }
    }
}
