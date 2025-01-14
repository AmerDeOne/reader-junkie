package com.tees.s3274351.readerjunkie.ui



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController




@Composable
fun BottomNav(navController: NavHostController, selectedItemIndexState: MutableState<Int>) {
    NavigationBar(containerColor = Color.Blue) {
        navItems.forEachIndexed { index, item ->
            var isSelected = selectedItemIndexState.value == index
            NavigationBarItem(
                selected = isSelected,
                onClick = {

                    selectedItemIndexState.value = index
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndexState.value == index) item.onSelectedIcon else item.defaultIcon,
                        contentDescription = item.title,
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


/**
 * Data class representing a navigation item for the bottom navigation bar.
 *
 * @param title The title of the navigation item.
 * @param onSelectedIcon The icon displayed when the item is selected.
 * @param defaultIcon The icon displayed when the item is not selected.
 */
data class NavigationItem(
    val title: String,
    val route: String,
    val onSelectedIcon: ImageVector,
    val defaultIcon: ImageVector,
)


val navItems = listOf<NavigationItem>(

    NavigationItem(
        title = "Review",
        route = "createReview",
        onSelectedIcon = Icons.Filled.Add,
        defaultIcon = Icons.Outlined.Add,
    ),

    NavigationItem(
        title = "My Reviews",
        route = "reviewScreen",
        onSelectedIcon = Icons.Filled.Menu,
        defaultIcon = Icons.Outlined.Menu,
    ),

    NavigationItem(
        title = "Account",
        route = "userAccountScreen",
        onSelectedIcon = Icons.Filled.Person,
        defaultIcon = Icons.Outlined.Person,
    )

)


