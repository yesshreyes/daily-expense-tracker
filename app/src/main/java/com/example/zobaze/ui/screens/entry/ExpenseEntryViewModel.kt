package com.example.zobaze.ui.screens.entry

import androidx.lifecycle.ViewModel
import com.example.zobaze.ui.data.model.Expense
import com.example.zobaze.ui.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ExpenseEntryUiState(
    val title: String = "",
    val category: String = "",
    val amount: String = "",
    val notes: String = "",
    val isCredit: Boolean = false,
    val showSuccessAnimation: Boolean = false
)

class ExpenseEntryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseEntryUiState())
    val uiState: StateFlow<ExpenseEntryUiState> = _uiState.asStateFlow()

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    // ---- Form updates ----
    fun onTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun onAmountChange(newAmount: String) {
        _uiState.value = _uiState.value.copy(amount = newAmount)
    }

    fun onNotesChange(newNotes: String) {
        _uiState.value = _uiState.value.copy(notes = newNotes.take(100)) // max 100 chars
    }

    fun onCreditToggle(isCredit: Boolean) {
        _uiState.value = _uiState.value.copy(isCredit = isCredit)
    }

    // ---- Business logic ----
    fun toExpense(): Expense? {
        val amountDouble = _uiState.value.amount.toDoubleOrNull()
        return if (_uiState.value.title.isNotBlank() && amountDouble != null) {
            Expense(
                title = _uiState.value.title,
                category = _uiState.value.category,
                amount = amountDouble,
                isCredit = _uiState.value.isCredit
            )
        } else null
    }


    fun addExpense(expense: Expense) {
        ExpenseRepository.addExpense(expense)
        _expenses.value = _expenses.value + expense
    }


    fun clearForm() {
        _uiState.value = ExpenseEntryUiState()
    }

}
