@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.zobaze.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zobaze.ui.components.ExpenseItem
import com.example.zobaze.ui.data.Expense
import com.example.zobaze.ui.data.FakeExpenseViewModel
import com.example.zobaze.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Expense(
    val title: String,
    val category: String,
    val amount: Double,
    val isCredit: Boolean // true = income/credit, false = expense/debit
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseViewModel,
) {
    val expenses by viewModel.expenses.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    // Filtered list based on category
    val filteredExpenses = expenses.filter {
        it.category.contains(searchQuery, ignoreCase = true) || searchQuery.isEmpty()
    }

    // Format date
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf(today) }

    val year = selectedDate.year.toString()
    val dayMonth = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM", Locale.getDefault()))

    // calculate total dynamically
    val totalAmount = expenses.sumOf { exp ->
        if (exp.isCredit) exp.amount else -exp.amount
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {

        // Date + Calendar
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
                        fontFamily = SourceSansProFamily,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary
                    )
                    Text(
                        text = dayMonth,
                        fontSize = 24.sp,
                        fontFamily = SourceSansProFamily,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                // 💰 Total Amount (plain text, no box)
                Text(
                    text = "₹ ${"%.2f".format(totalAmount)}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SourceSansProFamily,
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
                Text("Search by category", fontFamily = SourceSansProFamily, color = TextHint)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BorderFocused,
                unfocusedBorderColor = BorderLight
            ),
            textStyle = TextStyle(fontFamily = SourceSansProFamily)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Split screen → transactions scroll, summary fixed at bottom
        Column(modifier = Modifier.fillMaxSize()) {
            // Transactions list scrollable
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
                                fontFamily = SourceSansProFamily
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

    // Calendar Dialog
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

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = expense.title,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SourceSansProFamily,
                    fontSize = 16.sp,
                    color = TextPrimary
                )
                Text(
                    text = expense.category,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontFamily = SourceSansProFamily
                )
            }

            Text(
                text = (if (expense.isCredit) "+₹" else "-₹") + expense.amount.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SourceSansProFamily,
                color = if (expense.isCredit) SuccessGreen else ErrorRed
            )
        }
    }
}
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun ExpenseListScreenPreview() {
//    MaterialTheme {
//        ExpenseListScreen(viewModel = FakeExpenseViewModel())
//    }
//}