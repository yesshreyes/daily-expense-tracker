package com.example.zobaze.ui.data.repository

import android.content.Context
import com.example.zobaze.ui.data.local.ExpenseDatabase
import com.example.zobaze.ui.data.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseRoomRepository private constructor(context: Context) {

    private val dao = ExpenseDatabase.getDatabase(context).expenseDao()

    val expenses: Flow<List<Expense>> = dao.getAllExpenses()

    suspend fun addExpense(expense: Expense) {
        dao.insert(expense)
    }

    suspend fun deleteExpense(expense: Expense) {
        dao.delete(expense)
    }

    suspend fun clearAll() {
        dao.clearAll()
    }

    companion object {
        @Volatile
        private var INSTANCE: ExpenseRoomRepository? = null

        fun getInstance(context: Context): ExpenseRoomRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = ExpenseRoomRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
