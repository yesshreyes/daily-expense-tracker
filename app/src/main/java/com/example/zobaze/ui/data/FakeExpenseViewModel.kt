package com.example.zobaze.ui.data

import com.example.zobaze.ui.screens.ExpenseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeExpenseViewModel : ExpenseViewModel() {
    private val _expenses = MutableStateFlow(
        listOf(
            Expense("Office Lunch", "Food", 350.0, false),
            Expense("Client Payment", "Staff", 5000.0, true),
            Expense("Cab Ride", "Travel", 250.0, false),
            Expense("Electricity Bill", "Utility", 1200.0, false)
        )
    )
//    override val expenses: StateFlow<List<Expense>> = _expenses
}
