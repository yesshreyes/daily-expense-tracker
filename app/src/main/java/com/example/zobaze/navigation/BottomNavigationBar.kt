package com.example.zobaze.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zobaze.ui.theme.*

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Screen>,
    currentRoute: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = ShadowMedium)
            .clip(RoundedCornerShape(24.dp))
            .background(PrimaryColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { screen ->
            val selected = screen.route == currentRoute
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickable {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    .padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth(0.1f)
                        .clip(RoundedCornerShape(2.dp))
                        .background(if (selected) SecondaryColor else Transparent)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title,
                    tint = if (selected) SecondaryColor else TextSecondary,
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = screen.title,
                    color = if (selected) SecondaryColor else TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}
