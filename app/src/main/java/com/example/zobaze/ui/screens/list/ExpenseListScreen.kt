@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.zobaze.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zobaze.ui.components.ExpenseItem
import com.example.zobaze.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel,
) {
    val expenses by viewModel.expenses.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val filteredExpenses = expenses.filter {
        it.category.contains(searchQuery, ignoreCase = true) || searchQuery.isEmpty()
    }

    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf(today) }

    val year = selectedDate.year.toString()
    val dayMonth = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM", Locale.getDefault()))

    val totalAmount = expenses.sumOf { exp ->
        if (exp.isCredit) exp.amount else -exp.amount
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 📅 Date Block
                Column(
                    modifier = Modifier
                        .clickable { showDatePicker = true }
                ) {
                    Text(
                        text = year,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary
                    )
                    Text(
                        text = dayMonth,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Text(
                    text = "₹ ${"%.2f".format(totalAmount)}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (totalAmount >= 0) SuccessGreen else ErrorRed
                )
            }


        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = TextHint)
            },
            placeholder = {
                Text("Search by category", color = TextHint)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BorderFocused,
                unfocusedBorderColor = BorderLight
            ),
            textStyle = TextStyle()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (expenses.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No transactions found",
                                color = TextTertiary,
                                fontSize = 16.sp,
                            )
                        }
                    }
                } else {
                    items(filteredExpenses) { expense -> // ✅ use filteredExpenses here
                        ExpenseItem(expense = expense)
                    }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = rememberDatePickerState(
                    initialSelectedDateMillis = System.currentTimeMillis()
                ),
                showModeToggle = false
            )
        }
    }
}
}