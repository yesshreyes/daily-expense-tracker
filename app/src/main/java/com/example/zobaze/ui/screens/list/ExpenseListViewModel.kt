package com.example.zobaze.ui.screens.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobaze.ui.data.model.Expense
import com.example.zobaze.ui.data.repository.ExpenseRoomRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ExpenseListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExpenseRoomRepository.getInstance(application)

    val expenses: StateFlow<List<Expense>> = repository.expenses
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
