package com.example.zobaze.ui.data.repository

import com.example.zobaze.ui.data.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ExpenseRepository {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    fun addExpense(expense: Expense) {
        _expenses.value = _expenses.value + expense
    }

    fun clearAll() {
        _expenses.value = emptyList()
    }
}
