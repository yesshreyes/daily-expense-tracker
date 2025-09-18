package com.example.zobaze.ui.data.model

data class Expense(
    val title: String,
    val category: String,
    val amount: Double,
    val isCredit: Boolean
)