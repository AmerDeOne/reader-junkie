package com.tees.s3274351.readerjunkie.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tees.s3274351.readerjunkie.ui.CustomButton
import com.tees.s3274351.readerjunkie.ui.theme.ReaderjunkieTheme
import com.tees.s3274351.readerjunkie.viewmodel.BookReviewViewmodel


@Composable
fun BookReviewScreen(navController: NavController, vm: BookReviewViewmodel){

    val user by vm.currentUser.collectAsState()
    val errorMessage by vm.errorMessage.collectAsState()
    val successState by vm.successFullSubmitState.collectAsState()

    val bookTitleState = remember { mutableStateOf("") }
    val authorState = remember { mutableStateOf("") }
    val bookEditionState = remember { mutableStateOf("") }
    val bookPublisherState = remember { mutableStateOf("") }
    val bookReviewState = remember { mutableStateOf("") }

    if (successState){
        navController.navigate("reviewScreen"){
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White){

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        )
        {
            Spacer(Modifier.height(100.dp))
            if (errorMessage != null){
                Text(
                    text = errorMessage!!,
                    color = Color.Red.copy(0.65f),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }else {
                {}
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Review a Book",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = bookTitleState.value,
                onValueChange = {value -> bookTitleState.value = value},
                label = { Text("Book Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = authorState.value,
                onValueChange = {value -> authorState.value = value},
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bookEditionState.value,
                onValueChange = {bookEditionState.value = it},
                label = { Text("Edition") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bookPublisherState.value,
                onValueChange = { bookPublisherState.value = it},
                label = { Text("Publisher") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bookReviewState.value,
                onValueChange = {bookReviewState.value = it},
                label = { Text("Review Body") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 10,
                singleLine = false
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Submit Review",
                textColor = Color.White,
                loadingState = vm.loadingState
            ) {
                vm.refreshErrorMessage()
                val review:Map<String, String> = mapOf(
                    "userId" to user?.uid!!,
                    "title" to bookTitleState.value,
                    "author" to authorState.value,
                    "edition" to bookEditionState.value,
                    "publisher" to bookPublisherState.value,
                    "review" to bookReviewState.value
                )

                vm.submitReview(review)
            }
        }

    }
}




@Preview
@Composable
fun BookReviewPreview() {
    ReaderjunkieTheme {
        BookReviewScreen(rememberNavController(), BookReviewViewmodel())
    }
}