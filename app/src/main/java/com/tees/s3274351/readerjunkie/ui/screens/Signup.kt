package com.tees.s3274351.readerjunkie.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tees.s3274351.readerjunkie.ui.CustomButton
import com.tees.s3274351.readerjunkie.ui.CustomField
import com.tees.s3274351.readerjunkie.ui.theme.ReaderjunkieTheme
import com.tees.s3274351.readerjunkie.viewmodel.SignupViewModel


@Composable
fun SignUp(navController: NavController, vm: SignupViewModel){

    var errorMessage = vm.signUpError.collectAsState()
    var isSignedUp = vm.signUpSuccess.collectAsState()

    if (isSignedUp.value){
        navController.navigate("createReview"){
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Blue) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .fillMaxSize()
        ){
            Spacer(Modifier.height(100.dp))

            Text(
                text = "Create Account",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(100.dp))

            if (errorMessage.value.isNotEmpty()){
                Text(
                    text = errorMessage.value,
                    color = Color.Red.copy(0.65f),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }else{
                {}
            }

            CustomField(
                state = vm.nameState,
                label = "Name",
                leadingIcon = Icons.Default.Person,
                onFocused = {vm.clearErrorMessage()}

            ) { value ->
                vm.nameState.value = value
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomField(
                state = vm.emailState,
                label = "Email",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                onFocused = {vm.clearErrorMessage()}

                ) { value ->
                vm.emailState.value = value
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomField(
                onFocused = {vm.clearErrorMessage()},
                state = vm.passwordState,
                label = "Password",
                leadingIcon = Icons.Rounded.Lock,
                keyboardType = KeyboardType.Password

            ) { value ->
                vm.passwordState.value = value
            }


            Spacer(modifier = Modifier.height(20.dp))

            CustomField(
                onFocused = {vm.clearErrorMessage()},
                state = vm.confirmPasswordState,
                label = "Confirm Password",
                leadingIcon = null,
                keyboardType = KeyboardType.Password

            ) { value ->
                vm.confirmPasswordState.value = value
            }

            Spacer(modifier = Modifier.height(80.dp))

            CustomButton(
                text = "Create Account",
                buttonColor = Color.White,
                textColor = Color.Blue.copy(0.7f),
                loadingState = vm.loadingState
            ) {
                vm.createAccount()
            }

            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Already Have an Account?",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.White
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
                    color = Color.Yellow
                )
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun SignPreview() {
    ReaderjunkieTheme {
        SignUp(rememberNavController(), SignupViewModel())
    }
}