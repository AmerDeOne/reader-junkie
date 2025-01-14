package com.tees.s3274351.readerjunkie.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tees.s3274351.readerjunkie.model.BookReview
import com.tees.s3274351.readerjunkie.model.Review
import com.tees.s3274351.readerjunkie.viewmodel.ResultState
import com.tees.s3274351.readerjunkie.viewmodel.ReviewDetailViewModel


@Composable
fun ReviewDetailsScreen(review: Review, navController: NavController, vm: ReviewDetailViewModel) {
    val deleteState by vm.deleteState.collectAsState()

    if (deleteState is ResultState.DeleteSuccess){
        navController.navigate("reviewScreen"){
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(40.dp))
            // Top Bar with Close and Delete buttons
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Back Button
                TextButton(
                    onClick = {navController.popBackStack()},
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = "<- Go Back",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue.copy(0.85f)
                    )
                }

                // Delete Button
                TextButton(
                    onClick = {
                        vm.deleteReview(review.id)
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    if (deleteState is ResultState.Deleting){
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp)
                        )
                    }else{
                        Text(
                            text = "Delete Review",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                }
            }

            if (deleteState is ResultState.DeleteError){
                var errorMessage = (deleteState as ResultState.DeleteError).message
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    color = Color.Red,
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display review details
            Text(
                text = "Book Title",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(18.dp))


            // Author
            Text(
                text = "Author",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.author,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Publisher
            Text(
                text = "Publisher",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.publisher,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Edition
            Text(
                text = "Edition",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.edition,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Review
            Text(
                text = "Review",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.review,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
