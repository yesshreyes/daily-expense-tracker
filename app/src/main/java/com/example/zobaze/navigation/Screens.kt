package com.example.zobaze.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object ExpenseEntry : Screen("expense_entry", "Entry", Icons.Default.Add)
    object ExpenseList : Screen("expense_list", "List", Icons.Default.List)
    object ExpenseReport : Screen("expense_report", "Report", Icons.Default.BarChart)
}

val bottomNavItems = listOf(
    Screen.ExpenseEntry,
    Screen.ExpenseList,
    Screen.ExpenseReport
)
