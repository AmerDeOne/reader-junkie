package com.tees.s3274351.readerjunkie.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.tees.s3274351.readerjunkie.ui.ReviewCard
import com.tees.s3274351.readerjunkie.viewmodel.AllReviewState
import com.tees.s3274351.readerjunkie.viewmodel.AllReviewViewModel



@Composable
fun AllReviewScreen(navController: NavController, vm: AllReviewViewModel) {
    val state by vm.state.collectAsState()


    Surface(modifier = Modifier.fillMaxSize(), color = Color.Blue.copy(0.7f)){

        when(state){

            is AllReviewState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                }
            }

            is AllReviewState.Error -> {
                val error = (state as AllReviewState.Error).message
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    Text(
                        color = Color.Red,
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

            is AllReviewState.Success -> {
                val reviews = (state as AllReviewState.Success).reviews
                Column {
                    Spacer(modifier = Modifier.height(40.dp))

                    // Display the list of book reviews
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {

                        items(reviews) { review ->
                            ReviewCard(
                                title = review.title,
                                author = review.author,
                                edition = review.edition,
                                reviewBody = review.review,
                                modifier = Modifier.clickable {
                                    val reviewJson = Uri.encode(Gson().toJson(review))
                                    // Open Details page
                                    navController.navigate("details/$reviewJson")
                                }
                            )
                        }
                    }
                }



            }

            else -> {}
        }

    }
}