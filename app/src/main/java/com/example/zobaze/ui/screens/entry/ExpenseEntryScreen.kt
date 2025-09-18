package com.example.zobaze.ui.screens.entry

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zobaze.ui.components.CameraButton
import com.example.zobaze.ui.theme.*
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(
    entryViewModel: ExpenseEntryViewModel = viewModel()
) {
    val uiState by entryViewModel.uiState.collectAsState()
    val expenses by entryViewModel.expenses.collectAsState()
    val context = LocalContext.current

    val totalSpent = expenses.filter { !it.isCredit }.sumOf { it.amount }
    val categories = listOf("Staff", "Travel", "Food", "Utility")
    var expanded by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Total Spent Card (glass effect)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        6.dp,
                        RoundedCornerShape(16.dp),
                        spotColor = colors.secondary.copy(alpha = 0.15f),
                        ambientColor = colors.secondary.copy(alpha = 0.08f)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface.copy(alpha = 0.5f) // semi-transparent for glass effect
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Total Spent Today",
                        fontSize = 16.sp,
                        color = colors.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "₹ ${"%.2f".format(totalSpent)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )
                }
            }

            // Transaction input
            Card(
                modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { entryViewModel.onTitleChange(it) },
                        label = { Text("Add transactions") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.primary,
                            unfocusedBorderColor = colors.onSurface.copy(alpha = 0.3f),
                            textColor = colors.onSurface,
                            cursorColor = colors.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(color = colors.onSurface)
                    )
                    Spacer(Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = { },
                        modifier = Modifier.size(56.dp),
                        containerColor = colors.primary,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = colors.onPrimary)
                    }
                }
            }

            // Category dropdown
            Card(
                modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category", color = colors.onSurface) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.primary,
                            unfocusedBorderColor = colors.onSurface.copy(alpha = 0.3f),
                            textColor = colors.onSurface
                        ),
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    ExposedDropdownMenu(expanded, { expanded = false }) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category, color = colors.onSurface, fontWeight = FontWeight.Medium) },
                                onClick = {
                                    entryViewModel.onCategorySelected(category)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Credit/Debit toggle
            Card(
                modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Debit", color = colors.onSurface)
                        Switch(
                            checked = uiState.isCredit,
                            onCheckedChange = { entryViewModel.onCreditToggle(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.secondary,
                                uncheckedThumbColor = colors.error,
                                checkedTrackColor = colors.secondary.copy(alpha = 0.5f),
                                uncheckedTrackColor = colors.error.copy(alpha = 0.5f)
                            )
                        )
                        Text("Credit", color = colors.onSurface)
                    }

                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("₹", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = colors.primary)
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(
                            value = uiState.amount,
                            onValueChange = { entryViewModel.onAmountChange(it) },
                            placeholder = { Text("0", color = colors.onSurface.copy(alpha = 0.5f)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.primary,
                                unfocusedBorderColor = colors.onSurface.copy(alpha = 0.3f),
                                textColor = colors.onSurface,
                                cursorColor = colors.primary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, color = colors.onSurface)
                        )
                    }
                }
            }

            // Notes
            Card(
                modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = { entryViewModel.onNotesChange(it) },
                    placeholder = { Text("Add note (optional)", color = colors.onSurface.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = colors.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    supportingText = { Text("${uiState.notes.length}/100", color = colors.onSurface.copy(alpha = 0.5f), fontSize = 12.sp) }
                )
            }

            Spacer(Modifier.height(10.dp))

            // Submit + Camera button
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val expense = entryViewModel.toExpense()
                        if (expense != null) {
                            entryViewModel.addExpense(expense)
                            showSuccessAnimation = true
                            entryViewModel.clearForm()
                        } else {
                            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f).height(56.dp).shadow(8.dp, RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Submit", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colors.onPrimary)
                }
                CameraButton()
            }
        }

        // Success animation overlay
        if (showSuccessAnimation) {
            val scale = remember { Animatable(0f) }
            LaunchedEffect(Unit) {
                scale.animateTo(1.5f, animationSpec = tween(500, easing = FastOutSlowInEasing))
                delay(500)
                scale.animateTo(0f, animationSpec = tween(300))
                showSuccessAnimation = false
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colors.onBackground.copy(alpha = 0.5f))
                )
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = colors.secondary,
                    modifier = Modifier
                        .size(80.dp)
                        .graphicsLayer(
                            scaleX = scale.value,
                            scaleY = scale.value,
                            alpha = scale.value
                        )
                )
            }
        }
    }
}
