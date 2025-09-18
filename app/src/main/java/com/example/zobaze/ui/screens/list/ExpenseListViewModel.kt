package com.example.zobaze.ui.screens.list

import androidx.lifecycle.ViewModel
import com.example.zobaze.ui.data.model.Expense
import com.example.zobaze.ui.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.StateFlow

class ExpenseListViewModel : ViewModel() {
    val expenses: StateFlow<List<Expense>> = ExpenseRepository.expenses
}
