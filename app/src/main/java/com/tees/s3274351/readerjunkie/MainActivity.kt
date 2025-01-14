package com.tees.s3274351.readerjunkie

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.tees.s3274351.readerjunkie.ui.BottomNav
import com.tees.s3274351.readerjunkie.ui.screens.AllReviewScreen
import com.tees.s3274351.readerjunkie.ui.screens.BookReviewScreen
import com.tees.s3274351.readerjunkie.ui.screens.LogIn
import com.tees.s3274351.readerjunkie.ui.screens.ReviewDetailsScreen
import com.tees.s3274351.readerjunkie.ui.screens.SignUp
import com.tees.s3274351.readerjunkie.ui.screens.Splash
import com.tees.s3274351.readerjunkie.ui.screens.UserAccountScreen
import com.tees.s3274351.readerjunkie.ui.screens.Welcome
import com.tees.s3274351.readerjunkie.ui.theme.ReaderjunkieTheme
import com.tees.s3274351.readerjunkie.viewmodel.AccountViewModel
import com.tees.s3274351.readerjunkie.viewmodel.AllReviewViewModel
import com.tees.s3274351.readerjunkie.viewmodel.BookReviewViewmodel
import com.tees.s3274351.readerjunkie.viewmodel.SignupViewModel
import com.tees.s3274351.readerjunkie.viewmodel.SplashViewModel
import com.tees.s3274351.readerjunkie.model.Review
import com.tees.s3274351.readerjunkie.viewmodel.LogInViewModel
import com.tees.s3274351.readerjunkie.viewmodel.ReviewDetailViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Layout slag for entire application
        setLayoutFlags()
        setContent {
            ReaderjunkieTheme {
                Navigation()
            }
        }
    }



    fun setLayoutFlags(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

}



@Composable
fun Navigation(){
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("splash") }
    val bottomBarIndexState = rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener {first, destination, last ->
            currentRoute = destination.route!!
        }
    }

    if (currentRoute in listOf("splash", "login", "signup", "reviewScreen")){
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
    }


    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("createReview", "reviewScreen", "userAccountScreen")){
                systemUiController.setNavigationBarColor(color = Color.Black)
                Box(modifier = Modifier.background(Color.Black).padding(vertical = 0.dp)){
                    BottomNav(navController = navController, bottomBarIndexState)
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = "splash"
        ){

            composable("splash") {
                val splashVM = SplashViewModel(context)
                Splash(navController,splashVM)
            }

            composable("welcome") { Welcome(navController) }

            composable("signup") {
                val vm = SignupViewModel()
                SignUp(navController, vm)
            }

            composable("login") {
                val vm = LogInViewModel()
                LogIn(navController, vm)
            }

            composable("createReview") {
                bottomBarIndexState.value = 0
                val vm = BookReviewViewmodel()
                BookReviewScreen(navController, vm)
            }

            composable("reviewScreen") {
                bottomBarIndexState.value = 1
                val reviewViewModel: AllReviewViewModel = viewModel()
                AllReviewScreen(navController, reviewViewModel)
            }

            composable("userAccountScreen"){
                bottomBarIndexState.value = 2
                val vm: AccountViewModel = viewModel()
                UserAccountScreen(navController, vm)
            }

            composable(
                route = "details/{review}",
                arguments = listOf(
                    navArgument("review") { type = NavType.StringType }
                )
            ){ backStackEntry ->
                val vm: ReviewDetailViewModel = viewModel()
                val reviewJson = backStackEntry.arguments?.getString("review")
                val review = Gson().fromJson(reviewJson, Review::class.java)
                ReviewDetailsScreen(review, navController, vm)
            }

        }
    }

}
