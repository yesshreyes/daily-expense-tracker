package com.example.zobaze.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zobaze.ui.components.ZobazeTopBar
import com.example.zobaze.ui.screens.entry.ExpenseEntryScreen
import com.example.zobaze.ui.screens.list.ExpenseListScreen
import com.example.zobaze.ui.screens.ExpenseReportScreen
import com.example.zobaze.ui.screens.entry.ExpenseEntryViewModel
import com.example.zobaze.ui.screens.list.ExpenseListViewModel
import com.example.zobaze.ui.theme.SecondaryColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ZobazeApp(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val entryViewModel: ExpenseEntryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val listViewModel: ExpenseListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    var currentRoute by remember { mutableStateOf(Screen.ExpenseEntry.route) }

    Scaffold(
        containerColor = SecondaryColor,
        topBar = {
            ZobazeTopBar(
                title = "Zobaze",
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = bottomNavItems,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.ExpenseEntry.route
            ) {
                composable(Screen.ExpenseEntry.route) {
                    currentRoute = Screen.ExpenseEntry.route
                    ExpenseEntryScreen(entryViewModel)
                }
                composable(Screen.ExpenseList.route) {
                    currentRoute = Screen.ExpenseList.route
                    ExpenseListScreen(listViewModel)
                }
                composable(Screen.ExpenseReport.route) {
                    currentRoute = Screen.ExpenseReport.route
                    ExpenseReportScreen()
                }
            }
        }
    }
}
