package com.example.zobaze.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class ExpenseViewModel : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    open val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    open fun addExpense(expense: Expense) {
        _expenses.value = _expenses.value + expense
    }
}