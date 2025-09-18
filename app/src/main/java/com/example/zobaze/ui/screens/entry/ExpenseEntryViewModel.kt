package com.example.zobaze.ui.screens.entry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobaze.ui.data.model.Expense
import com.example.zobaze.ui.data.repository.ExpenseRoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ExpenseEntryUiState(
    val title: String = "",
    val category: String = "",
    val amount: String = "",
    val notes: String = "",
    val isCredit: Boolean = false,
    val showSuccessAnimation: Boolean = false
)

class ExpenseEntryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExpenseRoomRepository.getInstance(application)

    private val _uiState = MutableStateFlow(ExpenseEntryUiState())
    val uiState: StateFlow<ExpenseEntryUiState> = _uiState.asStateFlow()

    val expenses: StateFlow<List<Expense>> = repository.expenses
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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
        _uiState.value = _uiState.value.copy(notes = newNotes.take(100))
    }

    fun onCreditToggle(isCredit: Boolean) {
        _uiState.value = _uiState.value.copy(isCredit = isCredit)
    }

    fun toExpense(): Expense? {
        val amountDouble = _uiState.value.amount.toDoubleOrNull()
        return if (_uiState.value.title.isNotBlank() && amountDouble != null) {
            Expense(
                title = _uiState.value.title,
                category = _uiState.value.category,
                amount = amountDouble,
                isCredit = _uiState.value.isCredit,
                notes = _uiState.value.notes
            )
        } else null
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }

    fun clearForm() {
        _uiState.value = ExpenseEntryUiState()
    }
}
